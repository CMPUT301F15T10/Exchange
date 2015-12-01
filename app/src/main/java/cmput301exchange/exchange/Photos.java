package cmput301exchange.exchange;
/*
 * Represents photos that are attached to each item
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Photos {

    private ArrayList<String> compressedPhotos;

    private Long id;

    public void initID(){
        Random generator = new Random(System.nanoTime());
        this.id = generator.nextLong();
    }

    public Long getId() {
        return id;
    }

    public ArrayList<String> getCompressedPhotos() {
        return compressedPhotos;
    }

    public void setCompressedPhotos(ArrayList<String> compressedPhotos) {
        this.compressedPhotos = compressedPhotos;
    }

    public void addCompressedPhotos(String compressedPhoto) {
        this.compressedPhotos.add(compressedPhoto);
    }
}
