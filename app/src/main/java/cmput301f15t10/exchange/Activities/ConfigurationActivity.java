package cmput301f15t10.exchange.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import cmput301f15t10.exchange.Controllers.ConfigurationController;
import cmput301f15t10.exchange.R;

public class ConfigurationActivity extends AppCompatActivity {

    private CheckBox chkAutomaticPicDownloads;
    private ConfigurationController myConfigurationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        chkAutomaticPicDownloads= (CheckBox) findViewById(R.id.AutoDownloadcheckBox);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onConfirm(View view) {
        confirm_Handler();
    }

    private void confirm_Handler(){
        if (chkAutomaticPicDownloads.isChecked()==true){
            myConfigurationController.enableAutoPicDownloads();
        }
        else {
            myConfigurationController.disableAutoPicDownloads();
        }
    }
}
