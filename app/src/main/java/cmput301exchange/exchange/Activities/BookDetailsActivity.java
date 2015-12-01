package cmput301exchange.exchange.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import cmput301exchange.exchange.Adapters.PhotoAdapter;
import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Controllers.PhotoController;
import cmput301exchange.exchange.Interfaces.Observer;
import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.Photos;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;
import cmput301exchange.exchange.Serializers.ElasticSearch;

public class BookDetailsActivity extends ActionBarActivity implements Observer {
    // this is a class of show details of a book
    //it use gson to get the data about a specific book
    //and change edit text of the view shown on the screen
    private Book book;
    private ArrayList<Bitmap> imageList = new ArrayList<>();
    private ArrayList<String> compressedImages = new ArrayList<>();
    private DataIO dataIO = new DataIO(this, AddBookActivity.class);
    private TextView name;
    private ImageButton image;
    private Spinner photoList;
    private int currentBitmapPos = 0;
    private ElasticSearch elasticSearch;
    private Photos photos;
    private ProgressDialog progressDialog;
    private ArrayAdapter<Bitmap> bmpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        elasticSearch = new ElasticSearch(this);
        elasticSearch.addObserver(this);

        String bookString = dataIO.loadFromFile("book.sav");
        File file = new File(getFilesDir(), "book.sav");
        file.delete();
        Gson gson= new Gson();
        book=gson.fromJson(bookString, Book.class);

        getPhotos();
        initTextView();

    }

    private void getPhotos(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("checkbox_preference", true)){
            progressDialog = ProgressDialog.show(this,"Loading Photos", "Just a moment...", true);
            elasticSearch.fetchPhotoFromServer(book.getPhotoID());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_download) {
            progressDialog = ProgressDialog.show(this,"Loading Photos", "Just a moment...", true);
            elasticSearch.fetchPhotoFromServer(book.getPhotoID());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initTextView(){
        photoList = (Spinner) findViewById(R.id.photoListView);
        bmpAdapter = new PhotoAdapter(this, imageList);
        photoList.setAdapter(bmpAdapter);

        image = (ImageButton) findViewById(R.id.imageButton);

        name = (TextView) findViewById(R.id.bookName);
        name.setText(book.getName());

        TextView author= (TextView) findViewById(R.id.bookAuthor);
        author.setText(book.getAuthor());

        TextView quality= (TextView) findViewById(R.id.bookQuality);
        quality.setText(String.valueOf(book.getQuality()));

        TextView quantity= (TextView) findViewById(R.id.bookQuantity);
        quantity.setText(String.valueOf(book.getQuantity()));

        TextView category= (TextView) findViewById(R.id.bookCategory);
        category.setText(book.getCategory());

        TextView comment= (TextView) findViewById(R.id.bookComments);
        comment.setText(book.getComment());

        CheckBox is_Sharable= (CheckBox) findViewById(R.id.shareable_checkBox);
        if (book.isShareable()) {
            is_Sharable.setChecked(Boolean.TRUE);
        }

        if (imageList.size() == 0){
            image.setImageDrawable(null);
        }
        else {
            image.setImageBitmap(imageList.get(0));
        }
        bmpAdapter.notifyDataSetChanged();

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

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    public void createBitmapArray(ArrayList<String> compressedImages){
        for (String i: compressedImages){
            addToImageList(i);
        }
    }

    public void addToImageList(String i){
        PhotoController photoController = new PhotoController();
        Bitmap bm = photoController.getBitmapFromString(i);
        imageList.add(bm);
    }

    private void selectImage() {

        final CharSequence[] options = { "View Bigger Photo", "Save Photo"};

        AlertDialog.Builder builder = new AlertDialog.Builder(BookDetailsActivity.this);

        builder.setTitle("Photo Options");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("View Bigger Photo")) {
                    PhotoController photoController = new PhotoController();
                    String photo = photoController.getStringFromBitmap(imageList.get(currentBitmapPos));
                    dataIO.saveInFile("detailed_photo.sav", photo);

                    Intent intent = new Intent(BookDetailsActivity.this, DetailedPhoto.class);
                    startActivityForResult(intent, 0);

                } else if (options[item].equals("Save Photo")) {
                    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                    String item_title = name.getText().toString();
                    File newFolder = new File(path + "/Exchange/" + item_title);
                    newFolder.mkdir();
                    File file = new File(path + "/Exchange/" + item_title, item_title + String.valueOf(currentBitmapPos) + ".jpg"); // the File to save to
                    MediaStore.Images.Media.insertImage(getContentResolver(), imageList.get(currentBitmapPos), file.getName(), file.getName());
                }

            }

        });

        builder.show();

    }

    public void GoBack(View view) {
        Intent goBack = new Intent();
        setResult(RESULT_OK, goBack);
        this.finish();
    }

    @Override
    public void update() {
        this.photos = elasticSearch.getPhotos();
        progressDialog.dismiss();
        compressedImages = photos.getCompressedPhotos();
        createBitmapArray(compressedImages);
        if (imageList.size() == 0){
            image.setImageDrawable(null);
        }
        else {
            image.setImageBitmap(imageList.get(0));
        }
        bmpAdapter.notifyDataSetChanged();
    }
}
