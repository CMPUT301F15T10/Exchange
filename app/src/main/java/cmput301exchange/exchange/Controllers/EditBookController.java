package cmput301exchange.exchange.Controllers;

import android.util.Log;

import cmput301exchange.exchange.Book;

/**
 * Created by touqir on 29/10/15.
 */
public class EditBookController {
    private Book myBook;

    public EditBookController(Book book) {
        myBook=book;
    }

    public void updateComment(String comment){
        myBook.updateComment(comment);
    }

    public String getComment(){
        return myBook.getComment();
    }
    public String getName(){
        return myBook.getName();
    }
    public String getType(){
        return myBook.getItemType();
    }
    public String getQuality(){
        Integer quality=myBook.getQuality();
        return quality.toString();
    }
    public String getQuantity(){
        Integer quantity=myBook.getQuantity();
        return quantity.toString();
    }

    public void updateName(String name){
        myBook.updateTitle(name);
    }
    public void updateType(String type){
        myBook.setItemType(type);
    }
    public void updateQuantity(String Quantity){
        Log.e("here",Quantity);
        Double Quantity_D=Double.parseDouble(Quantity);
        myBook.updateQuantity(Quantity_D.intValue());
    }
    public void updateQuality(String Quality){
        Double Quality_D=Double.parseDouble(Quality);
        myBook.setQuality(Quality_D.intValue());
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

    public void setBookCategory(String category){
        myBook.updateCategory(category);
    }
}
