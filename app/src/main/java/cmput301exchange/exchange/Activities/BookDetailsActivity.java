package cmput301exchange.exchange.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.R;

public class BookDetailsActivity extends ActionBarActivity {
    // this is a class of show details of a book
    //it use gson to get the data about a specific book
    //and change edit text of the view shown on the screen
    private Book book;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Intent intent =getIntent();
        String json=intent.getStringExtra("Book");
        Gson gson= new Gson();
        book=gson.fromJson(json, Book.class);
        initTextView();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initTextView(){
        TextView name= (TextView) findViewById(R.id.bookName);
        name.setText(book.getName());

//        TextView id= (TextView) findViewById(R.id.BD_id);
//        id.setText(id.getText()+book.getID().toString());

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
    }

    public void GoBack(View view) {
        Intent goBack = new Intent();
        setResult(RESULT_OK, goBack);
        this.finish();
    }
}
