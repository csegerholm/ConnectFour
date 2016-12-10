package cms549.connectfour;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class AccountScreen extends AppCompatActivity {

    private TextView tvname;
    private TextView games;
    private TextView wins;
    private TextView score;
    private TextView streak;
    private TextView wlratio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_screen);
        tvname = (TextView) findViewById(R.id.tvname);
        games = (TextView) findViewById(R.id.gamestv);
        wins= (TextView) findViewById(R.id.winstv);
        score= (TextView) findViewById(R.id.scoretv);
        streak = (TextView) findViewById(R.id.streaktv);
        wlratio = (TextView) findViewById(R.id.wltv);

        loadFromPreference();
    }

    private void loadFromPreference() {
        // Restore preferences
        SharedPreferences myPref = getSharedPreferences("UserInfo", 0);
        String un= myPref.getString("username", null);
        int g = myPref.getInt("games",0);
        int w = myPref.getInt("wins",0);
        int s = myPref.getInt("score",0);
        int l= myPref.getInt("losses",0);
        int t= myPref.getInt("ties",0);
        int streak1 = myPref.getInt("streak",0);

        tvname.setText(un);
        games.setText(""+g);
        wins.setText(""+w+"/"+t+"/"+l);
        score.setText(""+s);
        streak.setText(""+streak1);
        if(g==0){
            wlratio.setText("-");
            return;
        }
        double wl = (w+0.0)/g;
        int full = (int) wl;
        int decimal = (int) ((wl-full)*1000);
        wlratio.setText(""+full+"."+decimal);


    }

    public void goBack(View view) {
        finish();
    }

    public void goToAchievements(View view) {
        //Jump to create account screen
        Intent nextScreen = new Intent(getApplicationContext(), AchievementsScreen.class);
        //start next screen
        startActivity(nextScreen);

    }
}
