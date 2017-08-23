import java.io.IOException;

public class Tester {

    public static void main(String[] args) throws IOException {
        String[] names = new String[]{"iuytrweyfckws","twitterapi", "wylsacom", "stalingulag", "cgtnrussian", "leprasorium","podslyshano","tvrain","ruslanusachev"};
        for (String name : names) {
            Client c = new Client();
            c.run(name);

        }
    }

}
