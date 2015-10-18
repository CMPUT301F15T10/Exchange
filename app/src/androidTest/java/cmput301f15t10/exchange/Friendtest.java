package cmput301f15t10.exchange;

/**
 * Created by hzhu6 on 10/9/15.
 */
public class Friendtest {

    public void testInstantiate(){
        try {
            Friends friends = new Friends();
        }catch (Exception e){
            throw new RuntimeException;
        }
    }

    public void testAdd(){
        Friends friends = new Friends();
        assert(friends.size()==0);
        Person aguy = new Person();
        aguy.setName=("Test testly");
        friends.add("Test testly");
        assert(friends.size()==1);

        assert(friends.getlist().get(0).getName()=="Test testly");
 
    }
    public void testRm(){
        Friends friends = new Friends();
        assert(friends.size()==0);
        Person aguy = new Person();
        aguy.setName=("Test testly");
        friends.add("Test testly");
        assert(friends.size()==1);
        friends.remove("something else");
        assert(friends.size()==1);
        friends.remove("Test testly");
        assert(friends.size()==0);
    }

    public void testViewP(){
        Friends friends = new Friends();
        assert(friends.size()==0);
        Person aguy = new Person();
        aguy.setName=("Test testly");
        aguy.setContactI = "7807807800";
        aguy.setLocation = "university"
        friends.add("Test testly");
        assert(friends.ViewP(aguy.getname())==aguy.getInfo());
    }
}

