package cmput301exchange.exchange;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by hzhu6 on 10/9/15.
 */


public class Friendtest extends ApplicationTestCase<Application> {


    public Friendtest(Class<Application> applicationClass) {
        super(applicationClass);
    }

    public void testAdd(){
        FriendList friends = new FriendList();
        //assert(friends.getFriendlist().size()==0);
        assertEquals(friends.getFriendlist().size(), 0);
        Friend aguy = new Friend("Test testly");
        friends.addfriend(aguy);
        //assert(friends.getFriendlist().size()==1);
        assertEquals(friends.getFriendlist().size(),1);
        //assert(friends.getFriendlist().get(0).getName()=="Test testly");
        assertEquals(friends.getFriendlist().get(0).getName(),"Test testly");

    }
    public void testRm(){
        FriendList friends = new FriendList();
        //assert(friends.getFriendlist().size()==0);
        assertEquals(friends.getFriendlist().size(),0);
        Friend aguy = new Friend("Test testly");
        Friend anotherguy = new Friend("something else");
        friends.addfriend(aguy);
        //assert(friends.getFriendlist().size()==1);
        assertEquals(friends.getFriendlist().size(),1);
        friends.removefirend(anotherguy);
        //assert(friends.getFriendlist().size()==1);
        assertEquals(friends.getFriendlist().size(),1);
        friends.removefirend(aguy);
        //assert(friends.getFriendlist().size()==0);
        assertEquals(friends.getFriendlist().size(),0);
    }

    public void testViewP(){
        FriendList friends = new FriendList();
        assert(friends.getFriendlist().size()==0);
        Friend aguy = new Friend("Test testly");
        aguy.setPhoneNumber((long)780780780);
        aguy.setLocation("University");
        aguy.setEmail("test@ualebrta.ca");
        friends.addfriend(aguy);
        //assert(aguy.getEmail()==friends.getFriendlist().get(0).getEmail());
        assertEquals(aguy.getEmail(), friends.getFriendlist().get(0).getEmail());
        //assert(aguy.getLocation()==friends.getFriendlist().get(0).getLocation());
        assertEquals(aguy.getLocation(), friends.getFriendlist().get(0).getLocation());

    }
}


