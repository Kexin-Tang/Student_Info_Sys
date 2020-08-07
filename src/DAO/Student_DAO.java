package DAO;

import Function.BaseDB;
import INFO.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Student_DAO extends BaseDB {



    public String[] show_one_student(String login) throws SQLException {

        String[] result = new String[7];

        conn = db.getConn();

        String sql = "SELECT * FROM user_info WHERE login=?";     //sql语句
        ps = conn.prepareStatement(sql);
        ps.setString(1,login);
        rs = ps.executeQuery();

        if(rs.next()){
            result[0] = rs.getString("login");
            result[1] = rs.getString("password");
            result[2] = rs.getString("name");
            result[3] = rs.getString("sex");
            result[4] = rs.getString("class");
            result[5] = rs.getString("email");
            result[6] = rs.getString("protect");
        }

        db.close();

        return result;
    }



    public String[][] show_student(String classroom) throws SQLException {

        String[][] result = null;
        List<Student> stu = new ArrayList<Student>();    //创建file列表
        int fieldnum = 7;                          //fieldnum是数据库的column数目

        conn = db.getConn();

        String sql = "SELECT * FROM user_info WHERE class=? AND login LIKE ? ORDER BY login ";     //sql语句
        ps = conn.prepareStatement(sql);
        ps.setString(1,classroom);
        ps.setString(2,"U%");
        rs = ps.executeQuery();

        try {
            //将sql中数据转入list中存储
            while (rs.next()) {
                buildList(rs, stu);
            }
            //将list中指定序号的信息保存到String[][] result
            if (stu.size() > 0) {
                result = new String[stu.size()][fieldnum];
                for (int j = 0; j < stu.size(); j++) {
                    buildResult(result, stu, j);
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            destroy();
        }
        return result;
    }





    public String[][] show_student_admin() throws SQLException {

        String[][] result = null;
        List<Student> stu = new ArrayList<Student>();    //创建file列表
        int fieldnum = 7;                          //fieldnum是数据库的column数目

        conn = db.getConn();

        String sql = "SELECT * FROM user_info ORDER BY class,login ";     //sql语句
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        try {
            //将sql中数据转入list中存储
            while (rs.next()) {
                buildList(rs, stu);
            }
            //将list中指定序号的信息保存到String[][] result
            if (stu.size() > 0) {
                result = new String[stu.size()][fieldnum];
                for (int j = 0; j < stu.size(); j++) {
                    buildResult(result, stu, j);
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            destroy();
        }
        return result;
    }



    public String[] Search_Class(String stu) throws SQLException {

        String[] result = new String[2];        //result[0] -> class
                                                //result[1] -> teacher
        conn = db.getConn();

        String sql = "SELECT * FROM user_info WHERE login = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1,stu);
        rs = ps.executeQuery();

        if(rs.next()){
            result[0] = rs.getString("class");
        }

        String sql2 = "SELECT * FROM user_info WHERE class=? AND login like ?";
        ps = conn.prepareStatement(sql2);
        ps.setString(1,result[0]);
        ps.setString(2,"%T%");
        rs = ps.executeQuery();

        if(rs.next()){
            result[1] = rs.getString("login");
        }

        db.close();
        return result;
    }




    public void Delete_stu(String login) throws SQLException {
        conn = db.getConn();

        String sql = "DELETE FROM user_info WHERE login=?";
        ps = conn.prepareStatement(sql);
        ps.setString(1,login);
        ps.executeUpdate();

        sql = "DELETE FROM login_info WHERE login=?";
        ps = conn.prepareStatement(sql);
        ps.setString(1,login);
        ps.executeUpdate();

        db.close();
    }



    private void buildList(ResultSet rs, List<Student> list) throws SQLException {
        Student stu = new Student();                     //新建一条List内容
        stu.setClassroom(rs.getString("class"));            //设置其班级
        stu.setEmail(rs.getString("email"));                //设置其email
        stu.setLogin(rs.getString("login"));                //设置其学号
        stu.setId(rs.getInt("id"));                         //设置其主键
        stu.setName(rs.getString("name"));                  //设置其姓名
        stu.setPassword(rs.getString("password"));          //设置其密码
        stu.setProtect(rs.getString("protect"));            //设置其密保问题
        stu.setSex(rs.getString("sex"));                    //设置其性别
        list.add(stu);                                                 //添加到list中
    }



    private void buildResult(String[][] result, List<Student> files, int j) {
        Student stu = files.get(j);

        result[j][0] = stu.getLogin();              //将数组的第0位设置为账号
        result[j][1] = stu.getPassword();           //将数组的第1位设置为密码
        result[j][2] = stu.getName();               //将数组的第2为设置为姓名
        result[j][3] = stu.getSex();                //将数组的第3位设置为性别
        result[j][4] = stu.getClassroom();          //将数组的第4位设置为班级
        result[j][5] = stu.getEmail();              //将数组的第5位设置为email
        result[j][6] = stu.getProtect();            //将数组的第6位设置为密保问题
    }
}

