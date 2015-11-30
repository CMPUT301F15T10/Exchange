package cmput301exchange.exchange;
/*
 * @author Kaleb
 */


import java.util.ArrayList;

public class Photo {

    private ArrayList<byte[]> photo;
    private boolean empty; // true if no photos in Arraylist

    public Photo(){
        photo = new ArrayList<>();
        empty = true;
    }

    public byte[] getPhoto(int index) { return photo.get(index); }

    public void addPhoto(byte[] b) {
            photo.add(0, b);
            setEmpty(true);
    }

   // public void swapPhoto(int index, byte[] b) { photo.set(index, b); }

    public void removePhoto(int index){ photo.remove(index); }

    public boolean getEmpty(){ return empty;}

    public ArrayList<byte[]> getPhoto(){ return photo; }

    public void setEmpty(boolean b){ empty = b; }

    //takes a photo object and sets it all here
    public void setPhotoObjects(Photo photos){
        int i = 0;
        while (i < photos.getPhoto().size()) {
            photo.add(i, photos.getPhoto().get(i));
            ++i;
        } if(photos.getPhoto().size() > 0) { empty = true;}

    }

}
