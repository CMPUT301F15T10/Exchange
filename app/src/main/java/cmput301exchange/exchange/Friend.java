package cmput301exchange.exchange;

import java.util.ArrayList;
/**
 *
 * Serves as an abstraction to allow ease of differentiation between User and Friend.
 * Extends person
 * @author: Yishuo
 * @param Username the name of the friend you wish to create
 */

public class Friend extends Person implements Comparable<Person>{


    public Friend(String Username){super(Username);}

    @Override
    public int compareTo(Person another) {
        /**
         * Allows sorting of FriendsLists
         * @param another the second person you are comparing to
         */
        return 0;
    }
}
