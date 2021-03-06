import Project.Graph;
import Project.Grid;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;



public class Main extends Application {

    private int ncol = 40;//number of columns of the grid
    private int nrow = 20;//number of row of the grid
    private int sizeSquare = 15;// square size of the cell from the grid
    private int borderv = 10; // vertical gap between the top of the scene and the grid
    private int borderh = 20;// horizontal gap between the left border of the scene and the grid
    private boolean isrunning = false;// value catching click on start  and reset buttons
    private boolean canmodifrule = false;// value catching click on rules button while not running
    private int tbirthmin = 3;// minimal (<= nb neighbor) number of alive neighbors to birth at a certain position
    private int tbirthmax = 3;// maximal (>= nb neighbor) number of alive neighbors to birth at a certain position
    private int tdeathLonelyness = 1;// minimal (<= nb neighbor) number of alive neighbors to die of lonelyness at a certain position
    private int tdeathOverpopulated = 4;// maximal (>= nb neighbor) number of alive neighbors to die of over population at a certain position
    private boolean binfbird = true;
    private boolean bsupbird = true;
    private boolean binfdie = true;
    private boolean bsupdie = true;
    // button creation function
    private Button buttonBottom(String strings, int left, int bottom) {
        /**
         * Create a predefine styled button at the position (x,y) = (left,botom)
         * @param strings  button text,name
         * @param left X position on the scene
         * @param bottom Y position on the scene
         * @return button the desirated button
         */

        //create button
        Button button = new Button(strings);

        //set his layaout, width and height
        button.setLayoutX(left);
        button.setLayoutY(bottom);
        button.setMinWidth(80);
        button.setMinHeight(30);

        //set his color
        button.setStyle("-fx-border-color: white;");
        button.setStyle("-fx-background-color: rgb(180,180,180)");

        return button;
    }

    private ComboBox<Integer> genCb(Group root,int inf, int sup,int thresh,int x, int y) {
        /**
         * Create a Combobox for choosing the threshold value for the death and birth situation
         * @param root the group for the graphical set up
         * @param inf the minimal value could be selected
         * @param sup the maximal value could be selected
         * @param thresh the preselected threshold value
         * @param x x position on the scene
         * @param y y position on the scene
         * @return cb the desirated comboBox
         */

        // create the comboBox of Integers
        ComboBox<Integer> cb= new ComboBox<Integer>();
        root.getChildren().add(cb);
        for (int i = inf;  i<=sup;i++)  cb.getItems().add(i);

        //set the position
        cb.setTranslateX(x);
        cb.setTranslateY( y);

        //preselect the value
        cb.getSelectionModel().select(thresh);

        // allow to choose or not  a value if we can do it
        // i.e. if not running, and if  after a Stop or before Start we clicked on Rules button before
        cb.setDisable(!canmodifrule);

        return cb;
    };

    public void start(Stage stage) throws Exception {


        int stepwise = 100;// a value to create a gap between the button bellow the grid
        int stepwisev = 30 ;// a value to create a vertical gap between the button at the right of the grid
        int left = borderh + 5;// gap between the left of the scene and the first button bellow the grid
        int bottom = Math.max(nrow * (sizeSquare + 1) ,6*stepwisev)+ borderv + 10;// y positon of the buttons bellow the grid
        int interCipher = 210;// gap between the button and comboboxes at the rigth of the grid
        int addh= 400;// add a horizontal gap for button
        int addv = 100;// add a vertical gap for button
        long timegap = (long) 0.5e9;// gap of time between 2 animations while running (started)

        //init graphical resources
        Group root = new Group();
        Scene scene = new Scene(root, Math.max(ncol * sizeSquare, 5*stepwise  ) + borderh + addh,
                Math.max(nrow * sizeSquare, 6*stepwisev )+ addv);
        Canvas canvas = new Canvas(Math.max(ncol * sizeSquare, 5*stepwise  ) + borderh + addh,
                Math.max(nrow * sizeSquare, 6*stepwisev ) + addv);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // background color and title
        gc.setFill(Color.rgb(250,250,220));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stage.setTitle("Game Life on the Grid");

        //init grid , graphical object and draw it
        Grid grid = new Grid(nrow, ncol);
        Graph gr  = new Graph(gc, ncol, nrow, sizeSquare, borderv,borderh);
        gr.paintGrid(grid);

        //create buttons and comboboxes at the right of the grid (threshold buttons)

        Button minForBird = buttonBottom("Min number of neighbors to bird: ",
                Math.max(ncol*(sizeSquare+1) , 6*stepwise)+ borderh +10,borderv +20);
        root.getChildren().add(minForBird);
        ComboBox<Integer> minForBirdcb = genCb(root,0,8,tbirthmin,
                Math.max(ncol*(sizeSquare+1) , 6*stepwise)+ borderh +10+interCipher,borderv +20);
//        minForBirdcb.setOnAction(e->{ //don't allow forbidden values
//            if(minForBirdcb.getSelectionModel().getSelectedItem()>tbirthmax) {
//                minForBirdcb.getSelectionModel().select(tbirthmin);
//            }
//            else tbirthmin = minForBirdcb.getSelectionModel().getSelectedItem()
//                    ;
//        }
//        );


        Button maxForBird = buttonBottom("Max number of neighbors to bird: ",
                Math.max(ncol*(sizeSquare+1) , 6*stepwise)+ borderh +10,borderv+2*stepwisev +20);
        root.getChildren().add(maxForBird);
        ComboBox<Integer> maxForBirdcb = genCb(root,0,8,tbirthmax,
                Math.max(ncol*(sizeSquare+1) , 6*stepwise)+ borderh +10+ interCipher,borderv + 2*stepwisev +20);

        maxForBirdcb.setDisable(true);

        maxForBirdcb.setOnAction(actionEvent -> {
                    if (binfbird) {
                        tbirthmax = maxForBirdcb.getSelectionModel().getSelectedItem();
                        bsupbird = false;
                        minForBirdcb.getItems().clear();
                        if (tbirthmin > tbirthmax) tbirthmin = tbirthmax;
                        for (int i = 0; i <= 8; i++) minForBirdcb.getItems().add(i);
                        minForBirdcb.getSelectionModel().select(tbirthmin);
                        bsupbird = true;

                    }
                }
        );
        minForBirdcb.setOnAction(actionEvent -> {
                    if(bsupbird) {
                        tbirthmin = minForBirdcb.getSelectionModel().getSelectedItem();
                        binfbird = false;
                        maxForBirdcb.getItems().clear();
                        if (tbirthmin > tbirthmax) tbirthmax = tbirthmin;
                        for (int i = 0; i <= 8; i++) maxForBirdcb.getItems().add(i);
                        maxForBirdcb.getSelectionModel().select(tbirthmax);
                        binfbird = true;
                    }

        }
        );

        Button minForDie = buttonBottom("Threshold  of loneliness: ",
                Math.max(ncol*(sizeSquare+1) , 6*stepwise)+ borderh +10,borderv +4* stepwisev +20);
        root.getChildren().add(minForDie);
        ComboBox<Integer> minForDiecb = genCb(root,0,8,tdeathLonelyness,
                Math.max(ncol*(sizeSquare+1) , 6*stepwise)+ borderh +10 + interCipher,borderv+4*stepwisev+20);



        Button maxForDie = buttonBottom("Threshold of overpopulation: ",
                Math.max(ncol*(sizeSquare+1) , 6*stepwise)+ borderh +10,
                borderv+6*stepwisev+20);
        root.getChildren().add(maxForDie);
        ComboBox<Integer> maxForDiecb = genCb(root,0,8,tdeathOverpopulated,
                Math.max(ncol*(sizeSquare+1) , 6*stepwise)+ borderh +10 +interCipher,borderv +20+6*stepwisev    );
        maxForDiecb.setOnAction(actionEvent -> {
                    if(binfdie) {
                        tdeathOverpopulated = maxForDiecb.getSelectionModel().getSelectedItem();
                        bsupdie = false;
                        minForDiecb.getItems().clear();
                        if (tdeathLonelyness > tdeathOverpopulated) tdeathLonelyness = tdeathOverpopulated;
                        for (int i = 0; i <= 8; i++) minForDiecb.getItems().add(i);
                        minForDiecb.getSelectionModel().select(tdeathLonelyness);
                        bsupdie = true;
                    }

                }
        );
        minForDiecb.setOnAction(actionEvent -> {
                    if(bsupdie) {
                        tdeathLonelyness = minForDiecb.getSelectionModel().getSelectedItem();
                        binfdie = false;
                        maxForDiecb.getItems().clear();
                        if (tdeathLonelyness > tdeathOverpopulated) tdeathOverpopulated = tdeathLonelyness;
                        for (int i = 0; i <= 8; i++) maxForDiecb.getItems().add(i);
                        maxForDiecb.getSelectionModel().select(tdeathOverpopulated);
                        binfdie = true;
                    }

                }
        );

        // create button below the grids

        //startStop button
        Button startStop = buttonBottom("Start", left, bottom);
        root.getChildren().add(startStop);
        startStop.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //catch click  event on the button
                isrunning = !isrunning;
                if(isrunning) {
                    //modify button label and don't allow modifying combobxes
                    startStop.setText("Stop");
                    canmodifrule = false;
                    maxForBirdcb.setDisable(true);
                    maxForDiecb.setDisable(true);
                    minForDiecb.setDisable(true);
                    minForBirdcb.setDisable(true);


                }
                else {
                    // modify the label button and don't allow modifying rules
                    startStop.setText("Start");
                    canmodifrule = false;
                };
            }
        });

        //nextStep button
        Button nextStep = buttonBottom("NextStep", left + stepwise, bottom);
        root.getChildren().add(nextStep);
        nextStep.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isrunning) {
                    //modify when we click on the button if not running(started)
                    ArrayList<Integer[]> L = new ArrayList<Integer[]>(grid.selectAliveCellIndex());
                    ArrayList<Integer[]> Lborn = new ArrayList<Integer[]>(grid.ListWillBorn( L, tbirthmin, tbirthmax));
                    ArrayList<Integer[]> Ldead = new ArrayList<Integer[]>(grid.ListWillDead( L, tdeathLonelyness, tdeathOverpopulated));
                    gr.updategrid( grid, Lborn, Ldead);
                }
            }
        });

        //insertion button
        Button insertion = buttonBottom("Insertion", left + 2 * stepwise, bottom);
        root.getChildren().add(insertion);

        //reset button
        Button reset = buttonBottom("Reset", left + 3 * stepwise, bottom);
        reset.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //reset cells values from the grid
                gr.resetCellByClick(grid);
                if(isrunning) {
                    //catch click and modify the button label of the startstop button
                    isrunning = !isrunning;
                    startStop.setText("Start");
                }
            }
        });
        root.getChildren().add(reset);

        Button rules = buttonBottom("Rules", left + 4 * stepwise, bottom);
        root.getChildren().add(rules);
        rules.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isrunning) {
                    //allow (or not) to modify combo boxes
                    canmodifrule = !canmodifrule;
                    maxForBirdcb.setDisable(!canmodifrule);
                    minForBirdcb.setDisable(!canmodifrule);
                    maxForDiecb.setDisable(!canmodifrule);
                    minForDiecb.setDisable(!canmodifrule);
                    //System.out.println(canmodifrule);
                }
            }
        });


        Button backUp = buttonBottom("BackUp", left + 5 * stepwise, bottom);
        root.getChildren().add(backUp);


        scene.setOnMouseClicked(mouseGridClick -> {

                    // positions of the grid
                    if (!isrunning && mouseGridClick.getSceneX() <= borderh + ncol * (sizeSquare + 1) && mouseGridClick.getSceneX() >= borderh
                            && mouseGridClick.getSceneY() <= borderv + nrow * (sizeSquare + 1) && mouseGridClick.getSceneY() >= borderv) {

                        //convert position on the grid to indexes
                        double coordx = mouseGridClick.getSceneX();
                        double coordy = mouseGridClick.getSceneY();
                        int idcol = (int) (coordx - borderh - coordx % 1) / (sizeSquare + 1);
                        int idrow = (int) (coordy - borderv - coordy % 1) / (sizeSquare + 1);
                        //System.out.println(idrow + "  " + idcol);

                        // modifying the cells of the grid if not started or stopped
                        gr.updateCell( grid, idrow, idcol);
                    }
                }
        );

        stage.setScene(scene);
        stage.show();

        // Animation
        AnimationTimer timer = new AnimationTimer() {
            long PrevTime =0;
            @Override
            public void handle(long now) {
                long dt = now-PrevTime;
                if(dt>timegap && isrunning) {
                    PrevTime = now;
                    // modify the grid every dt (1second) during the running and don't allow ot if not started
                    ArrayList<Integer[]> L = new ArrayList<Integer[]>(grid.selectAliveCellIndex());
                    ArrayList<Integer[]> Lborn = new ArrayList<Integer[]>(grid.ListWillBorn( L, tbirthmin,tbirthmax));
                    ArrayList<Integer[]> Ldead = new ArrayList<Integer[]>(grid.ListWillDead( L, tdeathLonelyness, tdeathOverpopulated));
                    gr.updategrid(grid, Lborn, Ldead);

                }
            }
        };
        timer.start();
    }
    public static void main(String[] args) {
        Application.launch(args);
    }

    }


