package cmput301exchange.exchange;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;

import cmput301exchange.exchange.Activities.ConfigurationActivity;
import cmput301exchange.exchange.Activities.EditBookActivity;
import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Controllers.ConfigurationController;
import cmput301exchange.exchange.Controllers.EditBookController;
import cmput301exchange.exchange.Fragments.EditBookCommentFragment;
import cmput301exchange.exchange.Fragments.EditBookFragment;
import cmput301exchange.exchange.Others.CharSequenceWrapper;
import cmput301exchange.exchange.Others.ObjectSaver;
import cmput301exchange.exchange.R;

/**
 * Created by touqir on 05/11/15.
 */
public class test_EditBookUI extends ActivityInstrumentationTestCase2 {

    private EditBookFragment editBook;
    private EditBookCommentFragment editComment;
    private EditBookActivity activity;
    private Book myBook, myBook2;

    public test_EditBookUI(){
        super(cmput301exchange.exchange.Activities.EditBookActivity.class);
    }

    // Tests whether the activity starts or not.
    public void testStart() throws Exception {
        Activity activity = getActivity();
        assertNotNull(activity);
    }

    @Override
    protected void setUp() throws Exception {
        initBook();
        super.setUp();
        Gson gson = new Gson();
        String json = gson.toJson(myBook);

        Intent intent = new Intent(); //Create a new Intent
        intent.putExtra("Edit_Item", json); //Pack the Intent with a blank Inventory
        setActivityIntent(intent); //Spoof the Intent

        activity= (EditBookActivity)getActivity();
        assertNotNull(activity.fm.findFragmentByTag(EditBookActivity.editBookTag)); // By default EditBookFragment should be started
        editBook=activity.getEditBookFragment();
        editComment=activity.getEditCommentFragment();
        editBook.getView();
//        initBook();
        initBook2();
        getInstrumentation().waitForIdleSync();
    }

    public void initBook(){
        myBook= new Book();
        EditBookController controller=new EditBookController(myBook);
        controller.updateName("Pearl");
        controller.updateType("Book");
        controller.updateQuality("2.45");
        controller.updateQuantity("5");
        controller.updateComment("Its bad");
        myBook=controller.getBook();
    }

    public void initBook2(){
        myBook2= new Book();
        EditBookController controller=new EditBookController(myBook2);
        controller.updateName("Pearlsss");
        controller.updateType("Book");
        controller.updateQuality("1.2");
        controller.updateQuantity("8");
        controller.updateComment("Its not bad!");
        myBook2=controller.getBook();
    }


    public void testPicStateChange(){

        activity.setBook(myBook);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                EditText Name=(EditText) editBook.getView().findViewById(R.id.EditItem_NameInput);
                EditText Type=(EditText) editBook.getView().findViewById(R.id.EditItem_TypeInput);
                EditText Quality=(EditText) editBook.getView().findViewById(R.id.EditItem_QualityInput);
                EditText Quantity=(EditText) editBook.getView().findViewById(R.id.EditItem_QuantityInput);
                Button viewComment=(Button) editBook.getView().findViewById(R.id.EditItem_ViewComments);
                Button save=(Button) editBook.getView().findViewById(R.id.EditItem_Done);
                Button photo=(Button) editBook.getView().findViewById(R.id.EditItem_Photo);
                EditBookController controller=editBook.getController();

                editBook.resetBook();
                assertEquals(Name.getText().toString(), myBook.getName());
                assertEquals(Type.getText().toString(), myBook.getItemType());
                assertEquals(Quality.getText().toString(), (new Integer(myBook.getQuality()).toString()));
                assertEquals(Quantity.getText().toString(), (new Integer(myBook.getQuantity()).toString()));
                viewComment.performClick();

                // when View comment button is pressed, EditBookFragment will shutdown and EditBookCommentFragment will be activated
                assertNull(activity.fm.findFragmentByTag(EditBookActivity.editBookTag));
                assertNotNull(activity.fm.findFragmentByTag(EditBookActivity.editCommentTag));
//                assertTrue(ObjectSaver.gotThere);

                EditText Comment=(EditText) editComment.getView().findViewById(R.id.Comment);
                Button CommentDone= (Button) editComment.getView().findViewById(R.id.Comment_Done);
                assertEquals(Comment.getText().toString(), myBook.getComment());

                String newComment="yess";
                String newName="Harry_";
                String newType="Something";
                Double newQuality=new Double(4.2);
                Integer newQuantity= 102;

                Comment.setText(new CharSequenceWrapper(newComment));
                assertEquals(Comment.getText().toString(), newComment);
                CommentDone.performClick();

                // when Save button is pressed, EditCommentBookFragment will shutdown and EditBookFragment will be activated
                assertNotNull(activity.fm.findFragmentByTag(EditBookActivity.editBookTag));
                assertNull(activity.fm.findFragmentByTag(EditBookActivity.editCommentTag));

                Name=(EditText) editBook.getView().findViewById(R.id.EditItem_NameInput);
                Type=(EditText) editBook.getView().findViewById(R.id.EditItem_TypeInput);
                Quality=(EditText) editBook.getView().findViewById(R.id.EditItem_QualityInput);
                Quantity=(EditText) editBook.getView().findViewById(R.id.EditItem_QuantityInput);
                viewComment=(Button) editBook.getView().findViewById(R.id.EditItem_ViewComments);
                save=(Button) editBook.getView().findViewById(R.id.EditItem_Done);
                photo=(Button) editBook.getView().findViewById(R.id.EditItem_Photo);

                Name.setText(new CharSequenceWrapper(newName));
                Type.setText(new CharSequenceWrapper(newType));
                Quality.setText(new CharSequenceWrapper(newQuality.toString()));
                Quantity.setText(new CharSequenceWrapper(newQuantity.toString()));

                //Test whether the data fed into the editText is displayed properly
                assertEquals(Name.getText().toString(), newName);
                assertEquals(Type.getText().toString(), newType);
                assertEquals(Quality.getText().toString(), newQuality.toString());
                assertEquals(Quantity.getText().toString(), newQuantity.toString());

                save.performClick(); // Save button is pressed and so the new data should be updated with the model
                assertTrue(ObjectSaver.gotThere);

                //Test whether the data is properly updated with the model
                controller.reloadData(); //Data is reloaded from the model
                assertEquals(controller.getName(), newName);
                assertEquals(controller.getType(), newType);
                assertEquals(controller.getQuality(), newQuality.toString());
                assertEquals(controller.getQuantity(), newQuantity.toString());
                assertEquals(controller.getComment(), newComment);
            }
        });

    }


}
