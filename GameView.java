import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GameView extends GridPane {

    private GameModel model;
    private GridPane gamePane;

    private ListView<String> puzzleList;
    private Label thumbnailLabel;
    private Button gameInitButton;
    private Label timeLabel;
    private TextField timeField;
    private Button[][] tiles;

    boolean gameInitState;

    public ListView<String> getPuzzleList() { return puzzleList; }

    public Button getGameInitButton() { return gameInitButton; }


    private int seconds;
    private int minutes;
    int totalSeconds;

    public GameView(GameModel m) {
        model = m;
        gameInitState = false;

        seconds = 0;
        minutes = 0;
        totalSeconds = 0;

        gamePane = new GridPane();
        gamePane.setPadding(new Insets(0, 0, 0, 0));
        gamePane.setHgap(1);
        gamePane.setVgap(1);
        tiles = new Button[4][4];


        setPadding(new Insets(5, 5, 5, 5));
        setHgap(5);
        setVgap(5);

        add(gamePane, 0, 0, 1, 4);

        thumbnailLabel = new Label();
        thumbnailLabel.setPrefSize(187, 187);
        add(thumbnailLabel, 1, 0, 2, 1);

        puzzleList = new ListView<String>();
        String[] puzzleNames = {"Pets", "Scenery", "Lego", "Numbers"};
        puzzleList.setItems(FXCollections.observableArrayList(puzzleNames));
        puzzleList.setPrefSize(187, 152);
        add(puzzleList, 1, 1, 2, 1);

        gameInitButton = new Button();
        gameInitButton.setAlignment(Pos.CENTER);
        gameInitButton.setPrefSize(187, 20);
        gameInitButton.setDisable(true);
        add(gameInitButton, 1, 2, 2, 1);

        timeLabel = new Label("Time:");
        setHalignment(timeLabel, HPos.LEFT);
        timeLabel.setPrefSize(90, 25);
        setValignment(timeLabel, VPos.TOP);
        add(timeLabel, 1, 3, 1, 1);

        timeField = new TextField();
        timeField.setText("0:00");
        timeField.setAlignment(Pos.CENTER_LEFT);
        timeField.setPrefSize(92, 25);
        setValignment(timeField, VPos.TOP);
        add(timeField, 2, 3, 1, 1);

        update(model);

    }

    public void update(GameModel m) {
        model = m;
        gamePane.getChildren().clear();

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                Button b = new Button();
                tiles[row][col] = b;
                tiles[row][col].setPadding(new Insets(0, 0, 0, 0));
                tiles[row][col].setPrefHeight(187);
                tiles[row][col].setPrefWidth(187);
                tiles[row][col].setDisable(model.getGameCompleted());
                tiles[row][col].setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                tiles[row][col].setGraphic(new ImageView(new Image(getClass().getResourceAsStream(model.getFileName(row, col)))));


                tiles[row][col].setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        for (int row = 0; row < 4; row++) {
                            for (int col = 0; col < 4; col++) {
                                if (event.getSource() == tiles[row][col]) {
                                    model.swaps(row, col);
                                    model.isDone();

                                    if (model.getGameCompleted() == true)
                                        gameInitState = false;

                                    update(model);
                                }

                            }
                        }
                    }
                });
                gamePane.add(tiles[row][col], col, row);
            }
        }

        thumbnailLabel.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(m.getThumbnail()))));

        if (gameInitState == true) {

            if (totalSeconds < 60){
                seconds = totalSeconds;
                minutes = 0;
            }

            else if(totalSeconds >= 60) {
                seconds = totalSeconds % 60 ;
                minutes = totalSeconds / 60;
            }

            if (seconds < 10)
                timeField.setText(String.format("%1d:%2s", minutes, "0" + seconds));

            else
                timeField.setText(String.format("%1d:%2d", minutes, seconds));

            thumbnailLabel.setDisable(true);
            gameInitButton.setText("Stop");
            gameInitButton.setStyle("-fx-font: 12 arial; -fx-base: DARKRED; -fx-text-fill: WHITE;");

            if (model.getGameCompleted() == true)
                gameInitState = false;
        }

        else {
            totalSeconds = 0;
            thumbnailLabel.setDisable(false);
            gameInitButton.setText("Start");
            gameInitButton.setStyle("-fx-font: 12 arial; -fx-base: DARKGREEN; -fx-text-fill: WHITE;");
        }

    }
}

