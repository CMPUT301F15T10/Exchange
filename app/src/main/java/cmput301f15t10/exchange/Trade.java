package cmput301f15t10.exchange;

import java.util.ArrayList;

/**
 * Created by touqir on 03/11/15.
 */
public class Trade {
    private Long ID, personID;
    private ArrayList<Item> items = new ArrayList<Item>() ;
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

    public void addItem(Item item){
        items.add(item);
    }

    public Item getItem(int index){
        return items.get(index);
    }

    public int getStatus(){
        return status;
    }

}