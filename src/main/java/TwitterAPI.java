import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class TwitterAPI {


    private HttpClient client;
    private String consumerKey;
    private String consumerSecret;
    private String token;
    private String tokenSecret;


    TwitterAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {
        client = HttpClientBuilder.create().build();
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.token = token;
        this.tokenSecret = tokenSecret;
    }


    void tryAccess() throws Exception {

        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);

        consumer.setTokenWithSecret(token, tokenSecret);
        HttpGet request = new HttpGet("https://api.twitter.com/1.1/followers/ids.json?cursor=-1&screen_name=josdirksen");
        consumer.sign(request);

        HttpResponse response = client.execute(request);

        if (response.getStatusLine().getStatusCode() == 200) {
            System.out.println(IOUtils.toString(response.getEntity().getContent()));
        }

    }

}
