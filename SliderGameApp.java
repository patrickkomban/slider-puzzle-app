import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

public class SliderGameApp extends Application {
    Timeline updateTimer;
    GameModel   model;
    GameView  view;
    Random rand = new Random();

    public void start(Stage primaryStage) {
        model = new GameModel();
        view = new GameView(model);

        view.getPuzzleList().setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) { handleListMouseClick(); }
        });

        view.getGameInitButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) { handleGameInitButtonPress(); }
        });

        updateTimer = new Timeline(new KeyFrame(Duration.millis(1000),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {

                        view.totalSeconds++;
                        view.update(model);

                    }
                }));
        updateTimer.setCycleCount(Timeline.INDEFINITE);


        primaryStage.setTitle("Slider Puzzle Game");
        primaryStage.setScene(new Scene(view));
        primaryStage.show();

    }

    private void handleListMouseClick() {
        int selectedInd = view.getPuzzleList().getSelectionModel().getSelectedIndex();
        String selected = view.getPuzzleList().getSelectionModel().getSelectedItem();

        if (selectedInd >= 0) {
            view.getGameInitButton().setDisable(false);
            model.setThumbnail(selected + "_Thumbnail.png");
            view.update(model);
        }
    }

    private void handleGameInitButtonPress() {
        if (view.getGameInitButton().getText() == "Start") {

            String selected = view.getPuzzleList().getSelectionModel().getSelectedItem();
            model = new GameModel(selected);
            view.gameInitState = true;

            updateTimer.play();

            int bRow = rand.nextInt(4);
            int bCol = rand.nextInt(4);

            model.replace(bRow,bCol);

            for (int i = 0; i < 5000; i++) {
                int rRow = rand.nextInt(4);
                int rCol = rand.nextInt(4);
                model.swaps(rRow,rCol);
            }

            view.update(model);
        }
        else {
            updateTimer.stop();
            model = new GameModel();
            view.gameInitState = false;
            view.update(model);
        }

    }



    public static void main(String[] args) {
        launch(args);
    }
}
