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
import android.widget.TextView;

import com.google.gson.Gson;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.R;

public class BookDetailsActivity extends ActionBarActivity {

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
        createCommentDialog();
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
        TextView name= (TextView) findViewById(R.id.BD_name);
        name.setText(name.getText()+book.getName());

        TextView id= (TextView) findViewById(R.id.BD_id);
        id.setText(id.getText()+book.getID().toString());

        TextView author= (TextView) findViewById(R.id.BD_author);
        author.setText(author.getText()+book.getAuthor());

        TextView quality= (TextView) findViewById(R.id.BD_Quality);
        quality.setText(quality.getText()+String.valueOf(book.getQuality()));

        TextView quantity= (TextView) findViewById(R.id.BD_Quantity);
        quantity.setText(quantity.getText()+String.valueOf(book.getQuantity()));

        TextView genre= (TextView) findViewById(R.id.BD_genre);
        genre.setText(genre.getText()+book.getCategory());

        TextView is_Sharable= (TextView) findViewById(R.id.BD_isSharable);
        if (book.isShareable()){
            is_Sharable.setText(is_Sharable.getText()+"Yes");
        }else{
            is_Sharable.setText(is_Sharable.getText()+"No");
        }
    }

    public void GoBack(View view) {
        Intent goBack = new Intent();
        setResult(RESULT_OK, goBack);
        this.finish();
    }

    public void createCommentDialog(){
        // Use the Builder class for convenient dialog construction
        builder = new AlertDialog.Builder(this);
        builder.setMessage(book.getComment())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
                    }
                });
    }

    public void ViewComment(View view) {
        builder.show();
    }
}
