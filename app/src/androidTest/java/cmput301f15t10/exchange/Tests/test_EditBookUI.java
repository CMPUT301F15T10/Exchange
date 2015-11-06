package cmput301f15t10.exchange.Tests;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import cmput301f15t10.exchange.Activities.ConfigurationActivity;
import cmput301f15t10.exchange.Activities.EditBookActivity;
import cmput301f15t10.exchange.Book;
import cmput301f15t10.exchange.Controllers.ConfigurationController;
import cmput301f15t10.exchange.Controllers.EditBookController;
import cmput301f15t10.exchange.Fragments.EditBookCommentFragment;
import cmput301f15t10.exchange.Fragments.EditBookFragment;
import cmput301f15t10.exchange.Others.CharSequenceWrapper;
import cmput301f15t10.exchange.Others.ObjectSaver;
import cmput301f15t10.exchange.R;

/**
 * Created by touqir on 05/11/15.
 */
public class test_EditBookUI extends ActivityInstrumentationTestCase2 {

    private EditBookFragment editBook;
    private EditBookCommentFragment editComment;
    private EditBookActivity activity;
    private Book myBook, myBook2;

    public test_EditBookUI(){
        super(cmput301f15t10.exchange.Activities.EditBookActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    @Override protected void setUp() throws Exception {
        super.setUp();
        activity= (EditBookActivity)getActivity();
        editBook=activity.getEditBookFragment();
        editComment=activity.getEditCommentFragment();
        editBook.getView();
        initBook();
        initBook2();
        getInstrumentation().waitForIdleSync();
    }

    public void initBook(){
        myBook= new Book();
        myBook.setName("Pearl");
        myBook.setType("Book");
        myBook.setQuality(2.532);
        myBook.setQuantity(3);
        myBook.setComment("Its bad!");
    }

    public void initBook2(){
        myBook2= new Book();
        myBook2.setName("Pearlsss");
        myBook2.setType("Book");
        myBook2.setQuality(1.532);
        myBook2.setQuantity(5);
        myBook2.setComment("Its not bad!");
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
                assertEquals(Type.getText().toString(), myBook.getType());
                assertEquals(Quality.getText().toString(), myBook.getQuality_String());
                assertEquals(Quantity.getText().toString(), myBook.getQuantity_String());
                viewComment.performClick();

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

                assertEquals(editBook.Name.getText().toString(), newName);
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
