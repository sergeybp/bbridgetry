package entities;

public class User {

    public User(){

    }

    public String json;

    public String username;

    public String lastUpdatedTimestamp;

    public User(String json, String username, String lastUpdatedTimestamp) {
        this.json = json;
        this.username = username;
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    public String getJson() {
        return json;
    }

    public String getUsername() {
        return username;
    }

    public String getlastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setlastUpdatedTimestamp(String lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }
}
