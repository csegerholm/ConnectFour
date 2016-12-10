package cms549.connectfour;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ChooseChipScreen extends AppCompatActivity {

    Spinner sp1,sp2;
    ArrayList<Chip> listOfChipColors;
    ArrayList<Chip> fullList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_chip_screen);
        sp1 = (Spinner) findViewById(R.id.spchip1);
        sp2 = (Spinner) findViewById(R.id.spchip2);
        createFullList();

        listOfChipColors = new ArrayList<Chip>();
        listOfChipColors.add(new Chip("Black", R.drawable.black_circle));
        listOfChipColors.add(new Chip("Red", R.drawable.red_circle));
        getExtraChips();


        ChipImageSpinnerAdapter chips  = new ChipImageSpinnerAdapter(this, R.layout.row_with_image, listOfChipColors);
        sp1.setAdapter(chips);
        sp2.setAdapter(chips);

    }

    private void createFullList() {
        fullList = new ArrayList<Chip>();
        fullList.add(new Chip("Blue",R.drawable.blue_circle ));
        fullList.add(new Chip("Cyan", R.drawable.cyan_circle ));
        fullList.add(new Chip("Orange", R.drawable.orange_circle ));
        fullList.add(new Chip("Gray", R.drawable.gray_circle ));
        fullList.add(new Chip("Green",R.drawable.green_circle ));
        fullList.add(new Chip("Pink", R.drawable.pink_circle));
        fullList.add(new Chip("Dark Green", R.drawable.darkgreen_circle ));
        fullList.add(new Chip("Purple", R.drawable.magenta_circle ));

    }

    private void getExtraChips() {
        // Restore preferences
        SharedPreferences myPref = getSharedPreferences("UserInfo", 0);
        String achString= myPref.getString("achievements", "");

        if(achString==""){
            return;
        }


        for(int i=0; i<achString.length(); i++){
            char num = achString.charAt(i);
            int n = num - '0';
            listOfChipColors.add(fullList.get(n));
        }

    }


    public void clickPlay(View view) {
        int i =sp1.getSelectedItemPosition();
        int j =sp2.getSelectedItemPosition();
        //Check that the players chips are not matching
         if(i==j){
             Toast.makeText(this, "Please select different chips.", Toast.LENGTH_SHORT).show();
             return;
         }

        Intent nextScreen = new Intent(getApplicationContext(), GameScreen.class);

        nextScreen.putExtra("chip1", listOfChipColors.get(i).id);
        nextScreen.putExtra("chip2", listOfChipColors.get(j).id);
        nextScreen.putExtra("aiDif",-1);

        //start next screen
        startActivityForResult(nextScreen,1);
    }

    public void backToMain(View view) {
        finish();
    }

    //Result listener
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode == Activity.RESULT_OK && data!=null) {
            //update the stats in preferences
            //Contact c = (Contact) data.getSerializableExtra("c");

        }
        finish();
    }

}
