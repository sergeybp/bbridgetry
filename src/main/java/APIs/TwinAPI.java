package APIs;

import APIs.APIException;
import APIs.BbridgeAPI;
import com.google.gson.JsonObject;
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


    public TwinAPI(String username) {
        this.username = username;
    }


    public JsonObject getResult() throws IOException, OAuthExpectationFailedException, APIException, OAuthCommunicationException, OAuthMessageSignerException, URISyntaxException {
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
        return (a.fullCycle(x.getKey(), x.getValue()));
    }
}
