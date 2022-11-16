package kz.nbt.cti.controller;

import com.avaya.jtapi.tsapi.TsapiPlatformException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kz.nbt.cti.AgentState;
import kz.nbt.cti.ops.AgentOps;
import kz.nbt.cti.restapi.CallRestAPI;
import kz.nbt.cti.websocket.WebSocketClient;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import static kz.nbt.cti.controller.AgentStateUI.static_chat_channel_status;
import static kz.nbt.cti.controller.AgentStateUI.static_voice_channel_status;


public class LoginController implements Initializable {



    @FXML
    public TextField ext;

    @FXML
    public TextField agent;

    @FXML
    public PasswordField passwd;





    @FXML
    protected void login() {

        AgentState.agentid = agent.getText();
        try {
            Parent root =  FXMLLoader.load(getClass().getClassLoader().getResource("AgentStateUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Agent State UI");
            stage.setScene(scene);
            stage.initModality(Modality.NONE);
            Image icon = new Image(getClass().getResourceAsStream("/images/cc_image.png"));
            stage.getIcons().add(icon);
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }


        AgentOps callJTapi = new AgentOps();
        try{
            callJTapi.login(ext.getText(),agent.getText(),passwd.getText());
            callJTapi.addCallListener();
            static_voice_channel_status.setText("Logged in");
            static_voice_channel_status.setStyle("-fx-background-color:#63E88C");
        }

        catch (Exception e){
            static_voice_channel_status.setText("Logged out");
            static_voice_channel_status.setStyle("-fx-background-color:#F68181");

        }


        WebSocketClient webSocketClient = new WebSocketClient();
        CallRestAPI rest = new CallRestAPI();

        try{

            webSocketClient.connect(agent.getText());
            String pattern = "yyyy-MM-dd'T'HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            String json =  "{ "
                    + "\"agentid\":\""+agent.getText()+"\","
                    + "\"lastUpdate\":\""+date+"\","
                    + "\"loginTime\":\""+date+"\"}";

            rest.doPost(json, "addAgent");
            static_chat_channel_status.setText("Logged in");
            static_chat_channel_status.setStyle("-fx-background-color:#63E88C");

        }
        catch (Exception e){
            static_chat_channel_status.setText("Logged out");
            static_chat_channel_status.setStyle("-fx-background-color:#F68181");

        }



    }

    @FXML
    protected void terminate(){
        System.exit(0);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}