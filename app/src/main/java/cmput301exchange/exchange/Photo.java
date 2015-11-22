package cmput301exchange.exchange;
/*
 * @author Kaleb
 * Represents a photo that is attached to each item
 */

import java.util.ArrayList;

public class Photo {

    private ArrayList<byte[]> photo;
    public Photo() {
        photo = new ArrayList<>();
    }

    public void addPhoto(byte[] b)  {
        photo.add(0, b);
    }
}
