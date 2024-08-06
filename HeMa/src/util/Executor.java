package util;

import com.github.javaparser.ast.body.MethodDeclaration;
import main.DataTask;
import main.GPTTask;
import main.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import predictor.Predictor;
import predictor.Signature;
import predictor.SignaturePredictor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Executor {

    private static final Logger logger = LoggerFactory.getLogger(Executor.class);

    /**
     * 多线程 任务队列 验证
     * @param numThreads
     * @param dir
     */
    public static void extractDir(int numThreads,String dir,boolean status) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);

        LinkedList<Task> tasks = new LinkedList<>();
        try {
            Files.walk(Paths.get(dir)).filter(Files::isRegularFile)
                    .filter(p -> p.toString().toLowerCase().endsWith(".java")).forEach(f -> {
                        Task task = new Task(f,status);
                        Counter.countJavaFile++;
                        tasks.add(task);
                    });
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    /**
     * 多线程 任务队列 制作训练集
     * @param numThreads
     * @param dir
     */
    public static void extractCSV(int numThreads,String dir) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);
        LinkedList<DataTask> tasks = new LinkedList<>();
        try {
            Files.walk(Paths.get(dir)).filter(Files::isRegularFile)
                    .filter(p -> p.toString().toLowerCase().endsWith(".java")).forEach(f -> {
                        DataTask task=new DataTask(f);
                        tasks.add(task);
                    });
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    /**
     * 将分级结果集进行测试
     * @param methodDeclarations
     * @param methodLevel
     */
    public static void executePredict(List<MethodDeclaration> methodDeclarations, int methodLevel) {

        List<MethodDeclaration> filterList = MethodLevel.methodLengthLevelFilter(methodDeclarations, methodLevel);
        logger.info("测试方法名长度等级为 {} 的结果", methodLevel);
        Predictor predictor =new Predictor(filterList);
        predictor.run();

    }

    public static void executePredictFromCSV(List<String> signatureInfo,int methodLevel){

        List<String> filterSignatureInfo = MethodLevel.methodLengthLevelFilter(methodLevel,signatureInfo);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(32);

        LinkedList<GPTTask> tasks =new LinkedList<>();

        System.out.println(filterSignatureInfo.size());
        Counter.total = filterSignatureInfo.size();
        for (String info : filterSignatureInfo) {
            GPTTask gptTask = new GPTTask(info);
            tasks.add(gptTask);
        }
        try {
            executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            executor.shutdown();
        }
    }
    /**
     * 方法名去重
     * @param methodDeclarations
     */
    public static void checkMethodRepeat(List<MethodDeclaration> methodDeclarations) {

        Map<Signature, Map<String, Integer>> filterTargetProjectMethod = new HashMap<>();

        for (MethodDeclaration methodDeclaration : methodDeclarations) {
            if (methodDeclaration==null){
                continue;
            }
            Signature signature = new Signature(methodDeclaration);
            String methodName = methodDeclaration.getName();
            if (filterTargetProjectMethod.containsKey(signature)) {
                Map<String, Integer> map = filterTargetProjectMethod.get(signature);
                if (map.containsKey(methodName)) {
                    map.put(methodName, map.get(methodName) + 1);
                    filterTargetProjectMethod.put(signature, map);
                } else {
                    map.put(methodName, 1);
                    filterTargetProjectMethod.put(signature, map);
                    Counter.methodDeclarationList.add(methodDeclaration);
                }
            } else {
                Map<String, Integer> map = new HashMap<>();
                map.put(methodName, 1);
                filterTargetProjectMethod.put(signature, map);
                Counter.methodDeclarationList.add(methodDeclaration);
            }
        }
        for (Map.Entry<Signature, Map<String, Integer>> entry : filterTargetProjectMethod.entrySet()) {
            Map<String, Integer> map = entry.getValue();
            for (Map.Entry<String, Integer> entry1 : map.entrySet()) {
                if (entry1.getValue() > 1) {
                    Counter.repeatMethod += (entry1.getValue()) - 1;
                }
            }
        }
    }

    /**
     * 测试
     * @param dir
     * @param numThreads
     * @param status
     * @param removeRepeat
     * @param methodLevel
     * @param resultName
     * @throws IOException
     */
    public static void doPredict(String dir,int numThreads,boolean status,boolean removeRepeat,int methodLevel,String resultName,String trainSetPath) throws IOException {

        logger.info("开始加载训练集");

        SignaturePredictor.load_trainset(trainSetPath);
        logger.info("加载训练集完成");

        File file = new File(dir);
        File[] files = file.listFiles();

        assert files != null;
        for (File file1 : files) {
            dir = file1.getPath();
            logger.info("项目路径：{}", dir);
            File root = new File(dir);
            if (root.exists() && root.isDirectory()) {
                File[] projs = root.listFiles();
                for (File proj : projs) {
                    dir = proj.getPath();
                    Executor.extractDir(numThreads,dir,status);
                }
            }

            if (status){
                //去重分级测试
                if (removeRepeat){
                    logger.info(" 执行 线程安全去重策略 ");
                    Executor.checkMethodRepeat(Counter.methodDeclarations);
                    Executor.executePredict(Counter.methodDeclarationList, methodLevel);
                }else {
                    logger.info(" 执行 线程安全不去重策略 ");
                    Executor.executePredict(Counter.methodDeclarations, methodLevel);
                }
            }else {
                if (removeRepeat){
                    logger.info(" 执行 线程不安全去重策略 ");
                    Executor.checkMethodRepeat(Counter.methodDeclarationsUnsafe);
                    Executor.executePredict(Counter.methodDeclarationList, methodLevel);
                }else {
                    logger.info(" 执行 线程不安全不去重策略");
                    Executor.executePredict(Counter.methodDeclarationsUnsafe, methodLevel);
                }
            }

            Counter.counterRefresh();
            Counter.print();
        }

        Excel.exportResult(new String[]{"total", "predicted", "correct", "precision", "recall"}, resultName);
        logger.info("导出结果");
    }

    public static void doPredictFromCSV(String dir,String resultName,String trainSetPath,int methodLevel) throws IOException {
        logger.info("开始加载训练集");
        SignaturePredictor.load_trainset(trainSetPath);
        logger.info("加载训练集完成");

        List<String> signatureInfo = FileUtil.read(dir);
        executePredictFromCSV(signatureInfo,methodLevel);
        Excel.exportResult(new String[]{"total", "predicted", "correct", "precision", "recall"}, resultName);
        Counter.print();
        logger.info("导出结果");
    }
    public static void doWrite(String dir,int  numThreads,String fileName){
        File file=new File(dir);
        File[] files=file.listFiles();
        assert files != null;
        for (File file1 : files){
            dir=file1.getPath();
            File root = new File(dir);
            logger.info("项目路径：{}",dir);
            if (root.exists() && root.isDirectory()) {
                File[] projs = root.listFiles();
                for (File proj : projs) {
                    dir = proj.getPath();
                    Executor.extractCSV(numThreads,dir);
                }
            }
            MethodDataCollect.writeCSV(Counter.createCVS,fileName);
            Counter.writeRefresh();
        }
        logger.info(" 写入结束 ");
    }


}
