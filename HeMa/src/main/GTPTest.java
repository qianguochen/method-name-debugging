package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Executor;
import util.MethodLevel;

import java.io.*;

public class GTPTest {
    private static String dir = null;
    private static String dirAll = null;
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) throws IOException {
        //测试集
        dir = "F:\\pycharmWorkSpace\\gpt_for_HeMa\\java400_from_GPT_ok.csv";
        dirAll = "F:\\pycharmWorkSpace\\gpt_for_HeMa\\java400_from_GPT.csv";
        //保存结果文件
        String resultName = "result_GPT_level_Filter_3";
        // 训练集
        String trainSetPath = "F:\\ideaWorkSpace\\HeMa\\trainset.csv";
        //分级
        int methodLevel = MethodLevel.METHOD_LEVEL_THREE;

        logger.info("开始测试");
        Executor.doPredictFromCSV(dir,resultName,trainSetPath,methodLevel);
        Executor.doPredictFromCSV(dirAll,resultName,trainSetPath,methodLevel);
        logger.info("测试完成");
    }
}
