package util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 获取Java项目测试目录
 */
public class JavaProjectMenu {

    public static void main(String[] args) throws IOException {
        exportMenu("G:\\JavaRepos400","javaProjectMenu_JavaRepos400.txt");
    }

    public static void exportMenu(String path,String fileName) throws IOException {

        File file = new File(path);
        File projectMenu=new File(fileName);
        FileOutputStream fs=new FileOutputStream(projectMenu);
        File[] files = file.listFiles();

        assert files != null;
        for (File file1 : files) {
            String name=file1.getName();
            fs.write(name.getBytes());
            fs.write("\n".getBytes());
        }
        fs.close();
    }
}
