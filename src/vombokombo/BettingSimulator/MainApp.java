package vombokombo.BettingSimulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vombokombo.BettingSimulator.controller.*;
import vombokombo.BettingSimulator.model.Match;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;


public class MainApp extends Application {

    public static final String EURO = "\u20AC";

    public static MainApp mainapp;

    public static Stage primaryStage;
    public static File currentFile = null;
    private static float money;
    private static int matchesWon;
    private static int matchesLost;
    private BorderPane rootLayout;
    private MainViewController mainViewController;
    private Text moneyText;
    private Text matchesWonText;
    private Text matchesLostText;

    /**
     * Returns a property file generated by the local values
     *
     * @return a Properties file
     */
    public static Properties getProperties() {
        Properties props = new Properties();
        props.put("money", money + "");
        props.put("matchesWon", matchesWon + "");
        props.put("matchesLost", matchesLost + "");
        return props;
    }

    /**
     * Sets the local values provided by the Properties file
     * @param props the properties to set
     */
    public static void setProperties(Properties props) {
        mainapp.setSave(Float.parseFloat((String) props.get("money")), Integer.parseInt((String) props.get("matchesWon")), Integer.parseInt((String) props.get("matchesLost")));
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @return the money
     */
    public static float getMoney() {
        return money;
    }

    /**
     * sets the money int and the text
     * @param money the value to set
     */
    public void setMoney(float money) {
        MainApp.money = money;
        DecimalFormat df = new DecimalFormat("0.00");
        moneyText.setText(df.format(money) + " " + MainApp.EURO);
    }

    /**
     * sets the save provided by the 3 parameters
     * @param money
     * @param matchesWon
     * @param matchesLost
     */
    public void setSave(float money, int matchesWon, int matchesLost) {
        setMoney(money);
        setMatchesWon(matchesWon);
        setMatchesLost(matchesLost);
    }

    @Override
    public void start(Stage primaryStage) {
//        Exception ex = new FileNotFoundException("File xyz.txt could not be found!");
//        ExceptionDialog.showExceptionDialog(ex);

//        PropertiesHelper.open();

        mainapp = this;

        MainApp.primaryStage = primaryStage;
        MainApp.primaryStage.setTitle("Main");
        MainApp.primaryStage.getIcons().add(new Image("icon.png"));

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
            rootLayout = loader.load();

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);

            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the liveticker with the provided values
     * @param teamA
     * @param teamB
     * @param betOnA
     * @param moneyBet
     * @param oddsA
     */
    public void showLiveticker(String teamA, String teamB, boolean betOnA, float moneyBet, int oddsA) {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/LivetickerView.fxml"));
            AnchorPane livetickerView = loader.load();

            Stage livetickerStage = new Stage();
            livetickerStage.setTitle("Liveticker");

            livetickerStage.initModality(Modality.WINDOW_MODAL);
            livetickerStage.initOwner(primaryStage);
            Scene scene = new Scene(livetickerView);
            livetickerStage.setScene(scene);
            livetickerStage.setResizable(false);

            LivetickerViewController controller = loader.getController();
            controller.setMainApp(this);
            controller.setImportantThing(teamA, teamB, betOnA, moneyBet, oddsA);

            livetickerStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the main window (in the root layout)
     */
    public void showMainView() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainView.fxml"));
            AnchorPane mainView = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(mainView);

            mainViewController = loader.getController();
            mainViewController.setMainApp(this);
            moneyText = mainViewController.getMoneyText();
            matchesWonText = mainViewController.getMatchesWonText();
            matchesLostText = mainViewController.getMatchesLostText();
            setSave(1000, 0, 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the end of match with the provided values
     * @param status 0=lost; 1=draw; 2=win
     * @param betMoney the amount bet
     */
    public void showEndOfMatchView(int status, float betMoney) {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/EndOfMatchView.fxml"));
            AnchorPane endOfMatchView = loader.load();

            Stage endOfMatchStage = new Stage();
            endOfMatchStage.setTitle("End of match!");

            endOfMatchStage.initModality(Modality.WINDOW_MODAL);
            endOfMatchStage.initOwner(primaryStage);
            Scene scene = new Scene(endOfMatchView);
            endOfMatchStage.setScene(scene);
            endOfMatchStage.setResizable(false);

            EndOfMatchViewController controller = loader.getController();
            controller.setMainApp(this);
            controller.setImportantThings(status, betMoney);


            endOfMatchStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the betting window with the values from the provided match
     * @param match
     */
    public void showBettingView(Match match) {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/BettingView.fxml"));
            Pane bettingView = loader.load();

            Stage bettingViewStage = new Stage();
            bettingViewStage.setTitle("Place your bet!");

            bettingViewStage.initModality(Modality.WINDOW_MODAL);
            bettingViewStage.initOwner(primaryStage);
            Scene scene = new Scene(bettingView);
            bettingViewStage.setScene(scene);
            bettingViewStage.setResizable(false);

            BettingViewController controller = loader.getController();
            controller.setMainApp(this);
            controller.setImportantThings(match);

            bettingViewStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * sets the textfield and the int to the provided value
     * @param matchesWon the number of matches won
     */
    public void setMatchesWon(int matchesWon) {
        MainApp.matchesWon = matchesWon;
        matchesWonText.setText(matchesWon + "");
    }

    /**
     * sets the textfield and the int to the provided value
     * @param matchesLost the number of matches lost
     */
    public void setMatchesLost(int matchesLost) {
        MainApp.matchesLost = matchesLost;
        matchesLostText.setText(matchesLost + "");
    }

    /**
     * increases the number matches won by one
     */
    public void increaseMatchesWon() {
        matchesWon++;
        matchesWonText.setText(matchesWon + "");
    }

    /**
     * increases the number matches lost by one
     */
    public void increaseMatchesLost() {
        matchesLost++;
        matchesLostText.setText(matchesLost + "");
    }
}