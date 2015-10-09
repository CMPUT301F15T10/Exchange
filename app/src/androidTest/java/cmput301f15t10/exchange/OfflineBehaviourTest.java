import junit.framework.*;

	// UserAccess class contains information regarding what services should be available to user once user is logged in based on whether there is internet connectivity or not
	// UserAccess should have a field called Mode. If Mode=1 then it suggests that there is internet connectivity. If it is 2 then no internet connectivity.

	// InventoryManager class contains methods for uploading local data to web server and for downloading back server data. Data can be trades, inventory information like item listings, etc.
	// CacheData class is responsible for any kind of cache management required for the App including caching visited friends' inventories.

	// I havent written any of these above classes yet so I dont have any concrete details about their methods yet.

public class OffineBehaviourTest{


	private InternetConnectivity myInternet= new InternetConnectivity();
	private UserAccess myUserAccess=new UserAccess();
    private	Inventory myInventory= new Inventory();
    private	Inventory myTestInventory= new Inventory();
	private	InventoryManager inventoryManager=new InventoryManager();
	private Trade myTrade= new Trade();
	private Friend myFriend= new Friend();
	private TradeManager myTradeManager= new TradeManager();
	private TradeQueue myTradeQueue= new TradeQueue();
	private PeopleInventory peopleInventory = new PeopleInventory();
	private InventoryManager inventoryManager = new InventoryManager();
	private CacheManager myCacheManager = new CacheManager();


	public void testItemAddOnline(){
		// Use Case: "Add Inventory Item offline"

		assertTrue(myInternet.disableInternet()); //Right now this method should turn off wifi. Returns true on Success.
		myUserAccess.updateConnectivityMode(); // Sets MyUserAccess.Mode=2 if the method finds out from android API calls that no internet connectivity is there.
		assertEquals(myUserAccess.getMode(),2); //checks whether MyUserAccess.Mode is set to 2.

		Book harryPotterBook= new Book();
		harryPotterBook.setName("Harry Potter");
		harryPotterBook.setGenreID(1);
		harryPotterBook.isListed(true);
		myInventory.addItem(harryPotterBook);
		myInventory.setUserAccessObject(myUserAccess);

		inventoryManager.setUserAccessObject(myUserAccess); //Any method of InventoryManager that needs internet connectivity for its work will throw a runtime exception if inventoryManager.Mode=2.

		// Testing if updateOnlineInventory method throws an exception. It should throw an exception as UserAccess.Mode=2.
		bool success;
		String connectivityExceptionMessage='User Access rule Violated! Wont access Internet with UserAccess.Mode=2';

		try {
			success=inventoryManager.updateOnlineInventory(myInventory); //updateOnlineInventory uploads any local changes into data server. Method returns true on success at uploading the inventory.
		} catch (RuntimeException e){
			assertEquals(e.getMessage(), connectivityExceptionMessage);
			success=false;
		}

		assertTrue(myInternet.enableInternet()); //enableInternet method activates wifi and waits until phone gets connected to a network. Returns true on success.
		myUserAccess.updateConnectivityMode();
		assertEquals(myUserAccess.getMode(),1); //myUserAccess.Mode=1 suggests valid internet connection

		try {
			success=inventoryManager.updateMyOnlineInventory(myInventory); //updateMyOnlineInventory uploads any local changes into data server. Method returns true on success at uploading the inventory.
		} catch (RuntimeException e){
			assertEquals(e.getMessage(), connectivityExceptionMessage);
			success=false;
		}

		assertTrue(success);

		try {
			myTestInventory=inventoryManager.getMyOnlineInventory(); //getMyOnlineInventory uploads any local changes into data server. Method returns Inventory object on success at receiving the inventory, else returns null.
		} catch (RuntimeException e){
			assertEquals(e.getMessage(), connectivityExceptionMessage);
		}

		assertEquals(myTestInventory, null);
		assertEquals(myInventory,myTestInventory);
	}


	public void testItemDeleteOnline(){

	 // Use Case: Delete Inventory Item Offline

		testItemUpload();
		myInventory.deleteItem("Harry Potter") //The method deletes item based on item name.
		bool success;
		String connectivityExceptionMessage='User Access rule Violated! Wont access Internet with UserAccess.Mode=2';

		try {
			success=inventoryManager.updateOnlineInventory(myInventory); //updateOnlineInventory uploads any local changes into data server. Method returns true on success at uploading the inventory.
		} catch (RuntimeException e){
			assertEquals(e.getMessage(), connectivityExceptionMessage);
			success=false;
		}

		assertTrue(success);

		try {
			myTestInventory=inventoryManager.getMyOnlineInventory(); //getMyOnlineInventory uploads any local changes into data server. Method returns Inventory object on success at receiving the inventory, else returns null.
		} catch (RuntimeException e){
			assertEquals(e.getMessage(), connectivityExceptionMessage);
		}

		assertEquals(myTestInventory, null);
		assertEquals(myInventory,myTestInventory);

	}

	public static void testTradeUpload(){

		//Use Case: View Friend's Inventory Offline
		Book harryPotterBook= new Book();
		harryPotterBook.setName("Harry Potter");
		harryPotterBook.setGenreID(1);
		harryPotterBook.isListed(true);

		Book lordOfTheRingsBook= new Book();
		harryPotterBook.setName("Lord of the Rings");
		harryPotterBook.setGenreID(1);

		myTrade.addMyItem(harryPotterBook)
		myTrade.addParticipantItem(lordOfTheRingsBook);

		myFriend.setName("Mat");
		myTrade.addParticipant(myFriend);
		myTrade.setID(1);
		myTradeManager.addTradeAsBorrower(myTrade);

		myInternet.disableInternet()
		myTradeManager.setUserAccessObject(myUserAccess);
		myTradeManager.sendTradeRequest(1)// sendTradeRequest takes in tradeID as input and then searches a list of "Trades as Burrower" from where it chooses that corresponding trade and adds to a TradeQueue object.
		assertFalse(myTradeManager.carryTransaction()) // If there is internet connectivity then it pulls trade objects from online and pushes trade objects from uploadTradeQueue. On success, return true and on failure returns false.
		myTradeQueue=myTradeManager.getTradeUploadQueue()//
		Trade mytestTrade=myTradeQueue.getItem(1) //1 refers to the first item in the queue which should be myTrade as sendTradeRequest first adds a trade into. If the queue is empy, then it returns null;
		assertNotNull(mytestTrade);
		assertEquals(mytestTrade, myTrade);

		myInternet.enableInternet();
		assertTrue(myTradeManager.carryTransaction()) // If there is internet connectivity then it pulls trade objects from online and pushes trade objects online from uploadTradeQueue. On success, return true and on failure returns false.
		myTradeQueue=myTradeManager.getTradeUploadQueue()//
		assertNull(myTradeQueue.getItem(1));

	}

	public static void testFriendInventoryCache(){

		//Use Case: Trade Offline
		String connectivityExceptionMessage='User Access rule Violated! Wont access Internet with UserAccess.Mode=2';
		inventoryManager.setUserAccessObject(myUserAccess);
		myInternet.enableInternet();
		String friendName="mat"
		PeopleInventory mat_Inventory=inventoryManager.getInventory(friendName) // method inputs friend/people name and checks local cache for this entry and if not found and if it has internet access defined by UserAcess, then it fetches inventory from server and returns it. If name doesnt match any friend/people entry in server, then it returns null. For now lets assume that there is an inventory of "mat" in server.

		myCacheManager.saveInventory(mat_Inventory);
		myInternet.disableInternet();
		assertEquals(myUserAccess.getMode(),2);
		PeopleInventory testInventory=inventoryManager.getInventory();
		assertNotNull(testInventory);
		assertEquals(mat_Inventory, testInventory);
	}


}
