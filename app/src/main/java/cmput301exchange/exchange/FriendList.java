package cmput301exchange.exchange;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by bq on 10/31/15.
 */
public class FriendList{
    private ArrayList<Person> friendlist;

    public ArrayList<Person> getFriendlist() {
        return friendlist;
    }

    public FriendList() {

    }
    public ArrayList<Person> searchfriend(String query){
        ArrayList<Person> results= new ArrayList<Person>();
        int n = friendlist.size();
        if(n==0){
            return results;
        }
        for(int i = 0;i<n;i++){
            if(friendlist.get(i).getName().toLowerCase().contains(query.toLowerCase())){
                results.add(friendlist.get(i));
            }
        }


        return results;
    }
    public void addfriend(Person aperson){
        friendlist.add(aperson);
    }
    public void removefirend(Person aperson){
        int n = friendlist.size();
        if(n==0){
            return;
        }
        for(int i = 0;i<n;i++){
            if(friendlist.get(i).getID()==aperson.getID()){
                friendlist.remove(i);
                break;
            }
        }
    }
}
