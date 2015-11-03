package cmput301exchange.exchange.Interfaces;

/**
 * Created by touqir on 29/10/15.
 */
public interface Observable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserver(Observer observer);
    void notifyAllObserver();
}
