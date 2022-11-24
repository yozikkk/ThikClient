package kz.nbt.cti.ops;

import kz.nbt.cti.AgentState;
import kz.nbt.cti.controller.AgentStateUI;
import kz.nbt.cti.restapi.CallRestAPI;
import org.json.JSONObject;

import java.io.IOException;

public class CRMOps extends AgentStateUI {

    public void getCustomerInfo(String clientid)  {



        try{

            CallRestAPI api = new CallRestAPI();
            String jsonString = api.getCustomerInfo("getCustomerInfo",Long.parseLong(clientid));
            JSONObject jsonObject = new JSONObject(jsonString);
            static_name.setText(jsonObject.getString("name"));
            static_surname.setText(jsonObject.getString("surname"));
            static_email.setText(jsonObject.getString("email"));
            Long balance = jsonObject.getLong("balance");
            System.out.println("Balance field:"+balance);
            static_balance.setText(balance.toString());

        }
        catch (Exception e){
            e.printStackTrace();

        }


    }


}
