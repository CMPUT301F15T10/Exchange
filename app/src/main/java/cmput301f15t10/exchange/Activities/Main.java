package cmput301f15t10.exchange.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import cmput301f15t10.exchange.Fragments.EditBookCommentFragment;
import cmput301f15t10.exchange.Fragments.EditBookFragment;
import cmput301f15t10.exchange.Book;
import cmput301f15t10.exchange.R;

public class Main extends AppCompatActivity {
    private Fragment BookEdit, CommentEdit, Photo;
    private FragmentManager fm;
    private FragmentTransaction fm_T;
    private int fragmentLayoutID=R.id.EditItem_fragmentLayout;
    private Book myBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initBook();
        if (savedInstanceState == null) {
            initFragments();
            switchFragment(1); // By default editBookfragment
        }
    }

    public void initFragments(){
        fm=getFragmentManager();
        fm_T=fm.beginTransaction();
        BookEdit = new EditBookFragment();
        CommentEdit = new EditBookCommentFragment();
        // Put here code for initializing photo view/edit fragment
    }

    public void switchFragment(int flag){
        fm_T=fm.beginTransaction();
        if (flag==1){
            fm_T.replace(fragmentLayoutID,BookEdit);
        }
        if (flag==2){
            fm_T.replace(fragmentLayoutID,CommentEdit);
        }
        if (flag==3){
            fm_T.replace(fragmentLayoutID,Photo);
        }
        fm_T.addToBackStack(null);
        fm_T.commit();
    }

    public Book getBook(){
        return myBook;
    }

    public void quitFragmentState(){
        getSupportFragmentManager().popBackStack();
    }

    public void initBook(){
        myBook= new Book();
        myBook.setName("Harry");
        myBook.setType("Book");
        myBook.setQuality(4.832);
        myBook.setQuantity(19);
        myBook.setComment("Its good!");
    }

}

