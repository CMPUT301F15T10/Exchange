package cmput301exchange.exchange.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import cmput301exchange.exchange.Activities.EditBookActivity;
import cmput301exchange.exchange.Activities.PhotoActivity;
import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Controllers.EditBookController;
import cmput301exchange.exchange.Controllers.EditPhotoController;
import cmput301exchange.exchange.Interfaces.BackButtonListener;
import cmput301exchange.exchange.Others.CharSequenceWrapper;
import cmput301exchange.exchange.Photo;
import cmput301exchange.exchange.R;

/**
 * Created by kstember on 11/29/15.
 */
public class EditPhotoFragment extends Fragment {
    private PhotoActivity myActivity;
    private View myView;
    private EditPhotoController myEditPhotoController;
    private Photo myPhoto;

    Button deletePhotoButton,
            selectPhotoButton,
            acceptButton;
    ImageView imageView;

    public void EditPhotoFragment() {
    }

    public void setupView() {
        deletePhotoButton = (Button)myView.findViewById(R.id.deletePhotoButton);
        selectPhotoButton = (Button)myView.findViewById(R.id.selectPhotoButton);
        acceptButton = (Button)myView.findViewById(R.id.acceptButton);
        imageView = (ImageView)myView.findViewById(R.id.imageView);
    }

    public void pushPhotoInfo() {
     /*   photoBitmap.setText(Name.getText().toString());

        myEditBookController.updateName(name_text.toString());
       */
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myEditPhotoController = getController(); // The item controller can be passed down if this becomes a fragment!
    }

    public EditPhotoController getController(){
        return myEditPhotoController;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView=inflater.inflate(R.layout.activity_edit_book, container, false); //might be khj.xml
        setupView();
        initButtons();
       // initSpinner();
        return myView;
    }

    public void initButtons(){

        deletePhotoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                removePhoto();
            }
        });

        selectPhotoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                choosePhoto();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                acceptChanges();
            }
        });

    }

    public void choosePhoto(){
        myActivity.selectPhoto();
    }

    public void removePhoto() {myActivity.deletePhoto();}

    public void acceptChanges() {myActivity.accept();}

}
