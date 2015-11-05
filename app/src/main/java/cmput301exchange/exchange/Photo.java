package cmput301exchange.exchange.Serializers;


import android.graphics.Bitmap;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;


import cmput301exchange.exchange.Interfaces.Observer;


/**
 * code written by joshua2ua on github:
 * https://github.com/kstem/AndroidElasticSearch/blob/master/app/src/main/java/ca/ualberta/ssrg/movies/Movies.java
 * Modified by kstember on 11/4/15.
 */
public class Photo extends Observable {
    private volatile ArrayList<Observer> observers = new ArrayList<Observer>();
    private static final String RESOURCE_URL = "http://cmput301.softwareprocess.es:8080/testing/";


   // influenced by: http://www.mkyong.com/java/how-to-get-file-size-in-java/
    // TODO: adapt to get size pf user's photo
    public double photoSize(Photo photo){
        File pic = new File("/cshome/kstember/Desktop/testButton.jpg");
        // note this only works on my computer in CSC.
        File pic2 = new File("/Exchange/app/src/main/res/drawable/testPhoto.jpg");
        // hopefully works on all computers with 'testPhoto.jpg' in drawable folder
        double bytes = 0;
        if(pic.exists()) {
            // bytes = photo.length() once we get there
            bytes = pic.length();
        } // else { throw an error}
        return bytes;
    }


    // code written by github user Santiago
    //http://stackoverflow.com/questions/31978299/how-to-covert-image-view-into-bitmap-in-android-studio
       /* public static Bitmap getScreenViewBitmap(View v) {
            v.setDrawingCacheEnabled(true);

            // this is the important code :)
            // Without it the view will have a dimension of 0,0 and the bitmap will be null
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

            v.buildDrawingCache(true);
            Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
            v.setDrawingCacheEnabled(false); // clear drawing cache

            return b;
        }
*/
    public Photo() {}


    //@Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    // @Override
    public void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(/*this*/);
        }
    }

    public String getResourceUrl() {
        return RESOURCE_URL;
    }

   /* public String getSearchUrl() {
        return SEARCH_URL;
    }
*/
    /**
     * Java wants this, we don't need it for Gson/Json
     */
    private static final long serialVersionUID = 3199561696102797345L;

}