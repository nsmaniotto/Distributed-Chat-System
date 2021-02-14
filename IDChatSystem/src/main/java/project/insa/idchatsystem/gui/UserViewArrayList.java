package project.insa.idchatsystem.gui;

import project.insa.idchatsystem.User.distanciel.User;

import java.util.ArrayList;
import java.util.Comparator;

class UserViewArrayList extends ArrayList<UserView> {
    public UserViewArrayList getListOrderedByName() {
        this.sort(Comparator.comparing(UserView::getUsername));
        return this;
    }
    public UserViewArrayList getListOrderedByPriority() {
        this.sort(Comparator.comparing(UserView::getPriority).reversed());
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
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserViewArrayList))
            return false;
        UserViewArrayList other = (UserViewArrayList) o;
        if(this.size() != other.size())
            return false;
        else {
            boolean same = true;
            for(int i=0;i<this.size();i++) {
                if(!this.get(i).fullEqual(other.get(i)))
                    same = false;
            }
            return same;
        }
    }
    public int contains(String idCheck) {
        for (int index = 0; index < this.size();index++) {
            if (this.get(index).getId().equals(idCheck))
                return index;
        }
        return -1;
    }
}
