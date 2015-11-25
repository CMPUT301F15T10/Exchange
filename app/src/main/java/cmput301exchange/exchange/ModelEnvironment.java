package cmput301exchange.exchange;

import android.content.Context;
import android.util.Log;

import cmput301exchange.exchange.Serializers.DataIO;

/**
 * ModelEnvironment serves as the Global Dumping ground for data related to the phone's instance
 * of the app. It contains an owner and some other later to be announced data.
 *
 *
 */
public class ModelEnvironment {

    private User owner;
    private boolean autoPicDownloads=false;
    private TradeManager tradeManager=null;
    private PersonList personList=null;

    public ModelEnvironment(Context myActivity, String userName){

        if (userName==null){
            loadInstance(myActivity);
        } else{
            initOwner(userName);
        }

        if (tradeManager==null){
            tradeManager= new TradeManager();
        }

        if (personList == null){
            personList= new PersonList();
        }

    }

    public boolean isAutoPicDownloadsEnabled(){
        return autoPicDownloads;
    }

    public void setAutoPicDownloads(boolean is){
        autoPicDownloads=is;
    }

    public ModelEnvironment loadInstance(Context myActivity){
        DataIO io = new DataIO(myActivity,ModelEnvironment.class);
        ModelEnvironment instance=io.loadEnvironment("GlobalENV");
        setOwner(instance.getOwner());
        setTradeManager(instance.getTradeManager());
        setPersonList(instance.getPersonList());
        return instance;
    }

    public PersonList getPersonList(){
        return personList;
    }

    public void setPersonList(PersonList list){
        personList=list;
    }

    public void saveInstance(Context myActivity){
        DataIO io = new DataIO(myActivity,ModelEnvironment.class);
        io.saveEnvironment("GlobalENV", this);
    }

    public User getOwner() {
        /**
         * Returns the user that is stored as the "main" user of the app
         */
        return owner;
    }

    public void initOwner(String username) {
        /**
         * Sets the main owner of the application
         */
        owner = new User(username);
    }

    public void setOwner(User owner){
        /**
         * sets owner
         */
        this.owner=owner;
    }

    public void setTradeManager(TradeManager tradeManager){
        this.tradeManager=tradeManager;
    }

    public TradeManager getTradeManager(){
        return tradeManager;
    }


//    private static final ModelEnvironment MODEL_ENVIRONMENT = new ModelEnvironment();
//    public static ModelEnvironment getInstance(){return MODEL_ENVIRONMENT;}

}
