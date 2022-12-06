package kz.nbt.cti.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import kz.nbt.cti.AgentState;
import kz.nbt.cti.connhandler.ConnStorage;
import kz.nbt.cti.ops.AgentOps;
import kz.nbt.cti.ops.ChatOps;

import javax.telephony.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AgentStateUI implements Initializable {

    public Button drop_call;
    @FXML
    private PieChart pieChartByChannels;
    @FXML
    private javafx.scene.control.Button logoutButton;
    public ToggleButton readyForWork;
    public TextArea ctiInfo;
    public TextArea chat_output;
    public TextField chat_input;
    public Label telega_output;
    public Label label_output;
    public Label whatsapp_output;
    public Label facebook_output;
    public Label instagram_output;
    public static TextArea static_ctiInfo;
    public static TextArea static_chat_output;
    public static Label static_telega_output;
    public static Label static_whatsapp_output;
    public static Label static_facebook_output;
    public static Label static_instagram_output;
    public static Label static_label_output;
    public  Label chat_channel_status;
    public  Label voice_channel_status;
    public static Label static_chat_channel_status;
    public  static Label static_voice_channel_status;
    public TextArea name;
    public TextArea surname;
    public TextArea address;
    public  TextArea email;
    public TextArea balance;
    public Label call_timer;
    public Label current_channel;
    public static Label static_call_timer;
    public static  TextArea static_name;
    public static  TextArea static_surname;
    public static  TextArea static_address;
    public static  TextArea static_email;
    public static  TextArea static_balance;
    public static  Label static_current_channel;





    @FXML
    protected void logout() throws InvalidArgumentException, InvalidStateException{
        AgentOps ops = new AgentOps();
        ops.logout();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }


    @FXML
    protected void setReadyForWork() throws InvalidArgumentException, InvalidStateException {
        AgentOps ops = new AgentOps();
        if(readyForWork.isSelected()){
            AgentState.isReady = true;
            readyForWork.setText("В работе");
            readyForWork.setStyle("-fx-background-color:#63E88C;-fx-background-radius: 5em");
            ops.updateAgentState(true);
        }
        else {
            AgentState.isReady = false;
            ops.updateAgentState(false);
            readyForWork.setText("Не готов");
            readyForWork.setStyle("-fx-background-color:#F68181;-fx-background-radius: 5em");
        }
    }



    @FXML
    public void showEvent() throws IOException {
        System.out.println("Alert event form");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.showAndWait();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        static_ctiInfo =ctiInfo;
        static_chat_output = chat_output;
        static_telega_output = telega_output;
        static_whatsapp_output= whatsapp_output;
        static_facebook_output= facebook_output;
        static_instagram_output= instagram_output;
        static_label_output=label_output;
        static_chat_channel_status = chat_channel_status;
        static_voice_channel_status = voice_channel_status;
        static_name = name;
        static_address=address;
        static_surname=surname;
        static_email=email;
        static_balance=balance;
        static_call_timer=call_timer;
        static_current_channel=current_channel;


        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Telegram",6),
                new PieChart.Data("Whatsapp",2),
                new PieChart.Data("Instagram",0),
                new PieChart.Data("Facebook",3)

        );
        pieChartByChannels.setData(pieChartData);

    }
    @FXML
    protected void send() {

            ChatOps chatOps = new ChatOps();
            chatOps.send(chat_input.getText());
            chat_input.clear();

    }

    @FXML


    protected void sendOnEnter() {

        chat_input.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    ChatOps chatOps = new ChatOps();
                    chatOps.send(chat_input.getText());
                    chat_input.clear();
                }
            }
        });

    }

    @FXML
    protected void nextChat() throws InvalidArgumentException, InvalidStateException, ResourceUnavailableException, MethodNotSupportedException, PrivilegeViolationException {

            AgentOps agentOps = new AgentOps();
            agentOps.dropConnection(ConnStorage.connection);
            ChatOps chatOps = new ChatOps();
            chat_output.clear();
            chatOps.nextChat();


    }

    @FXML
    protected void  dropTheCall() throws ResourceUnavailableException, MethodNotSupportedException, PrivilegeViolationException, InvalidStateException {
        AgentOps agentOps = new AgentOps();
        agentOps.dropConnection(ConnStorage.connection);

    }

}
