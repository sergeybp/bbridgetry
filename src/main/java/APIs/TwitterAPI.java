package APIs;

import APIs.APIException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.util.Pair;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TwitterAPI {

    private String PUNCTUATION = "`.,:?() ";

    private HttpClient client;
    private String consumerKey;
    private String consumerSecret;
    private String token;
    private String tokenSecret;
    private OAuthConsumer consumer;

    public TwitterAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
        client = HttpClientBuilder.create().build();
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.token = token;
        this.tokenSecret = tokenSecret;
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(token, tokenSecret);
    }


    Pair<ArrayList<String>, ArrayList<String>> fullCycle(String username) throws OAuthMessageSignerException, APIException, OAuthCommunicationException, OAuthExpectationFailedException, IOException {

        ArrayList<String> followers = getFollowers(username);
        ArrayList<String> texts = new ArrayList<>();
        ArrayList<String> urls = new ArrayList<>();
        for (String user : followers) {
            try {
                Pair<ArrayList<String>, ArrayList<String>> tmp = getUserTimeLine(user);
                texts.addAll(tmp.getKey());
                urls.addAll(tmp.getValue());
            } catch (APIException e) {
                System.err.println("APIs.TwitterAPI limit reached!");
                return new Pair<>(texts, urls);
            }
        }
        return new Pair<>(texts, urls);

    }

    private ArrayList<String> getFollowers(String username) throws APIException, IOException, OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException {

        HttpGet request = new HttpGet("https://api.twitter.com/1.1/followers/list.json?cursor=-1&screen_name=" + username + "&skip_status=true&include_user_entities=false");
        consumer.sign(request);
        HttpResponse response = client.execute(request);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new APIException(response.getStatusLine().getStatusCode(),  "TwitterAPI|getFollowers?user="+username);
        }
        JsonObject result = getJsonFromResponse(response);

        JsonArray users = result.getAsJsonArray("users");
        ArrayList<String> res = new ArrayList<>();
        for (JsonElement tmp : users) {
            res.add(tmp.getAsJsonObject().get("screen_name").getAsString());
        }
        return res;
    }


    private Pair<ArrayList<String>, ArrayList<String>> getUserTimeLine(String user) throws OAuthCommunicationException, OAuthExpectationFailedException, OAuthMessageSignerException, IOException, APIException {
        HttpGet request = new HttpGet("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=" + user);
        consumer.sign(request);
        client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new APIException(response.getStatusLine().getStatusCode(), "TwitterAPI|getTimeline?user="+user);
        }
        JsonArray result = getJsonArrayFromResponse(response);
        ArrayList<String> texts = new ArrayList<>();
        ArrayList<String> urls = new ArrayList<>();
        for (JsonElement tmp : result) {
            try {
                String x = tmp.getAsJsonObject().get("text").getAsString();
                String toAdd = "";
                for (int i = 0; i < x.length(); i++) {
                    if ((x.charAt(i) >= 'a' && x.charAt(i) <= 'z') || (x.charAt(i) >= 'A' && x.charAt(i) <= 'Z') || (PUNCTUATION.contains(""+x.charAt(i)))) {
                        toAdd += x.charAt(i);
                    }
                }
                texts.add(toAdd);
            } catch (Exception ignored) {
                //no text in tweet
            }
            try {
                JsonArray media = tmp.getAsJsonObject().get("entities").getAsJsonObject().get("media").getAsJsonArray();
                for (JsonElement t : media) {
                    try {
                        urls.add(t.getAsJsonObject().get("media_url").getAsString());
                    } catch (Exception ignored) {
                        // no media in element
                    }
                }
            } catch (Exception ignored) {
                //no media in tweet
            }
        }
        return new Pair<>(texts, urls);
    }

    private JsonObject getJsonFromResponse(HttpResponse response) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        JsonParser parser = new JsonParser();
        return (JsonObject) parser.parse(result.toString());
    }

    private JsonArray getJsonArrayFromResponse(HttpResponse response) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        JsonParser parser = new JsonParser();
        return (JsonArray) parser.parse(result.toString());
    }

}
