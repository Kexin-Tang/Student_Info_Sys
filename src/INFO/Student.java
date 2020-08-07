package INFO;

import java.util.Date;

public class Student {
    private int id;
    private String login;
    private String password;
    private String name;
    private String sex;
    private String classroom;
    private String email;
    private String protect;



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String  getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getClassroom() {
        return classroom;
    }
    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getProtect() {
        return protect;
    }
    public void setProtect(String protect) {
        this.protect = protect;
    }
}

