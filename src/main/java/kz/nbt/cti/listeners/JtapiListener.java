package kz.nbt.cti.listeners;

import com.avaya.jtapi.tsapi.LucentV5CallInfo;
import com.avaya.jtapi.tsapi.OriginalCallInfo;
import com.avaya.jtapi.tsapi.csta1.LucentV7DeliveredEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import kz.nbt.cti.AgentState;
import kz.nbt.cti.controller.AgentStateUI;
import kz.nbt.cti.restapi.CallRestAPI;
import kz.nbt.cti.timer.CallTimer;

import javax.telephony.Address;
import javax.telephony.CallEvent;
import javax.telephony.ConnectionEvent;
import javax.telephony.MetaEvent;
import javax.telephony.Terminal;
import javax.telephony.TerminalConnectionEvent;
import javax.telephony.callcenter.CallCenterCall;
import javax.telephony.callcontrol.CallControlConnectionEvent;
import javax.telephony.callcontrol.CallControlTerminalConnectionEvent;
import javax.telephony.callcontrol.CallControlTerminalConnectionListener;
import java.text.SimpleDateFormat;
import java.util.Date;


public class JtapiListener extends AgentStateUI implements CallControlTerminalConnectionListener {
    public static boolean is = true;
    public Address callingAddress=null;
    public Address calledAddress=null;
    public Address calledTerminal;
    public Terminal calledTerminalnew;
    public OriginalCallInfo originalcallinfo;
    public LucentV7DeliveredEvent event;
    public LucentV5CallInfo callId;
    public CallCenterCall ccall;

    @FXML
    public TextArea ctiInfo;




    @Override
    public void terminalConnectionBridged(CallControlTerminalConnectionEvent callControlTerminalConnectionEvent) {

    }

    @Override
    public void terminalConnectionDropped(CallControlTerminalConnectionEvent callControlTerminalConnectionEvent) {


        String ucid = CallUtils.getUCID(callControlTerminalConnectionEvent.getCall());
        long callID = CallUtils.getCallID(callControlTerminalConnectionEvent.getCall());
        callingAddress = callControlTerminalConnectionEvent.getCallingAddress();
        calledAddress = callControlTerminalConnectionEvent.getCalledAddress();

        try{

            long time = System.currentTimeMillis();
            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String str = dayTime.format(new Date(time));
            CallTimer.stop();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    static_ctiInfo.setText("");
                    static_current_channel.setText("none");
                    static_call_timer.setText("00:00:00");
                }
            });






            String json =  "{ "
                    + "\"agentid\":\""+ AgentState.agentid+"\"}";
            CallRestAPI api = new CallRestAPI();
            try{
                api.doPost(json,"unAssignChatToAgent");
            }
            catch (Exception e){
                e.printStackTrace();
            }




        }

        catch (Exception e){

            System.err.println(e);
        }






    }

    @Override
    public void terminalConnectionHeld(CallControlTerminalConnectionEvent callControlTerminalConnectionEvent) {

    }

    @Override
    public void terminalConnectionInUse(CallControlTerminalConnectionEvent callControlTerminalConnectionEvent) {

    }

    @Override
    public void terminalConnectionRinging(CallControlTerminalConnectionEvent callControlTerminalConnectionEvent) {

        System.out.println("terminalConnectionRinging");

    }

    @Override
    public void terminalConnectionTalking(CallControlTerminalConnectionEvent callControlTerminalConnectionEvent) {


        String ucid = CallUtils.getUCID(callControlTerminalConnectionEvent.getCall());
        long callID = CallUtils.getCallID(callControlTerminalConnectionEvent.getCall());
        callingAddress = callControlTerminalConnectionEvent.getCallingAddress();
        calledAddress = callControlTerminalConnectionEvent.getCalledAddress();

        try{

            long time = System.currentTimeMillis();
            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String str = dayTime.format(new Date(time));
            // static_ctiInfo.appendText("connectionAlerting :: :: CallID ::"+callID+"::Calling party::"+callingAddress.getName()+"::Called party::"+calledAddress.getName()+"\n");
            CallTimer timer = new CallTimer();
            timer.start();
            String json =  "{ "
                    + "\"agentid\":\""+ AgentState.agentid+"\"}";
            CallRestAPI api = new CallRestAPI();
            try{
                api.doPost(json,"unAssignChatToAgent");
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        catch (Exception e){
            System.err.println(e);
        }








    }

    @Override
    public void terminalConnectionUnknown(CallControlTerminalConnectionEvent callControlTerminalConnectionEvent) {

    }

    @Override
    public void terminalConnectionActive(TerminalConnectionEvent terminalConnectionEvent) {

    }

    @Override
    public void terminalConnectionCreated(TerminalConnectionEvent terminalConnectionEvent) {

    }

    @Override
    public void terminalConnectionDropped(TerminalConnectionEvent terminalConnectionEvent) {

    }

    @Override
    public void terminalConnectionPassive(TerminalConnectionEvent terminalConnectionEvent) {

    }

    @Override
    public void terminalConnectionRinging(TerminalConnectionEvent terminalConnectionEvent) {

    }

    @Override
    public void terminalConnectionUnknown(TerminalConnectionEvent terminalConnectionEvent) {

    }

    @Override
    public void connectionAlerting(CallControlConnectionEvent callControlConnectionEvent) {

        String ucid = CallUtils.getUCID(callControlConnectionEvent.getCall());
        long callID = CallUtils.getCallID(callControlConnectionEvent.getCall());
        callingAddress = callControlConnectionEvent.getCallingAddress();
        calledAddress = callControlConnectionEvent.getCalledAddress();

        try{

            long time = System.currentTimeMillis();
            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String str = dayTime.format(new Date(time));
           // static_ctiInfo.appendText("connectionAlerting :: :: CallID ::"+callID+"::Calling party::"+callingAddress.getName()+"::Called party::"+calledAddress.getName()+"\n");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    static_ctiInfo.setText(callingAddress.getName());
                    static_current_channel.setText("voice");
                }
            });




            String json =  "{ "
                    + "\"chatId\":\""+callingAddress.getName()+"\","
                    + "\"channel\":\""+"voice"+"\","
                    + "\"agentid\":\""+ AgentState.agentid+"\"}";
            CallRestAPI api = new CallRestAPI();
            try{
                api.doPost(json,"assignChatToAgent");
            }
            catch (Exception e){
                e.printStackTrace();
            }




        }

        catch (Exception e){

            System.err.println(e);
        }






    }

    @Override
    public void connectionDialing(CallControlConnectionEvent callControlConnectionEvent) {

    }

    @Override
    public void connectionDisconnected(CallControlConnectionEvent callControlConnectionEvent) {

    }

    @Override
    public void connectionEstablished(CallControlConnectionEvent callControlConnectionEvent) {

    }

    @Override
    public void connectionFailed(CallControlConnectionEvent callControlConnectionEvent) {

    }

    @Override
    public void connectionInitiated(CallControlConnectionEvent callControlConnectionEvent) {

    }

    @Override
    public void connectionNetworkAlerting(CallControlConnectionEvent callControlConnectionEvent) {

    }

    @Override
    public void connectionNetworkReached(CallControlConnectionEvent callControlConnectionEvent) {

    }

    @Override
    public void connectionOffered(CallControlConnectionEvent callControlConnectionEvent) {

    }

    @Override
    public void connectionQueued(CallControlConnectionEvent callControlConnectionEvent) {

    }

    @Override
    public void connectionUnknown(CallControlConnectionEvent callControlConnectionEvent) {

    }

    @Override
    public void connectionAlerting(ConnectionEvent connectionEvent) {

    }

    @Override
    public void connectionConnected(ConnectionEvent connectionEvent) {

    }

    @Override
    public void connectionCreated(ConnectionEvent connectionEvent) {

    }

    @Override
    public void connectionDisconnected(ConnectionEvent connectionEvent) {

    }

    @Override
    public void connectionFailed(ConnectionEvent connectionEvent) {

    }

    @Override
    public void connectionInProgress(ConnectionEvent connectionEvent) {

    }

    @Override
    public void connectionUnknown(ConnectionEvent connectionEvent) {

    }

    @Override
    public void callActive(CallEvent callEvent) {

    }

    @Override
    public void callInvalid(CallEvent callEvent) {

    }

    @Override
    public void callEventTransmissionEnded(CallEvent callEvent) {

    }

    @Override
    public void singleCallMetaProgressStarted(MetaEvent metaEvent) {

    }

    @Override
    public void singleCallMetaProgressEnded(MetaEvent metaEvent) {

    }

    @Override
    public void singleCallMetaSnapshotStarted(MetaEvent metaEvent) {

    }

    @Override
    public void singleCallMetaSnapshotEnded(MetaEvent metaEvent) {

    }

    @Override
    public void multiCallMetaMergeStarted(MetaEvent metaEvent) {

    }

    @Override
    public void multiCallMetaMergeEnded(MetaEvent metaEvent) {

    }

    @Override
    public void multiCallMetaTransferStarted(MetaEvent metaEvent) {

    }

    @Override
    public void multiCallMetaTransferEnded(MetaEvent metaEvent) {

    }
}
