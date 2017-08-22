package model;

import APIs.APIException;
import APIs.TwinAPI;
import com.google.gson.JsonObject;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class SingleRun extends Thread {

    private String username;
    private String jobId;

    SingleRun(String username, String jobId) {
        this.jobId = jobId;
        this.username = username;
    }

    public void run() {

        TwinAPI api = new TwinAPI(username);
        JsonObject object;
        try {
            object = api.getResult();
        } catch (IOException e) {
            object = new JsonObject();
            object.addProperty("status", "IOException");
        } catch (OAuthExpectationFailedException e) {
            object = new JsonObject();
            object.addProperty("status", "OauthException");
        } catch (APIException e) {
            object = new JsonObject();
            object.addProperty("status", "ERROR "+e.toString());
        } catch (OAuthCommunicationException e) {
            object = new JsonObject();
            object.addProperty("status", "OauthCommunicationException");
        } catch (OAuthMessageSignerException e) {
            object = new JsonObject();
            object.addProperty("status", "OAuthMessageSignerException");
        } catch (URISyntaxException e) {
            object = new JsonObject();
            object.addProperty("status", "URISyntaxException");
        }

        Utils.workingMap.put(jobId, object);
    }

}
