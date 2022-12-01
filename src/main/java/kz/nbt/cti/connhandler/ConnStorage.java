package kz.nbt.cti.connhandler;

import javax.telephony.*;
import java.util.ArrayList;

public class ConnStorage {
    public static Connection  connection;

    public void saveConnection(Connection[] connections) {
        Connection tempConn = null;
        for(int conCount =0;conCount < connections.length;conCount++){
            tempConn = connections[conCount];
            connection = tempConn;
        }
    }
}
