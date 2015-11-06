package cmput301exchange.exchange;

import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * Stores a list of friends
 * @author Baihong
 */


public class FriendList{

    private ArrayList<Person> friendlist;

    public ArrayList<Person> getFriendlist() {
        /**
         * Returns a List of Friends.
         */
        return friendlist;
    }

    public FriendList() {

    }
    public ArrayList<Person> searchfriend(String query){
        /**
         * Searches a friendslist for a string
         * @param query the string that you wish to search for.
         */
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
        /**
         * Adds a friend
         * @param aperson the person you wish to add.
         */
        friendlist.add(aperson);
    }
    public void removefirend(Person aperson){
        /**
         * Removes a friend
         * @param aperson the person you wish to remove from the friendslist
         */
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
