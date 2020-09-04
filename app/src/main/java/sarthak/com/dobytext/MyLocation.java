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

public class MyLocation extends AppCompatActivity {
    public static EditText editText4;
    public static final String Location = "LocationKey";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        editText4=(EditText)findViewById(R.id.editText4);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editText4.setText(sharedpreferences.getString(Location,"SENDLOCATION"));
        editText4.setSelection(editText4.getText().length());
        ImageView imageView11=(ImageView)findViewById(R.id.imageView11);
        imageView11.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(MyLocation.this,MainActivity.class);
                        startActivity(i);
                    }
                }
        );
        Button button5=(Button)findViewById(R.id.button5);
        button5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String n=editText4.getText().toString();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Location, n);
                        editor.apply();
                        Toast.makeText(MyLocation.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
}
