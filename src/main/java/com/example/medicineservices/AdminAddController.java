package com.example.medicineservices;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
public class AdminAddController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button addButton;
    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    void initialize() {
        addButton.setOnAction(event -> {
            AddMethod(event);
        });
    }
    public void AddMethod(ActionEvent event) {
        String nametext = name.getText();
        String pricetext = price.getText();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicine",
                    "root", "mysql");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM service WHERE name = ?");
            statement.setString(1, nametext);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                showAlert("Данная услуга уже существует");
            } else {
                statement = connection.prepareStatement("INSERT INTO service (name, price) VALUES (?, ?)");
                statement.setString(1, nametext);
                statement.setString(2, pricetext);
                statement.executeUpdate();
                showAlert("Успешно добавлено");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
