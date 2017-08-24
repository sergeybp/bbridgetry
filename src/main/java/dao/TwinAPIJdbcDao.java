package dao;

import entities.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

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
    public synchronized String getJsonString(String username, long timestamp) {
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
    public synchronized void setJsonString(String username, String json) {
        String sql = "INSERT INTO users (username, json, lastUpdatedTimestamp) VALUES (?, ?, ?) ";
        try {
            String time = "" + System.currentTimeMillis();
            getJdbcTemplate().update(sql, username, json, time);
        } catch (Exception ignored) {
        }
    }

    @Override
    public synchronized void updateJsonString(String username, String json) {
        String sql = "UPDATE users SET lastUpdatedTimestamp = "+System.currentTimeMillis()+" , json = "+json+" WHERE users.username = " + username;
        try {
            getJdbcTemplate().update(sql);
        } catch (Exception ignored) {
        }
    }
}
