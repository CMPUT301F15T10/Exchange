package cmput301exchange.exchange.Controllers;


import android.graphics.Bitmap;
import java.util.ArrayList;
import cmput301exchange.exchange.Interfaces.Observable;
import cmput301exchange.exchange.Interfaces.Observer;

/**
 * Created by Charles on 11/27/2015.
 */
public class AddBookController implements Observable{
    ArrayList<Observer> observerList = new ArrayList<>();

    @Override
    public void addObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObserver(Observer observer) {
        observer.update();
    }

    @Override
    public void notifyAllObserver() {
        for(Observer i : observerList){
            i.update();
        }
    }

    class ProcessThread extends Thread{
        private Bitmap bitmap;

        public ProcessThread(Bitmap bitmap){
            this.bitmap = bitmap;
        }

        @Override
        public void run(){

        }
    }
}
