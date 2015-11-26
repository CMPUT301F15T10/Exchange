package cmput301exchange.exchange.Mocks;

import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.PersonList;

/**
 * Created by touqir on 22/11/15.
 */
public class MockPersonList {
    public PersonList personList, friendList;

    public MockPersonList(){
        personList=new PersonList();
        friendList=new PersonList();

        Person A=new Person("Harry1");
        A.setName("Harry");
        personList.addPerson(A);
        friendList.addPerson(A);

        Person B=new Person("James1");
        B.setName("James");
        personList.addPerson(B);
        friendList.addPerson(B);

        Person C=new Person("Lily1");
        C.setName("Lily");
        personList.addPerson(C);

        Person D=new Person("Dumbledore1");
        D.setName("Dumbledore");
        personList.addPerson(D);
    }
}
