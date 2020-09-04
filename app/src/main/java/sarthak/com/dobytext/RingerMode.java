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

public class RingerMode extends AppCompatActivity {
    public static EditText editText9;
    public static EditText editText10;
    public static EditText editText11;
    public static final String ringMode = "ringModeKey";
    public static final String vibMode = "vibModeKey";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringer_mode);
        editText9=(EditText)findViewById(R.id.editText9);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editText9.setText(sharedpreferences.getString(ringMode,"RINGMODE"));
        editText9.setSelection(editText9.getText().length());

        editText10=(EditText)findViewById(R.id.editText10);
        editText10.setText(sharedpreferences.getString(vibMode,"VIBMODE"));
        editText10.setSelection(editText10.getText().length());

        ImageView ringerModeBackImage=(ImageView)findViewById(R.id.ringerModeBackImage);
        ringerModeBackImage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(RingerMode.this,MainActivity.class);
                        startActivity(i);
                    }
                }
        );
        Button button10=(Button)findViewById(R.id.button10);
        button10.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String n=editText9.getText().toString();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(ringMode, n);
                        editor.apply();
                        Toast.makeText(RingerMode.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                }
        );
        Button button11=(Button)findViewById(R.id.button11);
        button11.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String n=editText10.getText().toString();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(vibMode, n);
                        editor.apply();
                        Toast.makeText(RingerMode.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
}
