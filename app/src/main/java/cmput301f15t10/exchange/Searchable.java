package cmput301f15t10.exchange;

/**
 * Created by bq on 10/31/2015.
 */
public interface Searchable {
    Inventory searchinventory(Friend afriend);
    /*
    {
    return afriend.getMyInventory();
    }
     */
    Inventory seaechinventorybycate(Friend afriend, int genreID);//creat a new inventory and put all the result in it and return it
    /*
   {
    Inventory result = new Inventory();
    int n = afriend.getMyInventory().getInventory().size();
    for(int i=0; i<n;i++){
     if(afriend.getMyInventory().getInventory().get(i).getGenreID()==genreID){
     result.getInventory().add(afriend.getMyInventory().getInventory().get(i));
     }
    }
    return result;
   }
    */
    Inventory seaechinventorybytext(Friend afriend, String query);//creat a new inventory and put all the result in it and return it
        /*
   {
    Inventory result = new Inventory();
    int n = afriend.getMyInventory().getInventory().size();
    for(int i=0; i<n;i++){
     if(afriend.getMyInventory().getInventory().get(i).getBookName().toLowerCase().contains(query.toLowerCase())){
     result.getInventory().add(afriend.getMyInventory().getInventory().get(i));
     continue
     }

     if(afriend.getMyInventory().getInventory().get(i).getAuthor().toLowerCase().contains(query.toLowerCase())){
     result.getInventory().add(afriend.getMyInventory().getInventory().get(i));
     continue
     }

     if(afriend.getMyInventory().getInventory().get(i).getPublisher().toLowerCase().contains(query.toLowerCase())){
     result.getInventory().add(afriend.getMyInventory().getInventory().get(i));
     continue
     }

     if(afriend.getMyInventory().getInventory().get(i).getDescription().toLowerCase().contains(query.toLowerCase())){
     result.getInventory().add(afriend.getMyInventory().getInventory().get(i));
     continue
     }







    }
    return result;
   }
    */

}
