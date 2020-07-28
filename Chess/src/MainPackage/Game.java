package MainPackage;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class Game {
    private Pane chessBoardPane = new Pane();
    private AnchorPane anchorPane;
    private BorderPane mainPane;
    private ChessBoard chessBoard;
    FXMLLoader fxmlLoader;
    GamePaneController controller;
    Stage primaryStage;
    ClientManage client;
    Pane menuPane;
    StackPane createGamePane, joinGamePane;
    HBox bottomBox;
    TextField codeField;
    Text joinGameNotification;

    /*Game(DatabaseUserManage ds){
        this.ds = ds;
    }*/
    Game(){
        chessBoard = new ChessBoard();
    }
    Game(ClientManage client){
        this.client = client;
        chessBoard = new ChessBoard(client);
    }



    public void init(){
        menuPane = createMenuPane();
        createGamePane = createNewGamePane("loading...");
        joinGamePane = createJoinGamePane();
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("/FXMLS/game_layout.fxml"));
            anchorPane = fxmlLoader.load();
            controller = fxmlLoader.getController();
            controller.setGame(this);
            anchorPane.getChildren().add(menuPane);
            anchorPane.getChildren().add(createGamePane);
            anchorPane.getChildren().add(joinGamePane);
            mainPane = new BorderPane();
//            fxmlLoader.setController(controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage=new Stage();
        chessBoardPane = chessBoard.createMainPane();
        mainPane = controller.getBorderPane();
        mainPane.setCenter(chessBoardPane);
        //controller.setBorderPane(mainPane);

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<51; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
//        if(list.get(25)%2==0){
//            chessBoard.distributeItems(1,1);
//        }
//        else{
//            x = true;
//            chessBoard.distributeItems(1,2);
//        }
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(anchorPane));
        primaryStage.show();
//        System.out.println(primaryStage.getWidth()+", "+primaryStage.getHeight());

        showMenu();
    }
    public void initPracticeOffline(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<51; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        boolean x = false;
        if(list.get(25)%2==0){
            chessBoard.distributeItems(1,1);
        }
        else{
            x = true;
            chessBoard.distributeItems(1,2);
        }
        chessBoard.showFirstTurnStatus();
        if(x) chessBoard.doARotation(100);
    }


    public Pane createMenuPane(){
        Pane pane = new Pane();
        HBox hBox = new HBox(7.5);
        StackPane stackPane = new StackPane();
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(135);
        rectangle.setWidth(440);
        rectangle.setArcHeight(50);
        rectangle.setArcWidth(50);
        rectangle.setStyle("-fx-fill: darkslategray");

        hBox.setAlignment(Pos.CENTER);
        hBox.setPrefWidth(440);
        hBox.setPrefHeight(140);
        Button createGameButton, practiceGameButton, joinGameButton;
        createGameButton = new Button("Create Game");
        practiceGameButton = new Button(" Practice Offline");
        joinGameButton = new Button("Join Game");
        createGameButton.setPrefWidth(130);
        practiceGameButton.setPrefWidth(130);
        joinGameButton.setPrefWidth(130);
        createGameButton.setOnAction(e -> createGame());
        practiceGameButton.setOnAction(e -> practiceOffline());
        joinGameButton.setOnAction(e -> joinGame());
        hBox.getChildren().addAll(createGameButton, practiceGameButton, joinGameButton);
        stackPane.getChildren().addAll(rectangle, hBox);
        pane.getChildren().add(stackPane);
        pane.setVisible(false);
        pane.setDisable(true);
        return pane;
    }
    public void showMenu(){
        System.out.println("Called!");
        TranslateTransition tt = new TranslateTransition(Duration.millis(750));
        tt.setFromX(20);
        tt.setToX(20);
        tt.setFromY(mainPane.getHeight());
        tt.setToY(mainPane.getHeight() - 160);
        tt.setNode(menuPane);
        tt.play();
        menuPane.setDisable(false);
        menuPane.setVisible(true);
    }
    public void hideMenu(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(750));
        tt.setFromX(20);
        tt.setToX(20);
        tt.setFromY(mainPane.getHeight() - 155);
        tt.setToY(mainPane.getHeight());
        tt.setNode(menuPane);
        tt.play();
    }

    public StackPane createNewGamePane(String code){
        VBox vBox = new VBox(5);

        HBox hBox1 = new HBox();
        hBox1.setPrefWidth(400);
        hBox1.setPrefHeight(75);
        hBox1.setAlignment(Pos.BOTTOM_CENTER);
        Text text1 = new Text("New Game");
        text1.setStyle("-fx-fill: darkslateblue;"+
                "-fx-font-size: 36;");
        hBox1.getChildren().add(text1);

        HBox hBox2 = new HBox();
        hBox2.setPrefWidth(400);
        hBox2.setPrefHeight(30);
        hBox2.setAlignment(Pos.BOTTOM_CENTER);
        Text text2 = new Text("Share the code with your friend...");
        text2.setStyle("-fx-font-size: 18;");
        hBox2.getChildren().add(text2);

        HBox hBox3 = new HBox();
        hBox3.setPrefWidth(200);
        hBox3.setPrefHeight(25);
        hBox3.setAlignment(Pos.CENTER_RIGHT);
        Text text3 = new Text("Code: ");
        text3.setStyle("-fx-fill: chocolate;"+
                "-fx-font-size: 36;");
        hBox3.getChildren().add(text3);

        HBox hBox4 = new HBox();
        hBox4.setPrefWidth(200);
        hBox4.setPrefHeight(25);
        hBox4.setAlignment(Pos.CENTER_LEFT);
        Text text4 = new Text(code);
        text4.setStyle("-fx-fill: BLACK;"+
                "-fx-font-size: 18;");
        hBox4.getChildren().add(text4);

        HBox hbox = new HBox(hBox3, hBox4);
        hbox.setPrefHeight(80);
        hbox.setAlignment(Pos.CENTER);

        HBox hBox5 = new HBox();
        hBox5.setPrefWidth(200);
        hBox5.setPrefHeight(40);
        hBox5.setAlignment(Pos.CENTER_RIGHT);
        Text text5 = new Text("Select Color: ");
        text5.setStyle("-fx-fill: black;"+
                "-fx-font-size: 24;");
        hBox5.getChildren().add(text5);

        HBox hBox6 = new HBox(5);
        hBox6.setPrefWidth(200);
        hBox6.setPrefHeight(40);
        hBox6.setAlignment(Pos.CENTER_LEFT);

        Rectangle whiteRec = new Rectangle(50, 20);
        whiteRec.setFill(Color.WHITE);
        whiteRec.setOnMouseClicked(e -> sendToCreate("WHITE"));
        Rectangle blackRec = new Rectangle(50, 20);
        blackRec.setFill(Color.BLACK);
        blackRec.setOnMouseClicked(e -> sendToCreate("BLACK"));
        hBox6.getChildren().addAll(whiteRec, blackRec);

//        hBox1.setStyle("-fx-background-color: crimson");
//        hBox2.setStyle("-fx-background-color: crimson");
//        hBox3.setStyle("-fx-background-color: crimson");
//        hBox4.setStyle("-fx-background-color: crimson");
//        hBox5.setStyle("-fx-background-color: crimson");
//        hBox6.setStyle("-fx-background-color: crimson");


        bottomBox = new HBox(hBox5, hBox6);

        vBox.getChildren().addAll(hBox1, hBox2, hbox, bottomBox);


        Rectangle rectangle = new Rectangle();
        rectangle.setStyle("-fx-fill: lightslategray");
        rectangle.setWidth(400);
        rectangle.setHeight(300);
        rectangle.setArcWidth(60);
        rectangle.setArcHeight(60);

        StackPane sp = new StackPane(rectangle, vBox);
        sp.setVisible(false);
        sp.setDisable(true);
        return sp;
    }
    private void sendToCreate(String color) {
        //System.out.println(color);
        bottomBox.setDisable(true);
        bottomBox.setVisible(false);
        client.sendToServer("CREATE_NEW_GAME/"+color);
    }
    public void setCreateGameCode(String code){
        try {
            VBox vBox = (VBox) createGamePane.getChildren().get(1);
            HBox hBox = (HBox) vBox.getChildren().get(2);
            HBox hBox1 = (HBox) hBox.getChildren().get(1);
            Text text = (Text) hBox1.getChildren().get(0);
            text.setText(code);
            text.setStyle("-fx-fill: aquamarine;"+
                    "-fx-font-size: 36;");
            if(code.equals("loading..."))text.setStyle("-fx-font-size: 18;");
        }catch (Exception e){
            System.out.println("Failed to set text"+code);
            e.printStackTrace();
        }
    }
    public void showCreateGamePane(){
//        System.out.println("Called!");
        //anchorPane.getChildren().add(createGamePane);
        hideMenu();
        setCreateGameCode("loading...");
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000));
        tt.setFromX(40);
        tt.setToX(40);
        tt.setFromY(mainPane.getHeight());
        tt.setToY(90);
        tt.setNode(createGamePane);
        tt.play();
        createGamePane.setDisable(false);
        createGamePane.setVisible(true);
        bottomBox.setVisible(true);
        bottomBox.setDisable(false);
        //client.sendToServer("CREATE_NEW_GAME/Akash");
    }

    public StackPane createJoinGamePane(){

        HBox hBox1, hBox2, hBox3, hBox4, hBox5, hBox6;

        Text join_game = new Text("Join Game");
        join_game.setStyle("-fx-font-size: 36;"+
                "-fx-fill: #002163;");
        hBox1 = new HBox(join_game);
        hBox1.setPrefHeight(75);

        Text code = new Text("Code: ");
        code.setStyle("-fx-fill: #be8f03;"+
                "-fx-font-size: 24");

        codeField = new TextField();
        codeField.setPromptText("Game Code");
        codeField.setStyle("-fx-font-size: 18;"+
                "-fx-fill: #02ad47;");
        codeField.setPrefWidth(130);

        hBox2 = new HBox(code, codeField);
        hBox2.setPrefHeight(75);

        Text joinButton = new Text("JOIN");
        joinButton.setStyle("-fx-font-size: 30;"+
                "-fx-fill: #02ad47;");
        joinButton.setOnMouseClicked(e -> sendToJoin());
        hBox3 = new HBox(joinButton);
        hBox3.setPrefHeight(25);

        joinGameNotification = new Text("");
        hBox4 = new HBox(joinGameNotification);
        hBox4.setPrefHeight(50);

        Text text = new Text("Ask your friend for CODE!");
        hBox5 = new HBox(text);
        hBox5.setPrefHeight(50);

        hBox1.setPrefWidth(400);
        hBox2.setPrefWidth(400);
        hBox3.setPrefWidth(400);
        hBox4.setPrefWidth(400);
        hBox5.setPrefWidth(400);

        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);
        hBox3.setAlignment(Pos.CENTER);
        hBox4.setAlignment(Pos.CENTER);
        hBox5.setAlignment(Pos.TOP_CENTER);

        VBox vBox = new VBox(hBox1, hBox2, hBox3, hBox4, hBox5);
        Rectangle rectangle = new Rectangle(400, 300);
        rectangle.setArcHeight(60);
        rectangle.setArcWidth(60);
        rectangle.setFill(Color.valueOf("#b4c7e7"));

        StackPane sp = new StackPane(rectangle, vBox);
        sp.setDisable(true);
        sp.setVisible(false);
        return sp;
    }
    public void showJoinGamePane(){
//        System.out.println("Called!");
        //anchorPane.getChildren().add(createGamePane);
        hideMenu();
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000));
        joinGamePane.setDisable(false);
        tt.setFromX(40);
        tt.setToX(40);
        tt.setFromY(mainPane.getHeight());
        tt.setToY(90);
        tt.setNode(joinGamePane);
        tt.play();
        joinGamePane.setVisible(true);
        //client.sendToServer("CREATE_NEW_GAME/Akash");
    }

    private void sendToJoin() {
        String code = codeField.getText();
        client.sendToServer("JOIN_GAME/"+code);
    }

    public Pane getPane(){
        return menuPane;
    }

    private void joinGame() {
        showJoinGamePane();
    }

    private void practiceOffline() {
        chessBoard = new ChessBoard(client);
        chessBoardPane = chessBoard.createMainPane();
        mainPane.setCenter(chessBoardPane);
        hideMenu();
        initPracticeOffline();
    }
    public void createGame(){
        showCreateGamePane();
    }
    public void setClient(ClientManage client){
        this.client = client;
    }
}
