package cmput301exchange.exchange.Controllers;

import android.util.Log;

import cmput301exchange.exchange.Item;

/**
 * Created by touqir on 29/10/15.
 */
public class EditItemController {
    private Item myItem; //Not sure whether this Item or Book class

    public EditItemController(Item item) {
        myItem=item;
    }

    public void changeComment(String comment){
        myItem.setComment(comment);
//        myItem.saveComment();
    }

    public void updateName(String name){
        myItem.setName(name);
    }
    public void updateType(String type){
        myItem.setType(type);
    }
    public void updateQuantity(String Quantity){
        Log.e("here",Quantity);
        Double Quantity_D=Double.parseDouble(Quantity);
        myItem.setQuantity(Quantity_D.intValue());
    }
    public void updateQuality(String Quality){
        Double Quality_D=Double.parseDouble(Quality);
        Quality_D=Quality_D*10;
        Long Quality_L=Math.round(Quality_D);
        Quality_D=Quality_L.doubleValue()/10;
        myItem.setQuality(Quality_D);
    }
}
