package cmput301f15t10.exchange.Controllers;

import android.util.Log;

import cmput301f15t10.exchange.Book;

/**
 * Created by touqir on 29/10/15.
 */
public class EditBookController {
    private Book myBook; //Not sure whether this Book or Book class

    public EditBookController(Book Book) {
        myBook=Book;
    }

    public void changeComment(String comment){
        myBook.setComment(comment);
//        myBook.saveComment();
    }

    public void updateName(String name){
        myBook.setName(name);
    }
    public void updateType(String type){
        myBook.setType(type);
    }
    public void updateQuantity(String Quantity){
        Log.e("here",Quantity);
        Double Quantity_D=Double.parseDouble(Quantity);
        myBook.setQuantity(Quantity_D.intValue());
    }
    public void updateQuality(String Quality){
        Double Quality_D=Double.parseDouble(Quality);
        if (Quality_D>5){

        }
        Quality_D=Quality_D*10;
        Long Quality_L=Math.round(Quality_D);
        Quality_D=Quality_L.doubleValue()/10;
        myBook.setQuality(Quality_D);
    }
}
