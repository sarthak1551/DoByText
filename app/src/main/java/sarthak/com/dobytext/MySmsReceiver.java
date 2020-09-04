package sarthak.com.dobytext;

import android.Manifest;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.CallLog;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;
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


public class MySmsReceiver extends BroadcastReceiver {
    SharedPreferences sharedpreferences;
    public NotificationManager mNotificationManager;
    GPSTracker gps;

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        assert action != null;
        if (action.equals("android.provider.Telephony.SMS_RECEIVED")) {
            sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

            String messageBody = null;
            String phoneNumber = null;

            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                phoneNumber = smsMessage.getDisplayOriginatingAddress();
                messageBody = smsMessage.getMessageBody();

            }


            assert messageBody != null;
            if (messageBody.equals(sharedpreferences.getString(Location, "SENDLOCATION"))) {


                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                assert cm != null;
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    gps = new GPSTracker(context);
                    android.location.Location gpsLocation = gps.getLocation(LocationManager.GPS_PROVIDER);
                    if (gpsLocation != null) {
                        double latitude = gpsLocation.getLatitude();
                        double longitude = gpsLocation.getLongitude();
                        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                        List<Address> addresses = null;
                        try {
                            addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String address = null;
                        if (addresses != null) {
                            address = addresses.get(0).getAddressLine(0);
                        }
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber, null, address, null, null);

                    } else {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber, null, "Please Try Again Later,Location Not Available.", null, null);
                    }
                } else {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, "Please Try Again Later,Location Not Available.", null, null);

                }

            } else
                //Callback
                if (messageBody.equals(sharedpreferences.getString(Name, "CALLMEBACK"))) {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        Intent intent1 = new Intent(Intent.ACTION_CALL);
                        intent1.setData(Uri.parse("tel:" + phoneNumber));
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);
                    } else {
                        Intent intent1 = new Intent(Intent.ACTION_CALL);
                        intent1.setData(Uri.parse("tel:" + phoneNumber));
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        context.startActivity(intent1);
                    }
                } else if (messageBody.equals(sharedpreferences.getString(Vibrate, "VIBRATEPHONE"))) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        ((Vibrator) Objects.requireNonNull(context.getSystemService(VIBRATOR_SERVICE))).vibrate(VibrationEffect.createOneShot(15000, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        ((Vibrator) Objects.requireNonNull(context.getSystemService(VIBRATOR_SERVICE))).vibrate(15000);
                    }
                } else if (messageBody.equals(sharedpreferences.getString(Wifi, "WIFI"))) {
                    WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    assert wifi != null;
                    if (wifi.isWifiEnabled())
                        wifi.setWifiEnabled(false);
                    else
                        wifi.setWifiEnabled(true);
                } else if (messageBody.equals(sharedpreferences.getString(Buzz, "RING"))) {

                    int i = 0;
                    try {
                        i = Settings.Global.getInt(context.getContentResolver(), "zen_mode");
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                    AudioManager am;
                    am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    if (i == 0) {
                        if (am != null) {
                            int max_volume_level = am.getStreamMaxVolume(AudioManager.STREAM_RING);
                            am.setStreamVolume(AudioManager.STREAM_RING, max_volume_level, AudioManager.FLAG_ALLOW_RINGER_MODES);
                            am.setRingerMode(2);
                            Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                            final Ringtone ringtoneSound = RingtoneManager.getRingtone(context, ringtoneUri);
                            if (ringtoneSound != null) {
                                ringtoneSound.play();
                                final Timer t = new Timer();
                                t.schedule(new TimerTask() {
                                    public void run() {
                                        ringtoneSound.stop();
                                        t.cancel();
                                    }
                                }, 20000);


                            }
                        }
                    }

                } else if (messageBody.equals(sharedpreferences.getString(FlashOn, "FLASHLIGHTON"))) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        try {
                            CameraManager camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                            String cameraId = null; // Usually front camera is at 0 position.
                            if (camManager != null) {
                                cameraId = camManager.getCameraIdList()[0];
                                camManager.setTorchMode(cameraId, true);
                            }
                        } catch (CameraAccessException e) {
                        }
                    } else {

                        Camera camera = Camera.open();
                        Camera.Parameters parameters = camera.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                    }
                } else if (messageBody.equals(sharedpreferences.getString(FlashOff, "FLASHLIGHTOFF"))) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        try {
                            String cameraId;
                            CameraManager camManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                            if (camManager != null) {
                                cameraId = camManager.getCameraIdList()[0]; // Usually front camera is at 0 position.
                                camManager.setTorchMode(cameraId, false);
                            }
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Camera camera = Camera.open();
                        Camera.Parameters parameters = camera.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.stopPreview();
                    }
                } else if (messageBody.equals(sharedpreferences.getString(Calls, "CALLLOGS"))) {
                    String sb = "";
                    String allcalls = "";
                    String[] projection = new String[]{
                            CallLog.Calls.CACHED_NAME,
                            CallLog.Calls.NUMBER,
                            CallLog.Calls.TYPE,
                            CallLog.Calls.DATE,
                            CallLog.Calls.DURATION

                    };
                    String strOrder = CallLog.Calls.DATE + " DESC";
                    Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, null, null, strOrder);
                    int j = 10;
                    while (managedCursor.moveToNext() && j > 0) {
                        j = j - 1;
                        String callername = managedCursor.getString(0);
                        String phNum = managedCursor.getString(1);
                        String callTypeCode = managedCursor.getString(2);
                        String strcallDate = managedCursor.getString(3);
                        Date callDate = new Date(Long.valueOf(strcallDate));
                        String callDuration = managedCursor.getString(4);
                        String callType = null;
                        int callcode = Integer.parseInt(callTypeCode);
                        switch (callcode) {
                            case CallLog.Calls.OUTGOING_TYPE:
                                callType = "Outgoing";
                                break;
                            case CallLog.Calls.INCOMING_TYPE:
                                callType = "Incoming";
                                break;
                            case CallLog.Calls.MISSED_TYPE:
                                callType = "Missed";
                                break;
                        }
                        sb = ("Name:" + callername + "\nNo:" + phNum + "\nType:"
                                + callType + "\nDate:" + callDate
                                + "\nDuration in sec:" + callDuration) +
                                "\n\n";
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber, null, sb, null, null);
                    }


                    Objects.requireNonNull(managedCursor).close();


                } else if (messageBody.equals(sharedpreferences.getString(ringMode, "RINGMODE"))) {
                    int i = 0;
                    try {
                        i = Settings.Global.getInt(context.getContentResolver(), "zen_mode");
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                    AudioManager am;
                    am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    if (i == 0) {
                        if (am != null) {
                            int max_volume_level = am.getStreamMaxVolume(AudioManager.STREAM_RING);
                            am.setStreamVolume(AudioManager.STREAM_RING, max_volume_level, AudioManager.FLAG_ALLOW_RINGER_MODES);
                            am.setRingerMode(2);

                        }
                    }
                } else if (messageBody.equals(sharedpreferences.getString(vibMode, "VIBMODE"))) {
                    int i = 0;
                    try {
                        i = Settings.Global.getInt(context.getContentResolver(), "zen_mode");
                    } catch (Settings.SettingNotFoundException e) {
                        e.printStackTrace();
                    }
                    AudioManager am;
                    am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    if (i == 0) {
                        if (am != null) {
                            am.setRingerMode(1);
                        }
                    }
                } else if
                        (messageBody.equals(sharedpreferences.getString(Unhide, "UNHIDE"))) {
                    PackageManager p = context.getPackageManager();
                    ComponentName componentName = new ComponentName(context, SplashScreen.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
                    p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                } else if (messageBody.equals(sharedpreferences.getString(Hide, "HIDE"))) {
                    PackageManager p = context.getPackageManager();
                    ComponentName componentName = new ComponentName(context, SplashScreen.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
                    p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                } else if (messageBody.equals(sharedpreferences.getString(HelpKey, "HELP")))

                {


                    String s = "CALLBACK:" + sharedpreferences.getString(Name, "CALLMEBACK")
                            + "\nVIBRATE:" + sharedpreferences.getString(Vibrate, "VIBRATEPHONE")
                            + "\nWIFI:" + sharedpreferences.getString(Wifi, "WIFI")
                            + "\nLOCATION:" + sharedpreferences.getString(Location, "SENDLOCATION")
                            + "\nFLASHLIGHTON:" + sharedpreferences.getString(FlashOn, "FLASHLIGHTON")
                            + "\nFLASHLIGHTOFF:" + sharedpreferences.getString(FlashOff, "FLASHLIGHTOFF");
                    String p = "RING:" + sharedpreferences.getString(Buzz, "RING")
                            + "\nCALLLOGS:" + sharedpreferences.getString(Calls, "CALLLOGS")
                            + "\nRINGMODE:" + sharedpreferences.getString(ringMode, "RINGMODE")
                            + "\nVIBMODE:" + sharedpreferences.getString(vibMode, "VIBMODE")
                            + "\nHIDEAPP:" + sharedpreferences.getString(Hide, "HIDE")
                            + "\nUNHIDE:" + sharedpreferences.getString(Unhide, "UNHIDE")
                            + "\nHELP:" + sharedpreferences.getString(HelpKey, "HELP");
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, s, null, null);
                    smsManager.sendTextMessage(phoneNumber, null, p, null, null);
                }

        }

    }

}
