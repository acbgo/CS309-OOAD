public interface Subject <T>{
    //when create a ball object, it is needed to register the ball object into
    public void registerObserver(T t);
    // when restart a new game, it is needed to remove all observer from
    public void removeObserver();

    // when clicked keyboard, it is needed to notify all observers to update
    public void notifyObservers(char keyChar);
    // For task 2: when whiteRandom ball moved, it is needed to notify all
    public void notifyObservers();

    // or For task2 public void notifyObservers(int x, int y);
}
