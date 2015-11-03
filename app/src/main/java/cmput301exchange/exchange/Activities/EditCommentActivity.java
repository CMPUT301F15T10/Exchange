package cmput301exchange.exchange.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cmput301exchange.exchange.Controllers.EditItemController;
import cmput301exchange.exchange.Interfaces.Observer;
import cmput301exchange.exchange.Item;
import cmput301exchange.exchange.Others.CharSequenceWrapper;
import cmput301exchange.exchange.R;

public class EditCommentActivity extends AppCompatActivity implements Observer {

    private CharSequenceWrapper comment=null;
    private Item myItem; // Need to decide whether myItem should be an object of Item or Book class.
    private EditText commentBox;
    private EditItemController myEditItemController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comment);
        // instantiate the model of item comment.
        commentBox=(EditText) findViewById(R.id.Comment);
        myEditItemController = new EditItemController(myItem); // The item controller can be passed down if this becomes a fragment!
        update();
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_comment, menu);
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

    public void update(){
        if (comment==null) {
            comment = new CharSequenceWrapper(myItem.getComment());
        }
        else {
            comment.setText(myItem.getComment());
        }
        commentBox.setText(comment, TextView.BufferType.EDITABLE);
    }

    public void onDone(View view) {
        onDone_Handler();
    }

    public void onDone_Handler(){
        
        myEditItemController.changeComment(commentBox.getText().toString());

        // Code for closing the window
    }
}
