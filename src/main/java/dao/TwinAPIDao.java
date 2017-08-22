package dao;

public interface TwinAPIDao {

    String getJsonString(String username, long timestamp);

    boolean setJsonString(String username, String json);

    boolean updateJsonString(String username, String json);

}
