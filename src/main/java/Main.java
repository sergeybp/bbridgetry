import com.google.inject.servlet.GuiceFilter;
import com.sun.grizzly.http.embed.GrizzlyWebServer;
import com.sun.grizzly.http.servlet.ServletAdapter;
import config.ServletConfig;
import model.Utils;

import javax.servlet.http.HttpServlet;
import java.io.UTFDataFormatException;

public class Main {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Please run with parameters: port");
            System.exit(0);
        }
        int port = Integer.parseInt(args[0]);
        GrizzlyWebServer server = new GrizzlyWebServer(port);
        ServletAdapter adapter = new ServletAdapter(new DummySevlet());
        adapter.addServletListener(ServletConfig.class.getName());
        adapter.addFilter(new GuiceFilter(), "GuiceFilter", null);
        server.addGrizzlyAdapter(adapter, new String[]{ "/" });
        server.start();
        System.out.println("Server started.");
    }

    @SuppressWarnings("serial")
    public static class DummySevlet extends HttpServlet { }
}
