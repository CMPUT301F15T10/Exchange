package cmput301exchange.exchange.Activities;

import android.app.AlertDialog;
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
import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;

public class AddBookActivity extends ActionBarActivity {


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

    private AddBookController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        controller = new AddBookController(this, this);
        controller.Setup();
    }

    public void add(View view){
        controller.add(view);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

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
                    byte[] byteArray = stream.toByteArray();
                    compressedImages.add(byteArray);

                    controller.addToImageList(byteArray);
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

                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

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
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
