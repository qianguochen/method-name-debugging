package predictor;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;

import com.github.javaparser.ast.body.MethodDeclaration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Counter;
import util.MethodLevel;
import util.Tokenizer;

public class SignaturePredictor {
	
	private static final Map<Signature, Map<String, Integer>> trainSet = new HashMap<>();

	private static final Logger logger= LoggerFactory.getLogger(SignaturePredictor.class);

	private static ReentrantLock lock = new ReentrantLock();
	/**
	 * 单个项目测试
	 * @param node
	 * @return
	 */
	public static int predict(MethodDeclaration node) {
		String method_name = node.getName();
		Signature signature = new Signature(node);
		
		if(trainSet.containsKey(signature)) {
			Map<String, Integer> counter = trainSet.get(signature);
			int max = 0;
			String prediction = "";
			for(Entry<String, Integer> entry : counter.entrySet()) {
				if(entry.getValue() > max) {
					max = entry.getValue();
					prediction = entry.getKey();
				}
			}
			if(max > 0) {
				Counter.sPredicted++;

				String reference = Tokenizer.tokenize(method_name).toLowerCase();

				prediction = Tokenizer.tokenize(prediction).toLowerCase();

				int precision = reference.equals(prediction) ? 1 : 0;
				Counter.sCorrect += precision;
				return precision;
			}
		}
		return -1;
	}
	public static int predict(String node) {

		String[] params = node.split(",");
		Signature signature = new Signature(params[1].replaceAll("^\"|\"$",""));
		String method_name = params[0].replaceAll("^\"|\"$","");
		if(trainSet.containsKey(signature)) {
			Map<String, Integer> counter = trainSet.get(signature);
			int max = 0;
			String prediction = "";
			for(Entry<String, Integer> entry : counter.entrySet()) {
				if(entry.getValue() > max) {
					max = entry.getValue();
					prediction = entry.getKey();
				}
			}
			lock.lock();
			try {
				if(max > 0) {

					Counter.sPredicted++;

					String reference = Tokenizer.tokenize(method_name).toLowerCase();

					prediction = Tokenizer.tokenize(prediction).toLowerCase();

					int precision = reference.equals(prediction) ? 1 : 0;
					Counter.sCorrect += precision;
					return precision;
				}
			}finally {
				lock.unlock();
			}

		}
		return -1;
	}
	/**
	 * 加载trainset文件  将文件内容解析存入trainSet
	 */
	public static void load_trainset(String trainSetPath) {
		int total = 0;
		int count_void = 0;
		List<String> lines = read(trainSetPath);
		for(String line : lines){
			total++;
			String[] strs = line.split(",");
			try {
				String method_name = strs[0].substring(1, strs[0].length()-1);


			if (strs[1].substring(1, strs[1].length()-1).equals("void")){
				count_void++;
			}
			Signature signature = new Signature(strs[1].substring(1, strs[1].length()-1));
			
			if(trainSet.containsKey(signature)) {
				Map<String, Integer> counter = trainSet.get(signature);
				if(counter.containsKey(method_name)) {
					counter.put(method_name, counter.get(method_name)+1);
					trainSet.put(signature, counter);
				}else {
					counter.put(method_name, 1);
					trainSet.put(signature, counter);
				}
			}else {
				Map<String, Integer> counter = new HashMap<>();
				counter.put(method_name, 1);
				trainSet.put(signature, counter);
			}
			}catch (Exception e){

			}

		}

		logger.info("测试集: {} train samples loaded.",total);
		logger.info("测试集: {} train samples count_void.",count_void);

	}

	/**
	 * 读取库文件 存入list集合
	 * @param filePath
	 * @return
	 */
	public static List<String> read(String filePath){
		List<String> list = new ArrayList<String>();
        try{
            File file = new File(filePath);
            if (file.isFile() && file.exists()){
                InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = null;

                while ((line = bufferedReader.readLine()) != null)
                {
                    list.add(line);
                }
                bufferedReader.close();
                reader.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
	}
}
