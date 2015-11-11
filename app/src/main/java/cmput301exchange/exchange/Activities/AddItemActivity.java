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
    private String category, bookName="",bookAuthor="",bookComments="";
    private Integer bookQuality=5,bookQuantity=1;
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

        // TODO input verificiation: check to make sure nothing is empty, if empty fill with something
    }

    public void add(View view){

        Book book = new Book();

        bookName = name.getText().toString();
        bookAuthor = author.getText().toString();
        if (!(quality.getText().toString().isEmpty())) {
            bookQuality = Integer.parseInt(quality.getText().toString()); // add try, catch later
        }
        if (!(quantity.getText().toString().isEmpty())) {
            bookQuantity = Integer.parseInt(quantity.getText().toString()); // add try, catch later
        }
        bookComments = comments.getText().toString();

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
        Gson gson= new Gson();
        String json=gson.toJson(inventory);

        Intent added = new Intent().putExtra("Inventory",json);
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
