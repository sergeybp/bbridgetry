import java.io.IOException;

public class Tester {

    public static void main(String[] args) throws IOException {
        String[] names = new String[]{"iuytrweyfckws","twitterapi", "wylsacom", "stalingulag", "cgtnrussian", "leprasorium","podslyshano","tvrain","ruslanusachev"};
        for (int i = 0; i < names.length; i++) {
            Client c = new Client();
            c.run(names[i]);

        }
    }

}
