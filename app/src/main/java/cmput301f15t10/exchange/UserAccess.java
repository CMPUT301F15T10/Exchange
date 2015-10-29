package cmput301f15t10.exchange;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by touqir on 27/10/15.
 */
public class UserAccess {
    private int Mode;
    private Context myActivity;

    public UserAccess(Context context) {
        myActivity=context;
    }

    public int getMode() {
        updateMode();
        return Mode;
    }

    public void updateMode(){
        if (IsConnected()==true){
            Mode=1;
        }
        else {
            Mode=2;
        }
    }

    public boolean IsConnected(){
        ConnectivityManager connectivity = (ConnectivityManager) myActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity!=null){
            return true;
        }
        return false;
    }
}
