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
    private TextField addressField, messageField;
    @FXML
    private Button connectButton, sendButton;
    @FXML
    private ListView<String> listView;

    private ObservableList<String> observableList;
    private ArrayList<String> clientList = new ArrayList<>();
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    @FXML
    private void initialize() {
        observableList = FXCollections.observableArrayList(clientList);
        listView.setItems(observableList);
        sendButton.setDisable(true);
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
                updateList("Connected to server");

                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                sendButton.setDisable(false);

                String message;
                while ((message = reader.readLine()) != null) {
                    clientList.add("Server: " + message);
                    updateListView();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void sendMessage() {
        String message = messageField.getText();
        clientList.add("Client: " + message);
        updateListView();
        writer.println(message);
        messageField.clear();
    }

    private void updateList(String message) {
        Platform.runLater(() -> {
            clientList.add(message);
            updateListView();
        });
    }

    private void updateListView() {
        Platform.runLater(() -> observableList.setAll(clientList));
    }
}
