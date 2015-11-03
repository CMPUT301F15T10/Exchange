package cmput301exchange.exchange.Serializers;

import java.util.ArrayList;

/**
 * Created by touqir on 04/10/15.
 */
public class DataWrapper<DataClass>{
    private ArrayList<Object> myData;

    public DataWrapper(Class dataClassType,ArrayList<Object> toSave) {
        switch (getType(dataClassType)) {
            case 0:
                myData=toSave;
                return;
            case 1://Incase of Long
                myData = wrapLong(toSave);
                return;
            case 2://Incase of Array<Integer>
                myData = wrapInteger(toSave);
                myData=toSave;
                return;
        }
    }

    public ArrayList<Object> wrapLong(ArrayList<Object> data){
        return storeLongToDoubleArrayList(data);
    }

    public ArrayList<Object> wrapInteger(ArrayList<Object> data){
        return storeIntegerToDoubleArrayList(data);
    }

    private ArrayList<Object> storeIntegerToDoubleArrayList(ArrayList<Object> data) {
        ArrayList<Object> myDoubleArray= new ArrayList<Object>();
        for (Object datum: data){
            Integer temporary_data= (Integer) datum;
            myDoubleArray.add(temporary_data.doubleValue());
        }
        return myDoubleArray;
    }

    private ArrayList<Object> loadDoubleToIntegerArrayList(ArrayList<Object> data) {
        ArrayList<Object> myIntegerArray= new ArrayList<Object>();
        for (Object datum: data){
            Double temporary_data=(Double) datum;
            myIntegerArray.add(temporary_data.intValue());
        }
        return myIntegerArray;
    }

    private ArrayList<Object> storeLongToDoubleArrayList(ArrayList<Object> data){
        ArrayList<Object> myDoubleArray= new ArrayList<Object>();
        for (Object datum: data){
            Long temporary_data= (Long) datum;
            myDoubleArray.add(temporary_data.doubleValue());
        }
        return myDoubleArray;
    }

    private ArrayList<Object> loadDoubleToLongArrayList(ArrayList<Object> data){
        ArrayList<Object> myLongArray= new ArrayList<Object>();
        for (Object datum: data){
            Double temporary_data=(Double) datum;
            myLongArray.add(temporary_data.longValue());
        }
        return myLongArray;
    }

    public ArrayList<DataClass> getMyData(Class outputClassType){
        switch (getType(outputClassType)){
            case 0:
                return (ArrayList<DataClass>) myData;

            case 1:
                return (ArrayList<DataClass>)loadDoubleToLongArrayList(myData);

            case 2:
                return (ArrayList<DataClass>)loadDoubleToIntegerArrayList(myData);


        }
        return (ArrayList<DataClass>) myData;
    }

    private int getType(Class dataClassType){
        if (dataClassType==Long.class){
            return 1;
        }
        if (dataClassType==Integer.class){
            return 2;
        }
        return 0;
    }

}

