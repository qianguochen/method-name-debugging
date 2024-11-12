### 一、Environment Setup
* Operating system --> CentOS 7
* Development environment --> Python 3.7 Java 8

### 二、Extracting the dataset
* Java project
* clone HeMa
```
https://github.com/qianguochen/HeMa.git
```
* Run the HeMa project CreateTrainSetAPP to extract the dataset
* The operation is completed, and the extracted data set CVS format file is generated

### 三、Chat with GPT according to the dataset and save the results
* Execute batch_one.py to interact the extracted data set with GPT to obtain the result set of the HeMa project experiment logic
  * Modifying parameters 
    * api_key --> GPT KEY 
    * api_url --> GPT url
  * copy the script and change the 'count_line' parameter in the 'read_file_lines' method for batch processing
### 四、Result set preprocessing
* Due to GPT answer error, part of the data will not be completely in accordance with the specified answer format. Preliminary data processing of the result set is needed to obtain the result set of the specified format, and the python script datafilter.py is executed to process the result set
* According to the experiment logic of HeMa project, the result set needs to be further processed, and the python script filter_get_set_is.py is executed to obtain the final result set
### 五、HeMa tests the GPT result set
* run HeMa/src/main/GTPTest.java