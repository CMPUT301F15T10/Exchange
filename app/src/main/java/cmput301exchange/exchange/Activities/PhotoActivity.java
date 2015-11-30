package cmput301exchange.exchange.Activities;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import cmput301exchange.exchange.Activities.Adapters.PhotoAdapter;
import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.Photo;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;
import cmput301exchange.exchange.User;
import com.google.gson.Gson;

/** not sure if this activity is needed anymore.
 * feel free to delete if its not
 */
public class PhotoActivity extends AppCompatActivity {

    protected int REQUEST_CAMERA;
    protected int SELECT_FILE;

    private ArrayList<Bitmap> imageList = new ArrayList<>();
    private Book book;
    private String name;
   // private Long userID;
    private int currentBitmapPos;
    private Spinner photoList;
    private ArrayList<byte[]> compressedImages = new ArrayList<>();
    private ArrayAdapter<Bitmap> bmpAdapter;
    private Inventory inventory;

    Button deletePhotoButton,
           selectPhotoButton,
           acceptButton;
    ContentValues cValues;
    Uri uri;
    SavePhoto savePhoto;
    Photo photo;
    BitmapScaler bmScaler;
    ImageView iv;
    ImageButton imageButton;
    Person person;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        Bundle extras = getIntent().getExtras();
        Gson gson= new Gson();

        photo = new Photo();

        bmScaler = new BitmapScaler();
        savePhoto = new SavePhoto();
        photo.setPhotos(compressedImages);
        photoList = (Spinner) findViewById(R.id.photoListView);
        bmpAdapter = new PhotoAdapter(this, imageList);
        photoList.setAdapter(bmpAdapter);


        photo = savePhoto.loadPhoto(getApplicationContext());
        deletePhotoButton = (Button)findViewById(R.id.deletePhotoButton);
        selectPhotoButton = (Button)findViewById(R.id.selectPhotoButton);
        acceptButton = (Button)findViewById(R.id.acceptButton);
        imageButton = (ImageButton)findViewById(R.id.imageButton);
        acceptButton.setVisibility(View.GONE); // not visible initially
        imageButton.setImageResource(R.drawable.testphoto);

        selectPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });

        deletePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePhoto();
            }
        });

        // idk if below is needed
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accept();
            }
        });

        photoList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //on selecting a spinner item
                currentBitmapPos = position;
                imageButton.setImageBitmap(imageList.get(position));
            }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {  }
        });
    }

    public void add(View view){

        Book book = new Book();
        book.setPhotos(compressedImages);
        inventory.add(book);
        this.finishAdd();
    }

    public void finishAdd(){
        String json = inventory.toJson(); //Write the existing inventory data to Json
        DataIO dataIO = new DataIO(this, PhotoActivity.class);
        dataIO.saveInFile("photo.sav", json);

        Intent added = new Intent().putExtra("Edit Book", "photo.sav"); //Send it back to the inventory activity
        setResult(RESULT_OK, added);

        this.finish();

    }


    // Inspired by Tejas Jasani
    // http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample
    private void selectPhoto() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery"};
        final CharSequence[] more_options = { "View Bigger Photo", "Take Photo", "Choose from Gallery", "Save Photo", "Delete Photo"};

        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoActivity.this);

        builder.setTitle("Photo Options");

         if (null == imageButton.getDrawable()) {
             builder.setItems(options, new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int item) {

                     if (options[item].equals("Take Photo")) {
                         Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                         File f = new File(android.os.Environment.getExternalStorageDirectory(), "tempPic.jpg");
                         intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                         assert photo.fileUnderMaxSize(f);
                         startActivityForResult(intent, 1);
                     } else if (options[item].equals("Choose from Gallery")) {

                         Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                         startActivityForResult(intent, 2);

                     }
                 }

             });
         } else {
            builder.setItems(more_options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (more_options[item].equals("View Bigger Photo")) {
                        // send image to another activity where the full image can be expanded

                    } else if (more_options[item].equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);

                    } else if (more_options[item].equals("Choose from Gallery")) {

                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);

                    } else if (more_options[item].equals("Save Photo")) {
                        String path = Environment.getExternalStorageDirectory().toString();
                        String item_title = name;
                        File newFolder = new File(path + "/Exchange/" + item_title);
                        newFolder.mkdir();
                        File file = new File(path + "/Exchange/" + item_title, item_title + String.valueOf(currentBitmapPos) + ".jpg"); // the File to save to
                        try {
                            OutputStream fOut = new FileOutputStream(file);
                            Bitmap pictureBitmap = imageList.get(currentBitmapPos);
                            pictureBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
                            fOut.flush();
                            fOut.close();
                            MediaStore.Images.Media.insertImage(getContentResolver(), file.getPath(), file.getName(), file.getName());
                        } catch (FileNotFoundException e) {

                        } catch (IOException e) {

                        }

                    } else if (more_options[item].equals("Delete Photo")) {
                        deletePhoto();
                    }

                }

            });

        }

        builder.show();

    }

    public void deletePhoto() {
        imageList.remove(currentBitmapPos);
        compressedImages.remove(currentBitmapPos);
        if (imageList.size() == 0) {
            imageButton.setImageDrawable(null);
        } else {
            imageButton.setImageBitmap(imageList.get(0));
        }
        bmpAdapter.notifyDataSetChanged();

    }

    public void accept() {
        Intent result = new Intent();
        setResult(PhotoActivity.RESULT_OK, result);
        savePhoto.saveImage(getApplicationContext(), photo);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    String photoAddress = uriPath(uri);

                    BitmapFactory.Options bmfO = new BitmapFactory.Options();
                    // inSampleSize returns an image that is 1/4 the width/height of the original,
                    // and 1/16 the number of pixels
                    // via http://developer.android.com/reference/android/graphics/BitmapFactory.Options.html
                    bmfO.inSampleSize = 4;
                    Bitmap oldBitmap = BitmapFactory.decodeFile(photoAddress, bmfO);
                    ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
                    oldBitmap.compress(Bitmap.CompressFormat.PNG, 80, byteOutStream);
                    photo.addPhoto(byteOutStream.toByteArray());

                    Bitmap scale = bmScaler.scaleToFitWidth(oldBitmap, 250);
                    photo.empty = false;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    // Written by stackoverflow user 'antrromet'
    // http://stackoverflow.com/questions/10377783/low-picture-image-quality-when-capture-from-camera
    public String uriPath(Uri u) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(u, proj, null, null, null);
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }



}
