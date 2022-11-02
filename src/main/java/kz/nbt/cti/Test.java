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

            JtapiPeer peer = JtapiPeerFactory.getJtapiPeer(null);
            String[] myServices = peer.getServices();
            String providerString = myServices[0]+";login=crm1"+";passwd=P@ssword1";
            provider = peer.getProvider(providerString);
            list = new JtapiListener();

            lucentAddr = (LucentAddress)provider.getAddress("1001");
            lucentTerm = (LucentTerminal)provider.getTerminal("1001");

            lucentAgent = (LucentV7Agent)lucentTerm.addAgent(
            	lucentAddr, null, Agent.LOG_IN, 0, "2900", "12345");


            lucentTerm.addCallListener(list);


            //System.out.println("Lucent address : "+lucentAddr.getName());
            //System.out.println("Lucent terminal: "+lucentTerm.getAgents()[0].getAgentID());

            System.out.println("#####################################################################################");
            System.out.println("############ALL TERMINALS HAS BEEN ADDED TO LISTENER SERVICE.OPERATION COMPLETED#####");
            System.out.println("#####################################################################################");





        } catch (JtapiPeerUnavailableException e) {
            e.printStackTrace();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
