import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {

        Properties prop = new Properties();
        InputStream input = new FileInputStream("config.properties");
        prop.load(input);

        String bbridgeUserName = prop.getProperty("bbridgeusername");
        String bbridgePassword = prop.getProperty("bbridgepassword");
        String comsumerKey = prop.getProperty("consumerkey");
        String consumerSeceret = prop.getProperty("consumersecret");
        String token = prop.getProperty("accesstoken");
        String tokenSecret = prop.getProperty("accesssecret");

        BbridgeAPI a = new BbridgeAPI(bbridgeUserName, bbridgePassword);
        ArrayList<String> texts = new ArrayList<>();
        texts.add("CAT");
        texts.add("DOG");
        texts.add("TREE");
        ArrayList<String> images = new ArrayList<>();
        images.add("http://www.factroom.ru/facts/wp-content/uploads/2014/02/Koshki-620x465.jpg");
        images.add("https://ptzgovorit.ru/sites/default/files/original_nodes/sibirskaya-koshka2.jpg");
        System.out.println(a.fullCycle(texts, images).toString());

        TwitterAPI api = new TwitterAPI(comsumerKey, consumerSeceret, token, tokenSecret);
        api.tryAccess();


    }

}
