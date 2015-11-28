package cmput301exchange.exchange;
/*
 * @author Kaleb
 * Represents a photo that is attached to each item
 */

import java.util.ArrayList;

public class Photo {

    private ArrayList<byte[]> photo;      // list of bytes to store pics in
    private boolean downloadYesNo = true; // download photo by default
    private boolean empty = true;         // if empty list, i.e no photo, will be true
    private final int MAX_SIZE = 65536;   // max size of photo in bytes

    public Photo() {
        photo = new ArrayList<>();
        empty = true;
    }

    public boolean isEmpty() { return empty; }
    public ArrayList<byte[]> getPhoto() { return photo;}
    public void enableDownload() { downloadYesNo = true;}
    public void disableDownload() {downloadYesNo = false;}
    public boolean getDowbloadYesNo() { return downloadYesNo;}
    public void addPhoto(byte[] b) { photo.add(0, b); }

    // remove photo at given index
    public void removePhoto(int i) { photo.remove(i); }

}
