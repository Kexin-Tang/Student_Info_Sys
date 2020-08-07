package INFO;

public class Alarm {
    private int id;
    private String login;
    private String question_num;
    private String answer_num;
    private boolean flag;
    private String rate;



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


    public String getQuestion_num() {
        return question_num;
    }
    public void setQuestion_num(String question_num) {
        this.question_num = question_num;
    }


    public String getAnswer_num() {
        return answer_num;
    }
    public void setAnswer_num(String answer_num) {
        this.answer_num = answer_num;
    }


    public boolean getFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }


    public String getRate() {
        return rate;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }
}
