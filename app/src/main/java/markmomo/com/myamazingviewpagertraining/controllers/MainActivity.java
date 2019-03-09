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
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //PREFERENCES
    private SharedPreferences mPrefs ;
    public static final String [] PREF_MOOD = new String[]{"PREF_MOOD1","PREF_MOOD2","PREF_MOOD3","PREF_MOOD4",
            "PREF_MOOD5","PREF_MOOD6","PREF_MOOD7","PREF_MOOD8"};

    public static final String PREF_LAST_QUIT_DAY = "PREF_LAST_QUIT_DAY";
    public static final String PREF_LAST_QUIT_MOOD = "PREF_LAST_QUIT_MOOD";

    //HISTORY
    private ArrayList<Integer> mDayTracking;
    private ArrayList<Integer> mMoodHistory;
    private int mCurrentMood;
    private int mLastTimeDay;
    private int mLastTimeMood;
    //LAYOUTS
    private ImageButton mNoteIcon ;
    private ImageButton mHistoryIcon;
    private ViewPager mViewPager;
    private EditText mUserBox;
    //DATES
    private int mCurrentMin;
    private int mCurrentDay;
    private int mCurrentYear;
    private int mCurrentMonth;
    private int mCurrentHour;
    private int mCurrentSecond;


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
        this.initGetPrefs();

        //ACTIONS
        this.trackingData();
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
                //mCommentText = mUserBox.getText().toString();
            }
        });
        alert.setCancelable(false);
        alert.create();
        if(mUserBox.getParent() != null) {
            ((ViewGroup)mUserBox.getParent()).removeView(mUserBox); // <- fix
        }
        alert.show();
    }

    private void trackingData(){
        final  Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                mCurrentMood = mViewPager.getCurrentItem();
                mDayTracking.add(testTime());

                trackingMoods();

                System.out.println("------------------------------------------------------------------------");
                System.out.println(mCurrentYear + "-" + mCurrentMonth + "-" + mCurrentDay + "-" + mCurrentHour + "-" + mCurrentMin);
                System.out.println("------------------------------------------------------------------------");
                whenLastTime();

                mLastTimeMood = mViewPager.getCurrentItem();
                mLastTimeDay = testTime();
                handler.postDelayed(this, 1000);
            }
        }, 10);
    }

    private void trackingMoods(){
        System.out.println("mDayTracking = " + mDayTracking);

        while (mDayTracking.size()!=1){
            int lastItem = mDayTracking.get(mDayTracking.size()-1);
            int beforeLastItem = mDayTracking.get(mDayTracking.size()-2);

            if (lastItem - beforeLastItem > 0  && !mDayTracking.isEmpty()){
                int w = lastItem - beforeLastItem;

                for (int i = 1;i<8;i++){

                    if (lastItem - beforeLastItem == i){

                        while(w>1){

                            mMoodHistory.add(0, -1);
                            mMoodHistory.remove(mMoodHistory.size() - 1);
                            w--;
                        }
                        mMoodHistory.add(0, mCurrentMood);
                        mMoodHistory.remove(mMoodHistory.size() - 1);
                    }
                }
                while (mDayTracking.size() > 1){

                    mDayTracking.remove(0);
                }

            } else if (!mDayTracking.isEmpty()) {

                mDayTracking.remove(0);
            }
        }
        System.out.println("mDayTracking = " + mDayTracking);
        System.out.println("mLastTimeDay = " + mLastTimeDay);
        System.out.println("mMoodHistory = " + mMoodHistory);
    }

    private void initGetPrefs(){
        //INIT
        ArrayList<Integer> Day = new ArrayList<>();
        mMoodHistory = new ArrayList<>();
        mDayTracking = new ArrayList<>();

        //GET PREFS
        mLastTimeMood = mPrefs.getInt(PREF_LAST_QUIT_MOOD,2);
        mLastTimeDay = mPrefs.getInt(PREF_LAST_QUIT_DAY,testTime());


        if(mLastTimeDay == testTime()){
            mViewPager.setCurrentItem(mLastTimeMood);
            for (int i = 0;i<8;i++){
                Day.add(mPrefs.getInt(PREF_MOOD[i],-2));
                mMoodHistory.add(Day.get(i));
            }
        } else if (testTime()-mLastTimeDay > 7){
            mMoodHistory.clear();
            mViewPager.setCurrentItem(3);
            for (int i = 0;i<8;i++){
                mMoodHistory.add(i,-2);
            }
        } else {
            for (int i = 0;i<8;i++){
                Day.add(mPrefs.getInt(PREF_MOOD[i],-2));
                mMoodHistory.add(Day.get(i));
            }
            mDayTracking.add(mLastTimeDay);
            mViewPager.setCurrentItem(3);
        }


        System.out.println("initialization mMoodHistory = " + mMoodHistory);
        System.out.println("init prefs mLastTimeMood = " + mLastTimeMood);
        System.out.println("init prefs mLastTimeDay = " + mLastTimeDay);
        System.out.println("initialization testTime = " + testTime());
        System.out.println("initialization mDayTracking = " + mDayTracking);
    }

    private void putPrefs(){
        ArrayList<Integer> Day = new ArrayList<>();
        //SAVE STATE
        mLastTimeMood = mViewPager.getCurrentItem();
        mLastTimeDay = testTime();
        mMoodHistory.remove(0);
        mMoodHistory.add(0,mLastTimeMood);

        //PUT PREFS
        for (int i = 0;i<8;i++){
            Day.add(mMoodHistory.get(i));
            mPrefs.edit().putInt(PREF_MOOD[i],Day.get(i)).apply();
        }
        mPrefs.edit().putInt(PREF_LAST_QUIT_MOOD,mLastTimeMood).apply();
        mPrefs.edit().putInt(PREF_LAST_QUIT_DAY,mLastTimeDay).apply();
    }

    public int testTime(){
        mCurrentYear = Calendar.getInstance().get(Calendar.YEAR);
        mCurrentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
        mCurrentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        mCurrentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        mCurrentMin = Calendar.getInstance().get(Calendar.MINUTE);
        mCurrentSecond = Calendar.getInstance().get(Calendar.SECOND);
        return mCurrentMin;
    }

    private void whenLastTime() {
        int when = mCurrentMin - mLastTimeDay;

        if (when > 0)System.out.println("Last connexion " + when + " days ago");
        else System.out.println("Last connexion today!!!");
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
        putPrefs();
        //mPrefs.edit().clear().apply();
        System.out.println("onStop!!!!!!!!!!!onStop!!!!!!!!!!!!!onStop!!");
        System.out.println("mLastTimeDay = " + mLastTimeDay);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy!!!!!!!!!!!onDestroy!!!!!!!!!!!!!onDestroy!!");
    }
}