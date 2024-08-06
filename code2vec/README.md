### 一、环境搭建
* 操作系统 --> CentOS 7
* 软件环境 --> Python 3.7.16

### 二、准备数据集
* 准备大数据量的Java项目
* 运行数据集提取脚本 preprocess.sh
  * 修改脚本参数
    * TRAIN_DIR=/hy-tmp/code2vec  指向训练集的Java项目目录
    * VAL_DIR=/hy-tmp/code2vec   指向验证集的Java项目目录
    * TEST_DIR=/hy-tmp/JavaRepos 指向测试集的Java项目目录
    * DATASET_NAME=my_dataset1 数据集位置以及数据集的部分名称 data/my_dataset1
  * 脚本执行完成后在项目目录下的 data/my_dataset1 下保存提取的数据集
### 三、训练数据模型
* 执行脚本train.sh
  * 修改参数
    * type=java14m 训练模型的保存路径 models/{type} 
    * dataset_name=java14m 指向数据集的保存位置
### 四、发布模型
```
python3 code2vec.py --load models/java14_model/saved_model_iter8 --release
```
### 五、验证模型
```
python3 code2vec.py --load models/java14_model/saved_model_iter8.release --test data/java14m/java14m.test.c2v
```
  
