package project.insa.idchatsystem.logins.local_mode.distanciel.Facades;

import project.insa.idchatsystem.Observers.logins.Observers.UserModelReceiverObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.logins.local_mode.distanciel.Local.LocalUserModelReceiver;
import project.insa.idchatsystem.logins.local_mode.distanciel.UserModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserModelReceivers implements UserModelReceiverObserver  {
    private UserModel model;
    private LocalUserModelReceiver localReceiver;
    public UserModelReceivers(UserModel model,int portBroadcast) {
        this.model = model;
        this.localReceiver = new LocalUserModelReceiver(this,portBroadcast);
        new Thread(this.localReceiver).start();
    }
    @Override
    public void notifyNewMsg(String msg) {
//        System.out.printf(".(UserModelReceivers.java:21) : MSG : %s\n",msg);
        Pattern pattern_new_host = Pattern.compile("login,(?<username>[A-Za-z_.0-9]+),(?<id>[0-9]+),(?<ip>[0-9]+[.][0-9]+[.][0-9]+[.][0-9]+),(?<convListPort>[0-9]+),(?<local>[tf])");
        Pattern pattern_disconnected = Pattern.compile("login,(?<id>[0-9]+),disconnected");
        Pattern pattern_update = Pattern.compile("login,update");
        //Extraction of the informations of the packet thanks to a regex and named group
        Matcher m = pattern_new_host.matcher(msg);
        while (m.find()){
            //System.out.println("NEW USER");
            User new_user = new User(m.group("username"),Integer.parseInt(m.group("id")),m.group("ip"),m.group("local").equals("t"));
            new_user.setConversationHandlerListenerPort(Integer.parseInt(m.group("convListPort")));
//            System.out.printf("UserModelReceivers : ONLINE USER\n");
            this.model.addOnlineUser(new_user);//Add or refresh informations of the user based on the id
        }
        m = pattern_disconnected.matcher(msg);
        while (m.find()){
            System.out.printf("User %s disconnection signal\n",m.group("id"));
//            System.out.printf("UserModelReceivers : OFFLINE USER\n");
            this.model.removeOnlineUser(Integer.parseInt(m.group("id")));//Add or refresh informations of the user based on the id
        }
        m = pattern_update.matcher(msg);
        while (m.find()){
            System.out.print("msg ask for update\n");
            this.model.diffuseNewUsername();//Add or refresh informations of the user based on the id
        }
    }
}
