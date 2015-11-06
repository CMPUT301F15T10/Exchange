package cmput301exchange.exchange;

/**
 * Created by Charles on 10/16/2015.
 */
public class ModelEnvironment {
    /**
     * ModelEnvironment serves as the Global Dumping ground for data related to the phone's instance
     * of the app. It contains an owner and some other later to be announced data.
     *
     *
     */
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
