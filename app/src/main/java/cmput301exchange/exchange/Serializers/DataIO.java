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

import cmput301exchange.exchange.ModelEnvironment;
import cmput301exchange.exchange.R;

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
    private String FILENAME=null;
    private Class dataClassType;
//////////////////////////////////////////////////////////////////////////////////////////////////////

    public DataIO(Context context, Class<DataClass> dataClass){
        this.context=context;
        dataClassType=dataClass;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setFileName(String fileName){
        this.FILENAME=fileName;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////

    public ArrayList<DataClass> loadFromFile(){
        /**
         * @deprecated
         * Type Correct implementation of load from file, Might work for other classes, but for
         * our use so far, there are issues
         *
         * Loads the required arraylist from a file after having filename set by setFileName
         */

        if (FILENAME==null){
            throw new RuntimeException("For loading file from DataIO class, filename has not been provided.");
        }

        DataWrapper<DataClass> myDataWrapper;
        ArrayList<DataClass> dataList;
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<DataWrapper<DataClass>>(){}.getType();
            myDataWrapper = gson.fromJson(in, listType);
            dataList=myDataWrapper.getMyData(dataClassType);
            //Log.e("From DataIO, type of loadedData", size.toString());

        } catch (FileNotFoundException e) {
            return new ArrayList<DataClass>();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataList;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    public void saveInFile(boolean isLoadFromFile,ArrayList<DataClass> dataToSave) {
        /**
         * saves the required arraylist to a file of the name that has been set by setFileName
         *
         * @param isLoadFromFile boolean describing whether the file has previously been saved
         * @param datatosave an ArrayList of the data that you wish to save
         *
         *
         */

        ArrayList<DataClass> toWrite;
        if (isLoadFromFile==true) {
            toWrite=loadFromFile();
            toWrite.addAll(dataToSave);
        }
        else {
            toWrite=dataToSave;
        }

        DataWrapper<DataClass> myDataWrapper=new DataWrapper<DataClass>(dataClassType,(ArrayList<Object>)toWrite);
        try {
//            Log.e("From DataIO, type of saveInFile", dataToSave.get(0).getClass().toString());
            FileOutputStream fos = context.openFileOutput(FILENAME,
                    0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(myDataWrapper, writer);
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

////////////////////////////////////////////////////////////////////////////////////////////////////

    public void deleteData(){
        /**
         * Overwrites the save data at saveFileName()
         */
        saveInFile(false,new ArrayList<DataClass>());
    }

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
            return new ModelEnvironment();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
