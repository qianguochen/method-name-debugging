package util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class Excel {

    public static String[] fieldName(Class calzz) {
        Field[] fields = calzz.getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 导出方法名长度统计结果
     * @param numbers
     * @param header
     * @throws IOException
     */
    public static void export(List<LengthOfNumber> numbers, String[] header) throws IOException {
        String[] fieldNames = fieldName(LengthOfNumber.class);
        Workbook wb = new HSSFWorkbook();
        int rowSize = 0;
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(rowSize);
        for (int i = 0; i < header.length; i++) {
            row.createCell(i).setCellValue(header[i]);
        }
        try {
            for (int i = 0; i < numbers.size(); i++) {
                rowSize = 1;
                Row row1 = sheet.createRow(rowSize + i);
                for (int j = 0; j < header.length; j++) {
                    LengthOfNumber lengthOfNumber = numbers.get(i);
                    for (int k = 0; k < fieldNames.length; k++) {
                        String methodName = "get" + fieldNames[k].substring(0,1).toUpperCase() + fieldNames[k].substring(1);//获取属性的get方法名
                        Method method = lengthOfNumber.getClass().getMethod(methodName);
                        Object invoke = method.invoke(lengthOfNumber);//获取属性值
                        row1.createCell(k).setCellValue(invoke.toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("javaProject400MethodLength.xls");
            wb.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (outputStream!=null){
                outputStream.flush();
                outputStream.close();
            }
            if (wb!=null){
                wb.close();
            }
        }
    }

    /**
     * 导出实验结果
     * @param header
     * @param name
     * @throws IOException
     */
    public static void exportResult(String[] header,String name) throws IOException {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(0);
        for (int i = 0; i < header.length; i++) {
            row.createCell(i).setCellValue(header[i]);
        }
        try {
            int correct = Counter.gCorrect+Counter.sCorrect+Counter.mCorrect;
            int predict = Counter.gPredicted+Counter.sPredicted+Counter.mPredicted;
            Row row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue(Counter.total);
            row1.createCell(1).setCellValue(predict);
            row1.createCell(2).setCellValue(correct);
            row1.createCell(3).setCellValue(predict==0 ? 0 : correct*1.0/predict);
            row1.createCell(4).setCellValue(Counter.total==0 ? 0 : correct*1.0/Counter.total);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(name+".xls");
            wb.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (outputStream!=null){
                outputStream.flush();
                outputStream.close();
            }
            if (wb!=null){
                wb.close();
            }
        }
    }
}
