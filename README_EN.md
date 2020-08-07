# Description
This project is a **"Student Information Management System"** by **Java** and **MySQL**.

[中文版本](https://github.com/Kexin-Tang/Student_Info_Sys/blob/master/README.md)

----
# Functions
In this project, I successfully achieved the following functions:

1. *Register*, *Log in*, *Forget password*, Manage personal info
2. Upload / Download file
3. Ask / Answer questions
4. Publish announcement
5. Statistic answer rate
6. Remind teachers to answer questions in time

---
# Images
More details in [**photo**](https://github.com/Kexin-Tang/Student_Info_Sys/tree/master/photo) files

----
# Structure
File name | Usage
---       |: ---:
photo	  | the image file
src       | the function file
SQL       | .sql file

The **photo** file is designed by my teammate *Zhanheng Gao* via PS, thanks for his contribution!

For the **src** files, their functions are illustrated as below:

File name    |    Usage
-------------|:--------:
main         | Homepage for the whole system
UI           | Formate setting and background for pages
UI_part      | Formate setting and background for some functions
SQL          | Interface between UI and SQL, defined the functions
INFO         | Information about SQL tables 
Function     | Details about the realization of functions
DAO          | SQL command in Java



-----
##### Notes:
* Users are supposed to download [jdbc](https://dev.mysql.com/downloads/connector/j/) package for Java-MySQL connection;
* Please check the [Constant.java](https://github.com/Kexin-Tang/Student_Info_Sys/blob/master/src/Function/Constant.java) file, and set the correct link between line 12 to line 14;
```
    public static final String JDBC_URL = "jdbc:mysql://(user):(port)/(table name)?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT";
    public static final String JDBC_USERNAME = "yourusername";
    public static final String JDBC_PASSWORD = "yourpassword";  
```
* Please transfer the **Timezone** in your IDE to **GMT** !!
