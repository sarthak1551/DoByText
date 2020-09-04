package sarthak.com.dobytext;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import static sarthak.com.dobytext.CallBack.MyPREFERENCES;
import static sarthak.com.dobytext.CallBack.Name;
import static sarthak.com.dobytext.CallLogs.Calls;
import static sarthak.com.dobytext.FlashLight.FlashOff;
import static sarthak.com.dobytext.FlashLight.FlashOn;
import static sarthak.com.dobytext.Help.HelpKey;
import static sarthak.com.dobytext.MyLocation.Location;
import static sarthak.com.dobytext.Ring.Buzz;
import static sarthak.com.dobytext.RingerMode.ringMode;
import static sarthak.com.dobytext.RingerMode.vibMode;
import static sarthak.com.dobytext.UnhideApp.Hide;
import static sarthak.com.dobytext.UnhideApp.Unhide;
import static sarthak.com.dobytext.Vibrate.Vibrate;
import static sarthak.com.dobytext.WifiSettings.Wifi;


import static sarthak.com.dobytext.CallBack.MyPREFERENCES;

public class MainActivity extends AppCompatActivity {
    android.support.v7.app.ActionBar actionBar;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent gpstracker = new Intent(MainActivity.this, GPSTracker.class);
        startService(gpstracker);


        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff562c")));

                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_CALL_LOG) == PackageManager.PERMISSION_GRANTED)
                {
            //TO do here if permission is granted by user

        } else {
            //ask for permission if user didnot given
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.WRITE_CALL_LOG}, 0);
            }
        }
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String s=sharedpreferences.getString(FlashOn,"FLASHLIGHTON")+","+sharedpreferences.getString(FlashOff,"FLASHLIGHTOFF");
        String t=sharedpreferences.getString(ringMode,"RINGMODE")+","+sharedpreferences.getString(vibMode,"VIBMODE");
        String u=sharedpreferences.getString(Hide,"HIDE")+","+sharedpreferences.getString(Unhide,"UNHIDE");

        String[] desc = {sharedpreferences.getString(Name,"CALLMEBACK"),
                sharedpreferences.getString(Vibrate,"VIBRATEPHONE"),
                sharedpreferences.getString(Wifi,"WIFI"),
                sharedpreferences.getString(Location,"SENDLOCATION"),
                s,sharedpreferences.getString(Buzz,"RING"),sharedpreferences.getString(Calls,"CALLLOGS"),
                t,u,sharedpreferences.getString(HelpKey,"HELP")};


        String[] features = {"Call Back", "Vibrate", "Wifi Settings", "Location","FLASHLIGHT", "RING","Call Logs","Ringer Mode","Unhide Application","Help"};
        int[] images = {R.drawable.call,R.drawable.vibrate,R.drawable.wifi,R.drawable.gps,R.drawable.flashlight,R.drawable.ring,R.drawable.callogs,R.drawable.bell,R.drawable.unhide,R.drawable.help};
        ListView myListView = (ListView) findViewById(R.id.myListView);
        CustomAdapter myAdapter = new CustomAdapter(this, features,desc,images);

        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        if (position == 0) {
                            Intent i = new Intent(MainActivity.this, CallBack.class);
                            startActivity(i);


                        } else if (position == 1) {
                            Intent i = new Intent(MainActivity.this, Vibrate.class);
                            startActivity(i);
                        } else if (position == 2) {
                            Intent i = new Intent(MainActivity.this, WifiSettings.class);
                            startActivity(i);
                        } else if (position == 3) {
                            Intent i = new Intent(MainActivity.this, MyLocation.class);
                            startActivity(i);
                        } else if (position == 4) {
                            Intent i = new Intent(MainActivity.this, FlashLight.class);
                            startActivity(i);
                            }
                        else if (position == 5) {
                            Intent i = new Intent(MainActivity.this, Ring.class);
                            startActivity(i);
                        }
                        else if (position == 6) {
                            Intent i = new Intent(MainActivity.this, CallLogs.class);
                            startActivity(i);
                        }
                        else if (position == 7) {
                            Intent i = new Intent(MainActivity.this, RingerMode.class);
                            startActivity(i);
                        }
                        else if (position == 8) {
                            Intent i = new Intent(MainActivity.this, UnhideApp.class);
                            startActivity(i);
                        }
                        else if (position == 9) {
                            Intent i = new Intent(MainActivity.this, Help.class);
                            startActivity(i);
                        }
                    }
                }
        );

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED && grantResults[4] == PackageManager.PERMISSION_GRANTED && grantResults[5] == PackageManager.PERMISSION_GRANTED&& grantResults[6] == PackageManager.PERMISSION_GRANTED&& grantResults[7] == PackageManager.PERMISSION_GRANTED&& grantResults[8] == PackageManager.PERMISSION_GRANTED&& grantResults[9] == PackageManager.PERMISSION_GRANTED && grantResults[10] == PackageManager.PERMISSION_GRANTED && grantResults[11] == PackageManager.PERMISSION_GRANTED) {
                    //                    grantResult[0] means it will check for the first postion permission which is READ_EXTERNAL_STORAGE
                    //                    grantResult[1] means it will check for the Second postion permission which is CAMERA
                    Toast.makeText(this, "All Permissions Granted", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "All Permissions not Granted", Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflowmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.hide_application)
        {
            PackageManager p = getPackageManager();
            ComponentName componentName = new ComponentName(MainActivity.this,sarthak.com.dobytext.SplashScreen.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
            p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            ProgressDialog progress = new ProgressDialog(this);
            progress.setMessage("Hiding Application \nSend Message UNHIDE to unhide the application :) ");
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setIndeterminate(true);
            progress.show();

        }
        else
            if(item.getItemId()==R.id.contact_us)
            {
                Intent i =new Intent(MainActivity.this,ContactUs.class);
                startActivity(i);
            }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String s=sharedpreferences.getString(FlashOn,"FLASHLIGHTON")+","+sharedpreferences.getString(FlashOff,"FLASHLIGHTOFF");
        String t=sharedpreferences.getString(ringMode,"RINGMODE")+","+sharedpreferences.getString(vibMode,"VIBMODE");
        String u=sharedpreferences.getString(Hide,"HIDE")+","+sharedpreferences.getString(Unhide,"UNHIDE");

        String[] desc = {sharedpreferences.getString(Name,"CALLMEBACK"),
                sharedpreferences.getString(Vibrate,"VIBRATEPHONE"),
                sharedpreferences.getString(Wifi,"WIFI"),
                sharedpreferences.getString(Location,"SENDLOCATION"),
                s,sharedpreferences.getString(Buzz,"RING"),sharedpreferences.getString(Calls,"CALLLOGS"),
                t,u,sharedpreferences.getString(HelpKey,"HELP")};


        String[] features = {"Call Back", "Vibrate", "Wifi Settings", "Location","FLASHLIGHT", "RING","Call Logs","Ringer Mode","Unhide Application","Help"};
        int[] images = {R.drawable.call,R.drawable.vibrate,R.drawable.wifi,R.drawable.gps,R.drawable.flashlight,R.drawable.ring,R.drawable.callogs,R.drawable.bell,R.drawable.unhide,R.drawable.help};
        ListView myListView = (ListView) findViewById(R.id.myListView);
        CustomAdapter myAdapter = new CustomAdapter(this, features,desc,images);

        myListView.setAdapter(myAdapter);

        }
}


