package kz.nbt.cti.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import kz.nbt.cti.ops.AgentOps;
import kz.nbt.cti.AgentState;
import kz.nbt.cti.ops.ChatOps;

import javax.telephony.InvalidArgumentException;
import javax.telephony.InvalidStateException;
import java.net.URL;
import java.util.ResourceBundle;

public class AgentStateUI implements Initializable {



    @FXML
    private javafx.scene.control.Button logoutButton;

    public ToggleButton readyForChat;
    public TextArea ctiInfo;
    public TextArea chat_output;
    public TextField chat_input;
    public static TextArea static_ctiInfo;
    public static TextArea static_chat_output;


    @FXML
    protected void logout() throws InvalidArgumentException, InvalidStateException{
        AgentOps ops = new AgentOps();
        ops.logout();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void updateText(){
        ctiInfo.appendText("Text updated");
    }

    @FXML
    protected void readyForChat(){
        if(readyForChat.isSelected()){
            AgentState.isReady = true;
        }
        else {
            AgentState.isReady = false;

        }
    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        static_ctiInfo =ctiInfo;
        static_chat_output = chat_output;
    }


    @FXML
    protected void send() {
        ChatOps chatOps = new ChatOps();
        chatOps.send(chat_input.getText());
        chat_input.clear();


    }


    @FXML
    protected void nextChat(){
        ChatOps chatOps = new ChatOps();
        chat_output.clear();
        chatOps.nextChat();

    }



}
