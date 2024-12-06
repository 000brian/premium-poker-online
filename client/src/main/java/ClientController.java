import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ClientController {

    @FXML
    private TextField addressField, pairPlusField, anteField, playField, portNumberInput, ipAddressInput;
    @FXML
    private Button connectButton, pairAnteButton, playButton, foldButton, joinServerButton, exiButton;
    @FXML
    private ListView<String> listView;

    private ObservableList<String> observableList;
    private ArrayList<String> clientList = new ArrayList<>();
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private Player player;
    private PokerInfo pokerInfo;

    @FXML
    private void initialize() {
        observableList = FXCollections.observableArrayList(clientList);
        listView.setItems(observableList);
        Player player = new Player();
        pairAnteButton.setDisable(true);
        playButton.setDisable(true);
    }

    @FXML
    private void connectToServer() {
        String[] address = addressField.getText().split(":");
        String ip = address[0];
        int port = Integer.parseInt(address[1]);
        connectButton.setDisable(true);
        new Thread(() -> {
            try {
                socket = new Socket(ip, port);
                updateList("Player connected to server.");

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
                    if (obj instanceof PokerInfo) {
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

                    }
                    else if (pokerInfo.getGameStage() == 2)
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


                    }
                    else if (pokerInfo.getGameStage() == 4)
                    {
                        System.out.println("finished");
                    }


                }

            } catch (IOException e) {

                System.out.println(ip + ":" + port + " is invalid. Please try another address.");

            }
        }).start();
    }

    // This gets called when the pair+ante button is clicked.
    // It updates the game info on our end, and sends over an updated PokerInfo.
    // When the server sees gameStage == 1, it will deal cards.
    @FXML
    private void sendPairAnte() {
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

        try {
            oos.writeObject(pokerInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void fold()
    {
        // dont do anything and just restart
    }

    @FXML
    private void play()
    {
        int playBet = Integer.parseInt(playField.getText());
        player.setPlayBet(playBet);
        // TODO: check if play bet is equal to ante, if not prompt to retry
        player.setBalance(player.getBalance() - player.getPlayBet());
        pokerInfo.setPlayer(player);
        pokerInfo.setGameStage(3);
        try {
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

    private void updateListView() {
        Platform.runLater(() -> observableList.setAll(clientList));
    }

    private void printHand(ArrayList<Card> hand)
    {
        for(Card card : hand)
        {
            System.out.print(card.toString() + " ");
        }
    }

}
