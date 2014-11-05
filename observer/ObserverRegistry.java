package spacetrader.observer;

import java.util.HashSet;
import java.util.Set;
import spacetrader.ControlledScreen;

/**
 * Composite to notify each of the registered observers at once.
 *
 * @author Nico de Leon
 */
public class ObserverRegistry implements Observer<ControlledScreen> {
    private final Set<Observer<ControlledScreen>> screenChangeObservers = new HashSet<>();
    
    @Override
    public void notifyChange(ControlledScreen changedScreen) {
        for (Observer observer : screenChangeObservers) {
            observer.notifyChange(changedScreen);
        }
    }
    
    /**
     * Adds an observer to notify when the current ControlledScreen is changed.
     * 
     * @param observer the Observer to notify whenever the current ControlledScreen is changed
     * @return true if the observer was added, false if it had been added previously
     */
    public boolean registerObserver(Observer<ControlledScreen> observer) {
        return screenChangeObservers.add(observer);
    }
}
