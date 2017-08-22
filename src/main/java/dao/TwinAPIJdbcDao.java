package dao;

import entities.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.jws.soap.SOAPBinding;
import javax.sql.DataSource;
import java.util.List;

public class TwinAPIJdbcDao extends JdbcDaoSupport implements TwinAPIDao {

    public TwinAPIJdbcDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
        String sql = "CREATE TABLE IF NOT EXISTS `users` (" +
                " `username` VARCHAR ," +
                " `json` VARCHAR ," +
                " `lastUpdatedTimestamp` VARCHAR ," +
                " PRIMARY KEY (`username`) " +
                " )";
        getJdbcTemplate().update(sql);
    }


    @Override
    public String getJsonString(String username, long timestamp) {
        String sql = "SELECT * FROM users";
        List<User> users = getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(User.class));
        for (User x : users) {
            if (x.username.equals(username)) {
                long prev = Long.parseLong(x.lastUpdatedTimestamp);
                if ((timestamp - prev) / 1000 / 60 / 60 < 24) {
                    return x.json;
                } else {
                    return "UPDATE";
                }

            }
        }
        return "NO";
    }

    @Override
    public boolean setJsonString(String username, String json) {
        String sql = "INSERT INTO users (username, json, lastUpdatedTimestamp) VALUES (?, ?, ?) ";
        try {
            String time = "" + System.currentTimeMillis();
            getJdbcTemplate().update(sql, username, json, time);
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateJsonString(String username, String json) {
        String sql = "UPDATE users SET lastUpdatedTimestamp = "+System.currentTimeMillis()+" , json = "+json+" WHERE users.username = " + username;
        try {
            getJdbcTemplate().update(sql);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
