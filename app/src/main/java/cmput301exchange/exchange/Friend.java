package cmput301exchange.exchange;

import java.util.ArrayList;

/**
 * Created by Yishuo Wang on 2015/10/5.
 */
public class Friend extends Person implements Comparable<Person>{
    /**
     * @author Yishuo
     * Serves as an abstraction to allow ease of differentiation between User and Friend.
     * Extends person
     *
     * @param Username the name of the friend you wish to create
     */

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
