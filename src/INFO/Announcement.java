package INFO;

import java.util.Date;

public class Announcement {
    private String message;
    private String message_time;
    private int id;
    private String sender;
    private String title;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String  getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }


    public String getMessage_time() {
        return message_time;
    }
    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }



    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}

