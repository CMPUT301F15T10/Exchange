package cmput301exchange.exchange.Activities;


import android.support.v7.app.AppCompatActivity;

/* todo: What needs to be done is viewing a bigger photo of the selected photo,
 * todo: which can be done by sending it to a new activity and setting the imageview
 * -------------------------------------------------------------------------------------------------
 * todo: It needs to be added to edit book photo too,
 * todo: and viewing photos also needs to be implemented in book details
 * -------------------------------------------------------------------------------------------------
 * todo: Saving the image yo the disk also doesn't seem to work right,
 * todo: it only saves to the camera folder for now
 * -------------------------------------------------------------------------------------------------
*/
public class PhotoActivity extends AppCompatActivity {







}











/*
    Button selectPhoto,
           deletePhoto,
            acceptButton;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        selectPhoto = (Button)findViewById(R.id.selectPhotoButton);
        deletePhoto = (Button)findViewById(R.id.deletePhotoButton);
        acceptButton = (Button)findViewById(R.id.acceptButton);
        iv = (ImageView)findViewById(R.id.imageView);

        acceptButton.setVisibility(View.GONE); // hide the accept button initially

*/




































  /*
    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView imgPicture;
  //  Button uploadPhoto;
    Button useCamera;
    ImageView takenPhoto;

    ContentValues value;
    Bitmap thumbnail;
    private static final int CAM_REQUEST = 1313;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

      //  uploadPhoto = (Button) findViewById(R.id.takePhotoButton);
        imgPicture = (ImageView) findViewById(R.id.imgPicture);
        takenPhoto = (ImageView) findViewById(R.id.imagePicture);
        useCamera = (Button) findViewById(R.id.takePhotoButton);
        //uploadPhoto.setOnClickListener(new uploadPhotoClicker());
        useCamera.setOnClickListener(new takePhoto());
    }

    // take picture with camera
    private class takePhoto implements Button.OnClickListener {

        @Override
        public void onClick(View view) {

            // Written by stackoverflow user "Antrromet"
            // http://stackoverflow.com/questions/10377783/low-picture-image-quality-when-capture-from-camera, Nov 21, 2015
            value = new ContentValues();
            value.put(MediaStore.Images.Media.TITLE, "New Picture");
            value.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            Uri imageUri = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, 1);

        }
    }

    // code inside "// ##### " taken from android developer tutorials and is liscensed under the Apache 2.0 license
    // http://developer.android.com/training/camera/photobasics.html
// ##### START

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;

    // create a collision-resistant file name.
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
/*                ".jpg",         /* suffix */
  /*              storageDir      /* directory */
    //    );

        // Save a file: path for use with ACTION_VIEW intents
    /*    mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    //  invokes an intent to capture a photo.
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    // add photo to gallery
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imgPicture.getWidth();
        int targetH = imgPicture.getHeight();
// ##### end
        int resolution = targetH*targetW;
        int sizeOfPhoto = resolution*4; // if each pixel takes up 4 bytes

        // make sure photo is less than 65536 bytes
  /*      if (sizeOfPhoto >= 65536) {
           // throw some exception // todo

        } else { */
// ##### start
            // Get the dimensions of the bitmap
   /*         BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            imgPicture.setImageBitmap(bitmap);
      //  }
    }
//##### END

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // credit to YouTube account "Indragni Soft Solutions", https://www.youtube.com/watch?v=s7lo2wSE0zM
        // Nov 21, 2015
      /* Not sure if need
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAM_REQUEST) {
            thumbnail = (Bitmap) data.getExtras().get("data");
            takenPhoto.setImageBitmap(thumbnail);
        }
        */
// ##### START
  /*      if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
           /* if (imgPicture.getAllocationByteCount() < 65536) {
                imgPicture.setImageBitmap(imageBitmap);
            } // else { do somthing ...} */
//}

// ##### END
       /* not sure if need
        // credit to github user "discospiff", https://github.com/discospiff/PlantPlaces15s305
        // Nov 21, 2015
        if (resultCode == RESULT_OK) {
            // if we are here, everything processed successfully.
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                // if we are here, we are hearing back from the image gallery.

                // the address of the image on the SD Card.
                Uri imageUri = data.getData();

                // declare a stream to read the image data from the SD Card.
                InputStream inputStream;

                // we are getting an input stream, based on the URI of the image.
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);

                    // get a bitmap from the stream.
                    Bitmap image = BitmapFactory.decodeStream(inputStream);


                    // show the image to the user
                    imgPicture.setImageBitmap(image);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indictating that the image is unavailable.
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }

            }
        }
        */
 //   }

  /* not sure if need
    //**** START: choose photo from gallery

    public void onImageGalleryClicked(View v) {
        // invoke the image gallery using an implict intent.
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        // where do we want to find the data?
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        // finally, get a URI representation
        Uri data = Uri.parse(pictureDirectoryPath);

        // set the data and type.  Get all image types.
        photoPickerIntent.setDataAndType(data, "image/*");

        // we will invoke this activity, and get something back from it.
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }
    //**** END: choose photo from gallery

*/
            /*
}*/

