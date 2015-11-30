package cmput301exchange.exchange;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;

/**
 * Created by touqir on 05/11/15.
 */
public class test_ConfigurationUI extends ActivityInstrumentationTestCase2 {

    public test_ConfigurationUI() {
        super(cmput301exchange.exchange.Activities.ConfigurationActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testPicStateChange(){
        final ConfigurationActivity activity= (ConfigurationActivity) getActivity();
        final CheckBox chkPic=activity.getCheckBox_Pic(); //CheckBox for automatic picture download
        final ConfigurationController controller= activity.getController();
        final Button Confirm= activity.getConfirmButton();
        assertTrue(controller.getPicDownloadState());

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
            chkPic.setChecked(false);
            Confirm.performClick();
            assertFalse(controller.getPicDownloadState());
            chkPic.setChecked(true);
            Confirm.performClick();
            assertTrue(controller.getPicDownloadState());
            }
        });
    }
}