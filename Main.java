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
    public void start(Stage stage) throws Exception {
        VBox root = new VBox();
        Scene scene = new Scene(root,ncol*sizeSquare+500, nrow*sizeSquare+ 100);

        //background
        root.setBackground(new Background(new BackgroundFill(Color.rgb(180, 180, 180), null, null)));

        //gridPane definition
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






        stage.setScene(scene);
        stage.show();

        }
    public static void main(String[] args) {Application.launch(args);}
    }

