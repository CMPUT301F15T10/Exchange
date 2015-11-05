package cmput301f15t10.exchange;

import android.graphics.Picture;

import java.util.ArrayList;

/**
 * Created by Yishuo Wang on 2015/10/5.
 */
public class Book {
    public String bookName; // Required
    public String genre;
    public int genreID; // Required
    public String author;
    public String publisher;
    public ArrayList <Picture> covers;
    public String ISBN;
    public int condition; // Required
    public String description;
    public Boolean isListed; // Required, may set default to True
    private String comment;
    private String Type;
    private Double Quality;
    private Integer Quantity;

    public Book(String bookName, int genreID, int condition, Boolean isListed) {
        this.bookName = bookName;
        this.genreID = genreID;
        this.condition = condition;
        this.isListed = isListed;
    }

    public Book(){

    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment=comment;
    }

    public void saveItem(){
        // Logic for sav
    }

    public String getName() {
        return bookName;
    }

    public void setName(String name) {
        bookName = name;
    }

    public Double getQuality() {
        return Quality;
    }

    public String getQuality_String(){
        return Quality.toString();
    }

    public void setQuality(Double quality) {
        Quality = quality;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public String getQuantity_String(){
        return Quality.toString();
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
