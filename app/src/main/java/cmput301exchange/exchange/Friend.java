package cmput301exchange.exchange;

import java.util.ArrayList;

/**
 * Created by Yishuo Wang on 2015/10/5.
 */
public class Friend extends Person implements Comparable<Person>{

    public Friend(String Username){super(Username);}

    @Override
    public int compareTo(Person another) {
        return 0;
    }
}
