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

public class Ring extends AppCompatActivity {
    public static EditText editText7;
    public static final String Buzz = "buzzKey";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        editText7=(EditText)findViewById(R.id.editText7);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editText7.setText(sharedpreferences.getString(Buzz,"RING"));
        editText7.setSelection(editText7.getText().length());
        ImageView ringBackImage=(ImageView)findViewById(R.id.ringBackImage);
        ringBackImage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(Ring.this,MainActivity.class);
                        startActivity(i);
                    }
                }
        );
        Button button8=(Button)findViewById(R.id.button8);
        button8.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String n=editText7.getText().toString();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Buzz, n);
                        editor.apply();
                        Toast.makeText(Ring.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
