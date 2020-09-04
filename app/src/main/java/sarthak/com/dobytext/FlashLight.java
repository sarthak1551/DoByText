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

public class FlashLight extends AppCompatActivity {
    public static EditText editText5;
    public static EditText editText6;
    public static final String FlashOn = "flashonKey";
    public static final String FlashOff = "flashoffKey";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_light);
        editText5=(EditText)findViewById(R.id.editText5);
        editText6=(EditText)findViewById(R.id.editText6);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editText5.setText(sharedpreferences.getString(FlashOn,"FLASHLIGHTON"));
        editText5.setSelection(editText5.getText().length());
        editText6.setText(sharedpreferences.getString(FlashOff,"FLASHLIGHTOFF"));
        editText6.setSelection(editText6.getText().length());
        ImageView flashLightBackImage=(ImageView)findViewById(R.id.flashLightBackImage);
        flashLightBackImage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(FlashLight.this,MainActivity.class);
                        startActivity(i);
                    }
                }
        );
        Button button6=(Button)findViewById(R.id.button6);
        button6.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String n=editText5.getText().toString();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(FlashOn, n);
                        editor.apply();
                        Toast.makeText(FlashLight.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                }
        );
        Button button7=(Button)findViewById(R.id.button7);
        button7.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String n=editText6.getText().toString();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(FlashOff, n);
                        editor.apply();
                        Toast.makeText(FlashLight.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
}
