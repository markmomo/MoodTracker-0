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

    //PREFERENCES
    private SharedPreferences mPreferences ;
    public static final String PREF_MOOD_ARRAY1 = "PREF_MOOD_ARRAY1";
    public static final String PREF_MOOD_ARRAY2 = "PREF_MOOD_ARRAY2";
    public static final String PREF_MOOD_ARRAY3 = "PREF_MOOD_ARRAY3";
    public static final String PREF_MOOD_ARRAY4 = "PREF_MOOD_ARRAY4";
    public static final String PREF_MOOD_ARRAY5 = "PREF_MOOD_ARRAY5";
    public static final String PREF_MOOD_ARRAY6 = "PREF_MOOD_ARRAY6";
    public static final String PREF_MOOD_ARRAY7 = "PREF_MOOD_ARRAY7";
    public static final String PREF_MOOD_ARRAY8 = "PREF_MOOD_ARRAY8";
    public static final String LAST_TIME_DAY = "LAST_TIME_DAY";

    //HISTORY
    private ArrayList<Integer> mDayTrackingArray;
    private ArrayList<Integer> mMoodHistoryArray;
    private int mCurrentMoodPosition;
    private String mCommentText;


    //LAYOUTS
    private ImageButton mNoteButtonIcon ;
    private ImageButton mHistoryButtonIcon;
    private ViewPager mViewPager;
    private EditText mEditTextBox;

    //DATES
    private int mLastTimeDay;
    private int mCurrentYear;
    private int mCurrentMonth;
    private int mCurrentDay;
    private int mCurrentHour;
    private int mCurrentMinute;


    @Override
    public void onClick(View v) {
        int buttonIndex;
        buttonIndex = (int)v.getTag();

        if (buttonIndex == 1) {
            this.displayNoteBox();
        }
        if (buttonIndex == 2){
            Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
            intent.putIntegerArrayListExtra("moodHistoryArray",mMoodHistoryArray);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        //LOADING PREFERENCES
        mPreferences = getPreferences(MODE_PRIVATE);
        initializeHistory();

        mCommentText = "";


        //ACTIONS
        this.configureViewPager();
        this.trackingHistory();

    }

    private void configureViewPager(){

        PageAdapter pageAdapter;
        pageAdapter = new PageAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.colorPagesViewPager));
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setCurrentItem(3);

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

    private void trackingHistory(){

        final Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                mCurrentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                mCurrentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
                mCurrentYear = Calendar.getInstance().get(Calendar.YEAR);
                mCurrentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                mCurrentMinute = Calendar.getInstance().get(Calendar.MINUTE);
                mCurrentMoodPosition = mViewPager.getCurrentItem();
                mDayTrackingArray.add(mCurrentMinute);

                System.out.println("------------------------------------------------------------------------");
                System.out.println((new SimpleDateFormat("HH:mm:ss", Locale.FRANCE).format(new Date())));
                System.out.println(getCurrentDate());
                whenLastTime();
                System.out.println("------------------------------------------------------------------------");

                updateMoodHistory();

                handler.postDelayed(this, 20000);
            }
        }, 10);
    }

    private void updateMoodHistory(){

        while (mDayTrackingArray.size()!=1){

            if (mDayTrackingArray.get(mDayTrackingArray.size()-1) - mDayTrackingArray.get(mDayTrackingArray.size()-2) > 0  && !mDayTrackingArray.isEmpty()){

                switch (mDayTrackingArray.get(mDayTrackingArray.size()-1) - mDayTrackingArray.get(mDayTrackingArray.size()-2)){
                    case 1 :
                        mMoodHistoryArray.add(0,mCurrentMoodPosition);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        break;
                    case 2 :
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,mCurrentMoodPosition);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        break;
                    case 3 :
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,mCurrentMoodPosition);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        break;
                    case 4 :
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,mCurrentMoodPosition);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        break;
                    case 5 :
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,mCurrentMoodPosition);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        break;
                    case 6 :
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,mCurrentMoodPosition);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        break;
                    case 7 :
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,-1);
                        mMoodHistoryArray.add(0,mCurrentMoodPosition);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
                        break;
                    }

                while (mDayTrackingArray.size() > 1){
                    // supprime le premier éléments de mDayTrackingArray
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

    private void saveHistory(){
        int Day1,Day2,Day3,Day4,Day5,Day6,Day7;
        int Day8;
        Day1 = mMoodHistoryArray.get(0);
        Day2 = mMoodHistoryArray.get(1);
        Day3 = mMoodHistoryArray.get(2);
        Day4 = mMoodHistoryArray.get(3);
        Day5 = mMoodHistoryArray.get(4);
        Day6 = mMoodHistoryArray.get(5);
        Day7 = mMoodHistoryArray.get(6);
        Day8 = mMoodHistoryArray.get(7);
        mLastTimeDay = Calendar.getInstance().get(Calendar.MINUTE);

        mPreferences.edit().putInt(PREF_MOOD_ARRAY1,Day1).apply();
        mPreferences.edit().putInt(PREF_MOOD_ARRAY2,Day2).apply();
        mPreferences.edit().putInt(PREF_MOOD_ARRAY3,Day3).apply();
        mPreferences.edit().putInt(PREF_MOOD_ARRAY4,Day4).apply();
        mPreferences.edit().putInt(PREF_MOOD_ARRAY5,Day5).apply();
        mPreferences.edit().putInt(PREF_MOOD_ARRAY6,Day6).apply();
        mPreferences.edit().putInt(PREF_MOOD_ARRAY7,Day7).apply();
        mPreferences.edit().putInt(PREF_MOOD_ARRAY8,Day8).apply();

        mPreferences.edit().putInt(LAST_TIME_DAY,mLastTimeDay).apply();
    }

    private void initializeHistory(){
        mLastTimeDay = mPreferences.getInt(LAST_TIME_DAY,Calendar.getInstance().get(Calendar.MINUTE));
        mMoodHistoryArray = new ArrayList<>();
        mDayTrackingArray = new ArrayList<>();
        int Day1,Day2,Day3,Day4,Day5,Day6,Day7;
        int Day8;


        Day1 = mPreferences.getInt(PREF_MOOD_ARRAY1, -1);
        Day2 = mPreferences.getInt(PREF_MOOD_ARRAY2, -1);
        Day3 = mPreferences.getInt(PREF_MOOD_ARRAY3, -1);
        Day4 = mPreferences.getInt(PREF_MOOD_ARRAY4, -1);
        Day5 = mPreferences.getInt(PREF_MOOD_ARRAY5, -1);
        Day6 = mPreferences.getInt(PREF_MOOD_ARRAY6, -1);
        Day7 = mPreferences.getInt(PREF_MOOD_ARRAY7, -1);
        Day8 = mPreferences.getInt(PREF_MOOD_ARRAY8, -1);

        mDayTrackingArray.add(mLastTimeDay);

        mMoodHistoryArray.add(Day1);mMoodHistoryArray.add(Day2);mMoodHistoryArray.add(Day3);mMoodHistoryArray.add(Day4);
        mMoodHistoryArray.add(Day5);mMoodHistoryArray.add(Day6);mMoodHistoryArray.add(Day7);mMoodHistoryArray.add(Day8);
        System.out.println("initialisation de mMoodHistoryArray" + mMoodHistoryArray);
        System.out.println("initialisation de mLastTimeDay" + mLastTimeDay);
        System.out.println("initialisation de mDayTrackingArray" + mDayTrackingArray);
    }

    private void whenLastTime() {
        int when = mCurrentMinute - mLastTimeDay;
        if (when > 0){
            System.out.println("Je ne me suis pas connecté depuis " + when + " jours");
            when = 0;
        } else{
            System.out.println("Je ne me suis déja connecté aujourd'hui");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart!!!!!!!!!!!onStart!!!!!!!!!!!!!onStart!!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause!!!!!!!!!!!onPause!!!!!!!!!!!!!onPause!!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume!!!!!!!!!!!onResume!!!!!!!!!!!!!onResume!!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveHistory();
        System.out.println("onStop!!!!!!!!!!!onStop!!!!!!!!!!!!!onStop!!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy!!!!!!!!!!!onDestroy!!!!!!!!!!!!!onDestroy!!");
    }
}