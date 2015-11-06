package cmput301exchange.exchange;

/**
 * Created by bq on 11/1/15.
 */
public class BrowseInventories {
    Inventory searchInventory(Friend afriend){
        return afriend.getMyInventory();
    }

    Inventory searchByCategory(Friend afriend, int genreID){//creat a new inventory and put all the result in it and return it
        Inventory result = new Inventory();
        int n = afriend.getMyInventory().getInventoryList().size();
        for (int i = 0; i < n; i++) {
            if (afriend.getMyInventory().getInventoryList().get(i).getGenreID() == genreID) {
                result.getInventoryList().add(afriend.getMyInventory().getInventoryList().get(i));
            }
        }
        return result;
    }

    Inventory searchByText(Friend afriend, String query){//creat a new inventory and put all the result in it and return it
        Inventory result = new Inventory();
        int n = afriend.getMyInventory().getInventoryList().size();
        for (int i = 0; i < n; i++) {
            if (afriend.getMyInventory().getInventoryList().get(i).getBookName().toLowerCase().contains(query.toLowerCase())) {
                result.getInventoryList().add(afriend.getMyInventory().getInventoryList().get(i));
                continue;
            }

            if (afriend.getMyInventory().getInventoryList().get(i).getAuthor().toLowerCase().contains(query.toLowerCase())) {
                result.getInventoryList().add(afriend.getMyInventory().getInventoryList().get(i));
                continue;
            }

            if (afriend.getMyInventory().getInventoryList().get(i).getPublisher().toLowerCase().contains(query.toLowerCase())) {
                result.getInventoryList().add(afriend.getMyInventory().getInventoryList().get(i));
                continue;
            }

            if (afriend.getMyInventory().getInventoryList().get(i).getComment().toLowerCase().contains(query.toLowerCase())) {
                result.getInventoryList().add(afriend.getMyInventory().getInventoryList().get(i));
                continue;
            }

        }
        return result;
    }
}