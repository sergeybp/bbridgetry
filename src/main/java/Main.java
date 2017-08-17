import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Args: Username + Password for bbridge API");
        }
        BbridgeAPI a = new BbridgeAPI(args[0], args[1]);

        ArrayList<String> texts = new ArrayList<>();
        texts.add("Hi");
        texts.add("Robots");
        texts.add("dogs");

        ArrayList<String> images = new ArrayList<>();
        images.add("http://www.factroom.ru/facts/wp-content/uploads/2014/02/Koshki-620x465.jpg");
        //images.add("https://ptzgovorit.ru/sites/default/files/original_nodes/sibirskaya-koshka2.jpg");

        System.out.println(a.fullCycle(texts, images).toString());
    }

}
