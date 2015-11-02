package cmput301f15t10.exchange;

import java.util.ArrayList;

/**
 * Created by Yishuo Wang on 2015/10/5.
 */
public class Friend extends Person implements Searchable, Comparable<Person>{

    public Friend(){super();}

    @Override
    public int compareTo(Person another) {
        return 0;
    }
}
