package cmput301exchange.exchange;
/*
 * @author Kaleb
 * Represents a photo that is attached to each item
 */

import java.io.File;
import java.util.ArrayList;

public class Photo {

    private ArrayList<byte[]> photo = new ArrayList<>();     // list of bytes to store pics in
    private boolean downloadYesNo = true;                    // download photo by default
    private final int MAX_SIZE = 65536;                      // max size of photo in bytes
    public boolean empty = true;                             // if empty list, i.e no photo, will be true
    public int owner;                                        // Person.ID
    public int photoID;


    public Photo() {
        photo = new ArrayList<>();
        empty = true;
    }


    public int getPhotoID() {
        return photoID;
    }

    public int getOwner() {
        return owner;
    }

    public boolean isEmpty() { return empty; }
    //public ArrayList<byte[]> getPhoto() { return photo;}
    public void enableDownload() { downloadYesNo = true;}
    public void disableDownload() {downloadYesNo = false;}
    public boolean getDowbloadYesNo() { return downloadYesNo;}
    public void addPhoto(byte[] b) { photo.add(0, b); }

    public void setPhotos(ArrayList<byte[]> photo) {
        this.photo = photo;
    }

    // remove photo at given index
    public void removePhoto(int i) { photo.remove(i); }

    public boolean fileUnderMaxSize(File f) {
        if (f.length() < MAX_SIZE)
            return true;
        else
            return false;
    }

    public String getPhoto() { return "/cshome/kstember/Desktop/301/Exchange/app/src/main/res/drawable/testphoto.png";}

}
