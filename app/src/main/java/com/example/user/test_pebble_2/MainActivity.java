package com.example.user.test_pebble_2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.getpebble.android.*;
import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;
import org.w3c.dom.Text;

import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    private Context context;
    private TextView dataView_x;
    private TextView dataView_y;
    private TextView dataView_z;
    private String TAG="test";
    private PebbleKit.PebbleDataReceiver dataReceiver;

    final int AppKeyAge_x = 1;
    final int AppKeyAge_y = 2;
    final int AppKeyAge_z = 3;

    // The UUID of the watchapp
    final UUID appUuid = UUID.fromString("6136584b-35cb-4a20-82ba-0bcdf097db06");
//6136584b-35cb-4a20-82ba-0bcdf097db06
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;

        dataView_x=(TextView)findViewById(R.id.textView);
        dataView_y=(TextView)findViewById(R.id.textView3);
        dataView_z=(TextView)findViewById(R.id.textView4);

        // Create a new receiver to get AppMessages from the C app
        dataReceiver = new PebbleKit.PebbleDataReceiver(appUuid) {

            @Override
            public void receiveData(Context context, int transaction_id,
                                    PebbleDictionary dict) {
                // A new AppMessage was received, tell Pebble
                PebbleKit.sendAckToPebble(context, transaction_id);
                // If the tuple is present...
                Long ageValue_x = dict.getInteger(AppKeyAge_x);
                if(ageValue_x != null) {
                    // Read the integer value
                    int age = ageValue_x.intValue();
                    dataView_x.setText("X:"+ageValue_x);
                    Log.i("recieve_data_test:","success!");
                }
                // If the tuple is present...
                Long ageValue_y = dict.getInteger(AppKeyAge_y);
                if(ageValue_y != null) {
                    // Read the integer value
                    int age = ageValue_y.intValue();
                    dataView_y.setText("Y:"+ageValue_y);
                    Log.i("recieve_data_test:","success!");
                }
                // If the tuple is present...
                Long ageValue_z = dict.getInteger(AppKeyAge_z);
                if(ageValue_z != null) {
                    // Read the integer value
                    int age = ageValue_z.intValue();
                    dataView_z.setText("Z:"+ageValue_z);
                    Log.i("recieve_data_test:","success!");
                }
            }
        };
    }
    @Override
    public void onResume() {
        super.onResume();

        // Register the receiver
        PebbleKit.registerReceivedDataHandler(getApplicationContext(), dataReceiver);
    }
    @Override
    protected void onPause() {
        super.onPause();

        try {
            unregisterReceiver(dataReceiver);
        } catch(Exception e) {
            Log.w(TAG, "Receiver did not need to be unregistered");
        }
    }

}
