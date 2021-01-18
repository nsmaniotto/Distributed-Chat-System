package project.insa.idchatsystem.server;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<HTML>\n"+
                "<HEAD><TITLE>Hello WWW</TITLE</HEAD>\n" +
                "<BODY>\n"+
                "<H1>Hello WWW</H1>\n" +
                "</BODY></HTML>");
    }
}
