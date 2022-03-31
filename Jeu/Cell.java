package Jeu;
import javafx.scene.Group;
public class Cell {
    // attributte
    private boolean LiveHere; // Statut

    //constructors
    public Cell(){this.LiveHere = false;}
    public Cell(boolean b){this.LiveHere = b;}


    // getter

    public boolean getLiveHere(){ return this.LiveHere;}

    public int getBinaryState() {
        if (this.LiveHere) {
            return 1;
        }
        else return 0;
    }

    // create or delete a cell
    public void modifyCase(boolean b){this.LiveHere = b;}
}
