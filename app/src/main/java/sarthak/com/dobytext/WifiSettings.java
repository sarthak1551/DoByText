package sarthak.com.dobytext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class WifiSettings extends AppCompatActivity {
    public static EditText editText3;
    public static final String Wifi = "WifiKey";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_settings);
        editText3=(EditText)findViewById(R.id.editText3);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editText3.setText(sharedpreferences.getString(Wifi,"WIFI"));
        editText3.setSelection(editText3.getText().length());
        ImageView imageView10=(ImageView)findViewById(R.id.imageView10);
        imageView10.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(WifiSettings.this,MainActivity.class);
                        startActivity(i);
                    }
                }
        );
        Button button4=(Button)findViewById(R.id.button4);
        button4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String n=editText3.getText().toString();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Wifi, n);
                        editor.apply();
                        Toast.makeText(WifiSettings.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
}
