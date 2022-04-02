package Project;


import java.util.ArrayList;

public class Grid {
    //attribute
    private int ncol = 1; // nombre de colonnes
    private int nrow = 1; // nombre de ligne
    private Cell[][] grid;


    //Constructors
    public Grid(int l,int c){
        //set cols and rows
        if(c>0)setncol(c);
        if(l>0)setnrow(l);
        // create the  Array of Cells
        grid = new Cell[nrow][ncol];
        for(int i = 0; i<nrow ; i++){
            for (int j = 0 ; j<ncol;j++){ grid[i][j] = new Cell();}
        }
    }

    // getter and setter
    public int getncol(){return this.ncol;}
    public int getnrow(){return this.nrow;}
    public Cell getcell(int i, int j){return this.grid[Math.floorMod(i,nrow)][Math.floorMod(j,ncol)];}

    private void setncol(int n){this.ncol = n;}
    private void setnrow(int n){this.nrow = n;}


    //Rules function and usefull fonction for Rules

    private boolean neighborhoodAboveThresh( int i, int j, int t) {
        /**
         * check if the number of neighbor of the cell at the i,j position is above (>=) the threshold
         * @param i i position on the grid ( i row)
         * @param j j position on the grid ( j col)
         * @param t the threshold value to compare
         * @return a boolean true if the number of neighbor is >= to the threshold, false else
         */
        int cmp = 0;
        for (int k = 0; k <= 2; k++) {
            int l = (int) Math.ceil(k / 2.0) % 2;
            int c = 1 - (int) Math.floor(k / 2.0);
            if (this.getcell(i + l, j + c).getLive()) cmp++;
            if (this.getcell(i - l, j - c).getLive()) cmp++;
            if (l == 1 && c == 1 && this.getcell(i + l, j - c).getLive()) cmp++;
            if (l == 1 && c == 1 && this.getcell(i - l, j + c).getLive()) cmp++;
        }
        return cmp >= t;

    }

    private boolean neighborhoodBellowThresh( int i, int j, int t) {
        /**
         * check if the number of neighbor of the cell at the i,j position is under (<=) the threshold
         * @param i i position on the grid ( i row)
         * @param j j position on the grid ( j col)
         * @param t the threshold value to compare
         * @return a boolean true if the number of neighbor is <= to the threshold , false else
         */
        int cmp = 0;
        for (int k = 0; k <= 2; k++) {
            int l = (int) Math.ceil(k / 2.0);
            int c = 1 - (int) Math.floor(k / 2.0);
            if (this.getcell(i + l, j + c).getLive()) cmp++;
            if (this.getcell(i - l, j - c).getLive()) cmp++;
            if (l == 1 && c == 1 && this.getcell(i + l, j - c).getLive()) cmp++;
            if (l == 1 && c == 1 && this.getcell(i - l, j + c).getLive()) cmp++;
        }
        return cmp <= t;

    }

    
    
    
    public ArrayList<Integer[]> selectAliveCellIndex() {
        /**
         * catch the positions of the alive cells from the grid
         * @return a ArrayList of couple of integers postions (row,col)
         */
        ArrayList<Integer[]> L = new ArrayList<Integer[]>();
        for (int i = 0; i < nrow; i++) {
            for (int j = 0; j < ncol; j++) {
                if (this.getcell(i, j).getLive()) L.add(new Integer[]{i, j});
            }
        }
        return L;
    }

    public ArrayList<Integer[]> selectEmptyNeighborIndex( int i, int j) {
        /**
         * catch the positions of the unhabited cases close to an alive cell at the position i,j
         * @param i ith row of the alive cell to analyse
         * @param j jth col of the alive cell to analyse
         * @return a ArrayList of couple of integers postions (row,col)
         */
        ArrayList<Integer[]> Ln = new ArrayList<Integer[]>();
        for (int k = 0; k <= 2; k++) {
            int l = (int) Math.ceil(k / 2.0);
            int c = 1 - (int) Math.floor(k / 2.0);
            //System.out.println("l="+l+" , c = "+c);
            if (!this.getcell(i + l, j + c).getLive()) Ln.add(new Integer[]{i + l, j + c});
            if (!this.getcell(i - l, j - c).getLive()) Ln.add(new Integer[]{i - l, j - c});
            if (l == 1 && c == 1 && !this.getcell(i + l, j - c).getLive()) Ln.add(new Integer[]{i + l, j - c});
            if (l == 1 && c == 1 && !this.getcell(i - l, j + c).getLive()) Ln.add(new Integer[]{i - l, j + c});
        }
        return Ln;
    }


    public ArrayList<Integer[]> ListWillBorn(ArrayList<Integer[]> Lalive, int tmin,int tmax) {
        /**
         * catch the positions of the cases where a cell will born
         * @param Lalive
         * @return a ArrayList of couple of integers postions (row,col)
         */
        ArrayList<Integer[]> L = new ArrayList<Integer[]>();
        ArrayList<Integer[]> Ln;//list empty neighborhood

        for (Integer[] index : Lalive) {
            Ln = new ArrayList<>(this.selectEmptyNeighborIndex( index[0], index[1]));
            for (Integer[] neighbor : Ln) {
                if (this.neighborhoodAboveThresh(neighbor[0], neighbor[1], tmin)&& this.neighborhoodBellowThresh(neighbor[0], neighbor[1], tmax)) L.add(neighbor);
            }
        }
        ;
        return L;
    }

    public ArrayList<Integer[]> ListWillDead( ArrayList<Integer[]> Lalive, int tmin, int tmax) {

        ArrayList<Integer[]> L = new ArrayList<Integer[]>();
        ArrayList<Integer[]> Ln;//list empty neighborhood

        for (Integer[] index : Lalive) {

            if (this.neighborhoodAboveThresh(index[0], index[1], tmax)) L.add(index);
            if (this.neighborhoodBellowThresh(index[0], index[1], tmin)) L.add(index);
        }
        return L;
    }













}
