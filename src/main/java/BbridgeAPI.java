import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;

class BbridgeAPI {

    private String username;
    private String password;
    private HttpClient client;


    BbridgeAPI(String username, String password) {
        this.username = username;
        this.password = password;
        client = HttpClientBuilder.create().build();
    }

    String fullCycle(ArrayList<String> texts, ArrayList<String> images) throws IOException, URISyntaxException {
        JsonObject obj = new JsonObject();
        obj.addProperty("username", username);
        obj.addProperty("password", password);
        StringEntity requestEntity = new StringEntity(
                obj.toString(),
                ContentType.APPLICATION_JSON);
        HttpPost post = new HttpPost("http://bbridgeapi.cloudapp.net/v1/auth");
        post.setEntity(requestEntity);
        HttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() != 200) {
            return "[!ERROR!] WHILE AUTHORIZATION";
        }
        String token = getJsonFromResponse(response).get("token").toString();
        token = token.substring(1, token.length() - 1);

        URIBuilder builder = new URIBuilder("http://bbridgeapi.cloudapp.net");
        builder.setPath("/v1/profiling/personal")
                .setParameter("lang", "en")
                .setParameter("attr", "gender")
                .addParameter("attr", "age_group");
        obj = new JsonObject();
        JsonArray array = new JsonArray();
        for (String tmp : texts) {
            array.add(tmp);
        }
        obj.add("text", array);
        array = new JsonArray();
        for (String tmp : images) {
            array.add(tmp);
        }
        obj.add("image_urls", array);
        requestEntity = new StringEntity(obj.toString());
        post = new HttpPost(builder.toString());
        post.setHeader(HttpHeaders.AUTHORIZATION, token);
        post.setHeader("Content-type", "application/json");
        post.setEntity(requestEntity);
        response = client.execute(post);
        if (response.getStatusLine().getStatusCode() != 202) {
            return "[!ERROR!] WHILE AUTHORIZATION";
        }
        String requestId = getJsonFromResponse(response).get("request_id").toString();
        requestId = requestId.substring(1, requestId.length() - 1);

        builder = new URIBuilder("http://bbridgeapi.cloudapp.net");
        builder.setPath("/v1/response")
                .setParameter("id", requestId);

        HttpGet request = new HttpGet(builder.build());
        request.addHeader(HttpHeaders.AUTHORIZATION, token);

        while (true) {
            response = client.execute(request);
            if(response.getStatusLine().getStatusCode() != 204 && response.getStatusLine().getStatusCode() != 200){
                return "[!ERROR!] WHILE AUTHORIZATION";
            }
            if(response.getStatusLine().getStatusCode() == 204){
                continue;
            }
            return EntityUtils.toString(response.getEntity());
        }
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
}
