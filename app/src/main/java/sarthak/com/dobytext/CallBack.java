package sarthak.com.dobytext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CallBack extends AppCompatActivity {
    public EditText editText;
    public static final String Name = "nameKey";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_back);

          editText=(EditText)findViewById(R.id.editText);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editText.setText(sharedpreferences.getString(Name,"CALLMEBACK"));
        editText.setSelection(editText.getText().length());

        ImageView imageView8=(ImageView)findViewById(R.id.imageView8);
        imageView8.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(CallBack.this,MainActivity.class);
                        startActivity(i);
                    }
                }
        );

        Button button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String n=editText.getText().toString();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Name, n);
                        editor.apply();
                        Toast.makeText(CallBack.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
}
