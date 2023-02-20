Project tree structure:

.
├── DatabaseProgramHomework1-0.0.1-SNAPSHOT.jar
├── application.yml
├── file
│ ├── command.csv
│ ├── match.csv
│ ├── restaurant.csv
│ └── supplier.csv
├── lib
│ ├── bson-4.6.1.jar
│ ├── bson-record-codec-4.6.1.jar
│ ├── commons-beanutils-1.9.2.jar
│ ├── commons-collections-3.2.1.jar
│ ├── commons-lang3-3.12.0.jar
│ ├── commons-logging-1.1.1.jar
│ ├── hutool-core-5.8.5.jar
│ ├── jackson-annotations-2.13.4.jar
│ ├── jackson-core-2.13.4.jar
│ ├── jackson-databind-2.13.4.jar
│ ├── jackson-datatype-jdk8-2.13.4.jar
│ ├── jackson-datatype-jsr310-2.13.4.jar
│ ├── jackson-module-parameter-names-2.13.4.jar
│ ├── jakarta.annotation-api-1.3.5.jar
│ ├── jul-to-slf4j-1.7.36.jar
│ ├── log4j-api-2.17.2.jar
│ ├── log4j-to-slf4j-2.17.2.jar
│ ├── logback-classic-1.2.11.jar
│ ├── logback-core-1.2.11.jar
│ ├── lombok-1.18.24.jar
│ ├── mongodb-driver-core-4.6.1.jar
│ ├── mongodb-driver-sync-4.6.1.jar
│ ├── opencsv-3.8.jar
│ ├── slf4j-api-1.7.36.jar
│ ├── snakeyaml-1.30.jar
│ ├── spring-aop-5.3.23.jar
│ ├── spring-beans-5.3.23.jar
│ ├── spring-boot-2.7.4.jar
│ ├── spring-boot-autoconfigure-2.7.4.jar
│ ├── spring-boot-starter-2.7.4.jar
│ ├── spring-boot-starter-data-mongodb-2.7.4.jar
│ ├── spring-boot-starter-json-2.7.4.jar
│ ├── spring-boot-starter-logging-2.7.4.jar
│ ├── spring-boot-starter-tomcat-2.7.4.jar
│ ├── spring-boot-starter-web-2.7.4.jar
│ ├── spring-context-5.3.23.jar
│ ├── spring-core-5.3.23.jar
│ ├── spring-data-commons-2.7.3.jar
│ ├── spring-data-mongodb-3.4.3.jar
│ ├── spring-expression-5.3.23.jar
│ ├── spring-jcl-5.3.23.jar
│ ├── spring-tx-5.3.23.jar
│ ├── spring-web-5.3.23.jar
│ ├── spring-webmvc-5.3.23.jar
│ ├── tomcat-embed-core-9.0.65.jar
│ ├── tomcat-embed-el-9.0.65.jar
│ └── tomcat-embed-websocket-9.0.65.jar
└── output
└── result2_Wang_Zhimin.txt

- **DatabaseProgramHomework1-0.0.1-SNAPSHOT.jar**：Executable file.
- **application.yml**: The configuration file.
- **file**: The folder where you put the .csv files.
- **lib**: The folder to store libraries.
- **output**: The folder where store the result files.

**Execution Steps：**

1. Unzip the file I uploaded on Canvas, and the get in the Task1 folder.

   `cd {{YOUR PATH}}/Zhimin_Wang_CS7311_Programming_HW1/Task1`

2. Edit the application.yml file to enter your MongoDB information.

    ```yaml
    spring:
      data:
        mongodb:
          host: 127.0.0.1
          port: 27017
          database: homework1
          username: admin
          password: admin
          authentication-database: admin
    
    logging:
      level:
        org.springframework.data.mongodb.core: INFO
    
    taskName: task2
    ```

3. Put your .csv files in **file**.
4. Execute the command at the current path：

    ```bash
    java -jar **DatabaseProgramHomework1-0.0.1-SNAPSHOT.jar**
    ```

5. The program will exit automatically after execution, and then you can go to the output folder to view the output files.
6. To execution the Task2, switch the directory to Task2, then the steps are the same as above.