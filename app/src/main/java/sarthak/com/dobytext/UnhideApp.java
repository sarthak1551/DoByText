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

public class UnhideApp extends AppCompatActivity {
    public static EditText editText12;
    public static final String Hide = "hideKey";
    public static EditText editText13;
    public static final String Unhide = "unhideKey";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unhide_app);
        editText12=(EditText)findViewById(R.id.editText12);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editText12.setText(sharedpreferences.getString(Hide,"HIDE"));
        editText12.setSelection(editText12.getText().length());
        editText13=(EditText)findViewById(R.id.editText13);
        editText13.setText(sharedpreferences.getString(Unhide,"UNHIDE"));
        editText13.setSelection(editText13.getText().length());
        ImageView unhideBackImage=(ImageView)findViewById(R.id.unhideBackImage);
        unhideBackImage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(UnhideApp.this,MainActivity.class);
                        startActivity(i);
                    }
                }
        );
        Button button13=(Button)findViewById(R.id.button13);
        button13.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String n=editText12.getText().toString();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Hide, n);
                        editor.apply();
                        Toast.makeText(UnhideApp.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                }
        );
        Button button14=(Button)findViewById(R.id.button14);
        button14.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String n=editText13.getText().toString();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Unhide, n);
                        editor.apply();
                        Toast.makeText(UnhideApp.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                }
        );


    }
}
