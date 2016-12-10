package cms549.connectfour;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import java.util.HashSet;


public class CreateAccountScreen extends AppCompatActivity {

    EditText etname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_screen);
        etname = (EditText) findViewById(R.id.etname);

    }

    private boolean saveToPref(){
        String un = etname.getText().toString().trim();
        //validate username

        SharedPreferences myPref = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = myPref.edit();
        editor.putString("username", un);
        editor.putInt("games",0);
        editor.putInt("wins",0);
        editor.putInt("losses",0);
        editor.putInt("ties",0);
        editor.putInt("onlineWins",0);
        editor.putInt("streak",0);
        editor.putInt("score",0);
        editor.putString("achievements","01234567");
        /*
        HashSet<String> tokens = new HashSet<String>(2);
        tokens.add(""+R.drawable.red_circle);
        tokens.add(""+R.drawable.black_circle);
        editor.putStringSet("tokens", tokens);
        */
        editor.commit();
        return true;
    }

    public void saveClicked(View view) {
        if(saveToPref()){
            finish();
        }

    }
}
