/*************************************************
 *
 * @function:       存储JDBC操作数据库使用的常量
 *
 * @date:           21/12/2019
 *
 **************************************************/
package Function;

public class Constant {
    //数据库的类型、IP:port、库名、编码方式及时区等
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/stu_info_sys?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT";
    public static final String JDBC_USERNAME = "root";                      //数据库管理员名称
    public static final String JDBC_PASSWORD = "yourpassword";              //数据库密码
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";    //数据库JDBC的jar包
}
