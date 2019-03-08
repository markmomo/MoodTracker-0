package markmomo.com.myamazingviewpagertraining.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import markmomo.com.myamazingviewpagertraining.adapters.PageAdapter;
import markmomo.com.myamazingviewpagertraining.R;
import android.os.Handler;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences mPreferences ;

    private ArrayList<Integer> mDayTrackingArray;
    private ArrayList<Integer> mMoodHistoryArray;

    public static final String PREF_KEY_START_DAY = "PREF_KEY_START_DAY";

    //LAYOUTS
    private ImageButton mNoteButtonIcon ;
    private ImageButton mHistoryButtonIcon;
    private ViewPager mViewPager;
    private EditText mEditTextBox;

    //DATES
    private int mCurrentDaySaved;
    private int mCurrentYear;
    private int mCurrentMonth;
    private int mCurrentDay;
    private int mCurrentHour;
    private int mCurrentMinute;
    private int mCurrentSecond;

    //COMMENT

    private int mCurrentMoodPosition;
    private String mCommentText;
    public static final String PREF_KEY_TODAY_COMMENT = "PREF_KEY_TODAY_COMMENT";
    public static final String PREF_KEY_ONE_BEFORE_COMMENT = "PREF_KEY_ONE_BEFORE_COMMENT";
    public static final String PREF_KEY_TWO_BEFORE_COMMENT = "PREF_KEY_TWO_BEFORE_COMMENT";
    public static final String PREF_KEY_THREE_BEFORE_COMMENT = "PREF_KEY_THREE_BEFORE_COMMENT";
    public static final String PREF_KEY_FOUR_BEFORE_COMMENT = "PREF_KEY_FOUR_BEFORE_COMMENT";
    public static final String PREF_KEY_FIVE_BEFORE_COMMENT = "PREF_KEY_FIVE_BEFORE_COMMENT";
    public static final String PREF_KEY_SIX_BEFORE_COMMENT = "PREF_KEY_SIX_BEFORE_COMMENT";
    public static final String PREF_KEY_SEVEN_BEFORE_COMMENT = "PREF_KEY_SEVEN_BEFORE_COMMENT";

    //MOOD
    public static final String PREF_KEY_TODAY_COMMENT_MOOD = "PREF_KEY_TODAY_COMMENT_MOOD";
    public static final String PREF_KEY_ONE_BEFORE_COMMENT_MOOD = "PREF_KEY_ONE_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_TWO_BEFORE_COMMENT_MOOD = "PREF_KEY_TWO_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_THREE_BEFORE_COMMENT_MOOD = "PREF_KEY_THREE_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_FOUR_BEFORE_COMMENT_MOOD = "PREF_KEY_FOUR_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_FIVE_BEFORE_COMMENT_MOOD = "PREF_KEY_FIVE_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_SIX_BEFORE_COMMENT_MOOD = "PREF_KEY_SIX_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_SEVEN_BEFORE_COMMENT_MOOD = "PREF_KEY_SEVEN_BEFORE_COMMENT_MOOD";

    //DAYS
    public static final String PREF_KEY_TODAY_DAY = "PREF_KEY_TODAY_DAY";
    public static final String PREF_KEY_ONE_BEFORE_DAY = "PREF_KEY_ONE_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_TWO_BEFORE_DAY = "PREF_KEY_TWO_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_THREE_BEFORE_DAY = "PREF_KEY_THREE_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_FOUR_BEFORE_DAY = "PREF_KEY_FOUR_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_FIVE_BEFORE_DAY = "PREF_KEY_FIVE_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_SIX_BEFORE_DAY = "PREF_KEY_SIX_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_SEVEN_BEFORE_DAY = "PREF_KEY_SEVEN_BEFORE_COMMENT_MOOD";

    @Override
    public void onClick(View v) {
        int buttonIndex;
        buttonIndex = (int)v.getTag();

        if (buttonIndex == 1) {
            this.displayNoteBox();
        }
        if (buttonIndex == 2){
            Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
            intent.putExtra(PREF_KEY_ONE_BEFORE_COMMENT_MOOD,mPreferences.getInt(PREF_KEY_ONE_BEFORE_COMMENT_MOOD, -1));
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDayTrackingArray = new ArrayList<>();
        mMoodHistoryArray = new ArrayList<>();



        mPreferences = getPreferences(MODE_PRIVATE);
        mCurrentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        mPreferences.edit().putInt(PREF_KEY_START_DAY, mCurrentMinute).apply();



        mCommentText = "";

        //LAYOUTS
        mNoteButtonIcon = findViewById(R.id.activity_main_note_icon_button);
        mHistoryButtonIcon = findViewById(R.id.activity_main_history_icon_button);
        mEditTextBox = new EditText(this);
        mViewPager = findViewById(R.id.activity_main_view_pager);

        //LISTENERS
        mNoteButtonIcon.setOnClickListener(this);
        mHistoryButtonIcon.setOnClickListener(this);
        mNoteButtonIcon.setTag(1);
        mHistoryButtonIcon.setTag(2);



        //ACTIONS

        this.configureViewPager();

    }

    private void configureViewPager(){

        PageAdapter pageAdapter;
        pageAdapter = new PageAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.colorPagesViewPager));
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setCurrentItem(2);

        mNoteButtonIcon.setBackgroundColor(pageAdapter.getMainIconsColor());
        mHistoryButtonIcon.setBackgroundColor(pageAdapter.getMainIconsColor());
    }

    private void displayNoteBox(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("efface votre commentaire du jour");
        alert.setTitle("Commentaire");
        alert.setView(mEditTextBox);

        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mCommentText = mEditTextBox.getText().toString();

            }
        });
        alert.setCancelable(false);
        alert.create();
        if(mEditTextBox.getParent() != null) {
            ((ViewGroup)mEditTextBox.getParent()).removeView(mEditTextBox); // <- fix
        }
        alert.show();
    }

    private String getCurrentDate(){

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH)+1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);

        String yearString = String.valueOf(year);
        String monthString = String.valueOf(month);
        String dayString = String.valueOf(day);
        String hourString = String.valueOf(hour);
        String minuteString = String.valueOf(minute);

        return yearString + "-" + monthString + "-" + dayString + "-" + hourString + "-" + minuteString;
    }

    private void printTodayPreferences(){
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_TODAY_DAY : " + mPreferences.getInt(PREF_KEY_TODAY_DAY, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_TODAY_COMMENT : " + mPreferences.getString(PREF_KEY_TODAY_COMMENT, null));
        System.out.println("PREF_KEY_TODAY_COMMENT_MOOD : " + mPreferences.getInt(PREF_KEY_TODAY_COMMENT_MOOD, -1));
    }


    private void printPreferences (){
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_TODAY_DAY : " + mPreferences.getInt(PREF_KEY_TODAY_DAY, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_TODAY_COMMENT : " + mPreferences.getString(PREF_KEY_TODAY_COMMENT, null));
        System.out.println("PREF_KEY_TODAY_COMMENT_MOOD : " + mPreferences.getInt(PREF_KEY_TODAY_COMMENT_MOOD, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_ONE_BEFORE_DAY : " + mPreferences.getInt(PREF_KEY_ONE_BEFORE_DAY, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_ONE_BEFORE_COMMENT : " + mPreferences.getString(PREF_KEY_ONE_BEFORE_COMMENT, null));
        System.out.println("PREF_KEY_ONE_BEFORE_COMMENT_MOOD : " + mPreferences.getInt(PREF_KEY_ONE_BEFORE_COMMENT_MOOD, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_TWO_BEFORE_DAY : " + mPreferences.getInt(PREF_KEY_TWO_BEFORE_DAY, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_TWO_BEFORE_COMMENT : " + mPreferences.getString(PREF_KEY_TWO_BEFORE_COMMENT, null));
        System.out.println("PREF_KEY_TWO_BEFORE_COMMENT_MOOD : " + mPreferences.getInt(PREF_KEY_TWO_BEFORE_COMMENT_MOOD, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_THREE_BEFORE_DAY : " + mPreferences.getInt(PREF_KEY_THREE_BEFORE_DAY, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_THREE_BEFORE_COMMENT : " + mPreferences.getString(PREF_KEY_THREE_BEFORE_COMMENT, null));
        System.out.println("PREF_KEY_THREE_BEFORE_COMMENT_MOOD : " + mPreferences.getInt(PREF_KEY_THREE_BEFORE_COMMENT_MOOD, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_FOUR_BEFORE_DAY : " + mPreferences.getInt(PREF_KEY_FOUR_BEFORE_DAY, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_FOUR_BEFORE_COMMENT : " + mPreferences.getString(PREF_KEY_FOUR_BEFORE_COMMENT, null));
        System.out.println("PREF_KEY_FOUR_BEFORE_COMMENT_MOOD : " + mPreferences.getInt(PREF_KEY_FOUR_BEFORE_COMMENT_MOOD, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_FIVE_BEFORE_DAY : " + mPreferences.getInt(PREF_KEY_FIVE_BEFORE_DAY, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_FIVE_BEFORE_COMMENT : " + mPreferences.getString(PREF_KEY_FIVE_BEFORE_COMMENT, null));
        System.out.println("PREF_KEY_FIVE_BEFORE_COMMENT_MOOD : " + mPreferences.getInt(PREF_KEY_FIVE_BEFORE_COMMENT_MOOD, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_SIX_BEFORE_DAY : " + mPreferences.getInt(PREF_KEY_SIX_BEFORE_DAY, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_SIX_BEFORE_COMMENT : " + mPreferences.getString(PREF_KEY_SIX_BEFORE_COMMENT, null));
        System.out.println("PREF_KEY_SIX_BEFORE_COMMENT_MOOD : " + mPreferences.getInt(PREF_KEY_SIX_BEFORE_COMMENT_MOOD, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_SEVEN_BEFORE_DAY : " + mPreferences.getInt(PREF_KEY_SEVEN_BEFORE_DAY, -1));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("PREF_KEY_SEVEN_BEFORE_COMMENT : " + mPreferences.getString(PREF_KEY_SEVEN_BEFORE_COMMENT, null));
        System.out.println("PREF_KEY_SEVEN_BEFORE_COMMENT_MOOD : " + mPreferences.getInt(PREF_KEY_SEVEN_BEFORE_COMMENT_MOOD, -1));
    }

    private void putStatsInPrefKey (){

        mPreferences.edit().putInt(PREF_KEY_TODAY_DAY, mCurrentMinute).apply();
        mPreferences.edit().putString(PREF_KEY_TODAY_COMMENT, mCommentText).apply();
        mPreferences.edit().putInt(PREF_KEY_TODAY_COMMENT_MOOD, mCurrentMoodPosition).apply();


    }

    private void updateHistoric(){


        final Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                mCurrentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                mCurrentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
                mCurrentYear = Calendar.getInstance().get(Calendar.YEAR);
                mCurrentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                mCurrentMinute = Calendar.getInstance().get(Calendar.MINUTE);
                mCurrentSecond = Calendar.getInstance().get(Calendar.SECOND);
                mCurrentMoodPosition = mViewPager.getCurrentItem();

                mDayTrackingArray.add(mCurrentSecond);

                System.out.println("------------------------------------------------------------------------");
                System.out.println((new SimpleDateFormat("HH:mm:ss", Locale.FRANCE).format(new Date())));
                System.out.println(mCurrentYear + "-" + mCurrentMonth + "-" + mCurrentDay + "-" + mCurrentHour + "-" + mCurrentMinute);
                System.out.println("je ne me suis pas connecté depuis 1 ou plusieurs jours "+ myIsYesterday());
                System.out.println("------------------------------------------------------------------------");


                updateMoodHistoryArrayList();


                handler.postDelayed(this, 1000);
            }
        }, 10);
    }

    private void updateMoodHistoryArrayList(){
        while (mDayTrackingArray.size()!=1){

            if (mDayTrackingArray.get(mDayTrackingArray.size()-1) - mDayTrackingArray.get(mDayTrackingArray.size()-2) > 0  && !mDayTrackingArray.isEmpty()){

                if (mMoodHistoryArray.size() < 7){
                    // ajoute une entrée à l'historique à la suite
                    switch (mDayTrackingArray.get(mDayTrackingArray.size()-1) - mDayTrackingArray.get(mDayTrackingArray.size()-2)){
                        case 1 :
                            mMoodHistoryArray.add(0,mCurrentMoodPosition);
                            break;
                        case 2 :
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,mCurrentMoodPosition);
                            break;
                        case 3 :
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,mCurrentMoodPosition);
                            break;
                        case 4 :
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,mCurrentMoodPosition);
                            break;
                        case 5 :
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,mCurrentMoodPosition);
                            break;
                        case 6 :
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,mCurrentMoodPosition);
                            break;
                        case 7 :
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,mCurrentMoodPosition);
                            break;
                    }
                } else {
                    // retire le dernier élément et ajoute une entrée au début
                    switch (mDayTrackingArray.get(mDayTrackingArray.size()-1) - mDayTrackingArray.get(mDayTrackingArray.size()-2)){
                        case 1 :
                            mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                            mMoodHistoryArray.add(0,mCurrentMoodPosition);
                            break;
                        case 2 :
                            mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,mCurrentMoodPosition);
                            break;
                        case 3 :
                            mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,mCurrentMoodPosition);
                            break;
                        case 4 :
                            mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,mCurrentMoodPosition);
                            break;
                        case 5 :
                            mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,mCurrentMoodPosition);
                            break;
                        case 6 :
                            mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,mCurrentMoodPosition);
                            break;
                        case 7 :
                            mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,-1);
                            mMoodHistoryArray.add(0,mCurrentMoodPosition);
                            break;
                    }
                }

                while (mDayTrackingArray.size() > 1){
                    // suprime le premier éléments de mDayTrackingArray
                    mDayTrackingArray.remove(0);
                    System.out.println("La liste des jours a été nettoyée car on est passé au jour suivant");
                }

            } else if (!mDayTrackingArray.isEmpty()) {
                // supprime le dernier éléments de mDayTrackingArray
                mDayTrackingArray.remove(mDayTrackingArray.size()-1);
                System.out.println("le dernier éléméent a été retiré de la liste des jours car identique au précédent");
            }

        }
        System.out.println("voici le tracking des jours" + mDayTrackingArray);
        System.out.println("voici l'historique des humeur" + mMoodHistoryArray);
    }





    private boolean myIsYesterday(){
        return mCurrentMinute > mPreferences.getInt(PREF_KEY_TODAY_DAY, 0);
    }

    @Override
    protected void onStart() {
        mCurrentDaySaved = mPreferences.getInt(PREF_KEY_TODAY_DAY, 0);
        super.onStart();
        updateHistoric();
        System.out.println("onStart!!!!!!!!!!!onStart!!!!!!!!!!!!!onStart!!");

    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause!!!!!!!!!!!onPause!!!!!!!!!!!!!onPause!!");

        //myPrintPreferences ();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume!!!!!!!!!!!onResume!!!!!!!!!!!!!onResume!!");
        //myPrintPreferences ();
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop!!!!!!!!!!!onStop!!!!!!!!!!!!!onStop!!");
        //myPrintPreferences ();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy!!!!!!!!!!!onDestroy!!!!!!!!!!!!!onDestroy!!");

        //myPrintPreferences ();
    }
}