package cmput301exchange.exchange.Activities;

import android.accounts.Account;
import android.content.Context;
import android.provider.Contacts;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

/**
 * Code inspired by Joshua Campbell
 * https://github.com/joshua2ua/lonelyTwitter/blob/f15tuesday/app/src/main/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivity.java
 */
public class SavePhoto {

    protected static final String FILENAME = "fileName.sav";
    protected static final String PHOTO = "photo.sav";

    public SavePhoto() { }

    public void saveInFile(Context context,Account account) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            BufferedWriter out=new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson=new Gson();
            gson.toJson(account, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }


    public Account loadFromFile(Context context) {
        Account account=null;
        try {

            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson=new Gson();
            Type type=new TypeToken<Account>() {}.getType();
            account=gson.fromJson(in,type);

        } catch (FileNotFoundException e) { e.printStackTrace(); }
        return account;
    }

    //adding this for storing images to pass between actvities
    public void savePhotos(Context context, Contacts.Photos photoList){

        try {
            FileOutputStream fos = context.openFileOutput(PHOTO, 0);
            BufferedWriter out=new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson=new Gson();
            gson.toJson(photoList, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

}
