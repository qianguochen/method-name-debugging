package main;

import com.github.javaparser.ParseException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

public class DataTask implements Callable<Void> {
	String code;

	public DataTask(Path path) {
		try {
			this.code = new String(Files.readAllBytes(path));
		} catch (IOException e) {
			e.printStackTrace();
			this.code = "";
		}
	}

	@Override
	public Void call() throws Exception {
		try {
			FileParser featureExtractor = new FileParser(code);
			featureExtractor.extractFeaturesCSV();
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		return "Task{" +
				"code='" + code + '\'' +
				'}';
	}
}
