package cmput301exchange.exchange.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.gson.Gson;
import cmput301exchange.exchange.Controllers.EditBookController;
import cmput301exchange.exchange.Fragments.EditBookCommentFragment;
import cmput301exchange.exchange.Fragments.EditBookFragment;
import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Fragments.EditPhotoFragment;
import cmput301exchange.exchange.Interfaces.BackButtonListener;
import cmput301exchange.exchange.R;

public class EditBookActivity extends AppCompatActivity {
    private EditBookFragment BookEdit;
    private EditBookCommentFragment CommentEdit;
    private EditPhotoFragment PhotoEdit; // Its fragment type will be replaced by Photo's fragment class.;
    private FragmentTransaction fm_T;
    private Integer fragmentLayoutID=R.id.fragmentR;
    private Book myBook;
    private EditBookController myController;
    private BackButtonListener currentFragment;
    private boolean isBackPressed=false;
    private String originalBook_String=null;

    public FragmentManager fm;
    public static String editBookTag="EDIT_BOOK_TAG",editCommentTag="EDIT_Comment_TAG",viewPhotoTag="VIEW_BOOK_TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initBook();
        myController = new EditBookController(myBook); //Creating an empty EditBookController

        if (savedInstanceState == null) {
            initFragments();
            switchFragment(1); // By default EditBookFragment
        }

    }

    public void initFragments(){
        fm=getFragmentManager();
        fm_T=fm.beginTransaction();
        BookEdit = new EditBookFragment();
        CommentEdit = new EditBookCommentFragment();
        PhotoEdit = new EditPhotoFragment();
        // Put here code for initializing photo view/edit fragment
    }
    /**
     * This method if for if the user attaches a photo of the book.
     * @param flag - integer representing which fragment to switch to
     */
    public void switchFragment(int flag){
        fm_T=fm.beginTransaction();
        if (flag==1){
            fm_T.replace(fragmentLayoutID.intValue(),BookEdit,editBookTag);
            currentFragment=BookEdit;
        }
        if (flag==2){
            fm_T.replace(fragmentLayoutID.intValue(),CommentEdit,editCommentTag);
            currentFragment=CommentEdit;
        }
        if (flag==3){
            fm_T.replace(fragmentLayoutID.intValue(),PhotoEdit,viewPhotoTag);
            currentFragment= (BackButtonListener) PhotoEdit;
        }
//        fm_T.addToBackStack(null);
        fm_T.commitAllowingStateLoss();// Alternative is commit
//        fm_T.commit();
        fm.executePendingTransactions();
    }

    public Book getBook(){
        return myBook;
    }

    public void setBook(Book book){
        myBook=book;
    }

    @Override
    public void finish(){
        Gson gson = new Gson();
        String json;
        if (isBackPressed==false) {
            json = gson.toJson(myController.getBook());
        } else{
            json = originalBook_String;
        }
        Intent book = new Intent();
        book.putExtra("Book", json);
        setResult(RESULT_OK, book);
        super.finish();
    }

    public void initBook() {
        Intent intent=getIntent();
        if (intent==null){
            throw new RuntimeException("Intent is null!");
        }
        String json=intent.getExtras().getString("Edit_Item");
        Gson gson = new Gson();
        myBook=gson.fromJson(json,Book.class);
        originalBook_String=gson.toJson(myBook);
    }

    public EditBookFragment getEditBookFragment(){
        return BookEdit;
    }

    public EditBookCommentFragment getEditCommentFragment(){
        return CommentEdit;
    }

    //public EditPhotoFragment getEditPhotoFragment() {return PhotoEdit;}

//    protected void onSaveInstanceState(Bundle outState) {
//        // TODO Auto-generated method stub
//        super.onSaveInstanceState(outState);
//        ObjectSaver.savedObjects=new ArrayList<Object>();
//        ObjectSaver.savedObjects.add(BookEdit);
//        ObjectSaver.savedObjects.add(CommentEdit);
//        ObjectSaver.savedObjects.add(Photo);
//        ObjectSaver.savedObjects.add(fm);
//        ObjectSaver.savedObjects.add(fm_T);
//        ObjectSaver.savedObjects.add(fragmentLayoutID);
//        ObjectSaver.savedObjects.add(myBook);
//    }
//
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        super.onRestoreInstanceState(savedInstanceState);
//        BookEdit=(EditBookFragment) ObjectSaver.savedObjects.get(0);
//        CommentEdit=(EditBookCommentFragment) ObjectSaver.savedObjects.get(1);
//        Photo=(Fragment) ObjectSaver.savedObjects.get(2); // Change this to Photo fragments class type
//        fm=(FragmentManager) ObjectSaver.savedObjects.get(3);
//        fm_T=(FragmentTransaction) ObjectSaver.savedObjects.get(4);
//        fragmentLayoutID=(Integer) ObjectSaver.savedObjects.get(5);
//        myBook=(Book) ObjectSaver.savedObjects.get(6);
//    }

    public void setController(EditBookController controller){
        myController=controller;
    }

    public EditBookController getController(){
        return myController;
    }

    public void setCurrentFragment(BackButtonListener fragment){
        currentFragment=fragment;
    }

    @Override
    public void onBackPressed() {
        if (currentFragment!=null){
            currentFragment.onBackPress();
        } else{
            isBackPressed=true;
            super.onBackPressed();
        }
    }
}