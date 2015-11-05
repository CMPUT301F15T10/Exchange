package cmput301f15t10.exchange;

import java.util.ArrayList;

/**
 * Created by touqir on 03/11/15.
 */
public class Trade {
    private Long ID, personID;
    private ArrayList<Book> myItems = new ArrayList<Book>(), friendItems=new ArrayList<Book>() ;
    private int status;

    public Trade(){
    }

    public void setTradeID(Long id){
        ID=id;
    }
    public Long getTradeID(){
        return ID;
    }

    public void setUser(Long personID){
        this.personID=personID;
    }

    public Long getPersonID(){
        return personID;
    }

    public void addItem(Book item){
        myItems.add(item);
    }

    public Book getItem(int index){
        return myItems.get(index);
    }

    public int getStatus(){
        return status;
    }

    public ArrayList<Book> getListBookUser(){
        return myItems;
    }

    public ArrayList<Book> getListBookPartner(){
        return friendItems;
    }

}