package Project;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Graph {
    private GraphicsContext gc;
    private int ncol = 40;
    private int nrow = 20;
    private int sizeSquare = 15;
    private int borderv = 10;
    private int borderh = 20;
    public Graph(GraphicsContext gc, int ncol,int nrow,int sizeSquare,int borderv,int borderh){
        setGraph(gc);
        setncol(ncol);
        setnrow(nrow);
        setSizeSquare(sizeSquare);
        setBorderv(borderv);
        setBorderh(borderh);
    }
    private void setGraph(GraphicsContext gc){this.gc = gc;}
    private void setncol(int ncol){this.ncol = ncol;}
    private void setnrow(int nrow){this.nrow = nrow;}
    private void setSizeSquare(int sizeSquare){this.sizeSquare=sizeSquare;}
    private void setBorderv(int borderv){this.borderv=borderv;}
    private void setBorderh(int borderh){this.borderh = borderh;}

    public GraphicsContext getGc(){return this.gc;}

    public void paintGrid( Grid grid) {
        int r; int g; int b;
        for (int i = 0; i < grid.getnrow(); i++) {
            for (int j = 0; j < grid.getncol(); j++) {

                if (!grid.getcell(i, j).getLive()) {
                    // gray no life here
                    r = 150; g = 150; b = 150;
                } else { //yellow alive
                    r = 200; g = 120; b = 0;
                }
                gc.setFill(Color.rgb(r, g, b));
                gc.fillRect(borderh + j * (sizeSquare + 1), borderv + i * (sizeSquare + 1), sizeSquare, sizeSquare);
            }
        }
    }


    public void updateCell(Grid grid, int i, int j) {
        int r; int g; int b;
        int imod = Math.floorMod(i, grid.getnrow());
        int jmod = Math.floorMod(j, grid.getncol());
        grid.getcell(i, j).setLive(!grid.getcell(imod, j).getLive());
        if (!grid.getcell(i, j).getLive()) {
            // gray no life here
            r = 150; g = 150; b = 150;
        } else { //yellow alive
            r = 200; g = 120; b = 0;
        }
        gc.setFill(Color.rgb(r, g, b));
        gc.fillRect(borderh + jmod * (sizeSquare + 1), borderv + imod * (sizeSquare + 1), sizeSquare, sizeSquare);

    }

    public void updateBornedCell( Grid grid, int i, int j) {
        int r = 200; int g = 120; int b = 0;
        int imod = Math.floorMod(i, grid.getnrow());
        int jmod = Math.floorMod(j, grid.getncol());
        grid.getcell(i, j).setLive(true);
        gc.setFill(Color.rgb(r, g, b));
        gc.fillRect(borderh + jmod * (sizeSquare + 1), borderv + imod * (sizeSquare + 1), sizeSquare, sizeSquare);

    }

    public void updateDeadCell( Grid grid, int i, int j) {
        int r = 150; int g = 150; int b = 150;
        int imod = Math.floorMod(i, grid.getnrow());
        int jmod = Math.floorMod(j, grid.getncol());
        grid.getcell(i, j).setLive(false);
        gc.setFill(Color.rgb(r, g, b));
        gc.fillRect(borderh + jmod * (sizeSquare + 1), borderv + imod * (sizeSquare + 1), sizeSquare, sizeSquare);

    }


    public void updategrid( Grid grid, ArrayList<Integer[]> Lborn, ArrayList<Integer[]> Ldie) {
        for (Integer[] indexborn : Lborn) this.updateBornedCell( grid, indexborn[0], indexborn[1]);
        for (Integer[] indexdie : Ldie) this.updateDeadCell( grid, indexdie[0], indexdie[1]);
    }


    public void resetCellByClick( Grid grid) {
        int r = 150; int g = 150; int b = 150;
        for (int i = 0; i < grid.getnrow(); i++) {
            for (int j = 0; j < grid.getncol(); j++) {
                grid.getcell(i, j).setLive(false);
                grid.getcell(i, j).setLifeTime(false);
                gc.setFill(Color.rgb(r, g, b));
                gc.fillRect(borderh + j * (sizeSquare + 1), borderv + i * (sizeSquare + 1), sizeSquare, sizeSquare);
            }
        }
    }





}



