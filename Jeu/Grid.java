package Jeu;

import java.util.Arrays;

public class Grid {
    
    // grid size
    int x = 20;
    int y = 20;
    
    //constructor
    Cell [][] GridCase = new Cell[20][20];
    public Grid(){
        for(int i = 0; i<x ; i++){
            for (int j = 0 ; j<y;j++){GridCase [i][j] = new Cell(false);}
        }
    }

    public void affiche(){

        for (Cell[] l :GridCase) {
            String s = "";
            for (Cell c : l) s += c.getBinaryState() + " ";
            System.out.println(s);
        }
    }
    @Override
    public String toString(){return "Grid{"+"grilleCase=" + Arrays.toString(GridCase)+"}";}

    public void changeCase (int x, int y , boolean val){
        this.GridCase[x][y].modifyCase(val);
    }

    public static void main(String[] args){
        Grid g = new Grid();
        g.affiche();
        Grid gtest = new Grid();
        gtest.changeCase(5,4,true);
        gtest.affiche();

    }

}
