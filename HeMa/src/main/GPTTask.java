package main;

import predictor.SignaturePredictor;

import java.util.concurrent.Callable;

public class GPTTask implements Callable<Void> {
    private String info;
    // 构造
    public GPTTask(String info) {
        this.info = info;
    }

    @Override
    public Void call() throws Exception {
        SignaturePredictor.predict(this.info);
        return null;

    }
}
