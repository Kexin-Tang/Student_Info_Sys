package INFO;

import javax.swing.*;

public class Person {
    private String username;    //学号/账号
    private String password;    //密码
    private String name;        //姓名
    private String sex;         //性别
    private String clas;        //班级
    private String email;       //邮件地址
    private String protect;     //密保

    public Person(String[] result){
        if (result.length<7){
            JOptionPane.showMessageDialog(null, "输入字符串数过少！", "Error", 2);
            return;
        }
        setUsername (result[0]);
        setPwd      (result[1]);
        setName     (result[2]);
        setSex      (result[3]);
        setClas     (result[4]);
        setEmail    (result[5]);
        setProtect  (result[6]);
    }

    public String[] toStrings(){
        String[] result = {username, password, name, sex, clas, email, protect };
        return result;
    }

    /*
     *       获取学号和设置学号
     * */
    public String getUsername() {
        return username;
    }

    public void setUsername(String user_id) {
        this.username = user_id;
    }

    /*
     *       设置名字和获取名字
     * */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /*
     *       设置性别和获取性别
     * */
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    /*
     *       设置班级和获取班级
     * */
    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }


    /*
     *       设置密保和获取密保
     * */
    public String getProtect() {
        return protect;
    }

    public void setProtect(String protect) {
        this.protect = protect;
    }


    /*
     *       设置email和获取email
     * */
    public String getEmail() { return email;  }

    public void setEmail(String email) {
        this.email = email;
    }

    /*
     *       设置pwd和获取pwd
     * */
    public String getPwd() {
        return password;
    }

    public void setPwd(String password) {
        this.password = password;
    }


}
