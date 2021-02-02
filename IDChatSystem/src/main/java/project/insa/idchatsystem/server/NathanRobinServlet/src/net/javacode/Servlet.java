package net.javacode;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Servlet implementation class Servlet
 */
@WebServlet("/")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	class StructMsg{
		public String idSrc;
		public String idDest;
		public String msg;
		public StructMsg(String idSrc,String idDest,String msg) {
			this.idSrc = idSrc;
			this.idDest = idDest;
			this.msg = msg;
		}
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    class StructUser {
        public PrintWriter writer = null;
        public ArrayList<StructMsg> cacheLog = null;
        public ArrayList<StructMsg> cacheConv = null;
        public String state = "disconnected";
        public StructUser(PrintWriter writer,ArrayList<StructMsg> cacheLog,ArrayList<StructMsg> cacheConv,String state) {
            this.writer = writer;
            this.cacheLog = cacheLog;
            this.cacheConv = cacheConv;
            this.state = state;
        }
        public StructUser(PrintWriter writer,ArrayList<StructMsg> cacheLog,ArrayList<StructMsg> cacheConv) {
            this.cacheLog = cacheLog;
            this.cacheConv = cacheConv;
        }
    }
    class HashMapPerso extends HashMap<String,StructUser> {
        @Override
        public StructUser put(String key, StructUser value) {
            return super.put(key, value);
        }
    }
    public HashMapPerso users = new HashMapPerso();
    @Override
    public void init() {
    }
    @Override
    public void service(ServletRequest req, ServletResponse resp) throws IOException, ServletException {
        String message = req.getParameter("message");
    	Pattern pattern_message = Pattern.compile("message=(?<idSrc>[0-9a-z-]*),(?<idDest>[-]?[0-9a-z-]*),(?<other>[^&]+)[&]$");
        if(message!=null) {
	        Matcher m = pattern_message.matcher(message);
	        //System.out.println(message);
	        //Parsage des param�tres 
	        String idSrc = "";
	        String idDest = "";
	        String other = "";
	        while (m.find()){
	            idSrc = m.group("idSrc");
	            idDest = m.group("idDest");
	            other = m.group("other");
	        }
	        PrintWriter writer = null;
			//Subscription : add user to the list
			Pattern pattern_subscribe = Pattern.compile("(?<subscribe>subscribe)");
			m = pattern_subscribe.matcher(other);
			String subscribe = "";
			while (m.find()){
			    subscribe = m.group("subscribe");
			}
			//State : if receiver ready we can transmit packet to the id specified
			Pattern pattern_state = Pattern.compile("state,(?<state>ready|disconnected)");
			m = pattern_state.matcher(other);
			String state = "";
			while (m.find()){
			    state = m.group("state");
			}

			//Protocole : check if conversation or login
			Pattern pattern_extract_proto = Pattern.compile("(?<proto>conv|login),(?<messageContent>.*)");
			m = pattern_extract_proto.matcher(other);
			String proto = "";
			while (m.find()){
			    proto = m.group("proto");
			}
			//Check if getMessage pattern
			Pattern pattern_getMessage = Pattern.compile("(?<getMsg>getMessages)");
			m = pattern_getMessage.matcher(other);
			boolean getMessages = false;
			while (m.find()){
				getMessages = m.group("getMsg").equals("") ? false : true;
				//System.out.printf("ASKFORUPDATE %s\n", other);
			}
			
			if (!subscribe.equals("")) {
				System.out.println("-------------------------------------------------------------");
				System.out.printf("->   %d subscribed\n",idSrc);
				if (!users.containsKey(idSrc))
					users.put(idSrc, new StructUser(null,new ArrayList<StructMsg>(),new ArrayList<StructMsg>()));
				return;
			}
			else if (!state.equals("")) {
				System.out.printf("->   %d is %s\n",idSrc,state);
				setStatus(idSrc,state);
				if(state.equals("ready"))
					setWriter(idSrc,writer);
			}
			else if(getMessages && users.get(idSrc) != null && users.get(idSrc).state.equals("ready")) {
				sendAll(idSrc,resp,proto);
			}
			else {
				if(idDest.equals("")) {
					for (Object idObj : users.keySet().toArray()) {
						String id = (String)(idObj);
						addToCache(id,new StructMsg(idSrc,idDest,other),proto);
					}
				}
				else {
					addToCache(idDest,new StructMsg(idSrc,idDest,other),proto);
				}
			}
        }
            
    }
    public void addToCache(String id,StructMsg message,String protocole) {
    	if(!users.containsKey(id)) {
    		this.users.put(id, new StructUser(null,new ArrayList<StructMsg>(),new ArrayList<StructMsg>()));
    	}
    	StructUser storage = this.users.get(id);
    	System.out.printf("\t\taddToCache %s : %s\n",protocole,message.msg);
    	if(protocole.equals("conv")) {
	    	storage.cacheConv.add(message);
	    	this.users.put(id,storage );
    	}
    	else {
	    	storage.cacheLog.add(message);
	    	this.users.put(id,storage );
    	}
    }
    public void sendAll(String id,ServletResponse response,String protocole) {
    	response.setContentType("text/html;charset=UTF-8");
    	PrintWriter writer;
		try {
			writer = response.getWriter();

	    	if(protocole == null || protocole.equals("")) {
	    		System.out.println("Error protocole not detected");
	    		return;
	    	}
	    	ArrayList<StructMsg> messages = null;
	    	if(protocole.equals("conv"))
	    		messages = this.users.get(id).cacheConv;
	    	else 
		    	messages = this.users.get(id).cacheLog;
	    	if(messages != null && messages.size() >0)
	    		System.out.printf("\t\tsendAll cache\n");
	    	for(StructMsg message : messages) {
	    		if(message.idSrc != id) {
		    		System.out.printf("SENDED %s\n",message.msg);
		    		writer.println(message.msg);
		    		writer.flush();
	    		}
	    		else {
	    			System.out.println("Not sending to the same user");
	    		}
	    	}

	    	if(protocole.equals("conv"))
	    		this.users.get(id).cacheConv = new ArrayList<StructMsg>();
	    	else 
		    	this.users.get(id).cacheLog = new ArrayList<StructMsg>();
	    	//writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void setWriter(String id,PrintWriter writer) {
    	System.out.println("\t\tsetWriter");
    	StructUser user = this.users.get(id);
    	if(user != null) {
        	user.writer = writer;
        	this.users.put(id,user);
    	}
    }
    public void setStatus(String id,String status) {
    	System.out.println("setStatus");
    	if(!users.containsKey(id)) {
    		this.users.put(id, new StructUser(null,new ArrayList<StructMsg>(),new ArrayList<StructMsg>(),status));
    	}
    	else {
    		StructUser user = this.users.get(id);
    		user.state = status;
    		this.users.put(id, user);
    	}
    }

}
