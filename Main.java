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
    int ncol = 40;
    int nrow = 40;
    int sizeSquare = 15;

    static Button buttonBottom(String strings, int left, int bottom){
        Button button= new Button(strings);
        button.setTranslateX(left);
        button.setTranslateY(bottom);
        return button;

    }
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        Scene scene = new Scene(root,ncol*sizeSquare+500, nrow*sizeSquare+ 100);

        //background
        root.setBackground(new Background(new BackgroundFill(Color.rgb(180, 180, 180), null, null)));

        //gridPane definition
        GridPane pane = new GridPane();
        pane.setVgap(1);
        pane.setHgap(5);
        //pane.setPadding(new Insets(1,0,0,1));
        root.getChildren().add(pane);
        GridPane gridPane = new GridPane();
        gridPane.setVgap(1);
        gridPane.setHgap(1);
        gridPane.setPadding(new Insets(1,0,0,1));
        root.getChildren().add(gridPane);

        //case to be drawn in gridPane
        Rectangle[][] gridDraw = new Rectangle [nrow][ncol];

        //init grid and gridPane
        Grid grid = new Grid(nrow,ncol);
        for(int i = 0 ; i < nrow ; i++){
            for(int j = 0 ; j < ncol ; j++){
                gridDraw[i][j] = new Rectangle(0,0,sizeSquare,sizeSquare);
                if(!grid.getcell(i,j).getLive()) gridDraw[i][j].setFill(Color.rgb(150,150,150));
                else gridDraw[i][j].setFill(Color.rgb(200, 120, 0));
                gridPane.add(gridDraw[i][j], i, j);

            }
        }
        pane.add(gridPane,0,0);



        //create buttons
        int left = -625;
        int bottom = 350;
        int stepwise = 15;
        stage.setTitle("Game Life in the Grid");

        Button StartStop = buttonBottom("Start/Stop",left,bottom);
        pane.add(StartStop, 1, 0 );

        Button NextStep = buttonBottom("NextStep",left+stepwise,bottom);
        pane.add(NextStep,2,0 );

        Button Insertion = buttonBottom("Insertion",left+2*stepwise,bottom);
        pane.add(Insertion,3,0 );

        Button Reset = buttonBottom("Reset",left+3*stepwise,bottom);
        pane.add(Reset,4,0);

        Button Rules = buttonBottom("Rules",left+4*stepwise,bottom);
        pane.add(Rules,5,0);

        Button Browse = buttonBottom("Browse",left+5*stepwise,bottom);
        pane.add(Browse,6,0);


        stage.setScene(scene);
        stage.show();

        }
    public static void main(String[] args) {Application.launch(args);}
    }

