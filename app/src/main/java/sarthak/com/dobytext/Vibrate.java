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

public class Vibrate extends AppCompatActivity {
    public static EditText editText2;
    SharedPreferences sharedpreferences;
    public static final String Vibrate = "vibrateKey";
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrate);
        editText2=(EditText)findViewById(R.id.editText2);
        ImageView imageView9=(ImageView)findViewById(R.id.imageView9);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editText2.setText(sharedpreferences.getString(Vibrate,"VIBRATEPHONE"));
        editText2.setSelection(editText2.getText().length());
        imageView9.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(Vibrate.this,MainActivity.class);
                        startActivity(i);
                    }
                }
        );
        Button button3=(Button)findViewById(R.id.button3);
        button3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String n=editText2.getText().toString();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Vibrate, n);
                        editor.apply();
                        Toast.makeText(Vibrate.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
}
