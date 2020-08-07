package INFO;

import javax.swing.*;
import java.util.Date;

public class Question {
    private String id;
    private String question_title;
    private String question_content;
    private String question_answer;
    private String sender;
    private String receiver;
    private String send_time;
    private String answer_time;
    private String flag;

    public Question(){
        question_title = "";
        question_content = "";
        question_answer = "";
        sender = "";
        receiver = "";
        send_time = "";
        answer_time = "";

        flag = "未回复";
    }

    public Question(String[] s){
        if (s.length<9){
            JOptionPane.showMessageDialog(null, "输入字符串数过少！", "Error", 2);
            return;
        }
        id        = s[0];
        question_content   = s[1];
        question_title     = s[2];
        question_answer     = s[3];
        sender    = s[4];
        receiver = s[5];
        send_time      = s[6];
        answer_time   = s[7];
        flag      = s[8] ;
    }
    public String[] toStrings(){
        String[] result;
        result = new String[]{question_content, question_title, question_answer,sender, receiver, send_time, answer_time, flag};
        return result;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    public String  getTitle() {
        return question_title;
    }
    public void setTitle(String question_title) {
        this.question_title = question_title;
    }


    public String getContent() {
        return question_content;
    }
    public void setContent(String question_content) {
        this.question_content = question_content;
    }


    public String getAnswer() {
        return question_answer;
    }
    public void setAnswer(String question_answer) {
        this.question_answer = question_answer;
    }


    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }


    public String getSend_time() {
        return send_time;
    }
    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAnswer_time() {
        return answer_time;
    }
    public void setAnswer_time(String answer_time) {
        this.answer_time = answer_time;
    }

    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
}

