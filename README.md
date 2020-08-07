# 项目描述
该项目是一个 **"学生信息管理系统"** ，使用了**Java**和**MySQL**实现。

[English Version](https://github.com/Kexin-Tang/Student_Info_Sys/blob/master/README_EN.md)

----
# 功能描述
该项目实现了下列功能：

1. *注册*, *登录*, *忘记密码*, 管理个人信息
2. 上传/下载文件
3. 提问/回答问题
4. 发布公告（可定时发送）
5. 统计当日的答疑率
6. 管理员提醒老师及时回答问题

---
# 样式
点击[**照片**](https://github.com/Kexin-Tang/Student_Info_Sys/tree/master/photo) 了解详情

----
# 代码结构

文件**photo**是组员*高占恒*通过PS设计，感谢！

对于**src**文件，其代码结构如下：

File name    |    Usage
-------------|:--------:
main         | 登录界面等主要入口
UI           | 界面背景
UI_part      | 功能按键等小模块的样式
SQL          | 接口层，实现UI和SQL的连接
INFO         | 关于数据库信息
Function     | 实现功能函数的细节
DAO          | Java中操控SQL



-----
##### 注意事项:
* 使用的时候需要先下载[jdbc](https://dev.mysql.com/downloads/connector/j/) 实现Java与SQL的连接；
* 请查看[Constant.java](https://github.com/Kexin-Tang/Student_Info_Sys/blob/master/src/Function/Constant.java)中设置，确保正确连接您的数据库；
```
    public static final String JDBC_URL = "jdbc:mysql://(user):(port)/(table name)?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT";
    public static final String JDBC_USERNAME = "yourusername";
    public static final String JDBC_PASSWORD = "yourpassword";  
```
* 请在IDE中将您的**Timezone（时区）**设置为**GMT** !!
