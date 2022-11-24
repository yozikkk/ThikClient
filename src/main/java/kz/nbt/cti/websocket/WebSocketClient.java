package kz.nbt.cti.websocket;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;
import javafx.application.Platform;
import kz.nbt.cti.AgentState;
import kz.nbt.cti.controller.AgentStateUI;
import kz.nbt.cti.ops.AgentOps;
import kz.nbt.cti.ops.CRMOps;
import kz.nbt.cti.timer.CallTimer;
import org.json.JSONObject;

import javax.telephony.InvalidArgumentException;
import javax.telephony.InvalidStateException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WebSocketClient extends AgentStateUI
{

    private Date time;
    private String dtime;
    private SimpleDateFormat dt1;
    public void connect(String agentid) throws Exception
    {


        WebSocket websocket = new WebSocketFactory()
                .createSocket("ws://192.168.2.99:8181/chat/"+agentid)
                .addListener(new WebSocketAdapter() {
                    @Override
                    public void onTextMessage(WebSocket ws, String message) throws IOException, InvalidArgumentException, InvalidStateException {
                        time = new Date();
                        dt1 = new SimpleDateFormat("HH:mm:ss");
                        dtime = dt1.format(time);
                        JSONObject jsonObject = new JSONObject(message);
                        if(jsonObject.isNull("content")){
                            try {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        static_telega_output.setText(jsonObject.getString("telegram"));
                                        static_facebook_output.setText(jsonObject.getString("facebook"));
                                        static_whatsapp_output.setText(jsonObject.getString("whatsapp"));
                                        static_instagram_output.setText(jsonObject.getString("instagram"));
                                    }
                                });


                            }
                            catch (Exception e){
                            }
                        }


                       // if(AgentState.voiceIsReady){


                        //}
                        String fullMesage = jsonObject.getString("content");
                        static_chat_output.appendText(dtime+" Клиент: " +fullMesage+"\n");
                        String clientid = jsonObject.getString("from");
                        System.out.println("Tetst");
                        static_ctiInfo.setText(clientid);
                        CRMOps ops = new CRMOps();
                        ops.getCustomerInfo(clientid);

                        CallTimer  timer = new CallTimer();
                        timer.start();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                static_current_channel.setText("chat");
                            }
                        });


                        if(AgentState.voiceIsReady){
                            AgentOps agentOps = new AgentOps();
                            System.out.println("Set agent telephony state to false");
                            agentOps.setTelephonyState(false);

                        }




                        //AlertController alertController = new AlertController();
                        //alertController.showEvent();



                    }
                })
                .connect();
    }
}