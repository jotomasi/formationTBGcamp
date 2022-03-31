import Project.Grid;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main extends Application {
    //@Override
    private int ncol = 40;
    private int nrow = 20;
    private int sizeSquare = 15;
    private int borderv = 10;
    private int borderh = 20;


    // buttons functions
    private Button buttonBottom(String strings, int left, int bottom){
        Button button= new Button(strings);

        button.setLayoutX(left);
        button.setLayoutY(bottom);
        button.setMinWidth(80);
        button.setMinHeight(30);

        button.setStyle("-fx-border-color: white;");
        button.setStyle("-fx-background-color: rgb(180,180,180)");

        return button;

    }


    //graphics functions
    private void paintGrid (GraphicsContext gc, Grid grid){
        int r ;
        int g ;
        int b ;
        for(int i = 0 ; i < grid.getnrow() ; i++){
            for(int j = 0 ; j < grid.getncol() ; j++){

                if(!grid.getcell(i,j).getLive()) {
                    // gray no life here
                    r = 150;
                    g = 150;
                    b = 150;
                }
                else { //yellow alive
                    r = 200 ;
                    g = 120;
                    b = 0;
                }
                gc.setFill(Color.rgb(r,g,b));
                gc.fillRect( borderh+j*(sizeSquare+1),  borderv+i*(sizeSquare+1),  sizeSquare, sizeSquare);
            }
        }
    }

    private void updateCell(GraphicsContext gc, Grid grid,int i,int j){
        int r ;
        int g ;
        int b ;
        int imod = Math.floorMod(i,grid.getnrow());
        int jmod = Math.floorMod(j,grid.getncol());
        grid.getcell(i,j).setLive(!grid.getcell(imod,j).getLive());
        if(!grid.getcell(i,j).getLive()) {
            // gray no life here
            r = 150;
            g = 150;
            b = 150;
        }
        else { //yellow alive
            r = 200 ;
            g = 120;
            b = 0;
        }
        gc.setFill(Color.rgb(r,g,b));
        gc.fillRect( borderh+jmod*(sizeSquare+1),  borderv+imod*(sizeSquare+1),  sizeSquare, sizeSquare);

    }

    private void updateBornedCell(GraphicsContext gc, Grid grid,int i,int j){
        int r = 200 ;
        int g =120;
        int b =0;
        int imod = Math.floorMod(i,grid.getnrow());
        int jmod = Math.floorMod(j,grid.getncol());
        grid.getcell(i,j).setLive(true);
        gc.setFill(Color.rgb(r,g,b));
        gc.fillRect( borderh+jmod*(sizeSquare+1),  borderv+imod*(sizeSquare+1),  sizeSquare, sizeSquare);

    }

    private void updateDeadCell(GraphicsContext gc, Grid grid,int i,int j){
        int r = 150 ;
        int g =150;
        int b =150;
        int imod = Math.floorMod(i,grid.getnrow());
        int jmod = Math.floorMod(j,grid.getncol());
        grid.getcell(i,j).setLive(false);
        gc.setFill(Color.rgb(r,g,b));
        gc.fillRect( borderh+jmod*(sizeSquare+1),  borderv+imod*(sizeSquare+1),  sizeSquare, sizeSquare);

    }

    private void resetCellByClick (GraphicsContext gc, Grid grid){
        int r =150;
        int g =150;
        int b =150;
        for(int i = 0 ; i < grid.getnrow() ; i++){
            for(int j = 0 ; j < grid.getncol() ; j++){
                grid.getcell(i,j).setLive(false);
                grid.getcell(i,j).setLifeTime(false);
                gc.setFill(Color.rgb(r,g,b));
                gc.fillRect( borderh+j*(sizeSquare+1),  borderv+i*(sizeSquare+1),  sizeSquare, sizeSquare);
            }
        }
    }


    //Rules function and usefull fonction for Rules

    private boolean neighborhoodAboveThresh(Grid grid, int i , int j , int t) {
        int cmp = 0;
        for(int k=0;k<=2;k++){
            int l = (int) Math.ceil(k/2.0)%2;
            int c  = 1 - (int) Math.floor(k/2.0);
            if(grid.getcell(i+l,j+c).getLive()) cmp++ ;
            if(grid.getcell(i-l,j-c).getLive()) cmp++ ;
            if (l==1 && c==1 && grid.getcell(i+l,j-c).getLive() ) cmp++;
            if (l==1 && c==1 && grid.getcell(i-l,j+c).getLive() ) cmp++;
        }
        return cmp >= t;

    }

    private boolean neighborhoodBellowThresh(Grid grid, int i , int j , int t) {
        int cmp = 0;
        for(int k=0;k<=2;k++){
            int l = (int) Math.ceil(k/2.0);
            int c  = 1 - (int) Math.floor(k/2.0);
            if(grid.getcell(i+l,j+c).getLive()) cmp++ ;
            if(grid.getcell(i-l,j-c).getLive()) cmp++ ;
            if (l==1 && c==1 && grid.getcell(i+l,j-c).getLive() ) cmp++;
            if (l==1 && c==1 && grid.getcell(i-l,j+c).getLive() ) cmp++;
        }
        return cmp <= t;

    }

    private ArrayList<Integer[]> selectAliveCellIndex(Grid grid){
        ArrayList<Integer[]> L = new ArrayList<Integer[]>();
        for(int i=0; i<nrow;i++){
            for(int j=0; j<ncol; j++){
                if(grid.getcell(i,j).getLive()) L.add(new Integer[]{i, j});
            }
        }
        return L;
    }

    private ArrayList<Integer[]> selectEmptyNeighborIndex(Grid grid,int i,int j){
        ArrayList<Integer[]> Ln = new ArrayList<Integer[]>();
        for(int k=0;k<=2;k++){
            int l = (int) Math.ceil(k/2.0);
            int c  = 1 - (int) Math.floor(k/2.0);
            //System.out.println("l="+l+" , c = "+c);
            if(!grid.getcell(i+l,j+c).getLive()) Ln.add(new Integer[]{i+l,j+c}) ;
            if(!grid.getcell(i-l,j-c).getLive()) Ln.add(new Integer[]{i-l,j-c});
            if (l==1 && c==1 && !grid.getcell(i+l,j-c).getLive() ) Ln.add(new Integer[]{i+l,j-c});
            if (l==1 && c==1 && !grid.getcell(i-l,j+c).getLive() ) Ln.add(new Integer[]{i-l,j+c});
        }
        return Ln;
    }


    private ArrayList<Integer[]> ListWillBorn(Grid grid, ArrayList<Integer[]> Lalive, int t){

        ArrayList<Integer[]> L = new ArrayList<Integer[]>();
        ArrayList<Integer[]> Ln;//list empty neighborhood

        for(Integer[] index : Lalive){
            Ln = new ArrayList<>( selectEmptyNeighborIndex(grid,index[0],index[1]));
            for(Integer[] neighbor : Ln){
                if(neighborhoodAboveThresh(grid,neighbor[0],neighbor[1],t )) L.add(neighbor);
            }
        };
        return L;
    }

    private ArrayList<Integer[]> ListWillDead(Grid grid, ArrayList<Integer[]> Lalive, int tmin,int tmax){

        ArrayList<Integer[]> L = new ArrayList<Integer[]>();
        ArrayList<Integer[]> Ln;//list empty neighborhood

        for(Integer[] index : Lalive) {

            if (neighborhoodAboveThresh(grid, index[0], index[1], tmax)) L.add(index);
            if (neighborhoodBellowThresh(grid, index[0], index[1], tmin)) L.add(index);
        }
        return L;
    }
    
    private void updategrid(GraphicsContext gc, Grid  grid,ArrayList<Integer[]> Lborn,ArrayList<Integer[]> Ldie){
        for(Integer [] indexborn : Lborn) updateBornedCell(gc,grid,indexborn[0],indexborn[1]);
        for(Integer [] indexdie : Ldie) updateDeadCell(gc,grid,indexdie[0],indexdie[1]);
    }
        






    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root,ncol*sizeSquare+500, nrow*sizeSquare+ 150);
        Canvas canvas = new Canvas(ncol*sizeSquare+500, nrow*sizeSquare+ 150);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();




        //init grid and draw it
        Grid grid = new Grid(nrow,ncol);
        grid.getcell(0,10).setLive(true);
        grid.getcell(6,10).setLive(true);

        paintGrid (gc,grid);

        //create buttons
        int left = borderh +5 ;
        int bottom = nrow*(sizeSquare+1)+ borderv + 10;
        int stepwise = 100;

        stage.setTitle("Game Life on the Grid");

        Button startStop = buttonBottom("Start/Stop",left,bottom);
        root.getChildren().add(startStop);

        Button nextStep = buttonBottom("NextStep",left+stepwise,bottom);
        root.getChildren().add(nextStep);
        nextStep.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ArrayList<Integer[]> L = new ArrayList<Integer[]>(selectAliveCellIndex(grid));
                ArrayList<Integer[]> Lborn = new ArrayList<Integer[]>(ListWillBorn(grid,L,2));
                ArrayList<Integer[]> Ldead = new ArrayList<Integer[]>(ListWillDead(grid,L,0,0));
                updategrid(gc,grid,Lborn,Ldead);
            }
        });

        Button insertion = buttonBottom("Insertion",left+2*stepwise,bottom);
        root.getChildren().add(insertion);

        Button reset = buttonBottom("Reset",left+3*stepwise,bottom);
        reset.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                resetCellByClick(gc,grid);
            }
        });
        root.getChildren().add(reset);

        Button rules = buttonBottom("Rules",left+4*stepwise,bottom);
        root.getChildren().add(rules);

        Button backUp = buttonBottom("BackUp",left+5*stepwise,bottom);
        root.getChildren().add(backUp);

/** click mouse*/
        scene.setOnMouseClicked(mouseGridClick -> {
            if (mouseGridClick.getSceneX() <= borderh + ncol * (sizeSquare + 1) && mouseGridClick.getSceneX() >= borderh
                    && mouseGridClick.getSceneY() <= borderv + nrow * (sizeSquare + 1) && mouseGridClick.getSceneY() >= borderv) {


                double coordx = mouseGridClick.getSceneX();
                double coordy = mouseGridClick.getSceneY();
                int idcol= (int) (coordx - borderh - coordx % 1) / (sizeSquare + 1);
                int idrow = (int) (coordy - borderv - coordy % 1) / (sizeSquare + 1);
                System.out.println(idrow + "  " + idcol);
                updateCell(gc, grid, idrow, idcol);
            }
        }
        );

        System.out.println(Math.floorMod(-1,3));
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {Application.launch(args);}
    }

