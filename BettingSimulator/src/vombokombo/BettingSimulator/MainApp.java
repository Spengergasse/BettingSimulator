package vombokombo.BettingSimulator;

import java.io.IOException;

import vombokombo.BettingSimulator.view.LivetickerViewController;
import vombokombo.BettingSimulator.view.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Main");

        initRootLayout();

        showMainView();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showLiveticker() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LivetickerView.fxml"));
            AnchorPane livetickerView = (AnchorPane) loader.load();
            
            Stage livetickerStage = new Stage();
            livetickerStage.setTitle("Liveticker");

            livetickerStage.initModality(Modality.WINDOW_MODAL);
            livetickerStage.initOwner(primaryStage);
            Scene scene = new Scene(livetickerView);
            livetickerStage.setScene(scene);
            
            LivetickerViewController controller = loader.getController();
            controller.setMainApp(this);
            
            livetickerStage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void showMainView() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainView.fxml"));
            AnchorPane mainView = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(mainView);
            
            MainViewController controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}