package cmput301exchange.exchange.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;

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
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;

public class AddBookActivity extends ActionBarActivity {

    private EditText name, author, quality, quantity, comments;
    private ImageButton image;
    private String category;
    private Inventory inventory;
    private Book cloneBook;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Spinner photoList;

    private ArrayList<byte[]> compressedImages = new ArrayList<>();
    private ArrayList<Bitmap> imageList = new ArrayList<>();
    private ArrayAdapter<Bitmap> bmpAdapter;
    private int currentBitmapPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        Gson gson= new Gson();

        Bundle extras = getIntent().getExtras();

        String json1 = extras.getString("Inventory");
        String json2 = extras.getString("Book");

        photoList = (Spinner) findViewById(R.id.photoListView);
        bmpAdapter = new PhotoAdapter(this, imageList);
        photoList.setAdapter(bmpAdapter);

        inventory = gson.fromJson(json1, Inventory.class);

        image = (ImageButton) findViewById(R.id.imageButton);
        name = (EditText) findViewById(R.id.editName);
        author = (EditText) findViewById(R.id.editAuthor);
        quality = (EditText) findViewById(R.id.editQuality);
        quantity = (EditText) findViewById(R.id.editQuantity);
        comments = (EditText) findViewById(R.id.editComment);

        Spinner spinner = (Spinner) findViewById(R.id.categories_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        if (json2 != null) {
            cloneBook = gson.fromJson(json2, Book.class);
            name.setText(cloneBook.getName());
            author.setText(cloneBook.getAuthor());
            quality.setText(String.valueOf(cloneBook.getQuality()));
            quantity.setText(String.valueOf(cloneBook.getQuantity()));
            comments.setText(cloneBook.getComment());
            CheckBox is_Sharable= (CheckBox) findViewById(R.id.shareable_checkBox);
            if (cloneBook.isShareable()) {
                is_Sharable.setChecked(Boolean.TRUE);
            }
            spinner.setSelection(adapter.getPosition(cloneBook.getCategory()));
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //on selecting a spinner item
                category = parent.getItemAtPosition(position).toString();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        photoList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //on selecting a spinner item
                currentBitmapPos = position;
                image.setImageBitmap(imageList.get(position));

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        image.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                selectImage();

            }
        });
    }

    public void add(View view){

        Book book = new Book();

        // set quality and quantity to 0 if nothing entered
        if (quality.getText().toString().isEmpty()) {
            quality.setText("0");
        }
        if (quantity.getText().toString().isEmpty()) {
            quantity.setText("0");
        }

        String bookName = name.getText().toString(); //Fetch the book title from the document.
        String bookAuthor = author.getText().toString(); //Fetch the Author from the document
        String bookComments = comments.getText().toString();
        Integer bookQuantity = Integer.parseInt(quantity.getText().toString());
        Integer bookQuality = Integer.parseInt(quality.getText().toString());


        final CheckBox checkBox = (CheckBox) findViewById(R.id.shareable_checkBox);
        if (checkBox.isChecked()) {
            book.setShareable(Boolean.TRUE);
        }
        else {
            book.setShareable(Boolean.FALSE);
        }

        book.updateTitle(bookName);
        book.updateAuthor(bookAuthor);
        book.updateQuantity(bookQuantity);
        book.updateQuality(bookQuality);
        book.updateCategory(category);
        book.updateComment(bookComments);

        book.setPhotos(compressedImages);
        
        inventory.add(book);
        this.finishAdd();
    }

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery"};
        final CharSequence[] more_options = { "View Bigger Photo", "Take Photo", "Choose from Gallery", "Save Photo", "Delete Photo"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AddBookActivity.this);

        builder.setTitle("Photo Options");

        if (null == image.getDrawable()) {
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (options[item].equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        startActivityForResult(intent, 1);
                    } else if (options[item].equals("Choose from Gallery")) {

                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);

                    }
                }

            });
        }
        else {
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
                       String item_title = name.getText().toString();
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
                       } catch (FileNotFoundException e){

                       } catch (IOException e){

                       }

                   } else if (more_options[item].equals("Delete Photo")) {
                       imageList.remove(currentBitmapPos);
                       compressedImages.remove(currentBitmapPos);
                       if (imageList.size() == 0){
                           image.setImageDrawable(null);
                       }
                       else {
                           image.setImageBitmap(imageList.get(0));
                       }
                       bmpAdapter.notifyDataSetChanged();
                    }

                }

            });

        }

        builder.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {
                //h=0;
                File f = new File(Environment.getExternalStorageDirectory().toString());

                for (File temp : f.listFiles()) {

                    if (temp.getName().equals("temp.jpg")) {

                        f = temp;
                        File photo = new File(Environment.getExternalStorageDirectory(), "temp.jpg");

                        break;

                    }

                }

                try {

                    Bitmap bitmap;

                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();


                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),

                            bitmapOptions);


                    image.setImageBitmap(bitmap);

                    // new stuff hope it doesn't break
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
                    byte[] byteArray = stream.toByteArray();
                    compressedImages.add(byteArray);

                    addToImageList(byteArray);
                    bmpAdapter.notifyDataSetChanged();

                    stream.flush();
                    stream.close();

                    f.delete();

                } catch (Exception e) {

                    e.printStackTrace();

                }

            } else if (requestCode == 2) {


                Uri selectedImage = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                String picturePath = c.getString(columnIndex);

                c.close();

                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                Log.w("path image from gallery", picturePath + "");

                image.setImageBitmap(thumbnail);
            }

        }
    }

    private void addToImageList(byte[] array){
        Bitmap bm = BitmapFactory.decodeByteArray(array, 0, array.length); //use android built-in functions
        imageList.add(bm);
    }

    public void finishAdd(){
        String json = inventory.toJson(); //Write the existing inventory data to Json
        DataIO dataIO = new DataIO(this, AddBookActivity.class);
        dataIO.saveInFile("book.sav", json);

        Intent added = new Intent().putExtra("Inventory", "book.sav"); //Send it back to the inventory activity
        setResult(RESULT_OK, added);

        this.finish();

    }

    @Override
    public void onStop(){
        //We want this function to be called whenever the activity is killed to prevent losing data
        //I pulled the gson writing from the add function, and generalized it.

        Gson gson= new Gson(); //Create a new Gson Instance
        String json=gson.toJson(inventory); //Write the existing inventory data to Json

        Intent added = new Intent().putExtra("Inventory",json); //Send it back to the inventory activity
        setResult(RESULT_OK, added);

        super.onStop(); //Required for the onStop Function to work
    }

    @Override
    public void onBackPressed(){
        //This method is called when the back button is pressed, regardless of what data is entered.
        //It basically just stops the data from being entered into the inventory, and quits activity
        this.finishAdd();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
