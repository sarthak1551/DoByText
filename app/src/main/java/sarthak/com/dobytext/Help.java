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

public class Help extends AppCompatActivity {
    public static EditText editText14;
    public static final String HelpKey = "helpKey";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        editText14=(EditText)findViewById(R.id.editText14);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editText14.setText(sharedpreferences.getString(HelpKey,"HELP"));
        editText14.setSelection(editText14.getText().length());
        ImageView helpBackImage=(ImageView)findViewById(R.id.helpBackImage);
        helpBackImage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(Help.this,MainActivity.class);
                        startActivity(i);
                    }
                }
        );
        Button button15=(Button)findViewById(R.id.button15);
        button15.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String n=editText14.getText().toString();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(HelpKey, n);
                        editor.apply();
                        Toast.makeText(Help.this,"Saved",Toast.LENGTH_SHORT).show();

                    }
                }
        );

    }

}
