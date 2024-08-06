package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Executor;


/**
 * 自制训练集
 */
public class CreateTrainSetAPP {
    private static String dir = null;
    private final static int numThreads = 16;


    private static final Logger logger= LoggerFactory.getLogger(CreateTrainSetAPP.class);

    public static void main(String[] args) {

        dir="G:\\MY_WORK_DATA\\JavaRepos400";
        String fileName = "Java400.csv";

        Executor.doWrite(dir,numThreads,fileName);

    }
}
