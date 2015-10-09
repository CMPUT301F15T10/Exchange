package cmput301f15t10.exchange;

/**
 * Created by bq on 10/8/2015.
 */

public class BrowseTest {
    public void testInstantiate(){
        try {
            Inventory inventory = new Inventory();
        }catch (Exception e){
            throw new RuntimeException;
        }
    }

    //test case for a view
    public void testBrowse(){
        Inventory inventory= new Inventory();
        inventory.setInventory();
        try{
            inventory.showInventory()
        }catchcatch (Exception e){
            throw new RuntimeException;
        }
    }


    //test if is browed by one Category
    public void testCategory{
        Inventory inventory= new Inventory();
        inventory.setInventory();
        inventory.searchCategoty(int X);
          for (int i;i<inventory.size(); i++){
              if (inventory.get(i).getCategory()!=x){
                  throw new RuntimeException();
              }
          }
    }



    //test if is browsed by a text
    public void testCategory{
        Inventory inventory= new Inventory();
        inventory.setInventory();
        inventory.searchtext(string //a text);
        for (int i;i<inventory.size(); i++){
            if (//should discuss how to match the text){
                throw new RuntimeException();
            }
        }
    }

   

}