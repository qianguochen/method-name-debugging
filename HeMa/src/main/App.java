package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Counter;
import util.Executor;
import util.MethodLevel;
import java.io.IOException;

public class App {
    private static String dir = null;
    private final static int numThreads = 32;

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws IOException {

        //测试集
        dir = "F:\\\\ideaWorkSpace\\\\rocketmq";
        //保存结果文件
        String resultName = "result";
        //是否线程安全
        boolean status= false;
        //是否去重
        boolean removeRepeat = true;
        //分级等级
        int methodLevel = MethodLevel.METHOD_LEVEL_FOUR;

        // 训练集
//        String trainSetPath = "F:\\ideaWorkSpace\\HeMa\\trainSetJavaRepos400.csv";
        String trainSetPath = "F:\\pycharmWorkSpace\\gpt_for_HeMa\\java400_from_GPT.csv";


        logger.info("开始测试");
        Executor.doPredict(dir,numThreads,status,removeRepeat,methodLevel,resultName,trainSetPath);
        logger.info("该项目Java文件数目 {}", Counter.countJavaFile);
        logger.info("测试完成");
    }
}
