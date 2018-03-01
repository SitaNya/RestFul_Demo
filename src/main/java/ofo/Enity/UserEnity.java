package ofo.Enity;

import javax.ws.rs.QueryParam;

public class UserEnity {
    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getGroup() {
        return group;
    }

    public String getLevel() {
        return level;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    private String user;
    private String password;
    private String group;
    private String level;
    private int phone;
    private String email;

    public UserEnity(String user, String password, String group, String level, int phone, String email) {
        this.user = user;
        this.group = group;
        this.password = password;
        this.level = level;
        this.phone = phone;
        this.email = email;
    }

}
