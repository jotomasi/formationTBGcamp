import Project.Cell;
import Project.Grid;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    //@Override
    private int ncol = 40;
    private int nrow = 40;
    private int sizeSquare = 15;
    private int borderh = 10;
    private int borderv = 20;

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
                gc.fillRect( borderh+i*(sizeSquare+1),  borderv+j*(sizeSquare+1),  sizeSquare, sizeSquare);
            }
        }
    }

    private void updateCellByClick(GraphicsContext gc, Grid grid,int i,int j){
        int r ;
        int g ;
        int b ;
        int imod = i%grid.getnrow();
        int jmod = j%grid.getncol();
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
        gc.fillRect( borderh+imod*(sizeSquare+1),  borderv+jmod*(sizeSquare+1),  sizeSquare, sizeSquare);

    }

    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root,ncol*sizeSquare+500, nrow*sizeSquare+ 150);
        Canvas canvas = new Canvas(ncol*sizeSquare+500, nrow*sizeSquare+ 150);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();




        //init grid and draw it
        Grid grid = new Grid(nrow,ncol);
        grid.getcell(5,10).setLive(true);
        paintGrid (gc,grid);
        updateCellByClick(gc, grid, 5,10);
        //updateCellByClick(gc, grid, 5,10);

        //create buttons
        int left = borderh +5 ;
        int bottom = nrow*(sizeSquare+1)+ borderv + 10;
        int stepwise = 100;

        stage.setTitle("Game Life on the Grid");

        Button StartStop = buttonBottom("Start/Stop",left,bottom);
        root.getChildren().add(StartStop);

        Button NextStep = buttonBottom("NextStep",left+stepwise,bottom);
        root.getChildren().add(NextStep);

        Button Insertion = buttonBottom("Insertion",left+2*stepwise,bottom);
        root.getChildren().add(Insertion);

        Button Reset = buttonBottom("Reset",left+3*stepwise,bottom);
        root.getChildren().add(Reset);

        Button Rules = buttonBottom("Rules",left+4*stepwise,bottom);
        root.getChildren().add(Rules);

        Button BackUp = buttonBottom("BackUp",left+5*stepwise,bottom);
        root.getChildren().add(BackUp);

/** add mouse */
        scene.setOnMouseClicked(mouseEventpositon -> {
            if (mouseEventpositon.getSceneX() <= borderh + nrow * (sizeSquare + 1) && mouseEventpositon.getSceneX() >= borderh
                    && mouseEventpositon.getSceneY() <= borderv + ncol * (sizeSquare + 1) && mouseEventpositon.getSceneY() >= borderv) {


                double coordx = mouseEventpositon.getSceneX();
                double coordy = mouseEventpositon.getSceneY();
                int icoordx = (int) (coordx - borderh - coordx % 1) / (sizeSquare + 1);
                int icoordy = (int) (coordy - borderv - coordy % 1) / (sizeSquare + 1);
                System.out.println(icoordx + "  " + icoordy);
                updateCellByClick(gc, grid, icoordx, icoordy);
            }
        }
        );




        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {Application.launch(args);}
    }

