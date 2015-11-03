package cmput301f15t10.exchange.Interfaces;

/**
 * Created by touqir on 29/10/15.
 */
public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserver(Observer observer);
    void notifyAllObserver();
}
