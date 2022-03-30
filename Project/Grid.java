package Project;


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
    public Cell getcell(int i, int j){return this.grid[i%nrow][j%ncol];}

    private void setncol(int n){this.ncol = n;}
    private void setnrow(int n){this.nrow = n;}


}
