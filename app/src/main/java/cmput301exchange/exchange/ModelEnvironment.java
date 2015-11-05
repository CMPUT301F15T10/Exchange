package cmput301exchange.exchange;

/**
 * Created by Charles on 10/16/2015.
 */
public class ModelEnvironment {
    //Experementing with Singletons
    private User owner;

    public User getOwner() {
        return owner;
    }

    public void setOwner(String username) {
        owner = new User(username);
    }

    private static final ModelEnvironment MODEL_ENVIRONMENT = new ModelEnvironment();
    public static ModelEnvironment getInstance(){return MODEL_ENVIRONMENT;}

}
