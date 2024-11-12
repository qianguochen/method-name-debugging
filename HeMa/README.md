### 一、Environment Setup
1. Operating system：CentOS 7
2. Development environment：Java 8
3. Dependency management：maven

### 二、Project dataset
The data set of this project is Java source files, which can be consulted by the GitHub excellent open source project list provided
- javaProjectMenu_JavaRepos.txt
- javaProjectMenu_JavaRepos400.txt

### 三、Extracting the training set

- run src/main/CreateTrainSetAPP.java
- dir --> Java project path

### 四、Experimental testing

- run src/main/App.java

parameters
- dir --> Java project path (test set)
- resultName --> Save the result file
- status --> Thread-safe or not   
- removeRepeat --> Deduplication or not
- methodLevel --> Grading level
- trainSetPath --> Extract the training set path
