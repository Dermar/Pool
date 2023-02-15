package PoolGame.memento;

/**
 * Caretaker for GameMemento objects, which hold a deep copy of a GameWindow's game state
 */
public class GameCaretaker {
    private GameMemento memento;

    // setters and getters
    public GameMemento getMemento(){
        return memento;
    }

    public void setMemento(GameMemento memento){
        this.memento = memento;
    }

}
