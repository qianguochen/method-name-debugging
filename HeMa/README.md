### 一、项目环境搭建
1. 操作系统：CentOS 7
2. 软件环境：Java 8
3. 依赖管理：maven

### 二、项目数据集
该项目的数据集为Java源码文件，可参考提供的GitHub优秀开源项目列表
- javaProjectMenu_JavaRepos.txt
- javaProjectMenu_JavaRepos400.txt

### 三、提取训练集
位置
- src/main/CreateTrainSetAPP.java

参数
- dir --> Java项目路径

### 四、实验测试
位置
- src/main/App.java

参数
- dir --> Java项目路径(测试集)
- resultName --> 保存结果文件 
- status --> 是否线程安全    
- removeRepeat --> 是否去重
- methodLevel --> 分级等级
- trainSetPath --> 指向提取训练集路径
