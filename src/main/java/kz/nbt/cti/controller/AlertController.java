package kz.nbt.cti.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import kz.nbt.cti.ops.AgentOps;

import javax.telephony.InvalidArgumentException;
import javax.telephony.InvalidStateException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AlertController implements Initializable {


    @FXML
    private Button incoming_accept;


    @FXML
    protected void close() {
        Stage stage = (Stage) incoming_accept.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void showEvent() throws IOException {


        Platform.runLater(new Runnable(){
            @Override
            public void run() {

                try{


                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Принять входящий вызов?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
                    alert.showAndWait();
/*
                    Parent root =  FXMLLoader.load(getClass().getClassLoader().getResource("Incoming_event.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Agent State UI");
                    stage.setScene(scene);
                    stage.initModality(Modality.NONE);
                    stage.show();


 */
                }

                catch (Exception e){
                    e.printStackTrace();
                }


            }

        });




    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}