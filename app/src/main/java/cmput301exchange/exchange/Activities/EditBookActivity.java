package cmput301exchange.exchange.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import cmput301exchange.exchange.Adapters.PhotoAdapter;
import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Controllers.AddBookController;
import cmput301exchange.exchange.Controllers.EditBookController;
import cmput301exchange.exchange.Interfaces.Observer;
import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;

public class EditBookActivity extends ActionBarActivity implements Observer {


    private EditText name, author, quality, quantity, comments;
    private ImageButton image;
    private String category;
    private Inventory inventory;
    private Book cloneBook;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Spinner photoList;

    private ArrayList<byte[]> compressedImages = new ArrayList<>();
    private ArrayList<Bitmap> imageList = new ArrayList<>();
    private ArrayAdapter<Bitmap> bmpAdapter;
    private int currentBitmapPos;

    private DataIO dataIO = new DataIO(this, AddBookActivity.class);

    private EditBookController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        controller = new EditBookController(this, this);
        controller.getElasticSearch().addObserver(this);
        controller.Setup();
    }

    public void done(View view){
        controller.done(view);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        controller.ActivityResult(requestCode,resultCode,data);
    }



    @Override
    public void onStop(){
        //We want this function to be called whenever the activity is killed to prevent losing data
        //I pulled the gson writing from the add function, and generalized it.

        Gson gson= new Gson(); //Create a new Gson Instance
        String json=gson.toJson(inventory); //Write the existing inventory data to Json

        Intent added = new Intent().putExtra("Inventory",json); //Send it back to the inventory activity
        setResult(RESULT_OK, added);

        super.onStop(); //Required for the onStop Function to work
    }

    @Override
    public void onBackPressed(){
        //This method is called when the back button is pressed, regardless of what data is entered.
        //It basically just stops the data from being entered into the inventory, and quits activity
        controller.finishAdd();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_book, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_download){
            controller.getElasticSearch().addObserver(this);
            controller.downloadPhotos();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void update() {
        controller.update();

    }
}

