package cmput301exchange.exchange.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;
import cmput301exchange.exchange.User;

public class AddItemActivity extends ActionBarActivity {

    ModelEnvironment GlobalENV;

    private EditText name, author, quality, quantity, comments;
    private String category;
    private Integer bookQuality,bookQuantity;
    private Inventory inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Gson gson= new Gson();
        String json=getIntent().getStringExtra("Add_Item");
        inventory=gson.fromJson(json,Inventory.class);

        name = (EditText) findViewById(R.id.editName);
        author = (EditText) findViewById(R.id.editAuthor);
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //on selecting a spinner item
                category = parent.getItemAtPosition(position).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {

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
        bookQuantity = Integer.parseInt(quantity.getText().toString());
        bookQuality = Integer.parseInt(quality.getText().toString());


        final CheckBox checkBox = (CheckBox) findViewById(R.id.shareable_checkBox);
        if (checkBox.isChecked()) {
            book.setShareable(Boolean.TRUE);
        }
        else {
            book.setShareable(Boolean.FALSE);
        }

        book.updateTitle(bookName);
        book.updateAuthor(bookAuthor);
        book.updateQuantity(bookQuantity);
        book.setQuality(bookQuality);
        book.updateCategory(category);
        book.updateComment(bookComments);
        
        inventory.add(book);
        this.onStop();
    }
    @Override
    public void onStop(){
        //We want this function to be called whenever the activity is killed to prevent losing data
        //I pulled the gson writing from the add function, and generalized it.

        Gson gson= new Gson(); //Create a new Gson Instance
        String json=gson.toJson(inventory); //Write the existing inventory data to Json

        Intent added = new Intent().putExtra("Inventory",json); //Send it back to the inventory activity
        setResult(RESULT_OK, added);

        this.finish();
        super.onStop(); //Required for the onStop Function to work
    }

    @Override
    public void onBackPressed(){
        //This method is called when the back button is pressed, regardless of what data is entered.
        //It basically just stops the data from being entered into the inventory, and quits activity
        this.onStop();
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
