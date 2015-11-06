package cmput301f15t10.exchange.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import java.util.ArrayList;

import cmput301f15t10.exchange.Controllers.EditBookController;
import cmput301f15t10.exchange.Fragments.EditBookCommentFragment;
import cmput301f15t10.exchange.Fragments.EditBookFragment;
import cmput301f15t10.exchange.Book;
import cmput301f15t10.exchange.Others.ObjectSaver;
import cmput301f15t10.exchange.R;

public class EditBookActivity extends AppCompatActivity {
    private EditBookFragment BookEdit;
    private EditBookCommentFragment CommentEdit;
    private Fragment Photo; // Its fragment type will be replaced by Photo's fragment class.;
    private FragmentManager fm;
    private FragmentTransaction fm_T;
    private Integer fragmentLayoutID=R.id.EditItem_fragmentLayout;
    private Book myBook;
    private EditBookController myController;

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
            fm_T.replace(fragmentLayoutID.intValue(),BookEdit);
        }
        if (flag==2){
            fm_T.replace(fragmentLayoutID.intValue(),CommentEdit);
        }
        if (flag==3){
            fm_T.replace(fragmentLayoutID.intValue(),Photo);
        }
//        fm_T.addToBackStack(null);
//        fm_T.commitAllowingStateLoss();// Alternative is commit
        fm_T.commit();
        fm.executePendingTransactions();
    }

    public Book getBook(){
        return myBook;
    }

    public void setBook(Book book){
        myBook=book;
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

    public EditBookFragment getEditBookFragment(){
        return BookEdit;
    }

    public EditBookCommentFragment getEditCommentFragment(){
        return CommentEdit;
    }

    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        ObjectSaver.savedObjects=new ArrayList<Object>();
        ObjectSaver.savedObjects.add(BookEdit);
        ObjectSaver.savedObjects.add(CommentEdit);
        ObjectSaver.savedObjects.add(Photo);
        ObjectSaver.savedObjects.add(fm);
        ObjectSaver.savedObjects.add(fm_T);
        ObjectSaver.savedObjects.add(fragmentLayoutID);
        ObjectSaver.savedObjects.add(myBook);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        BookEdit=(EditBookFragment) ObjectSaver.savedObjects.get(0);
        CommentEdit=(EditBookCommentFragment) ObjectSaver.savedObjects.get(1);
        Photo=(Fragment) ObjectSaver.savedObjects.get(2); // Change this to Photo fragments class type
        fm=(FragmentManager) ObjectSaver.savedObjects.get(3);
        fm_T=(FragmentTransaction) ObjectSaver.savedObjects.get(4);
        fragmentLayoutID=(Integer) ObjectSaver.savedObjects.get(5);
        myBook=(Book) ObjectSaver.savedObjects.get(6);
    }

    public void setController(EditBookController controller){
        myController=controller;
    }

    public EditBookController getController(){
        return myController;
    }
}

