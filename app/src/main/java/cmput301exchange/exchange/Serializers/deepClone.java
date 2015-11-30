package cmput301exchange.exchange.Serializers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by touqir on 27/11/15.
 *
 * A class for performing deep copy.
 * Taken from http://alvinalexander.com/java/java-deep-clone-example-source-code
 *
 */

public class deepClone {
    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            throw new RuntimeException("failed to perform deep clone");
//            e.printStackTrace();
//            return null;
        }
    }
}
