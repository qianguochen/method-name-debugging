package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
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
