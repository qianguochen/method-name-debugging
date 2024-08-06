package util;

import com.github.javaparser.ast.body.MethodDeclaration;
import java.util.*;

public class MethodLevel {
    public static final int METHOD_LEVEL_ONE = 1;
    public static final int METHOD_LEVEL_TWO = 2;
    public static final int METHOD_LEVEL_THREE = 3;
    public static final int METHOD_LEVEL_FOUR = 4;
    public static final int METHOD_LEVEL_FIVE = 5;
    public static final int METHOD_LEVEL_SIX = 6;
    public static Map<Integer,List<MethodDeclaration>> methodMap=new HashMap<>();

    /**
     * 统计训练集 方法名长度
     */
    public static Map<Integer,Integer> map=new TreeMap<>();


    /**
     * 统计测试集相同长度的方法名
     * @param methodDeclarations
     */
    public static void methodLevelFilter(List<MethodDeclaration> methodDeclarations){
        for (MethodDeclaration methodDeclaration : Counter.methodDeclarationList){
            int length=methodDeclaration.getName().length();
            List<MethodDeclaration> list;
            if (!methodMap.containsKey(length)){
                list = new ArrayList<>();
            }else {
                list = methodMap.get(length);
            }
            list.add(methodDeclaration);
            methodMap.put(length,list);
        }
    }

    /**
     * 方法名分级
     * @param methodDeclarations
     * @param level
     * @return
     */
    public static List<MethodDeclaration> methodLengthLevelFilter(List<MethodDeclaration> methodDeclarations,int level){

        List<MethodDeclaration> list = new ArrayList<>();
        if (level==METHOD_LEVEL_ONE){
            for (MethodDeclaration methodDeclaration : methodDeclarations){
                if (methodDeclaration.getName().length()<=3){
                    list.add(methodDeclaration);
                }
            }
        }
        if (level==METHOD_LEVEL_TWO){
            for (MethodDeclaration methodDeclaration : methodDeclarations){
                if (methodDeclaration.getName().length()<28 && methodDeclaration.getName().length()>3){
                    list.add(methodDeclaration);
                }
            }
        }
        if (level==METHOD_LEVEL_THREE){
            for (MethodDeclaration methodDeclaration : methodDeclarations){
                if (methodDeclaration.getName().length()< 52 && methodDeclaration.getName().length() >27){
                    list.add(methodDeclaration);
                }
            }
        }
        if (level==METHOD_LEVEL_FOUR){
            for (MethodDeclaration methodDeclaration : methodDeclarations){
                if (methodDeclaration.getName().length()< 80 && methodDeclaration.getName().length() >51){
                    list.add(methodDeclaration);
                }
            }
        }
        if (level==METHOD_LEVEL_FIVE){
            for (MethodDeclaration methodDeclaration : methodDeclarations){
                if (methodDeclaration.getName().length() >= 80){
                    list.add(methodDeclaration);
                }
            }
        }
        if (level==METHOD_LEVEL_SIX){
            list=methodDeclarations;
        }
        return list;
    }

    public static List<String> methodLengthLevelFilter(int level,List<String> signatureInfo){

        List<String> list = new ArrayList<>();

        if (level==METHOD_LEVEL_ONE){
            for (String info : signatureInfo){
                String methodName = info.split(",")[0].replaceAll("^\"|\"$","");
                if (methodName.length()<=3){
                    list.add(info);
                }
            }
        }
        if (level==METHOD_LEVEL_TWO){
            for (String info : signatureInfo){
                String methodName = info.split(",")[0].replaceAll("^\"|\"$","");
                if (methodName.length()<28 && methodName.length()>3){
                    list.add(info);
                }
            }
        }
        if (level==METHOD_LEVEL_THREE){
            for (String info : signatureInfo){
                String methodName = info.split(",")[0].replaceAll("^\"|\"$","");
                if (methodName.length()< 52 && methodName.length() >27){
                    list.add(info);
                }
            }
        }
        if (level==METHOD_LEVEL_FOUR){
            for (String info : signatureInfo){
                String methodName = info.split(",")[0].replaceAll("^\"|\"$","");
                if (methodName.length()< 80 && methodName.length() >51){
                    list.add(info);
                }
            }
        }
        if (level==METHOD_LEVEL_FIVE){
            for (String info : signatureInfo){
                String methodName = info.split(",")[0].replaceAll("^\"|\"$","");
                if (methodName.length() >= 80){
                    list.add(info);
                }
            }
        }
        if (level==METHOD_LEVEL_SIX){
            list=signatureInfo;
        }
        return list;
    }
}
