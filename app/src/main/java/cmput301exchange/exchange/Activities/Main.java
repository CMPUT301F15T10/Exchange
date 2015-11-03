package cmput301exchange.exchange.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cmput301exchange.exchange.Fragments.EditItemCommentFragment;
import cmput301exchange.exchange.Fragments.EditItemFragment;
import cmput301exchange.exchange.Item;
import cmput301exchange.exchange.R;

public class Main extends AppCompatActivity {
    private Fragment ItemEdit, CommentEdit, Photo;
    private FragmentManager fm;
    private FragmentTransaction fm_T;
    private int fragmentLayoutID=R.id.fragmentR;
    private Item myItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initItem();
        if (savedInstanceState == null) {
            initFragments();
            switchFragment(1); // By default editItemfragment
        }
    }

    public void initFragments(){
        fm=getFragmentManager();
        fm_T=fm.beginTransaction();
        ItemEdit = new EditItemFragment();
        CommentEdit = new EditItemCommentFragment();
        // Put here code for initializing photo view/edit fragment
    }

    public void switchFragment(int flag){
        fm_T=fm.beginTransaction();
        if (flag==1){
            fm_T.replace(fragmentLayoutID,ItemEdit);
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

    public Item getItem(){
        return myItem;
    }

    public void quitFragmentState(){
        getSupportFragmentManager().popBackStack();
    }

    public void initItem(){
        myItem= new Item();
        myItem.setName("Harry");
        myItem.setType("Book");
        myItem.setQuality(4.832);
        myItem.setQuantity(19);
        myItem.setComment("Its good!");
    }

}

