package project.insa.idchatsystem.logins.local_mode.distanciel.Facades;

import project.insa.idchatsystem.Exceptions.Uninitialized;
import project.insa.idchatsystem.Observers.logins.Observers.UserModelReceiverObserver;
import project.insa.idchatsystem.User.distanciel.User;
import project.insa.idchatsystem.logins.local_mode.distanciel.Local.LocalUserModelReceiver;
import project.insa.idchatsystem.logins.local_mode.distanciel.UserModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserModelReceivers implements UserModelReceiverObserver  {
    private UserModel model;
    private LocalUserModelReceiver localReceiver;
    public UserModelReceivers(UserModel model,int portBroadcast,boolean local) {
        this.model = model;
        if(local) {
            this.localReceiver = new LocalUserModelReceiver(this, portBroadcast);
            new Thread(this.localReceiver).start();
        }
    }
    @Override
    public void notifyNewMsg(String msg) {
        Pattern pattern_new_host = Pattern.compile("(?<username>[A-Za-z_.0-9]+),(?<id>[0-9a-z-]*),(?<ip>[0-9]+[.][0-9]+[.][0-9]+[.][0-9]+),(?<convListPort>[0-9]+),(?<local>[tf])");
        Pattern pattern_disconnected = Pattern.compile("(?<id>[0-9a-z-]*),disconnected");
        Pattern pattern_update = Pattern.compile("update,.+");
        //Extraction of the informations of the packet thanks to a regex and named group
        Matcher m = pattern_new_host.matcher(msg);
        while (m.find()){
            //System.out.println("NEW USER");
            User new_user = new User(m.group("username"),m.group("id"),m.group("ip"),m.group("local").equals("t"));
            try {
                if(!new_user.get_id().equals(User.get_current_id())) {
                    this.model.addOnlineUser(new_user);//Add or refresh informations of the user based on the id
                }
                new_user.setConversationHandlerListenerPort(Integer.parseInt(m.group("convListPort")));
            } catch (Uninitialized uninitialized) {
                uninitialized.printStackTrace();
            }
//            System.out.printf("UserModelReceivers : ONLINE USER\n");
        }
        m = pattern_disconnected.matcher(msg);
        while (m.find()){
            System.out.printf("User %s disconnection signal\n",m.group("id"));
//            System.out.printf("UserModelReceivers : OFFLINE USER\n");
            try {
                if(!m.group("id").equals(User.get_current_id())) {
                    this.model.removeOnlineUser(m.group("id"));//Add or refresh informations of the user based on the id
                }
            } catch (Uninitialized uninitialized) {
                uninitialized.printStackTrace();
            }
        }
        m = pattern_update.matcher(msg);
        while (m.find()){
            this.model.diffuseNewUsername();//Add or refresh informations of the user based on the id
        }
    }
}
