### 一、Environment Setup
* Operating system --> CentOS 7
* Development environment --> Python 3.7.16

### 二、Preparing the dataset
* Prepare Java projects with large amounts of data
* Run the dataset extraction script preprocess.sh
  * Modify script parameters
    * TRAIN_DIR=/hy-tmp/code2vec  the Java project directory of the training set
    * VAL_DIR=/hy-tmp/code2vec   the Java project directory of the validation set
    * TEST_DIR=/hy-tmp/JavaRepos the Java project directory of the test set
    * DATASET_NAME=my_dataset1 Dataset location and the partial name of the dataset -- data/my_dataset1
  * After the script is finished, save the extracted dataset under data/my_dataset1 in the project directory
### 三、Training the data model
* run the train.sh
  * Modify script parameters
    * type=java14m Saved path to train the model models/{type} 
    * dataset_name=java14m the location where the dataset is saved
### 四、Release model
```
python3 code2vec.py --load models/java14_model/saved_model_iter8 --release
```
### 五、Validating models
```
python3 code2vec.py --load models/java14_model/saved_model_iter8.release --test data/java14m/java14m.test.c2v
```
  
