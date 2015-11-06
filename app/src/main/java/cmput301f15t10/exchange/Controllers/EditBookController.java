package cmput301f15t10.exchange.Controllers;

import android.util.Log;

import cmput301f15t10.exchange.Book;

/**
 * Created by touqir on 29/10/15.
 */
public class EditBookController {
    private Book myBook;

    public EditBookController(Book book) {
        myBook=book;
    }

    public void changeComment(String comment){
        myBook.setComment(comment);
    }

    public String getComment(){
        return myBook.getComment();
    }
    public String getName(){
        return myBook.getName();
    }
    public String getType(){
        return myBook.getType();
    }
    public String getQuality(){
        return myBook.getQuality_String();
    }
    public String getQuantity(){
        return myBook.getQuantity_String();
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

    public void setBook(Book book){
        myBook=book;
    }

    public Book getBook(){
        return myBook;
    }

    //TODO
    public void save(){
        //Will include functionality for saving mybook to storage.
    }

    //TODO
    public void reloadData(){
        //Will implement loading the same book from model.
    }
}
