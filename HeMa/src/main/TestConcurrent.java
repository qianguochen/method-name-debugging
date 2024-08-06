package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestConcurrent {
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 100;
    private static final Long KEEP_ALIVE_TIME = 1L;
    private static List<Integer> testlist=new ArrayList<>();

    public static void main(String[] args) {


        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());

            int i=0;
            while (i<=10){
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        testlist.addAll(list);
                    }
                });
                i++;
            }

        executor.shutdown();


        System.out.println(testlist);
    }

}
