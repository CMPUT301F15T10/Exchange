package cmput301exchange.exchange;

/**
 * ModelEnvironment serves as the Global Dumping ground for data related to the phone's instance
 * of the app. It contains an owner and some other later to be announced data.
 *
 *
 */
public class ModelEnvironment {

    private static User owner;

    public static User getOwner() {
        /**
         * Returns the user that is stored as the "main" user of the app
         */
        return owner;
    }

    public void setOwner(String username) {
        /**
         * Sets the main owner of the application
         */
        owner = new User(username);
    }

    private static final ModelEnvironment MODEL_ENVIRONMENT = new ModelEnvironment();
    public static ModelEnvironment getInstance(){return MODEL_ENVIRONMENT;}

}
