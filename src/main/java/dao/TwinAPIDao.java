package dao;

public interface TwinAPIDao {

    String getJsonString(String username, long timestamp);

    void setJsonString(String username, String json);

    void updateJsonString(String username, String json);

}
