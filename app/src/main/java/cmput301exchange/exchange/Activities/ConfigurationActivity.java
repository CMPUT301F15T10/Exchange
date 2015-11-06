package cmput301exchange.exchange.Activities;

import android.preference.PreferenceActivity;
import android.os.Bundle;

import cmput301exchange.exchange.R;

public class ConfigurationActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.configuration);
    }
}