package cmput301exchange.exchange;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Created by hzhu6 on 10/9/15.
 */


public class Friendtest extends ApplicationTestCase<Application> {


    public Friendtest() {
        super(Application.class);
    }

    public void testAdd(){
        PersonList friends = new PersonList();
        //assert(friends.getFriendlist().size()==0);
        assertEquals(friends.getPersonList().size(), 0);
        Friend aguy = new Friend("Test testly");
        friends.addPerson(aguy);
        //assert(friends.getFriendlist().size()==1);
        assertEquals(friends.getPersonList().size(),1);
        //assert(friends.getFriendlist().get(0).getName()=="Test testly");
        assertEquals(friends.getPersonList().get(0).getName(),"Test testly");

    }
    public void testRm(){
        PersonList friends = new PersonList();
        //assert(friends.getFriendlist().size()==0);
        assertEquals(friends.getPersonList().size(),0);
        Friend aguy = new Friend("Test testly");
        Friend anotherguy = new Friend("something else");
        friends.addPerson(aguy);
        //assert(friends.getFriendlist().size()==1);
        assertEquals(friends.getPersonList().size(),1);
        friends.removePerson(anotherguy);
        //assert(friends.getFriendlist().size()==1);
        assertEquals(friends.getPersonList().size(),1);
        friends.removePerson(aguy);
        //assert(friends.getFriendlist().size()==0);
        assertEquals(friends.getPersonList().size(),0);
    }

    public void testViewP(){
        PersonList friends = new PersonList();
        assert(friends.getPersonList().size()==0);
        Friend aguy = new Friend("Test testly");
        aguy.setPhoneNumber("780780780");
        aguy.setLocation("University");
        aguy.setEmail("test@ualebrta.ca");
        friends.addPerson(aguy);
        //assert(aguy.getEmail()==friends.getFriendlist().get(0).getEmail());
        assertEquals(aguy.getEmail(), friends.getPersonList().get(0).getEmail());
        //assert(aguy.getLocation()==friends.getFriendlist().get(0).getLocation());
        assertEquals(aguy.getLocation(), friends.getPersonList().get(0).getLocation());

    }
}


