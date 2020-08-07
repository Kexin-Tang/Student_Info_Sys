/********************************************************************************************
 *
 * @function:       配置JDBC，用Java连接Mysql
 *
 * @date:           21/12/2019
 *
 * @class:          DataBaseConnect
 *
 * @content:        public static   DataBaseConnect getDBUtil(void)
 *                  public          int             executeUpdate(String sql)
 *                  public          int             executeUpdate(String sql, Object[] obj)
 *                  public          ResultSet       executeQuery(String sql)
 *                  public          ResultSet       executeQuery(String sql, Object[] obj)
 *                  public          Connection      getConn()
 *                  public          void            close()
 *
 *******************************************************************************************/
package Function;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseConnect {
    private static DataBaseConnect db;

    private Connection conn;        //连接
    private PreparedStatement ps;   //准备
    private ResultSet rs;           //结果


    //
    //  @input      :   NULL
    //  @output     :   DataBaseConnect
    //  @function   :   检查是否创建了class，如果没有则创建一个
    //
    public static DataBaseConnect getDBUtil() {
        if (db == null) {
            db = new DataBaseConnect();
        }
        return db;
    }



    //
    //  @input      :   String sql  -> 操作MYSQL的语句，"增删查改"均要使用
    //  @output     :   int
    //  @function   :   用sql语句改变数据库状态
    //
    public int executeUpdate(String sql) {
        int result = -1;
        if (getConn() == null) {
            return result;
        }
        try {
            ps = conn.prepareStatement(sql);    //将连接好的sql语句装载，准备生效
            result = ps.executeUpdate();        //生效执行
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //
    //  @input      :   String      ->  sql语句控制MYSQL，"增删查改"均要使用
    //                  Object[]    ->  为数据库中对应表的COLUMN要填入的信息
    //  @output     :   int
    //  @function   :   用于将Object[]中的内容填入String中控制MYSQL语句中对应的空格
    //
    public int executeUpdate(String sql, Object[] obj) {
        int result = -1;
        if (getConn() == null) {
            return result;
        }
        try {
            ps = conn.prepareStatement(sql);
            //将Object[]中的内容填入sql中对应空格
            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1, obj[i]);
            }
            result = ps.executeUpdate();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    //
    //  @input      :   String      ->  获取数据库中的一条信息，一般为"SELECT * FROM table_name"
    //  @output     :   ResultSet
    //  @function   :   查询MYSQL，将所获得的内容填入ResultSet
    //
    public ResultSet executeQuery(String sql) {
        if (getConn() == null) {
            return null;
        }
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();             //将查询到的内容填入rs，通过.getXX()即可获得表中信息
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }




    //
    //  @input      :   String      ->  获取数据库中的一条信息，一般为"SELECT * FROM table_name WHERE condition=?"
    //                  Object[]    ->  控制condition，即筛选表中信息显示
    //  @output     :   ResultSet
    //  @function   :   查询MYSQL，且通过Object[]控制查找特定的记录
    //
    public ResultSet executeQuery(String sql, Object[] obj) {
        if (getConn() == null) {
            return null;
        }
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1, obj[i]);
            }
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }




    //
    //  @input      :   NULL
    //  @output     :   Connection
    //  @function   :   连接数据库
    //
    public Connection getConn() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(Constant.JDBC_DRIVER);
                conn = DriverManager.getConnection(Constant.JDBC_URL, Constant.JDBC_USERNAME,
                        Constant.JDBC_PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("jdbc driver is not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }




    //
    //  @input      :   NULL
    //  @output     :   NULL
    //  @function   :   将已经操作过的临时空间内容清空，一般在执行完数据库操作后调用
    //
    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

