import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OnlineServlet extends HttpServlet {
    class StructUser {
        public PrintWriter writer = null;
        public ArrayList<String> cache = null;
        public String state = "disconnected";
        public StructUser(PrintWriter writer,ArrayList<String> cache,String state) {
            this.writer = writer;
            this.cache = cache;
            this.state = state;
        }
        public StructUser(PrintWriter writer,ArrayList<String> cache) {
            this.writer = writer;
            this.cache = cache;
        }
    }
    class HashMapPerso extends HashMap<Integer,StructUser> {
        @Override
        public StructUser put(Integer key, StructUser value) {
            StructUser previous = this.get(key);
            if(previous != null) {
                if(previous.cache != null) {
                    if(value.cache != null) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.addAll(previous.cache);
                        tmp.addAll(value.cache);
                        value.cache = tmp;
                    }
                    else {
                        value.cache = previous.cache;
                    }
                }
                if(value.state.equals("previousState"))
                    value.state = previous.state;
            }
            else {
                value.state = "disconnected";
            }
            return super.put(key, value);
        }
    }

    public HashMapPerso users = new HashMapPerso();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        Pattern pattern_message = Pattern.compile("(?<idSrc>[0-9]+),(?<idDst>[0-9]+),(?<other>.*)");
        String message = req.getParameter("message");
        Matcher m = pattern_message.matcher(message);
        int idSrc = -1;
        int idDest = -1;
        String other = "";
        while (m.find()){
            idSrc = Integer.parseInt(m.group("idSrc"));
            idDest = Integer.parseInt(m.group("idDest"));
            other = m.group("other");
        }
        PrintWriter writer = resp.getWriter();
        Pattern pattern_subscribe = Pattern.compile("state,(?<subscribe>subscribeLocal)");
        m = pattern_subscribe.matcher(other);
        String subscribe = "";
        while (m.find()){
            subscribe = m.group("subscribe");
        }
        Pattern pattern_state = Pattern.compile("state,(?<state>ready|disconnected)");
        m = pattern_state.matcher(other);
        String state = "";
        while (m.find()){
            state = m.group("state");
        }
        if(idSrc != -1 && !subscribe.equals("")) {
            users.put(idSrc,new StructUser(writer,null,"previousState"));
        }
        if(idSrc != -1 && !state.equals("")) {
            users.put(idSrc,new StructUser(writer,null,state));
        }
        if(!users.containsKey(idDest) || writer == null || !users.get(idDest).state.equals("ready")) {
            ArrayList<String> cache = new ArrayList<>();
            cache.add(other);
            users.put(idDest,new StructUser(null,cache,"previousState"));
        }
        else {
            users.put(idDest,new StructUser(writer,null,"previousState"));
            users.get(idDest).writer.println(other);
        }
    }
}
