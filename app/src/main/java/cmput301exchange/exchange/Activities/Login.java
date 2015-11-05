

package cmput301exchange.exchange.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.Serializers.DataIO;

public class Login extends Activity {
    public EditText username;
    private ModelEnvironment globalENV = new ModelEnvironment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.editText);


        /*
        Button testEmailButton = (Button) findViewById(R.id.testEmailButton);
        testEmailButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SendEmailActivity.class);
                startActivity(intent);
            }
        });
        */
    }

    public void login(View  view) {
        globalENV.setOwner(username.getText().toString());
        DataIO WriteModel = new DataIO(getApplicationContext(), ModelEnvironment.class);
        WriteModel.setFileName("GlobalENV");
        ArrayList sendenv = new ArrayList();
        sendenv.add(globalENV);
        WriteModel.saveInFile(false,sendenv);


        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
