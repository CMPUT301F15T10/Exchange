package cmput301f15t10.exchange.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import cmput301f15t10.exchange.Activities.Main;
import cmput301f15t10.exchange.Book;
import cmput301f15t10.exchange.Controllers.EditBookController;
import cmput301f15t10.exchange.Item;
import cmput301f15t10.exchange.Others.CharSequenceWrapper;
import cmput301f15t10.exchange.R;

public class EditBookFragment extends Fragment {

    private Main myActivity;
    private View myView;
    private EditBookController myEditBookController;
    private Book myBook;
    private EditText Name,Type,Quantity,Quality;
    private CharSequenceWrapper name_text=null, type_text=null, quantity_text=null, quality_text=null;
    private Spinner Category;
    private Button ViewComment,Done,ViewPhoto;

    public EditBookFragment() {
        // Required empty public constructor
    }

    public void setupView(){
        Name=(EditText) myView.findViewById(R.id.EditItem_NameInput);
        Type=(EditText) myView.findViewById(R.id.EditItem_TypeInput);
        Quality=(EditText) myView.findViewById(R.id.EditItem_QualityInput);
        Quantity=(EditText) myView.findViewById(R.id.EditItem_QuantityInput);
        Category=(Spinner) myView.findViewById(R.id.EditItem_CategorySpinner);
    }

    public void pushItemInfo(){
        name_text.setText(Name.getText().toString());
        type_text.setText(Type.getText().toString());
        quality_text.setText(Quality.getText().toString());
        quantity_text.setText(Quantity.getText().toString());
        // add category

        myEditBookController.updateName(name_text.toString());
        myEditBookController.updateType(type_text.toString());
        myEditBookController.updateQuantity(quantity_text.toString());
        myEditBookController.updateQuality(quality_text.toString());
        // add category
    }

    public void fetchItemInfo(){

        if (name_text==null){
            name_text= new CharSequenceWrapper(myBook.getName());
        } else {
            name_text.setText(myBook.getName());
        }

        if (type_text==null){
            type_text= new CharSequenceWrapper(myBook.getType());
        } else {
            type_text.setText(myBook.getType());
        }

        if (quantity_text==null){
            quantity_text= new CharSequenceWrapper(myBook.getQuantity_String());
        } else {
            quantity_text.setText(myBook.getQuantity_String());
        }

        if (quality_text==null){
            quality_text= new CharSequenceWrapper(myBook.getQuality_String());
        } else {
            quality_text.setText(myBook.getQuality_String());
        }
        // Need to add Category

    }


    public void updateView(){
        fetchItemInfo();
        Name.setText(name_text, TextView.BufferType.EDITABLE);
        Type.setText(type_text, TextView.BufferType.EDITABLE);
        Quality.setText(quality_text, TextView.BufferType.EDITABLE);
        Quantity.setText(quantity_text, TextView.BufferType.EDITABLE);
        // Need to add Category
    }

    public void ViewComments_Handler(){
        pushItemInfo();
        myActivity.switchFragment(2);
    }

    public void ClickPhoto_Handler(){
        pushItemInfo();
        myActivity.switchFragment(3);
    }

    public void Save_Handler(){
        pushItemInfo();
        myBook.saveItem();
        myActivity.quitFragmentState();
    }

    public void onViewComments(View view) {
        ViewComments_Handler();
    }

    public void onClickPhoto(View view) {
        ClickPhoto_Handler();
    }

    public void onSave(View view) {
        Save_Handler();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myBook=myActivity.getBook();
        myEditBookController=new EditBookController(myBook);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView=inflater.inflate(R.layout.activity_edit_book, container, false); //might be khj.xml
        setupView();
        initButtons();
        return myView;
    }

    public void initButtons(){
        ViewComment= (Button) myView.findViewById(R.id.EditItem_ViewComments);
        Done= (Button) myView.findViewById(R.id.EditItem_Done);
        ViewPhoto= (Button) myView.findViewById(R.id.EditItem_Photo);

        ViewComment.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ViewComments_Handler();
            }
        });

        Done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Save_Handler();
            }
        });

        ViewPhoto.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ClickPhoto_Handler();
            }
        });

    }
    public void onResume(){
        super.onResume();
        updateView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = (Main) activity;
    }
}
