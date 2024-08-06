### 一、环境搭建
* 操作系统 --> CentOS 7
* 软件环境 --> Python 3.7 Java 8

### 二、提取数据集
* Java项目
* clone HeMa项目
```
https://github.com/qianguochen/HeMa.git
```
* 运行HeMa项目CreateTrainSetAPP 提取数据集
* 执行完成，生成提取的数据集CVS格式文件

### 三、根据数据集与GPT交互，保存结果
* 执行python脚本batch_one.py将提取的数据集与GPT交互得到HeMa项目实验逻辑的结果集
  * 修改参数 
    * api_key --> GPT KEY 
    * api_url --> GPT 访问链接
  * 可以将脚本复制修改 `read_file_lines` 方法中的`count_line` 参数进行批处理
### 四、结果集预处理
* 由于GPT回答误差，部分数据不会完全按照指定回答格式，需对结果集初步数据处理，得到指定格式的结果集，执行python脚本 datafilter.py 进行结果集处理
* 根据HeMa项目实验逻辑，需对结果集进行进一步处理，执行python脚本 filter_get_set_is.py 得到最终结果集
### 五、HeMa测试GPT结果集
* 运行HeMa/src/main/GTPTest.java