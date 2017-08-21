
import APIs.APIException;
import APIs.TwinAPI;
import APIs.TwitterAPI;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.rmi.server.ServerCloneException;

@WebServlet("/TwinAPI")
public class SimpleServlet extends HttpServlet {

    public SimpleServlet(){
        super();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String yourName = request.getParameter("yourName");
        TwinAPI a = new TwinAPI(yourName);
        try {
            String res = a.getResult().toString();
            PrintWriter writer1 = response.getWriter();
            writer1.println(res);
            writer1.close();
        } catch (Exception e){
            PrintWriter writer1 = response.getWriter();
            writer1.println("Error occurred.");
            writer1.close();
        }

    }

}
