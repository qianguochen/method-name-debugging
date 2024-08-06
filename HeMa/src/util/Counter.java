package util;

import com.github.javaparser.ast.body.MethodDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Counter {

    public static int total = 0;
    public static int predicted = 0;
    public static float correct = 0;

    public static int gPredicted = 0;
    public static int gCorrect = 0;

    public static int sPredicted = 0;
    public static int sCorrect = 0;

    public static int mPredicted = 0;
    public static int mCorrect = 0;

    public static int repeatMethod = 0;

    public static int countJavaFile=0;

    /**
     * 制作测试集
     */
    public static List<String> createCVS = new CopyOnWriteArrayList<>();

    /**
     * 方法去重前 线程安全
     */
    public static List<MethodDeclaration> methodDeclarations = new CopyOnWriteArrayList<>();

    /**
     * 方法去重前 线程不安全
     */
    public static List<MethodDeclaration> methodDeclarationsUnsafe = new ArrayList<>();


    /**
     * 方法去重后
     */
    public static List<MethodDeclaration> methodDeclarationList = new ArrayList<>();

    private static Logger logger = LoggerFactory.getLogger(Counter.class);
    public static void print() {
        predicted = gPredicted + sPredicted + mPredicted;
        correct = gCorrect + sCorrect + mCorrect;

        logger.info("total = {}",total);
        logger.info("predicted = {}",predicted);
        logger.info("correct = {}",correct);
        logger.info("precision = {}",(predicted == 0 ? 0 : correct * 1.0 / predicted));
        logger.info("recall = {}",(total == 0 ? 0 : correct * 1.0 / total));
        logger.info("repeatMethod = {}",repeatMethod);
    }

    public static void counterRefresh() {
        methodDeclarationList.clear();
        methodDeclarations.clear();
        methodDeclarationsUnsafe.clear();
    }

    public static void refresh() {
        total = 0;
        predicted = 0;
        correct = 0;
        gPredicted = 0;
        gCorrect = 0;
        sPredicted = 0;
        sCorrect = 0;
        mPredicted = 0;
        mCorrect = 0;
        repeatMethod = 0;
    }

    public static void writeRefresh(){
        createCVS.clear();
    }
}
