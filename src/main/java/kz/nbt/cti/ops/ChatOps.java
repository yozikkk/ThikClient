package kz.nbt.cti.ops;

import kz.nbt.cti.AgentState;
import kz.nbt.cti.controller.AgentStateUI;
import kz.nbt.cti.restapi.CallRestAPI;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
            static_chat_output.appendText(date1+"  "+message+"\n");


        }
        catch (Exception e){
            e.printStackTrace();
        }


    }


    public void nextChat()  {



        String pattern = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        CallRestAPI callRest = new CallRestAPI();


        try{


            String jsonInputString =  "{ "
                    + "\"agentid\":\""+AgentState.agentid+"\"}";
            callRest.doPost(jsonInputString, "unAssignChatToAgent");



            JSONObject jsonObject = new JSONObject(callRest.doGet("getMessage",AgentState.chatid));
            String fullMesage = jsonObject.getString("message");

            System.out.println("локальная переменная :"+AgentState.chatid);
            System.out.println("переменная из веб сервиса :"+jsonObject.getLong("chatid"));
            if(AgentState.chatid.equals(jsonObject.getLong("chatid"))) {



                System.out.println("ID совпали");
                AgentState.chatid =null;
                JSONObject jsonNextObj = new JSONObject(callRest.doGet("getMessage",null));
                String nextMessage = jsonNextObj.getString("message");
                Long nextChatid = jsonNextObj.getLong("chatid");
                AgentState.chatid = nextChatid;
                for(String message :nextMessage .split(";") ) {
                    System.out.println(message);
                    static_chat_output.appendText(date+"  "+message+"\n");
                }

            }
            else {

                System.out.println("ID не совпали");
                AgentState.chatid = jsonObject.getLong("chatid");
                String jsonAssignChat =  "{ "
                        + "\"agentid\":\""+AgentState.agentid+"\","
                        + "\"chatId\":\""+jsonObject.getLong("chatid")+"\"}";
                callRest.doPost(jsonAssignChat, "assignChatToAgent");


                for(String message :fullMesage .split(";") ) {
                    System.out.println(message);
                    static_chat_output.appendText(date+"  "+message+"\n");
                }

            }


        }
        catch (Exception e){


        }


    }


}
