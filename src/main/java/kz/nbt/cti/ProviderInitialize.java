package kz.nbt.cti;

import javax.telephony.JtapiPeer;
import javax.telephony.JtapiPeerFactory;
import javax.telephony.Provider;

public class ProviderInitialize {

    private Provider provider;
    public Provider getProvider() {

        try{
            JtapiPeer peer = JtapiPeerFactory.getJtapiPeer(null);
            String[] myServices = peer.getServices();
            String providerString = myServices[0]+";login=crm1"+";passwd=P@ssword1";
            provider = peer.getProvider(providerString);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return provider;
    }


}
