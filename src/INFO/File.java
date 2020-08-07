package INFO;

import java.util.Date;

public class File {
    private String name;
    private byte[] content;
    private int id;
    private String time;
    private String sender;


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String  getUploadDate() {
        return time;
    }
    public void setUploadDate(String time) {
        this.time = time;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    public byte[] getContent() {
        return content;
    }
    public void setContent(byte[] content) {
        this.content = content;
    }

    public String  getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
}
