package util;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.Type;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MethodDataCollect {


    private static final Logger logger = LoggerFactory.getLogger(MethodDataCollect.class);

    public static List<String> collectLines(List<MethodDeclaration> methodDeclarations){
        List<String> lines=new ArrayList<>();
        for (MethodDeclaration methodDeclaration : methodDeclarations){
            StringBuilder csvLine=new StringBuilder();
            csvLine.append('"').append(methodDeclaration.getName()).append('"').append(',');
            Type<?> returnType = methodDeclaration.getType();
            removeComment(returnType);

            List<Parameter> parameters = methodDeclaration.getParameters();
            if (parameters.size()==0){
                csvLine.append('"').append(returnType).append('"');
            }else {
                csvLine.append('"').append(returnType).append(';');
                if (parameters.size()==1){
                    Type<?> paramType = parameters.get(0).getType();
                    removeComment(paramType);
                    csvLine.append(paramType).append(';');
                    csvLine.append(parameters.get(0).getId().getName()).append('"');
                }else {
                    for(int i = 0; i < parameters.size()-1; i++) {
                        Type<?> paramType = parameters.get(i).getType();
                        removeComment(paramType);
                        csvLine.append(paramType).append(';');
                        csvLine.append(parameters.get(i).getId().getName()).append(';');
                    }
                    Type<?> paramType = parameters.get((parameters.size()-1)).getType();
                    removeComment(paramType);
                    csvLine.append(paramType).append(';');
                    csvLine.append(parameters.get((parameters.size()-1)).getId().getName()).append('"');
                }
            }
            String line = csvLine.toString();
            lines.add(line);
        }
        return lines;
    }

    public static void writeCSV(List<String> data, String path){
        logger.info(" 准备写入文件{} ",path);
        logger.info(" 开始写入文件 ");
        BufferedWriter bufferedWriter =null;
        try {
            File file=new File(path);
            FileWriter fileWriter=new FileWriter(file,true);
            bufferedWriter=new BufferedWriter(fileWriter);
            for(String line : data){
                if (!StringUtils.isBlank(line)){
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bufferedWriter!=null){
                    bufferedWriter.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private static void removeComment(Node node) {
        node.setComment(null);
        for(Node child : node.getChildrenNodes()) {
            removeComment(child);
        }
    }

}
