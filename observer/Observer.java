package spacetrader.observer;

/**
 * Simple interface for classes that need to be notified of a change in a certain aspect of state.
 *
 * @author Nico de Leon
 * @param <T> the type of the changed object to watch for
 */
public interface Observer<T> {
    /**
     * This method is called every time the current object of type T is changed.
     *
     * @param changedObject the new (or modified) T
     */
    public void notifyChange(T changedObject);
}
