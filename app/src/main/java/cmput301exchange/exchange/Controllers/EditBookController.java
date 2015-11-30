//package cmput301exchange.exchange.Controllers;
//
//import android.util.Log;
//
//import cmput301exchange.exchange.Book;
//
///**
// * Created by touqir on 29/10/15.
// */
//public class EditBookController {
//    private Book myBook;
//
//    public EditBookController(Book book) {
//        myBook=book;
//    }
//
//    public void updateComment(String comment){
//        myBook.updateComment(comment);
//    }
//
//    public String getComment(){
//        return myBook.getComment();
//    }
//    public String getName(){
//        return myBook.getName();
//    }
//    public String getType(){
//        return myBook.getItemType();
//    }
//    public String getQuality(){
//        Integer quality=myBook.getQuality();
//        return quality.toString();
//    }
//    public String getQuantity(){
//        Integer quantity=myBook.getQuantity();
//        return quantity.toString();
//    }
//    public String getCategory(){
//        return myBook.getCategory();
//    }
//
//    public void updateName(String name){
//        myBook.updateTitle(name);
//    }
//    public void updateType(String type){
//        myBook.setItemType(type);
//    }
//    public void updateQuantity(String Quantity){
//        Log.e("here",Quantity);
//        Double Quantity_D=Double.parseDouble(Quantity);
//        myBook.updateQuantity(Quantity_D.intValue());
//    }
//    public void updateQuality(String Quality){
//        Double Quality_D=Double.parseDouble(Quality);
//        myBook.updateQuality(Quality_D.intValue());
//    }
//
//    public void setBook(Book book){
//        myBook=book;
//    }
//
//    public Book getBook(){
//        return myBook;
//    }
//
//    //TODO
//    public void save(){
//        //Will include functionality for saving mybook to storage.
//    }
//
//    //TODO
//    public void reloadData(){
//        //Will implement loading the same book from model.
//    }
//
//    public void updateCategory(String category){
//        myBook.updateCategory(category);
//    }
//}

package cmput301exchange.exchange.Controllers;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import cmput301exchange.exchange.Activities.DetailedPhoto;
import cmput301exchange.exchange.Adapters.PhotoAdapter;
import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Interfaces.Observable;
import cmput301exchange.exchange.Interfaces.Observer;
import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;

/**
 * Created by Charles on 11/27/2015.
 */
public class EditBookController implements Observable{
    ArrayList<Observer> observerList = new ArrayList<>();
    private Context context;
    private Activity activity;

    private String category;
    private Inventory inventory;
    private Book editBook;


    private ImageButton image;
    private EditText name;
    private EditText author;
    private EditText quality;
    private EditText quantity;
    private EditText comments;
    private Spinner photoList;
    private Spinner CategoriesSpinner;
    private CheckBox checkBox;

    private int currentBitmapPos;
    private ArrayList<String> compressedImages = new ArrayList<>();
    private ArrayList<Bitmap> imageList = new ArrayList<>();
    private ArrayAdapter<Bitmap> bmpAdapter;
    private ArrayAdapter<CharSequence> adapter;

    private DataIO dataIO;

    public EditBookController(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
        dataIO = new DataIO(context, ModelEnvironment.class);
    }

    public void Setup(){
        initializeViews();
        initializeSpinner();
        setupPhotoSpinnerOnClick();
        setupSpinnerOnClick();
        setupPhotoOnClick();
        Gson gson= new Gson();

        String inventoryString = dataIO.loadFromFile("inventory.sav");
        inventory = gson.fromJson(inventoryString, Inventory.class);
        File file1 = new File(context.getFilesDir(), "inventory.sav");
        file1.delete();

        String bookString = dataIO.loadFromFile("book.sav");
        editBook = gson.fromJson(bookString, Book.class);
        File file2 = new File(context.getFilesDir(), "book.sav");
        file2.delete();

        compressedImages = editBook.getPhotos();
        createBitmapArray(compressedImages);
        name.setText(editBook.getName());
        author.setText(editBook.getAuthor());
        quality.setText(String.valueOf(editBook.getQuality()));
        quantity.setText(String.valueOf(editBook.getQuantity()));
        comments.setText(editBook.getComment());
        CheckBox is_Sharable= (CheckBox) activity.findViewById(R.id.shareable_checkBox);
        if (editBook.isShareable()) {
            is_Sharable.setChecked(Boolean.TRUE);
        }
        CategoriesSpinner.setSelection(this.adapter.getPosition(editBook.getCategory()));
        if (imageList.size() == 0){
            image.setImageDrawable(null);
        }
        else {
            image.setImageBitmap(imageList.get(0));
        }
        bmpAdapter.notifyDataSetChanged();

    }

    public void createBitmapArray(ArrayList<String> compressedImages){
        for (String i: compressedImages){
            addToImageList(i);
        }
    }

    public void initializeViews(){
        this.photoList = (Spinner) activity.findViewById(R.id.photoListView);
        this.bmpAdapter = new PhotoAdapter(activity, imageList);
        this.photoList.setAdapter(bmpAdapter);
        this.photoList = (Spinner) activity.findViewById(R.id.photoListView);
        this.name = (EditText) activity.findViewById(R.id.editName);
        this.author = (EditText) activity.findViewById(R.id.editAuthor);
        this.quality = (EditText) activity.findViewById(R.id.editQuality);
        this.quantity = (EditText) activity.findViewById(R.id.editQuantity);
        this.comments = (EditText) activity.findViewById(R.id.editComment);
        this.image = (ImageButton) activity.findViewById(R.id.imageButton);
        this.checkBox = (CheckBox) activity.findViewById(R.id.shareable_checkBox);
    }

    public void initializeSpinner(){
        this.CategoriesSpinner = (Spinner) activity.findViewById(R.id.categories_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        this.adapter = ArrayAdapter.createFromResource(context,
                R.array.categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        this.adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        this.CategoriesSpinner.setAdapter(this.adapter);
    }

    public void setupSpinnerOnClick(){
        CategoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //on selecting a spinner item
                category = parent.getItemAtPosition(position).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setupPhotoSpinnerOnClick(){
        photoList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //on selecting a spinner item
                currentBitmapPos = position;
                image.setImageBitmap(imageList.get(position));

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setupPhotoOnClick(){
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }




    public void add(View view){

        Book book = new Book();

        // set quality and quantity to 0 if nothing entered
        if (quality.getText().toString().isEmpty()) {
            quality.setText("0");
        }
        if (quantity.getText().toString().isEmpty()) {
            quantity.setText("0");
        }

        String bookName = name.getText().toString(); //Fetch the book title from the document.
        String bookAuthor = author.getText().toString(); //Fetch the Author from the document
        String bookComments = comments.getText().toString();
        Integer bookQuantity = Integer.parseInt(quantity.getText().toString());
        Integer bookQuality = Integer.parseInt(quality.getText().toString());


        //checkBox = (CheckBox) view.findViewById(R.id.shareable_checkBox);
        if (checkBox.isChecked()) {
            book.setShareable(Boolean.TRUE);
        }
        else {
            book.setShareable(Boolean.FALSE);
        }

        book.updateTitle(bookName);
        book.updateAuthor(bookAuthor);
        book.updateQuantity(bookQuantity);
        book.updateQuality(bookQuality);
        book.updateCategory(category);
        book.updateComment(bookComments);

        book.setPhotos(compressedImages);

        inventory.add(book);
        this.finishAdd();
    }

    public void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery"};
        final CharSequence[] more_options = { "View Bigger Photo", "Take Photo", "Choose from Gallery", "Save Photo", "Delete Photo"};

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Photo Options");

        if (null == image.getDrawable()) {
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (options[item].equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        activity.startActivityForResult(intent, 1);
                    } else if (options[item].equals("Choose from Gallery")) {

                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        activity.startActivityForResult(intent, 2);

                    }
                }

            });
        }
        else {
            builder.setItems(more_options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (more_options[item].equals("View Bigger Photo")) {
                        PhotoController photoController = new PhotoController();
                        String photo = photoController.getStringFromBitmap(imageList.get(currentBitmapPos)); //Write the existing inventory data to Json
                        dataIO.saveInFile("detailed_photo.sav", photo);

                        Intent intent = new Intent(activity, DetailedPhoto.class);
                        activity.startActivityForResult(intent, 0);

                    } else if (more_options[item].equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        activity.startActivityForResult(intent, 1);

                    } else if (more_options[item].equals("Choose from Gallery")) {

                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        activity.startActivityForResult(intent, 2);

                    } else if (more_options[item].equals("Save Photo")) {
                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                        String item_title = name.getText().toString();
                        File newFolder = new File(path + "/Exchange/" + item_title);
                        newFolder.mkdir();
                        File file = new File(path + "/Exchange/" + item_title, item_title + String.valueOf(currentBitmapPos) + ".jpg"); // the File to save to
                        MediaStore.Images.Media.insertImage(activity.getContentResolver(), imageList.get(currentBitmapPos), file.getName(), file.getName());

                    } else if (more_options[item].equals("Delete Photo")) {
                        imageList.remove(currentBitmapPos);
                        compressedImages.remove(currentBitmapPos);
                        if (imageList.size() == 0){
                            image.setImageDrawable(null);
                        }
                        else {
                            image.setImageBitmap(imageList.get(0));
                        }
                        bmpAdapter.notifyDataSetChanged();
                    }

                }

            });

        }

        builder.show();

    }

    public void ActivityResult(int requestCode, int resultCode, Intent data){

        if (resultCode == activity.RESULT_OK) {

            if (requestCode == 1) {
                //h=0;
                File f = new File(Environment.getExternalStorageDirectory().toString());

                for (File temp : f.listFiles()) {

                    if (temp.getName().equals("temp.jpg")) {

                        f = temp;
                        File photo = new File(Environment.getExternalStorageDirectory(), "temp.jpg");

                        break;

                    }

                }

                try {

                    Bitmap bitmap;

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();


                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.2), (int)(bitmap.getHeight()*0.2), true);


                    image.setImageBitmap(bitmap);

                    // new stuff hope it doesn't break
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    resized.compress(Bitmap.CompressFormat.JPEG, 30, stream);
                    PhotoController photoController = new PhotoController();
                    compressedImages.add(photoController.getStringFromByte(stream.toByteArray()));

                    this.addToImageList(stream.toString());
                    bmpAdapter.notifyDataSetChanged();

                    stream.flush();
                    stream.close();

                    f.delete();

                } catch (Exception e) {

                    e.printStackTrace();

                }

            } else if (requestCode == 2) {


                Uri selectedImage = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = activity.getContentResolver().query(selectedImage, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                String picturePath = c.getString(columnIndex);

                c.close();

                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                Log.w("path image from gallery", picturePath + "");

                image.setImageBitmap(thumbnail);
            }

        }
    }


    public void addToImageList(String photo){
        PhotoController photoController = new PhotoController();
        Bitmap bm = photoController.getBitmapFromString(photo); //use android built-in functions
        imageList.add(bm);
    }

    public void finishAdd(){
        String json = inventory.toJson(); //Write the existing inventory data to Json
        dataIO.saveInFile("book.sav", json);

        Intent added = new Intent().putExtra("Inventory", "book.sav"); //Send it back to the inventory activity
        activity.setResult(activity.RESULT_OK, added);

        activity.finish();

    }





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

