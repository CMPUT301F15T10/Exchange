package cmput301exchange.exchange.Serializers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;
import android.provider.ContactsContract;

import cmput301exchange.exchange.Inventory;
import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.R;
import cmput301exchange.exchange.User;

/**
 * Created by touqir on 28/09/15.
 */

// I have used pieces of code from lab3 for saveInFile and loadFromFile
// (https://github.com/touqir14/lonelyTwitter/blob/f15tuesday/app/src/main/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivity.java)
// I have also used http://stackoverflow.com/questions/3403909/get-generic-type-of-class-at-runtime for getting class of generic types.

public class DataIO<DataClass> {
    /**
     * @author Touqir, Chuck
     *
     * Used for writing and saving data to the disk. Should contain type-safe methods in the future.
     *
     */

    private Context context;
    private Class dataClassType;
    private ElasticSearch elasticSearch = new ElasticSearch();

    public DataIO(Context context, Class<DataClass> dataClass){
        this.context=context;
        dataClassType=dataClass;
    }


    public String loadFromFile(String filename){
        // http://stackoverflow.com/questions/19459082/read-and-write-data-with-gson

        StringBuilder sb;

        try {
            FileInputStream fis = context.openFileInput(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

    public void saveInFile(String filename, String dataToSave) {
        // http://stackoverflow.com/questions/19459082/read-and-write-data-with-gson

        try {
            FileOutputStream fos = context.openFileOutput(filename, 0);
            fos.write(dataToSave.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

//    public void deleteData(){
//        /**
//         * Overwrites the save data at saveFileName()
//         */
//        saveInFile(false,new ArrayList<DataClass>());
//    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    public ModelEnvironment loadEnvironment(String filename){
        /**
         * Updated, loads the ModelEnvironment. Only for this class, no others.
         * @param filename the name of where the file is stored.
         */
        //removed the needless mental acrobatics needed to use the above.
        ModelEnvironment DataEnviro;
        try {
            FileInputStream fis = context.openFileInput(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //Type listType = new TypeToken<DataWrapper<DataClass>>(){}.getType();
            DataEnviro = gson.fromJson(in,ModelEnvironment.class);
            //Log.e("From DataIO, type of loadedData", size.toString());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



//        if (elasticSearch.UserExists(DataEnviro.getOwner().getName()) && // If There Exists a User by this name in the database
//                elasticSearch.fetchUser(DataEnviro.getOwner().getName()).getTimeStamp()
//                        > //And their timestamp is better than ours.
//                DataEnviro.getOwner().getTimeStamp()){
//            DataEnviro.setOwner(elasticSearch.fetchUser(DataEnviro.getOwner().getName())); //Fetch the owner and make him ours
//        }
        return DataEnviro;
    }
    public void saveEnvironment(String filename, ModelEnvironment globalenv){
        /**
         * saves the given ModelEnvironment to a file
         *
         * @param filename the save location
         * @param globalenv the ModelEnvironment you wish to save
         */
    //Removed the needless mental acrobatics needed to understand the above.
        globalenv.getOwner().setTimeStamp();
        try {
            FileOutputStream fos = context.openFileOutput(filename,
                    0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(globalenv, writer);
            writer.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




}
