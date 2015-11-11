package cmput301exchange.exchange.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import cmput301exchange.exchange.Activities.EditBookActivity;
import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Controllers.EditBookController;
import cmput301exchange.exchange.Others.CharSequenceWrapper;
import cmput301exchange.exchange.Others.ObjectSaver;
import cmput301exchange.exchange.R;

public class EditBookFragment extends Fragment {

    private EditBookActivity myActivity;
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
            name_text= new CharSequenceWrapper(myEditBookController.getName());
        } else {
            name_text.setText(myEditBookController.getName());
        }

        if (type_text==null){
            type_text= new CharSequenceWrapper(myEditBookController.getType());
        } else {
            type_text.setText(myEditBookController.getType());
        }

        if (quantity_text==null){
            quantity_text= new CharSequenceWrapper(myEditBookController.getQuantity());
        } else {
            quantity_text.setText(myEditBookController.getQuantity());
        }

        if (quality_text==null){
            quality_text= new CharSequenceWrapper(myEditBookController.getQuality());
        } else {
            quality_text.setText(myEditBookController.getQuality());
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
        myEditBookController.save();
        exit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myBook=myActivity.getBook();
        myEditBookController=new EditBookController(myBook);
        myActivity.setController(myEditBookController);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView=inflater.inflate(R.layout.activity_edit_book, container, false); //might be khj.xml
        setupView();
        initButtons();
        initSpinner();
        return myView;
    }

    public void initButtons(){
        ViewComment= (Button) myView.findViewById(R.id.EditItem_ViewComments);
        Done= (Button) myView.findViewById(R.id.EditItem_Done);
        ViewPhoto= (Button) myView.findViewById(R.id.EditItem_Photo);

        ViewComment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ViewComments_Handler();
            }
        });

        Done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Save_Handler();
            }
        });

        ViewPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
        myActivity = (EditBookActivity) activity;
    }

    public void resetBook(){
        myEditBookController.setBook(myActivity.getBook());
        updateView();
    }

    public void exit(){
        myActivity.setBook(myEditBookController.getBook());
        myActivity.finish();
    }

    public EditBookController getController(){
        return myEditBookController;
    }
    
    public void initSpinner(){
        Category=(Spinner) myView.findViewById(R.id.EditItem_CategorySpinner);
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(myActivity,
                R.array.categories_selection, android.R.layout.simple_spinner_item);
        Category.setAdapter(adapter);
        Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myEditBookController.setBookCategory((String) Category.getItemAtPosition(position));
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
}
