import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Client {

    void run(String username) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://0.0.0.0:9999/start_job_user?username="+username);
        HttpResponse response = client.execute(post);
        JsonObject obj = getJsonFromResponse(response);

        while (true) {
            HttpGet get = new HttpGet("http://0.0.0.0:9999/get_result?job_id=" + obj.get("job_id").getAsString());
            client = HttpClientBuilder.create().build();
            response = client.execute(get);
            if(response.getStatusLine().getStatusCode() != 200){
                //System.err.println("Not ready");
                continue;
            }
            System.out.println("---------------------");
            System.out.println("[NAME]:"+username);
            System.out.println(getJsonFromResponse(response).toString());
            System.out.println("---------------------");
            break;
        }
    }

    private static JsonObject getJsonFromResponse(HttpResponse response) throws IOException {
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
