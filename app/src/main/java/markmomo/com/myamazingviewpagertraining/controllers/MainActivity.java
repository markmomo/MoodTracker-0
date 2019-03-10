package markmomo.com.myamazingviewpagertraining.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import markmomo.com.myamazingviewpagertraining.adapters.PageAdapter;
import markmomo.com.myamazingviewpagertraining.R;
import android.os.Handler;
import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //PREFERENCES
    private SharedPreferences mPrefs ;
    public static final String [] PREF_MOOD = new String[]{"PREF_MOOD1","PREF_MOOD2","PREF_MOOD3","PREF_MOOD4",
            "PREF_MOOD5","PREF_MOOD6","PREF_MOOD7","PREF_MOOD8"};

    public static final String [] PREF_NOTES = new String[] {"PREF_NOTES1","PREF_NOTES2","PREF_NOTES3","PREF_NOTES4",
            "PREF_NOTES5","PREF_NOTES6","PREF_NOTES7","PREF_NOTES8"};

    public static final String PREF_LAST_QUIT_MOOD_DAY = "PREF_LAST_QUIT_DAY";
    public static final String PREF_LAST_QUIT_MOOD = "PREF_LAST_QUIT_MOOD";
    public static final String PREF_LAST_QUIT_NOTE_DAY = "PREF_LAST_QUIT_NOTE";



    //HISTORY
    private ArrayList<Integer> mMoodHistory;
    private int mDayOfLastMood;
    private int mLastMood;

    private ArrayList<String> mUserNotesHistory;
    private int mDayOfLastNote;


    //LAYOUTS
    private ImageButton mNoteIcon ;
    private ImageButton mHistoryIcon;
    private ViewPager mViewPager;
    private EditText mUserBox;


    @Override
    public void onClick(View v) {
        int index;
        index = (int)v.getTag();

        if (index == 1) {
            this.displayUserBox();
        }
        if (index == 2){
            Intent intent = new Intent(MainActivity.this,HistoryActivity.class);
            intent.putIntegerArrayListExtra("moodHistory",mMoodHistory);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate!!!!!!!!!!!onCreate!!!!!!!!!!!!!onCreate!!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LAYOUTS
        mNoteIcon = findViewById(R.id.act_main_note_icon);
        mHistoryIcon = findViewById(R.id.act_main_history_icon);
        mUserBox = new EditText(this);
        mViewPager = findViewById(R.id.act_main_view_pager);
        this.configViewPager();
        //LISTENERS
        mNoteIcon.setOnClickListener(this);
        mHistoryIcon.setOnClickListener(this);
        mNoteIcon.setTag(1);
        mHistoryIcon.setTag(2);

        //INITIALIZATION PREFERENCES
        mPrefs = getPreferences(MODE_PRIVATE);
        this.getMoodsPrefs();
        this.getNotesPrefs();

        //ACTIONS
        this.updatingData();
    }

    private void configViewPager(){
        PageAdapter pageAdapter;
        pageAdapter = new PageAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.colorPagesViewPager));
        mViewPager.setAdapter(pageAdapter);

        mNoteIcon.setBackgroundColor(pageAdapter.getMainIconsColor());
        mHistoryIcon.setBackgroundColor(pageAdapter.getMainIconsColor());
    }

    private void displayUserBox(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("efface votre commentaire du jour");
        alert.setTitle("Commentaire");
        alert.setView(mUserBox);


        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                trackingNotesFromPrefs();
                mDayOfLastNote = testTime();
            }
        });
        alert.setCancelable(false);
        alert.create();
        if(mUserBox.getParent() != null) {
            ((ViewGroup)mUserBox.getParent()).removeView(mUserBox); // <- fix
        }
        alert.show();
    }

    private void updatingData(){
        final  Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                trackingMoodsFromPrefs();

                mLastMood = mViewPager.getCurrentItem();
                mDayOfLastMood = testTime();

                handler.postDelayed(this, 10000);
            }
        }, 10);
    }

    private void trackingMoodsFromPrefs(){
        Log.e("trackingMoodsFromPrefs","BEFORE BEFORE BEFORE BEFORE BEFORE");
        Log.e("trackingMoodsFromPrefs","mDayOfLastMood() " +mDayOfLastMood) ;
        Log.e("trackingMoodsFromPrefs","mMoodHistory " + mMoodHistory);
        Log.e("trackingMoodsFromPrefs","____________________________________");

        //cleaning mMoodHistory if more than 8 items
        while (mMoodHistory.size()>8){
            mMoodHistory.remove(mMoodHistory.size()-1);
        }//checking if we are today or not
        if ((testTime() -  mDayOfLastMood > 0)){
            //adding missing days information
            for (int i = 1; i < (testTime() -  mDayOfLastMood);i++){
                mMoodHistory.add(0, -1);
            }
            mMoodHistory.add(0, mViewPager.getCurrentItem());
            //replacing today entry by a new one
        }else {
            mMoodHistory.remove(0);
            mMoodHistory.add(0, mViewPager.getCurrentItem());
        }


        Log.e("trackingMoodsFromPrefs","AFTER AFTER AFTER AFTER AFTER");
        Log.e("trackingMoodsFromPrefs","mDayOfLastMood() " +mDayOfLastMood) ;
        Log.e("trackingMoodsFromPrefs","mMoodHistory " + mMoodHistory);
        Log.e("trackingMoodsFromPrefs","____________________________________");
    }
    private void trackingNotesFromPrefs() {
        Log.e("trackingNotesFromPrefs","BEFORE BEFORE BEFORE BEFORE BEFORE");
        Log.e("trackingNotesFromPrefs","mDayOfLastMood " +mDayOfLastMood) ;
        Log.e("trackingNotesFromPrefs","testTime() " +testTime()) ;
        Log.e("trackingNotesFromPrefs","mUserNotesHistory " + mUserNotesHistory);
        Log.e("trackingNotesFromPrefs","____________________________________");


        //cleaning mUserNotesHistory if more than 8 items
        while (mUserNotesHistory.size()>8){
            mUserNotesHistory.remove(mUserNotesHistory.get(mUserNotesHistory.size()-1));
        }
        //initializing an ArrayList containing 8 items
        if (mUserNotesHistory.isEmpty()){
            for (int i=0;i<=7;i++){
                mUserNotesHistory.add(i,"default");
            }
        }//managing missing days information
        if (testTime() -  mDayOfLastNote > 0){
            //adding missing days information
            for (int i = 1; i < testTime() -  mDayOfLastNote;i++){
                mUserNotesHistory.add(0,"no note");
            }
            mUserNotesHistory.add(0,mUserBox.getText().toString());
        //replacing today entry by a new one
        }else {
            mUserNotesHistory.remove(0);
            mUserNotesHistory.add(0,mUserBox.getText().toString());
        }

        Log.e("trackingNotes","AFTER AFTER AFTER AFTER AFTER");
        Log.e("trackingNotes","mDayOfLastNote " +mDayOfLastNote) ;
        Log.e("trackingNotes","testTime() " +testTime()) ;
        Log.e("trackingNotes","mUserNotesHistory " + mUserNotesHistory);
        Log.e("trackingNotes","____________________________________");
    }

    private void putNotesPrefs (){
        ArrayList<String> Day = new ArrayList<>();
        for (int i = 0;i<8;i++){
            Day.add(mUserNotesHistory.get(i));
            mPrefs.edit().putString(PREF_NOTES[i],Day.get(i)).apply();
        }
        mPrefs.edit().putInt(PREF_LAST_QUIT_NOTE_DAY,mDayOfLastNote).apply();
    }

    private void getNotesPrefs (){
        mDayOfLastNote = testTime();

        ArrayList<String> index = new ArrayList<>();
        mUserNotesHistory = new ArrayList<>();
        for (int i = 0;i<8;i++){
            index.add(mPrefs.getString(PREF_NOTES[i],"default"));
            mUserNotesHistory.add(index.get(i));
        }
        mDayOfLastNote = mPrefs.getInt(PREF_LAST_QUIT_NOTE_DAY,testTime());
    }
    private void getMoodsPrefs(){
        Log.e("initGetPrefs","BEFORE BEFORE BEFORE BEFORE BEFORE");
        Log.e("initGetPrefs","mLastMood " +mLastMood) ;
        Log.e("initGetPrefs","mDayOfLastMood() " +mDayOfLastMood) ;
        Log.e("initGetPrefs","____________________________________");
        //VARIABLES INIT
        ArrayList<Integer> index = new ArrayList<>();
        mMoodHistory = new ArrayList<>();

        //GET PREFS
        mLastMood = mPrefs.getInt(PREF_LAST_QUIT_MOOD,2);
        mDayOfLastMood = mPrefs.getInt(PREF_LAST_QUIT_MOOD_DAY,testTime());

        for (int i = 0;i<8;i++){
            index.add(mPrefs.getInt(PREF_MOOD[i],-2));
            mMoodHistory.add(index.get(i));
        }
        //recovering view
        if(mDayOfLastMood == testTime())mViewPager.setCurrentItem(mLastMood);
        else mViewPager.setCurrentItem(3);

        Log.e("initGetPrefs","AFTER AFTER AFTER AFTER AFTER");
        Log.e("initGetPrefs","mLastMood " +mLastMood) ;
        Log.e("initGetPrefs","mDayOfLastMood() " +mDayOfLastMood) ;
        Log.e("initGetPrefs","____________________________________");
    }

    private void putMoodPrefs(){
        ArrayList<Integer> Day = new ArrayList<>();
        //SAVE STATE
        mLastMood = mViewPager.getCurrentItem();
        mDayOfLastMood = testTime();
        mMoodHistory.remove(0);
        mMoodHistory.add(0,mLastMood);

        //PUT PREFS
        for (int i = 0;i<8;i++){
            Day.add(mMoodHistory.get(i));
            mPrefs.edit().putInt(PREF_MOOD[i],Day.get(i)).apply();
        }
        mPrefs.edit().putInt(PREF_LAST_QUIT_MOOD,mLastMood).apply();
        mPrefs.edit().putInt(PREF_LAST_QUIT_MOOD_DAY,mDayOfLastMood).apply();
    }

    public int testTime(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        int currentMin = Calendar.getInstance().get(Calendar.MINUTE);
        int currentSecond = Calendar.getInstance().get(Calendar.SECOND);
        return currentMin;
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
        putMoodPrefs();
        putNotesPrefs ();
        //mPrefs.edit().clear().apply();
        System.out.println("onStop!!!!!!!!!!!onStop!!!!!!!!!!!!!onStop!!");
        System.out.println("mDayOfLastMood = " + mDayOfLastMood);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy!!!!!!!!!!!onDestroy!!!!!!!!!!!!!onDestroy!!");
    }
}