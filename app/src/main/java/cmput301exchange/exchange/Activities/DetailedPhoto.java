package cmput301exchange.exchange.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.io.File;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;

public class DetailedPhoto extends ActionBarActivity {
    Bitmap detailedPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_photo);

        DataIO dataIO = new DataIO(this, DetailedPhoto.class);
        String photoString = dataIO.loadFromFile("detailed_photo.sav");
        detailedPhoto = getBitmapFromString(photoString);
        File file = new File(getFilesDir(), "detailed_photo.sav");
        file.delete();

        ImageView image = (ImageView) findViewById(R.id.imageView);

        image.setImageBitmap(detailedPhoto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detailed_photo, menu);
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

    private Bitmap getBitmapFromString(String stringPicture) {
    /*
    * This Function converts the String back to Bitmap
    * */
            byte[] decodedString = Base64.decode(stringPicture, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

}
