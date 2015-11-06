package cmput301exchange.exchange.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;
import cmput301exchange.exchange.User;

public class AddItemActivity extends ActionBarActivity {

    ModelEnvironment GlobalENV;

    public EditText name;
    public EditText type;
    public EditText quality;
    public EditText quantity;
    // TODO get category from spinner
    public EditText comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        name = (EditText) findViewById(R.id.editName);
        type = (EditText) findViewById(R.id.editType);
        quality = (EditText) findViewById(R.id.editQuality);
        quantity = (EditText) findViewById(R.id.editQuantity);
        comments = (EditText) findViewById(R.id.editComment);


        Spinner spinner = (Spinner) findViewById(R.id.categories_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //check to make sure nothing is empty, if empty fill with something
    }

    public void add(View view){

        DataIO io = new DataIO(getApplicationContext(),ModelEnvironment.class);
        GlobalENV = io.loadEnvironment("GlobalENV");

        User user = GlobalENV.getOwner();

        Book book = new Book();

        String bookName = name.getText().toString();
        String bookType = type.getText().toString();
        String bookQuality = quality.getText().toString();
        String bookQuantity = quantity.getText().toString();
        String bookComments = comments.getText().toString();

        book.updateTitle(bookName);
        book.updateComment(bookComments);

        user.getMyInventory().add(book);

        io.saveEnvironment("GlobalENV", GlobalENV);

        Intent added = new Intent();
        setResult(RESULT_OK, added);

        this.finish();
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
