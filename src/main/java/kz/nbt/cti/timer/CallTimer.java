package kz.nbt.cti.timer;

import javafx.application.Platform;
import kz.nbt.cti.controller.AgentStateUI;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class CallTimer extends AgentStateUI {


    static long min,sec,hr,totalSec=0;
    static Timer timerCustom;

    public static  void  convertTime(){

        min = TimeUnit.SECONDS.toMinutes(totalSec);
        sec = totalSec - (min*60);
        hr = TimeUnit.MINUTES.toHours(min);
        min = min -(hr*60);
        //System.out.println(hr+":"+min+":"+sec);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                static_call_timer.setText(hr+":"+min+":"+sec);
            }
        });



        totalSec++;

    }

    public static  void start(){

        /// totalSec= 10 *30;

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                convertTime();

            }
        };

        timerCustom = new Timer();
        timerCustom.schedule(timerTask,0,1000);






    }

    public static void stop(){
        timerCustom.cancel();
        timerCustom.purge();
        totalSec=0;
    }





}
