package cmput301f15t10.exchange.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cmput301f15t10.exchange.Controllers.EditBookController;
import cmput301f15t10.exchange.Interfaces.Observer;
import cmput301f15t10.exchange.Book;
import cmput301f15t10.exchange.Others.CharSequenceWrapper;
import cmput301f15t10.exchange.R;

public class EditCommentActivity extends AppCompatActivity implements Observer {

    private CharSequenceWrapper comment=null;
    private Book myBook; // Need to decide whether myBook should be an object of Book or Book class.
    private EditText commentBox;
    private EditBookController myEditBookController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comment);
        // instantiate the model of Book comment.
        commentBox=(EditText) findViewById(R.id.Comment);
        myEditBookController = new EditBookController(myBook); // The Book controller can be passed down if this becomes a fragment!
        update();
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds Books to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_comment, menu);
        return true;
    }


    public void update(){
        if (comment==null) {
            comment = new CharSequenceWrapper(myBook.getComment());
        }
        else {
            comment.setText(myBook.getComment());
        }
        commentBox.setText(comment, TextView.BufferType.EDITABLE);
    }

    public void onDone(View view) {
        onDone_Handler();
    }

    public void onDone_Handler(){
        
        myEditBookController.changeComment(commentBox.getText().toString());

        // Code for closing the window
    }
}
