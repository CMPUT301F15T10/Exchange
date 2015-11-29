package cmput301exchange.exchange.Controllers;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;

import cmput301exchange.exchange.Book;
import cmput301exchange.exchange.Photo;

/**
 * Created by kstember on 11/29/15.
 */
public class EditPhotoController {

    private Photo myPhoto;

    public EditPhotoController(Photo photo) {
        myPhoto = photo;
    }

    public Boolean getDownload(){
        return myPhoto.getDowbloadYesNo();
    }
    public void allowDownloads(){
        myPhoto.enableDownload();
    }
    public void disallowDownloads(){
        myPhoto.disableDownload();
    }
    public void addPhotoControl(byte[] b){
        myPhoto.addPhoto(b);
    }

    public void removePhotoControl(int i){
        myPhoto.removePhoto(i);
    }

    public boolean fileUnderMaxSizeControl(File f){
        return myPhoto.fileUnderMaxSize(f);
    }


    //TODO
    public void save(){
        //Will include functionality for saving mybook to storage.
    }

    //TODO
    public void reloadData(){
        //Will implement loading the same book from model.
    }
/*
    public void updateCategory(String category){
        myPhoto.updateCategory(category);
    }
*/
}
