import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.application.Application;

import java.io.*;
import java.net.*;
import java.util.ArrayList;



public class ClientController extends Application
{

    private Stage primaryStage;
    @FXML
    private TextField addressField, pairPlusField, anteField, playField, portNumberInput, ipAddressInput;
    @FXML
    private Button connectButton, pairAnteButton, playButton, foldButton, joinServerButton, exitButton;
    @FXML
    private ListView<String> listView;

    private ObservableList<String> observableList;
    private ArrayList<String> clientList = new ArrayList<>();
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private Player player = new Player();
    private PokerInfo pokerInfo;



    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }
    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Premium Poker Online");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/Welcome.fxml"));
        Parent root = loader.load();

        ClientController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize()
    {
        /*
        observableList = FXCollections.observableArrayList(clientList);
        listView.setItems(observableList);
        pairAnteButton.setDisable(true);
        playButton.setDisable(true);
        foldButton.setDisable(true);
        */

    }


    @FXML
    private void connectToServer() throws Exception
    {
        int port = Integer.parseInt(portNumberInput.getText());
        String ip = ipAddressInput.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/Client.fxml"));
        Parent root = loader.load();
        primaryStage.getScene().setRoot(root);

        new Thread(() ->
        {
            try
            {
                socket = new Socket(ip, port);


                // For the Client.fxml
                observableList = FXCollections.observableArrayList(clientList);
                listView.setItems(observableList);
                pairAnteButton.setDisable(true);
                playButton.setDisable(true);
                foldButton.setDisable(true);



                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                socket.setTcpNoDelay(true);

                while (true)
                {
                    // Bullshit I have to do to read a PokerInfo from ois
                    Object obj = new Object();
                    try
                    {
                        obj = ois.readObject();
                    } catch (ClassNotFoundException e)
                    {
                        System.out.println("somehow the object read from ois was not a PokerInfo");
                    }
                    if (obj instanceof PokerInfo)
                    {
                        pokerInfo = (PokerInfo) obj;
                    }

                    // Doing things based on what game stage it is
                    if (pokerInfo.getGameStage() == 0)
                    {
                        System.out.println("heelo i am client i need to make ante + play lmfao");
                        pairAnteButton.setDisable(false);
                        playButton.setDisable(true);
                        foldButton.setDisable(true);

                        if (pokerInfo.getPlayer().isAnteLocked())
                        {
                            // TODO : Lock ante bet
                        }

                    } else if (pokerInfo.getGameStage() == 2)
                    {

                        pairAnteButton.setDisable(false);
                        foldButton.setDisable(false);
                        playButton.setDisable(false);
                        playField.setDisable(false);

                        System.out.println("i need to fold or play!");
                        System.out.println("player cards");
                        System.out.println(pokerInfo.getPlayer().getHand().toString());
                        System.out.println("dealer cards");
                        System.out.println(pokerInfo.getDealersHand().toString());


                    } else if (pokerInfo.getGameStage() == 4)
                    {
                        System.out.println("you win!");
                        System.out.println("total winnings: " + pokerInfo.getPlayer().getBalance());

                    } else if (pokerInfo.getGameStage() == 5)
                    {
                        System.out.println("you losttt lmfao");
                        System.out.println("total LOSSES: " + pokerInfo.getPlayer().getBalance());

                    }


                }

            } catch (IOException e)
            {

                System.out.println(ip + ":" + port + " is invalid. Please try another address.");

            }
        }).start();


    }

    // This gets called when the pair+ante button is clicked.
    // It updates the game info on our end, and sends over an updated PokerInfo.
    // When the server sees gameStage == 1, it will deal cards.
    @FXML
    private void sendPairAnte()
    {
        int anteBet = Integer.parseInt(anteField.getText());
        int pairBet = Integer.parseInt(pairPlusField.getText());

        // TODO: add logic for checking if bets are valid numbers

        // I did this in initialize() but i guess it disappeared bc without this i get a null pointer exception?
        Player player = new Player();

        player.setAnteBet(anteBet);
        player.setPairPlusBet(pairBet);
        player.setBalance(player.getBalance() - player.getAnteBet() - player.getAnteBet());
        pokerInfo.setPlayer(player);
        pokerInfo.setGameStage(1);

        clientList.add("Player made pair bet: " + pairBet + " and ante bet: " + anteBet);
        updateListView();

        try
        {
            oos.writeObject(pokerInfo);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void sendFold()
    {
        // Over on the server, it will just see this
        // and send a pokerInfo with gameStage 5 back
        pokerInfo.setGameStage(5);
        try
        {
            oos.writeObject(pokerInfo);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void sendPlay()
    {
        player = pokerInfo.getPlayer();
        int playBet = Integer.parseInt(playField.getText());
        player.setPlayBet(playBet);
        // TODO: check if play bet is equal to ante, if not prompt to retry
        player.setBalance(player.getBalance() - player.getPlayBet());
        pokerInfo.setPlayer(player);
        pokerInfo.setGameStage(3);
        try
        {
            oos.writeObject(pokerInfo);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    private void updateList(String message)
    {
        Platform.runLater(() ->
        {
            clientList.add(message);
            updateListView();
        });
    }

    private void updateListView()
    {
        Platform.runLater(() -> observableList.setAll(clientList));
    }

    private void printHand(ArrayList<Card> hand)
    {
        for (Card card : hand)
        {
            System.out.print(card.toString() + " ");
        }
    }

}
