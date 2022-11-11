package kz.nbt.cti.ops;


import com.avaya.jtapi.tsapi.LucentAddress;
import com.avaya.jtapi.tsapi.LucentAgent;
import com.avaya.jtapi.tsapi.LucentTerminal;
import com.avaya.jtapi.tsapi.LucentV7Agent;
import kz.nbt.cti.AgentState;
import kz.nbt.cti.ProviderInitialyze;
import kz.nbt.cti.listeners.JtapiListener;
import kz.nbt.cti.restapi.CallRestAPI;

import javax.telephony.InvalidArgumentException;
import javax.telephony.InvalidStateException;
import javax.telephony.Provider;
import javax.telephony.callcenter.Agent;

public class AgentOps {

    public static LucentAddress lucentAddr;
    public static LucentTerminal lucentTerm;
    public static LucentAgent lucentAgent;
    public static Provider provider;

    public static JtapiListener listener;

    public static String station;



    public void login(String station,String agent,String passwd){

        this.station = station;
        ProviderInitialyze providerInitialyze = new ProviderInitialyze();
        provider =  providerInitialyze.getProvider();
        try{
            lucentAddr = (LucentAddress)provider.getAddress(station);
            lucentTerm = (LucentTerminal)provider.getTerminal(station);
            lucentAgent = (LucentV7Agent)lucentTerm.addAgent(
                    lucentAddr, null, Agent.LOG_IN, 0, agent, passwd);

        }
        catch (Exception e){
            System.err.println(e);
        }

    }

    public void logout() throws InvalidArgumentException, InvalidStateException {

        String json =  "{ "
                + "\"agentid\":\""+ AgentState.agentid+"\"}";
        CallRestAPI api = new CallRestAPI();
        try{
            api.doDelete(json, "removeAgent");
            lucentTerm.removeAgent(lucentAgent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    public void addCallListener(){
        try{
            System.out.println("Провайдер: "+provider.getTerminals().toString());
            System.out.println("Добавляем терминал :"+lucentTerm.getName());

            lucentTerm = (LucentTerminal)provider.getTerminal(station);
            listener = new JtapiListener();
            lucentTerm.addCallListener(listener);
            //provider.getTerminal(lucentTerm.getName()).addCallListener(listener);
        }
        catch (Exception e){
            System.err.println(e);
        }

    }

    public void  updateAgentState(boolean state){

        String json =  "{ "
                + "\"ready\":\""+state+"\","
                + "\"agentid\":\""+ AgentState.agentid+"\"}";
        CallRestAPI api = new CallRestAPI();
        try{
            api.doPost(json,"updateAgentState");
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }



}


