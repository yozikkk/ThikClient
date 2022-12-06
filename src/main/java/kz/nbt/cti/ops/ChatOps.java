package kz.nbt.cti.ops;

import javafx.application.Platform;
import kz.nbt.cti.AgentState;
import kz.nbt.cti.controller.AgentStateUI;
import kz.nbt.cti.restapi.CallRestAPI;
import kz.nbt.cti.timer.CallTimer;
import org.json.JSONObject;

import javax.telephony.InvalidArgumentException;
import javax.telephony.InvalidStateException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatOps extends AgentStateUI {

    public void send(String message){

        SimpleDateFormat simpleDateFormat;
        String pattern = "yyyy-MM-dd'T'HH:mm:ss";
        simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        String channel = "telegram";
        try{

            String jsonInputString =  "{ "
                    + "\"message\":\""+message+"\","
                    + "\"agentid\":\""+ AgentState.agentid +"\","
                    + "\"date\":\""+date+"\","
                    + "\"channel\":\""+channel+"\"}";

            CallRestAPI api = new CallRestAPI();
            api.doPost(jsonInputString,"sendMessage");
            String pattern1 = "HH:mm:ss";
            simpleDateFormat = new SimpleDateFormat(pattern1);
            String date1 = simpleDateFormat.format(new Date());
            static_chat_output.appendText(date1+" Агент: "+message+"\n");

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }


    public void nextChat() throws InvalidArgumentException, InvalidStateException {


        try{

            AgentOps agentOps = new AgentOps();
            agentOps.setTelephonyState(true);
            CallTimer.stop();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    static_call_timer.setText("00:00:00");
                    static_current_channel.setText("none");
                }
            });
        }

        catch (Exception e){
            e.printStackTrace();
        }


/*

        String pattern = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        CallRestAPI callRest = new CallRestAPI();

        try{
                String jsonInputString =  "{ "
                    + "\"agentid\":\""+AgentState.agentid+"\"}";
                callRest.doPost(jsonInputString, "unAssignChatToAgent");
                JSONObject jsonNextObj = new JSONObject(callRest.doGet("getMessage",null));

                if(!jsonNextObj.isNull("message")){
                    String jsonAssignChat =  "{ "
                            + "\"chatId\":\""+jsonNextObj.getLong("chatid")+"\","
                            + "\"channel\":\""+jsonNextObj.getString("channel")+"\","
                            + "\"agentid\":\""+AgentState.agentid+"\"}";
                    callRest.doPost(jsonAssignChat,"assignChatToAgent");
                    String nextMessage = jsonNextObj.getString("message");
                    for(String message :nextMessage .split(";") ) {
                        System.out.println(message);
                        static_chat_output.appendText(date+"  "+message+"\n");
                }
            }
                else {
                    static_chat_output.appendText(date+" нет активных чатов \n");
                }
        }
        catch (Exception e){
            e.printStackTrace();
        }

 */


    }


    public void getNextChat(){

        String pattern = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        CallRestAPI callRest = new CallRestAPI();

        try{

            JSONObject jsonNextObj = new JSONObject(callRest.doGet("getMessage",null));
            if(!jsonNextObj.isNull("message")){
                String jsonAssignChat =  "{ "
                        + "\"chatId\":\""+jsonNextObj.getLong("chatid")+"\","
                        + "\"channel\":\""+jsonNextObj.getString("channel")+"\","
                        + "\"agentid\":\""+AgentState.agentid+"\"}";
                callRest.doPost(jsonAssignChat,"assignChatToAgent");
                String nextMessage = jsonNextObj.getString("message");
                for(String message :nextMessage .split(";") ) {
                    System.out.println(message);
                    static_chat_output.appendText(date+"  "+message+"\n");
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }


}
