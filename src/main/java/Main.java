import APIs.APIException;
import APIs.TwinAPI;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {


    public static void main(String[] args) throws URISyntaxException, OAuthExpectationFailedException, IOException, APIException, OAuthMessageSignerException, OAuthCommunicationException {
        TwinAPI a = new TwinAPI("twitterapi");
        System.out.println(a.getResult().toString());
    }
}
