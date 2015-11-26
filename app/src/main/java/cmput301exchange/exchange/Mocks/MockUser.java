package cmput301exchange.exchange.Mocks;

import cmput301exchange.exchange.PersonList;
import cmput301exchange.exchange.User;

/**
 * Created by nobody on 11/20/15.
 */
public class MockUser extends User {
    public MockUser(String Username) {
        super(Username);
        MockPersonList persons=new MockPersonList();
        super.setFriendList(persons.friendList);
    }
}
