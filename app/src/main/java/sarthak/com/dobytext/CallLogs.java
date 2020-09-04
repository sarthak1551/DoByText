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

public class CallLogs extends AppCompatActivity {
    public static EditText editText8;
    public static final String Calls = "CallKey";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_logs);
        editText8=(EditText)findViewById(R.id.editText8);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editText8.setText(sharedpreferences.getString(Calls,"CALLLOGS"));
        editText8.setSelection(editText8.getText().length());
        ImageView callLogsBackImage=(ImageView)findViewById(R.id.callLogsBackImage);
        callLogsBackImage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(CallLogs.this,MainActivity.class);
                        startActivity(i);
                    }
                }
        );
        Button button9=(Button)findViewById(R.id.button9);
        button9.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String n=editText8.getText().toString();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Calls, n);
                        editor.apply();
                        Toast.makeText(CallLogs.this,"Saved",Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
