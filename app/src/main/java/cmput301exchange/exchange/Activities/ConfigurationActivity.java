package cmput301exchange.exchange.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.gson.Gson;

import cmput301exchange.exchange.Controllers.ConfigurationController;
import cmput301exchange.exchange.Person;
import cmput301exchange.exchange.R;

public class ConfigurationActivity extends AppCompatActivity {

    private CheckBox chkAutomaticPicDownloads;
    private ConfigurationController myConfigurationController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        chkAutomaticPicDownloads= (CheckBox) findViewById(R.id.AutoDownloadcheckBox);
        myConfigurationController= new ConfigurationController();
        init();
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

    public CheckBox getCheckBox_Pic(){
        return chkAutomaticPicDownloads;
    }

    public ConfigurationController getController(){
        return myConfigurationController;
    }

    /**
     * Used to confirm the automatic download of pictures
     */
    public void confirm_Handler(){
        if (chkAutomaticPicDownloads.isChecked()){
            myConfigurationController.enableAutoPicDownloads();
        } else {
            myConfigurationController.disableAutoPicDownloads();
        }
        finish();
    }

    public Button getConfirmButton(){
        return (Button) findViewById(R.id.C_confirm);
    }

    public void init(){
        Intent intent=getIntent();
        myConfigurationController.setAutoPictureDownload(intent.getExtras().getBoolean("Configuration_picDown"));
        chkAutomaticPicDownloads.setChecked(myConfigurationController.getPicDownloadState());
    }
    @Override
    public void finish() {
        Intent intent=new Intent();
        intent.putExtra("Configuration_picDown",myConfigurationController.getPicDownloadState());
        setResult(RESULT_OK, intent);
        super.finish();
    }
}
