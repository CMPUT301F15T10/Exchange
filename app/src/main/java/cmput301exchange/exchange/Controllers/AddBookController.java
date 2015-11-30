package cmput301exchange.exchange.Controllers;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Interfaces.Observable;
import cmput301exchange.exchange.Interfaces.Observer;
import cmput301exchange.exchange.R;

/**
 * Created by Charles on 11/27/2015.
 */
public class AddBookController implements Observable{
    ArrayList<Observer> observerList = new ArrayList<>();
    private Context context;
    private View view;


    private ImageButton image;
    private EditText name;
    private EditText author;
    private EditText quality;
    private EditText quantity;
    private EditText comments;
    private Spinner photoList;

    public void AddBookController(Context context, View view){
        this.context = context;
        this.view = view;
    }

    public void initializeViews(){
        photoList = (Spinner) view.findViewById(R.id.photoListView);
    }

    class ProcessThread extends Thread{
        private Bitmap bitmap;

        public ProcessThread(Bitmap bitmap){
            this.bitmap = bitmap;
        }

        @Override
        public void run(){

        }
    }
//    public void add(View view){
//
//        Book book = new Book();
//
//        // set quality and quantity to 0 if nothing entered
//        if (quality.getText().toString().isEmpty()) {
//            quality.setText("0");
//        }
//        if (quantity.getText().toString().isEmpty()) {
//            quantity.setText("0");
//        }
//
//        String bookName = name.getText().toString(); //Fetch the book title from the document.
//        String bookAuthor = author.getText().toString(); //Fetch the Author from the document
//        String bookComments = comments.getText().toString();
//        Integer bookQuantity = Integer.parseInt(quantity.getText().toString());
//        Integer bookQuality = Integer.parseInt(quality.getText().toString());
//
//
//        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.shareable_checkBox);
//        if (checkBox.isChecked()) {
//            book.setShareable(Boolean.TRUE);
//        }
//        else {
//            book.setShareable(Boolean.FALSE);
//        }
//
//        book.updateTitle(bookName);
//        book.updateAuthor(bookAuthor);
//        book.updateQuantity(bookQuantity);
//        book.updateQuality(bookQuality);
//        book.updateCategory(category);
//        book.updateComment(bookComments);
//
//        book.setPhotos(compressedImages);
//
//        inventory.add(book);
//        this.finishAdd();
//    }




















    // Below are the Observer Methods
    @Override
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObserver(Observer observer) {
        observer.update();
    }

    @Override
    public void notifyAllObserver() {
        for(Observer i : observerList){
            i.update();
        }
    }


}
