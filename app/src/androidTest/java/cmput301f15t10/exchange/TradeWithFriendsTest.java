package cmput301f15t10.exchange;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Yishuo Wang on 2015/10/8.
 */

/*
  Functions that I need:
    - Inventory class
      - public void add(Book myBook)
          - Adds myBook to the list

    - Friend class
      - public void add(Person person)
        - adds one person to the friend list

      - public Boolean searchFriend(String name)
          - Go through the friends list to see if one friend with that specific name is contained in
            the friend list. The function return True if that name is found, otherwise return False.
          - Note that we can use user id instead

    - Book class
      - public Book(String bookName, int genreID, int condition, Boolean isListed)
        - constructor

    - Person class
      - public Person(String name, int ID, String userName, String email)
        - constructor

    - TradeManager class
      - public void viewCurrentTrade()
        - Display a list of current trades

      - public void viewPastTrade()
        - Display a list of past trades

    - TradeActivity class
      - Needs constructor

      - public ArrayList<Book> borrowerBookList
        - List of books that borrower wants to trade (selected from borrower's inventory)

      - public String bookName
        - Book that borrower chose from their friend's inventory

      - public void trade()
        - Borrower select one book bookName from their friend's inventory list, trade with
          borrower's own list of books in borrowerBookList, can be 0 or more, then perform trade
          activity

      - public void declineTrade()
        - Decline the trade activity

      - public void counterTrade()
        - Make a counter trade. Similar to making a trade (hope so).
        - Needs to access to the other person's inventory, probably consider adding more variables
          in this class that duplicate all information
        - Can be called inside the declineTrade() (if user chooses to make a counter trade)

      - public void editTrade()
        - Edit the trade details
        - Similar to Trade (?)

      - public void updateTrade()
        - Update the trade details
        - Similar to Trade (?)

      - public void saveTrade()
        - Saves the trade activity

      - public void notification()
        - Sends notification to user

      - public void deleteTrade()
        - Delete curerent trade

      - public void emailTrade()
        - Sends email to both borrower and owner
        - Opens an email app that pre-installed in android

 */
public class TradeWithFriendsTest extends ActivityInstrumentationTestCase2  {
    int i; // tempory variable for general usage
    Boolean temp;

    // People constructor
    String name;
    int ID;
    String userName;
    String email;

    // Book constructor
    String bookName;
    int genreID;
    int condition;
    Boolean isListed;

    Inventory myInventory;
    Book myBook;
    Person myPerson;
    Friend myFriend;
    Book friendBook;

    public TradeWithFriendsTest() {
        super(cmput301f15t10.exchange.MainActivity.class);
    }

    public void construction() {
        // *************************************************************************************
        bookName = "My Book";
        genreID = 1;
        condition = 2;
        isListed = Boolean.TRUE;

        // set up borrower
        myInventory = new Inventory();
        myBook = new Book(bookName, genreID, condition, isListed);
        myInventory.add(myBook); // might do it couple times

        // add one friend to the friend list
        name = "Friends Name";
        ID = 1;
        userName = "Friends User Name";
        email = "friends@ualberta.ca";
        myPerson = new Person(name, ID, userName, email);
        myFriend = new Friend();
        myFriend.add(myPerson);

        String myFriendname = "Firends Name";

        // search a friend in borrower's friends list
        temp = myFriend.searchFriend(myFriendname);
        assertTrue(temp); // assume that friend is in that firend list

        // set up friend
        Inventory firendInventory = new Inventory();
        bookName = "Friend's Book";
        genreID = 1;
        condition = 2;
        isListed = Boolean.TRUE;
        friendBook = new Book(bookName, genreID, condition, isListed);
        firendInventory.add(friendBook);
        // *************************************************************************************

        // if construction() fails then add the following code to each of the functions
        // construction() will fail. Add the following code to each of the function before the
        //   actural testing
        /*
        // *************************************************************************************
        // Construction
        int i; // tempory variable for general usage
        Boolean temp;

        // People constructor
        String name;
        int ID;
        String userName;
        String email;

        // Book constructor
        String bookName = "My Book";
        int genreID = 1;
        int condition = 2;
        Boolean isListed = Boolean.TRUE;

        // set up borrower
        Inventory myInventory = new Inventory();
        Book myBook = new Book(bookName, genreID, condition, isListed);
        myInventory.add(myBook); // might do it couple times

        // add one friend to the friend list
        name = "Friends Name";
        ID = 1;
        userName = "Friends User Name";
        email = "friends@ualberta.ca";
        Person myPerson = new Person(name, ID, userName, email);
        Friend myFriend = new Friend();
        myFriend.add(myPerson);

        String myFriendname = "Firends Name";

        // search a friend in borrower's friends list
        temp = myFriend.searchFriend(myFriendname);
        assertTrue(temp); // assume that friend is in that firend list

        // set up friend
        Inventory firendInventory = new Inventory();
        bookName = "Friend's Book";
        genreID = 1;
        condition = 2;
        isListed = Boolean.TRUE;
        Book friendBook = new Book(bookName, genreID, condition, isListed);
        firendInventory.add(friendBook);
        // *************************************************************************************
        */
    }

    /*
     US04.01.01
     As an borrower, I want to offer a trade with a friend. A trade will consist of an item
     from the owner's inventory and 0 or more items from the borrower's inventory.
    */
    public void tradeWithFriendTest() {
        int checker;
        construction();
        TradeActivity myTrade = new TradeActivity();
        checker = myTrade.trade();
        assertEquals(checker, 0);
        checker = myTrade.saveTrade();
        assertEquals(checker, 0);
    }


    /*
     US04.02.01
     As an owner, when a borrower offers a trade I will be notified of the trade.
    */
    public void notificationTest() {
        construction();
        int checker();
        TradeActivity myTrade = new TradeActivity();
        checker = myTrade.trade();
        assertEquals(checker, 0);
        checker = myTrade.notification();
        assertEquals(checker, 0);
    }


    /*
     US04.03.01
     As an owner, I can accept or decline a trade.
    */
    public void declineTradeTest() {
        construction();
        int checker;
        TradeActivity myTrade = new TradeActivity();
        checker = myTrade.trade();
        assertEquals(checker, 0);
        checker = myTrade.declineTrade();
        assertEquals(checker, 0);
        // do we need to save the trade history
    }


    /*
     US04.04.01
     As an owner, upon declining a trade I can offer a counter trade initialized with
     the items of the declined trade.
    */
    public void counterTradeTest() {
        construction();
        int checker;
        TradeActivity myTrade = new TradeActivity();
        checker = myTrade.trade();
        assertEquals(checker, 0);
        checker = myTrade.declineTrade();
        assertEquals(checker, 0);
        checker = myTrade.counterTrade(); // if counterTrade() is called inside the declineTrade(), then it
                                          // is not necessary to call counterTrade() here
        assertEquals(checker, 0);
    }


    /*
     US04.05.01
     As a borrower or owner, when composing a trade or counter-trade I can edit the trade.
    */
    public void editTradeTest() {
        construction();
        int checker;
        TradeActivity myTrade = new TradeActivity();
        checker = myTrade.trade();
        assertEquals(checker, 0);
        checker = myTrade.editTrade();
        assertEquals(checker, 0);
        checker = myTrade.updateTrade();
        assertEquals(checker, 0);
        checker = myTrade.saveTrade();
        assertEquals(checker, 0);
    }


    /*
     US04.06.01
     As a borrower, when composing a trade or counter-trade I can delete the trade
    */
    public void deleteTradeTest() {
        construction();
        int checker;
        TradeActivity myTrade = new TradeActivity();
        checker = myTrade.trade();
        assertEquals(checker, 0);
        checker = myTrade.deleteTrade();
        assertEquals(checker, 0);
    }


    /*
     US04.07.01
     As an owner, if I accept a trade both parties will be emailed all trade and item
     information relevant to the trade, as well owner comments for how to continue on
     with the trade.
    */
    public void emailTradeTest() {
        construction();
        int checker;
        TradeActivity myTrade = new TradeActivity();
        checker = myTrade.trade();
        assertEquals(checker, 0);
        checker = myTrade.emailTrade();
        assertEquals(checker, 0);
    }


    /*
     US04.08.01
     As an owner or borrower, I can browse all past and current trades involving me.
    */

    /*
     US04.09.01
     As an owner or borrower, I can browse all past and current trades involving me as
     either borrower or owner. I should look at my trades and trades offered to me.
    */
    public void viewHistoryTest() {
        construction();
        int checker;
        TradeManager tradeManager = new TradeManager();
        checker = tradeManager.viewCurrnetTrade();
        assertEquals(checker, 0);
        checker = tradeManager.viewPastTrade();
        assertEquals(checker, 0);
    }


}
