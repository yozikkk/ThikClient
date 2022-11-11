package kz.nbt.cti.websocket;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;
import kz.nbt.cti.AgentState;
import kz.nbt.cti.controller.AgentStateUI;
import org.json.JSONObject;

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
                .createSocket("ws://localhost:8181/chat/"+agentid)
                .addListener(new WebSocketAdapter() {
                    @Override
                    public void onTextMessage(WebSocket ws, String message) {
                        time = new Date();
                        dt1 = new SimpleDateFormat("HH:mm:ss");
                        dtime = dt1.format(time);
                        JSONObject jsonObject = new JSONObject(message);
                        if(jsonObject.isNull("content")){
                            try {
                                static_telega_output.setText(jsonObject.getString("telegram"));
                                static_facebook_output.setText(jsonObject.getString("facebook"));
                                static_whatsapp_output.setText(jsonObject.getString("whatsapp"));
                                static_instagram_output.setText(jsonObject.getString("instagram"));
                            }
                            catch (Exception e){
                            }
                        }
                        String fullMesage = jsonObject.getString("content");
                        static_chat_output.appendText(dtime+" " +fullMesage+"\n");
                    }
                })
                .connect();
    }
}