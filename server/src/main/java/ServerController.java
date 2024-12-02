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
    private BufferedReader reader;
    private PrintWriter writer;

    @FXML
    private void initialize() {
        observableList = FXCollections.observableArrayList(serverList);
        listView.setItems(observableList);
        sendButton.setDisable(true);
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
                updateList("Client connected");

                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream(), true);

                sendButton.setDisable(false);

                String message;
                while ((message = reader.readLine()) != null) {
                    serverList.add("Client: " + message);
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
        serverList.add("Server: " + message);
        updateListView();
        writer.println(message);
        messageField.clear();
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
}
