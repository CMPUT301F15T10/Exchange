package cmput301exchange.exchange;

import android.graphics.Picture;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

import cmput301exchange.exchange.Interfaces.Serializable;
import cmput301exchange.exchange.Interfaces.Shareable;
/**

 * Book is the object that makes up the inventory class
 * Book book = new Book(){
 *     book.updateTitle("Harry Potter");
 *     book.updateAuthor("J.K Rowling");
 *     ...
 *
 * }
 * @Author: Charles Laing
 * @version: 1.0.0
 */
public class Book implements Shareable, Comparable<Book>, Serializable {

    protected String bookName = ""; // Required
    private String genre = ""; //String to Contain Genre. A spinner is used to set this, so don't worry about the 10 requirement.
    private int genreID = 1; // Required. Let default be category 1.
    protected String author = ""; //String to contain the Author
    private String publisher = "";
    private ArrayList<byte[]> photos = new ArrayList<>(); //List of the pictures. Be sure to use the photo's add method to ensure that the photos are under the size req.
    private String ISBN = "";
    protected int quality = 5; // value between 1 and 5. Let default be 5
    protected int quantity = 1; // default quantity 5
    protected String category = ""; //Some ~10 values
    protected String comment = ""; //Arbitrary string
    protected boolean sharable = false; //Default items to false.
    private String itemType = "";
    private Long ID;
    private String availability = "";


    public void setAvailability(String availability) {
        availability = availability;
    }

    public ArrayList<byte[]> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<byte[]> photos) {
        this.photos = photos;
    }

    public Long getID() {
        return ID;
    }

    public Book() {
        initID();
        this.bookName = "";
        this.author = "";

    }

    public void initID() {
        Random generator = new Random(System.nanoTime());
        ID = generator.nextLong();
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    /**
     * Returns a Boolean representing if the book is shareable or not.
     */
    public boolean isShareable() {

        return this.sharable;

    }

    /**
     * Used to set the value of whether the book is shareable or not.
     *
     * @param setValue the value of shareable you want to set.
     */
    public void setShareable(boolean setValue) {

        this.sharable = setValue;
    }

    /**
     * Updates the title of the Book. Accepts a String Object.
     *
     * @param title The title you want to update
     */
    public void updateTitle(String title) {

        this.bookName = title;

    }

    /**
     * Updates the stored Author. Accepts a String Object.
     *
     * @param author The author you want to append to the book.
     */
    public void updateAuthor(String author) {

        this.author = author;
    }

    /**
     * Updates the Name of the Book. returns a String Object.
     */
    public String getName() {

        return this.bookName;
    }

    /**
     * Implementation of CompareTo, used for Shareable. This method compares two books together by Title.
     *
     * @param book the book you need to compare this to.
     */
    public int compareTo(Book book) {

        return bookName.compareTo(book.getName());
    } //Method is used for searching, and searchable, and comparable.

    /**
     * Sets the Quality of the Book on a scale of 1-5. Accepts an int.
     *
     * @param setCondition an integer value representing the quality of the item
     */
    public void updateQuality(int setCondition) {

        //We have to ensure that the value of the condition never exceeds a certain value
        if (setCondition > 5) {
            this.quality = 5;
        } else if (setCondition < 0) {
            this.quality = 0;
        } else {
            this.quality = setCondition;
        }
    } //Sets to a value between 1 and 5

    /**
     * Returns the Stored Quality value as an int
     */
    public int getQuality() {

        return this.quality;
    } //Returns the Quality Integer

    /**
     * Updates the Category of the book to an Arbitrary String.
     * The 10 Categories rule is imposed by the design of the Sorting, adding, and Browsing pages.
     *
     * @param updatedcat the new category to assign to the item
     */
    public void updateCategory(String updatedcat) {

        this.category = updatedcat;
    }

    /**
     * Returns a String of the Book's Stored Category.
     */
    public String getCategory() {

        return this.category;
    }

    /**
     * Updates the Quantity. Accepts and int.
     *
     * @param updateint Quantity that you want to append to the book
     */
    public void updateQuantity(int updateint) {

        this.quantity = updateint;
    }

    /**
     * Returns an int representing a quantity of books.
     */
    public int getQuantity() {

        return quantity;
    }

    /**
     * Returns a string representing the User comment on the Book.
     */
    public String getComment() {

        return comment;
    }

    /**
     * Accepts a string and updates the comment.
     *
     * @param comment the comment that you want to append to the book.
     */
    public void updateComment(String comment) {

        this.comment = comment;
    }

    /**
     * Returns an int of the GenreID
     */
    public int getGenreID() {

        return genreID;
    }

    /**
     * Returns a Book Name String.
     */
    public String getBookName() {

        return bookName;
    }

    /**
     * Returns a string representing the Author.
     */
    public String getAuthor() {

        return author;
    }

    /**
     * Returns a String representing the Publisher.
     */
    public String getPublisher() {

        return publisher;
    }

    /**
     * Used to accomodate the use of an ArrayAdapter. Returns a condensed, two line string
     * With the Author and the Title laid out like so
     * <p/>
     * Title: Harry Potter and the Philosopher's Stone
     * Author: J. K. Rowling
     */
    public String toString() {

        return "Title: " + this.getName() + ". " + "Author: " + this.getAuthor()+"\n"+"Quantity: "+String.valueOf(this.getQuantity()) + availability;
    }

    public boolean equals(Book another) {
        if (this.ID == another.getID()) {
            return true;
        }
        return false;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
