package cmput301exchange.exchange.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cmput301exchange.exchange.Activities.EditBookActivity;
import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Controllers.EditBookController;
import cmput301exchange.exchange.Others.CharSequenceWrapper;
import cmput301exchange.exchange.Others.ObjectSaver;
import cmput301exchange.exchange.R;

/**
 * Created by touqir on 01/11/15.
 */
public class EditBookCommentFragment extends Fragment{

    private CharSequenceWrapper comment=null;
    private EditBookActivity myActivity;
    private View myView;
    private Book myBook;
    private EditText commentBox;
    private EditBookController myEditBookController;
    private Button Done;

    public EditBookCommentFragment() {
    }

    public void update(){
        if (comment==null) {
            comment = new CharSequenceWrapper(myEditBookController.getComment());
        }
        else {
            comment.setText(myEditBookController.getComment());
        }
        commentBox.setText(comment, TextView.BufferType.EDITABLE);
    }

    public void Done_Handler(){

        myEditBookController.updateComment(commentBox.getText().toString());
        exit();
        // Code for closing the window
    }

    public void onBackPressed() {
        exit();
    }

    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myEditBookController = myActivity.getController(); // The item controller can be passed down if this becomes a fragment!
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.activity_edit_comment, container, false); //might be khj.xml
        commentBox=(EditText) myView.findViewById(R.id.Comment);
        initButton();
        return myView;
    }

    public void onStart(){
        super.onStart();
//        update();
    }

    public void initButton(){
        Done=(Button) myView.findViewById(R.id.Comment_Done);

        Done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Done_Handler();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = (EditBookActivity) activity;
    }

    public void exit(){
        myActivity.switchFragment(1);
    }
}