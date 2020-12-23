package project.insa.idchatsystem.gui;

import java.util.ArrayList;
import java.util.Comparator;

class UserViewArrayList extends ArrayList<UserView> {
    public ArrayList<UserView> getListOrderedByName() {
        this.sort(Comparator.comparing(UserView::getUsername));
        return this;
    }
    public ArrayList<UserView> getListOrderedByPriority() {
        this.sort(Comparator.comparing(UserView::getPriority));
        return this;
    }
    @Override
    public boolean add(UserView userView) {
        int indexElem = this.indexOf(userView);
//            System.out.printf("CHATWINDOW add : passe dans %d\n",indexElem);
        if(indexElem == -1)//We add the element if it is not already present
            return super.add(userView);
        else {//Else we only update the user
            UserView pastUserViewUpdated = this.get(indexElem);
            pastUserViewUpdated.setUsername(userView.getUsername());
//                System.out.printf("CHATWINDOW add : newUserName : %s\n",pastUserViewUpdated.getUsername());
            pastUserViewUpdated.setLastSeen(userView.getLastSeen());
            this.set(indexElem,pastUserViewUpdated);
            return false;
        }
    }
}
