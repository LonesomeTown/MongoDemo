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
