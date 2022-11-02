package kz.nbt.cti.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kz.nbt.cti.AgentState;
import kz.nbt.cti.ops.AgentOps;
import kz.nbt.cti.restapi.CallRestAPI;
import kz.nbt.cti.socketsimple.Client;
import kz.nbt.cti.websocket.WebSocketClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LoginController {



    @FXML
    public TextField ext;
    public TextField agent;
    public TextField passwd;

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
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }




/*
        AgentOps callJTapi = new AgentOps();
        try{
            callJTapi.login(ext.getText(),agent.getText(),passwd.getText());
            callJTapi.addCallListener();
        }
        catch (Exception e){
            e.printStackTrace();
        }

 */





        //Client client = new Client();
        WebSocketClient webSocketClient = new WebSocketClient();
        CallRestAPI rest = new CallRestAPI();

        try{

            webSocketClient.connect(agent.getText());
            //client.runClient();
            String pattern = "yyyy-MM-dd'T'HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            String json =  "{ "
                    + "\"agentid\":\""+agent.getText()+"\","
                    + "\"loginTime\":\""+date+"\"}";

            rest.doPost(json, "addAgent");
        }
        catch (Exception e){



        }



    }

    @FXML
    protected void terminate(){
        System.exit(0);

    }


}