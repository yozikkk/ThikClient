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
                        time = new Date(); // текущая дата
                        dt1 = new SimpleDateFormat("HH:mm:ss"); // берем только время до секунд
                        dtime = dt1.format(time); // время
                        //System.out.println("Received msg: " + message);

                        JSONObject jsonObject = new JSONObject(message);
                        String fullMesage = jsonObject.getString("content");
                        //String from = jsonObject.getString("from");
                        //static_chat_output.appendText(dtime+" from  "+from+ " "+fullMesage+"\n");
                        static_chat_output.appendText(dtime+" " +fullMesage+"\n");

                        /*

                        if(AgentState.isReady) {
                            if(AgentState.chatid !=null) {
                                JSONObject jsonObject = new JSONObject(message);
                                String fullMesage = jsonObject.getString("content");
                                String from = jsonObject.getString("from");
                                static_chat_output.appendText(dtime+" from  "+from+ " "+fullMesage+"\n");
                            }
                            else{




                                JSONObject jsonObject = new JSONObject(message);
                                String fullMesage = jsonObject.getString("content");
                                JSONObject jsonChatid = new JSONObject(fullMesage);
                                Long chatid = jsonChatid.getLong("chatid");
                                String from = jsonObject.getString("from");
                                AgentState.chatid= chatid;

                                static_chat_output.appendText(dtime+" from  "+from+ " "+fullMesage+"\n");



                            }



                            }
                            */



                    }
                })
                .connect();
       // websocket.sendText("Hey ha ha ha1");
        // Don't forget to call disconnect() after use.
        // websocket.disconnect();
    }
}