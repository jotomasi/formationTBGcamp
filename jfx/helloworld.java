package jfx;//import com.sun.javafx.stage.EmbeddedWindow;
//import com.sun.javafx.stage.EmbeddedWindow;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
//import  javafx.stage.Screen;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class helloworld extends Application {


    //    Scene scene = new scene(root,height,width);
//
//    scene.setFill(Color.WHITE);
//    stage.setTitle("test")
//
//   //add text
//    Text text = new Text();
//    text.setFont(new Font(v 45));
//    text.setsiza
    @Override
    public void start(Stage stage) throws Exception {


        Group root = new Group();
        Scene theScene = new Scene(root);
        stage.setScene(theScene);
        Canvas canvas = new Canvas( 600, 600 );
        root.getChildren().add( canvas );
        GraphicsContext gc =canvas.getGraphicsContext2D();
        gc.setFill( Color.BLUE );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gc.setFont( theFont );
        gc.fillText( "Hello, World!", 40, 50 );
        gc.strokeText( "Hello, World!", 40, 50 );
        Image earth = new Image( "https://nimax-img.de/Produktbilder/zoom/59661_1/Globe-a-relief-AstroReality-EARTH.jpg" );
        gc.drawImage( earth, 40, 40 );
        addButton(root);
        // drawImage(Image, x,y,hauteur,largeur)
        stage.show();
    }

    private void addButton(Group root){
        Button button = new Button("Test");
        root.getChildren().add(button);
        button.setTranslateX(100);
        button.setTranslateY(100);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(("click"));
            }
        });
        //Image image = new ImageView("Ressource/bouton.jpg");


    }
}