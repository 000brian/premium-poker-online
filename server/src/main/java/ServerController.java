import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerController {

    @FXML
    private TextField portField, messageField;
    @FXML
    private Button startButton, sendButton;
    @FXML
    private ListView<String> listView;

    private ObservableList<String> observableList;
    private ArrayList<String> serverList = new ArrayList<>();
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private PokerInfo pokerInfo;
    private Dealer dealer;

    @FXML
    private void initialize() {
        observableList = FXCollections.observableArrayList(serverList);
        listView.setItems(observableList);
        dealer = new Dealer();
    }

    @FXML
    private void startServer() {
        int port = Integer.parseInt(portField.getText());
        startButton.setDisable(true);
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                updateList("Server started on port " + port);
                clientSocket = serverSocket.accept();
                updateList("Client has connected. Player, make your ante and pair plus wagers.");

                oos = new ObjectOutputStream(clientSocket.getOutputStream());
                ois = new ObjectInputStream(clientSocket.getInputStream());
                clientSocket.setTcpNoDelay(true);

                pokerInfo = new PokerInfo();
                oos.writeObject(pokerInfo);

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
                    if (pokerInfo.getGameStage() == 1)
                    {

                        System.out.println("pair: " + pokerInfo.getPlayer().getPairPlusBet());
                        System.out.println("ante: " + pokerInfo.getPlayer().getAnteBet());
                        System.out.println("its time to deal cards!");

                        // Giving out cards to both players
                        dealer.setHand(dealer.dealHand());
                        ArrayList<Card> dealerHand = dealer.getHand();
                        ArrayList<Card> playerHand = dealer.dealHand();

                        pokerInfo.getPlayer().setHand(playerHand);
                        pokerInfo.setDealersHand(dealerHand);
                        pokerInfo.setGameStage(2);

                        try {
                            oos.writeObject(pokerInfo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void sendMessage(PokerInfo pokerInfo) {
        String message = messageField.getText();
        serverList.add("Server sent pokerInfo:" + pokerInfo.toString());
        updateListView();
        try {
            oos.writeObject(pokerInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateList(String message) {
        Platform.runLater(() -> {
            serverList.add(message);
            updateListView();
        });
    }

    private void updateListView() {
        Platform.runLater(() -> observableList.setAll(serverList));
    }

    private void printHand(ArrayList<Card> hand)
    {
        for(Card card : hand)
        {
            System.out.print(card.toString() + " ");
        }
    }
}
