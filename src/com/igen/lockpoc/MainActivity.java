package com.igen.lockpoc;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Switch;

import java.io.*;
import java.net.*;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    //Execute the SendTask background task.  This method is run when the switch changes state.
    public void switchState(View view) {
    	new SendTask().execute(view);
    }
    	
	private class SendTask extends AsyncTask<View,Void,Void> {
		//Create a network socket to connect to the arduino, and a PrintWriter to write data to the socket.
		Socket arduinoSocket;
		PrintWriter arduino;
		
		@Override
		protected Void doInBackground(View... params) {
		    try {
		    		//Initialize the socket to connect to the address and port that the ethernet shield was configured for.
				arduinoSocket = new Socket("192.168.2.177",44);
				//Create a printwriter that writes data into the OutputStream of the Socket.
				arduino = new PrintWriter(arduinoSocket.getOutputStream(),true);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	    //If the switch is on, send an l.  Otherwise send a u.
		    if(((Switch) params[0]).isChecked()){
		    	arduino.println("l");
		    }	
		    else {
		    	arduino.println("u");
		    }
		//Close the PrintWriter 
	    	arduino.close();
	    	
	    	try {		//Disconnect from the arduino.
				arduinoSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
}    


