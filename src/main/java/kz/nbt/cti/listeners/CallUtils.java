/**
 * This class contains two static utility methods which are used by 
 * the JtapiListener class.
 */
package kz.nbt.cti.listeners;

import javax.telephony.Call;

import com.avaya.jtapi.tsapi.ITsapiCallIDPrivate;
import com.avaya.jtapi.tsapi.LucentV7CallInfo;

public class CallUtils {


	/**
	This method is used by JtapiListner methods to retrieve information 
	about Call ID.
	 */
	public static long getCallID(Call c) {
	
		return ((c == null) ? 0 : ((ITsapiCallIDPrivate) c).getTsapiCallID());
	}

	/**
	This method is used by JtapiListner methods to retrieve information 
	about Universal Call ID.
	
	
	 */
	
	public static String getUCID(Call call){
		
		if (call instanceof LucentV7CallInfo){	
			LucentV7CallInfo lucentCall = (LucentV7CallInfo)call;
			String ucid =lucentCall.getUCID();
		
			return ucid;
		}
		return null;
	}
}


