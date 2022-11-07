package kz.nbt.cti;

import com.avaya.jtapi.tsapi.LucentAddress;
import com.avaya.jtapi.tsapi.LucentTerminal;
import com.avaya.jtapi.tsapi.LucentV7Agent;
import kz.nbt.cti.listeners.JtapiListener;

import javax.telephony.Address;
import javax.telephony.JtapiPeer;
import javax.telephony.JtapiPeerFactory;
import javax.telephony.JtapiPeerUnavailableException;
import javax.telephony.Provider;
import javax.telephony.Terminal;
import javax.telephony.callcenter.ACDAddress;
import javax.telephony.callcenter.ACDAddressListener;
import javax.telephony.callcenter.Agent;
import javax.telephony.callcenter.AgentTerminal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {

    static Address myAddress;
    static ACDAddress myACDAddress = null;
    public static LucentTerminal lucentTerm;
    static LucentAddress lucentAddr;
    private static LucentV7Agent lucentAgent;
    static ACDAddressListener myACDListener = null;
    static AgentTerminal agentTerm;
    static Agent agent;
    static Provider provider ;
    static JtapiListener list;

    public static void main(String[] args){


        try {


            String jsonInputString =  "{"
                    + "\"chatid\":\""+"77012111143"+"\","
                    + "\"message\":\""+"Echo message"+"\"}";
            //rest.doSslPost(jsonInputString,"send_message","https://86.nbt.kz/");
            doPost(jsonInputString,"send_message","http://localhost:9999/");






        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private static  String doPost(String jsonInputString,String action,String url) throws IOException {

        StringBuilder response = new StringBuilder();
        URL createurl = new URL(url + action);
        HttpURLConnection con = (HttpURLConnection) createurl.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        System.out.println(jsonInputString);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {

            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        con.disconnect();
        return response.toString();


    }






}
