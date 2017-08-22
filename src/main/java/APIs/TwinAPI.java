package APIs;

import APIs.APIException;
import APIs.BbridgeAPI;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import config.JdbcDaoContextConfiguration;
import dao.TwinAPIDao;
import javafx.util.Pair;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Properties;

public class TwinAPI {

    String username;

    private TwinAPIDao twinAPIDao = new JdbcDaoContextConfiguration().twinAPIJdbcDao();

    public TwinAPI(String username) {
        this.username = username;
    }


    public JsonObject getResult() throws IOException, OAuthExpectationFailedException, APIException, OAuthCommunicationException, OAuthMessageSignerException, URISyntaxException {


        boolean needUpdate = false;
        boolean needCreate = false;
        String inDB = twinAPIDao.getJsonString(username, System.currentTimeMillis());
        switch (inDB){
            case "UPDATE":
                needCreate = true;
                break;
            case "NO":
                needCreate = true;
                break;
            default:
                JsonParser parser = new JsonParser();
                return (JsonObject) parser.parse(inDB);
        }

        Properties prop = new Properties();
        InputStream input = new FileInputStream("config.properties");
        prop.load(input);
        String bbridgeUserName = prop.getProperty("bbridgeusername");
        String bbridgePassword = prop.getProperty("bbridgepassword");
        String comsumerKey = prop.getProperty("consumerkey");
        String consumerSeceret = prop.getProperty("consumersecret");
        String token = prop.getProperty("accesstoken");
        String tokenSecret = prop.getProperty("accesssecret");
        TwitterAPI api = new TwitterAPI(comsumerKey, consumerSeceret, token, tokenSecret);
        Pair<ArrayList<String>, ArrayList<String>> x = api.fullCycle(username);
        BbridgeAPI a = new BbridgeAPI(bbridgeUserName, bbridgePassword);
        JsonObject result = (a.fullCycle(x.getKey(), x.getValue()));

        if(needCreate){
            twinAPIDao.setJsonString(username, result.toString());
        }

        if(needUpdate){
            twinAPIDao.updateJsonString(username, result.toString());
        }

        return result;
    }
}
