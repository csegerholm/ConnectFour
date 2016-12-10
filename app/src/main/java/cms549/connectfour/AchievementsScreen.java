package cms549.connectfour;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;


public class AchievementsScreen extends AppCompatActivity {

    ListView lv;
    ArrayList<Achievement> list;
    ArrayList<Achievement> fullList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_screen);
        lv = (ListView) findViewById(R.id.achievementList);
        createFullList();
        list = new ArrayList<Achievement>();
        loadFromPreference();
        AchievementAdapter items  = new AchievementAdapter(this, R.layout.row_achievement, list);
        lv.setAdapter(items);
    }

    private void createFullList() {
        fullList = new ArrayList<Achievement>();
        fullList.add(new Achievement("Venturing Out", "Play an Online Game.",0,R.drawable.blue_circle ));
        fullList.add(new Achievement("Breaking Friendships", "Win a game against your friend.",1,R.drawable.cyan_circle ));
        fullList.add(new Achievement("Got Them Connections", "Win 50 online games.",2,R.drawable.orange_circle ));
        fullList.add(new Achievement("Smarter Than You Look", "Win a game against the AI.",3,R.drawable.gray_circle ));
        fullList.add(new Achievement("Not a Noob Anymore", "Obtain a player score of 50 points.",4,R.drawable.green_circle ));
        fullList.add(new Achievement("Get a life", "Obtain a player score of 1000 points.",5,R.drawable.pink_circle));
        fullList.add(new Achievement("Rough Day", "Lose 10 times in a row.",6,R.drawable.darkgreen_circle ));
        fullList.add(new Achievement("Streaking", "Win 10 times in a row.",6,R.drawable.magenta_circle ));

    }

    private void loadFromPreference() {
        // Restore preferences
        SharedPreferences myPref = getSharedPreferences("UserInfo", 0);
        String achString= myPref.getString("achievements", null);

        if(achString==""){
            list.add(new Achievement("Looks like you don't have any :( ", "Play games to unlock new coins!",0, R.drawable.black_circle));
            return;
        }


        for(int i=0; i<achString.length(); i++){
            char num = achString.charAt(i);
            int n = num - '0';
            list.add(fullList.get(n));
        }
    }


    public void goBackToAccount(View view) {
        finish();
    }

}
