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
    private SharedPreferences mPreferences ;
    public static final String [] PREF_MOOD_ARRAY = new String[]{"PREF_MOOD_ARRAY1","PREF_MOOD_ARRAY2","PREF_MOOD_ARRAY3","PREF_MOOD_ARRAY4",
            "PREF_MOOD_ARRAY5","PREF_MOOD_ARRAY6","PREF_MOOD_ARRAY7","PREF_MOOD_ARRAY8"};

    public static final String PREF_LAST_TIME_DAY = "LAST_TIME_DAY";
    public static final String PREF_LAST_TIME_MOOD_POSITION = "PREF_LAST_TIME_MOOD_POSITION";

    //HISTORY
    private ArrayList<Integer> mDayTrackingArray;
    private ArrayList<Integer> mMoodHistoryArray;
    private int mCurrentMoodPosition;
    private int mLastTimeDay;
    private int mLastTimePosition;
    //LAYOUTS
    private ImageButton mNoteButtonIcon ;
    private ImageButton mHistoryButtonIcon;
    private ViewPager mViewPager;
    private EditText mEditTextBox;

    //DATES
    private int mCurrentMinute;
    private int mCurrentDay;
    private int mCurrentYear = year();
    private int mCurrentMonth = month();
    private int mCurrentHour = hour();


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
        System.out.println("onCreate!!!!!!!!!!!onCreate!!!!!!!!!!!!!onCreate!!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LAYOUTS
        mNoteButtonIcon = findViewById(R.id.activity_main_note_icon_button);
        mHistoryButtonIcon = findViewById(R.id.activity_main_history_icon_button);
        mEditTextBox = new EditText(this);
        mViewPager = findViewById(R.id.activity_main_view_pager);
        this.configureViewPager();

        //LISTENERS
        mNoteButtonIcon.setOnClickListener(this);
        mHistoryButtonIcon.setOnClickListener(this);
        mNoteButtonIcon.setTag(1);
        mHistoryButtonIcon.setTag(2);

        //LOADING PREFERENCES
        mPreferences = getPreferences(MODE_PRIVATE);
        initializeHistory();

        //ACTIONS

        this.trackingHistory();

    }

    private void configureViewPager(){

        PageAdapter pageAdapter;
        pageAdapter = new PageAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.colorPagesViewPager));
        mViewPager.setAdapter(pageAdapter);

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
                //mCommentText = mEditTextBox.getText().toString();
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

        return "Year : " + String.valueOf(Calendar.getInstance().get(Calendar.YEAR)) +" Month : "
                + String.valueOf(Calendar.getInstance().get(Calendar.MONTH+1)) + " Day : "
                + String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) + " Hour : "
                + String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) + " Minute : "
                +  String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
    }

    private void trackingHistory(){

        final Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {


            @Override
            public void run() {
                mCurrentYear = year();
                mCurrentMonth = month();
                mCurrentHour = hour();
                mCurrentDay = day();
                mCurrentMinute = minute();
                mCurrentMoodPosition = mViewPager.getCurrentItem();
                mDayTrackingArray.add(mCurrentMinute);

                updateMoodHistory();

                System.out.println("------------------------------------------------------------------------");
                System.out.println(getCurrentDate());
                System.out.println(mCurrentYear + "-" + mCurrentMonth + "-" + mCurrentDay + "-" + mCurrentHour + "-" + mCurrentMinute);
                System.out.println(mCurrentDay+""+mCurrentMinute);
                whenLastTime();
                System.out.println("------------------------------------------------------------------------");

                handler.postDelayed(this, 20000);
            }
        }, 10);
    }

    private void updateMoodHistory(){
        System.out.println("mDayTrackingArray = " + mDayTrackingArray);
        int lastItem = mDayTrackingArray.get(mDayTrackingArray.size()-1);
        int beforeLastItem = mDayTrackingArray.get(mDayTrackingArray.size()-2);

        while (mDayTrackingArray.size()!=1){

            if (lastItem - beforeLastItem > 0  && !mDayTrackingArray.isEmpty()){
                int w = lastItem - beforeLastItem;
                for(int i = 1;i<8;i++){

                    System.out.println("for");
                    if (lastItem - beforeLastItem == i){

                        while(w>1){
                            System.out.println("while");
                            mMoodHistoryArray.add(0, -1);
                            mMoodHistoryArray.remove(mMoodHistoryArray.size() - 1);
                            w--;
                        }
                        mMoodHistoryArray.add(0, mCurrentMoodPosition);
                        mMoodHistoryArray.remove(mMoodHistoryArray.size() - 1);
                    } else{
                        System.out.println("même jour");
                    }
                }
                System.out.println("mMoodHistory après "+ mMoodHistoryArray);
//                switch (lastItem - beforeLastItem){
//                    case 1 :
//                        mMoodHistoryArray.add(0,mCurrentMoodPosition);
//                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
//                        break;
//                    case 2 :
//                        mMoodHistoryArray.add(0,-1);
//                        mMoodHistoryArray.add(0,mCurrentMoodPosition);
//                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
//                        mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
//                        break;
//                    case 3 :
//                        for(int i = 0; i < 2; i ++){
//                            mMoodHistoryArray.add(0,-1);
//                        }
//                        mMoodHistoryArray.add(0,mCurrentMoodPosition);
//                        for(int i = 0; i < 3; i ++){
//                            mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
//                        }
//                        break;
//                    case 4 :
//                        for(int i = 0; i < 3; i ++){
//                            mMoodHistoryArray.add(0,-1);
//                        }
//                        mMoodHistoryArray.add(0,mCurrentMoodPosition);
//                        for(int i = 0; i < 4; i ++){
//                            mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
//                        }
//                        break;
//                    case 5 :
//                        for(int i = 0; i < 4; i ++){
//                            mMoodHistoryArray.add(0,-1);
//                        }
//                        mMoodHistoryArray.add(0,mCurrentMoodPosition);
//                        for(int i = 0; i < 5; i ++){
//                            mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
//                        }
//                        break;
//                    case 6 :
//                        for(int i = 0; i < 5; i ++){
//                            mMoodHistoryArray.add(0,-1);
//                        }
//                        mMoodHistoryArray.add(0,mCurrentMoodPosition);
//                        for(int i = 0; i < 6; i ++){
//                            mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
//                        }
//                        break;
//                    case 7 :
//                        for(int i = 0; i < 6; i ++){
//                            mMoodHistoryArray.add(0,-1);
//                        }
//                        mMoodHistoryArray.add(0,mCurrentMoodPosition);
//                        for(int i = 0; i < 7; i ++){
//                            mMoodHistoryArray.remove(mMoodHistoryArray.size()-1);
//                        }
//                        break;
//                    }

                while (mDayTrackingArray.size() > 1){

                    mDayTrackingArray.remove(0);
                    System.out.println("mDayTrackingArray cleared except last item");
                }

            } else if (!mDayTrackingArray.isEmpty()) {

                mDayTrackingArray.remove(mDayTrackingArray.size()-1);
                System.out.println("mDayTrackingArray last Item removed");
            }
        }
        System.out.println("mDayTrackingArray = " + mDayTrackingArray);
        System.out.println("mMoodHistoryArray = " + mMoodHistoryArray);
    }

    private void saveHistory(){
        ArrayList<Integer> Day = new ArrayList<>();

        mMoodHistoryArray.remove(0);
        mMoodHistoryArray.add(0,mViewPager.getCurrentItem());
        for (int i = 0;i<8;i++){
            Day.add(mMoodHistoryArray.get(i));
            mPreferences.edit().putInt(PREF_MOOD_ARRAY[i],Day.get(i)).apply();
        }
        mLastTimePosition = mViewPager.getCurrentItem();
        mPreferences.edit().putInt(PREF_LAST_TIME_MOOD_POSITION,mLastTimePosition).apply();
        mLastTimeDay = minute();
        mPreferences.edit().putInt(PREF_LAST_TIME_DAY,mLastTimeDay).apply();
    }

    private void initializeHistory(){
        mLastTimePosition = mPreferences.getInt(PREF_LAST_TIME_MOOD_POSITION,2);
        mLastTimeDay = mPreferences.getInt(PREF_LAST_TIME_DAY,minute());
        mMoodHistoryArray = new ArrayList<>();
        mDayTrackingArray = new ArrayList<>();
        ArrayList<Integer> Day = new ArrayList<>();

        if(mLastTimeDay == minute()){
            mViewPager.setCurrentItem(mLastTimePosition);
        } else {
            mViewPager.setCurrentItem(3);
        }

        for (int i = 0;i<8;i++){
            Day.add(mPreferences.getInt(PREF_MOOD_ARRAY[i],-2));
            mMoodHistoryArray.add(Day.get(i));
        }
        mDayTrackingArray.add(mLastTimeDay);

        System.out.println("initialization mMoodHistoryArray = " + mMoodHistoryArray);
        System.out.println("initialization mLastTimeDay = " + mLastTimeDay);
        System.out.println("initialization mDayTrackingArray = " + mDayTrackingArray);
    }

    public int minute(){
        return Calendar.getInstance().get(Calendar.MINUTE);
    }
    public int day(){
        return  Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }
    public int month(){
        return Calendar.getInstance().get(Calendar.MONTH)+1;
    }
    public int year(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }
    public int hour(){
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    private void whenLastTime() {
        int when = mCurrentMinute - mLastTimeDay;

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
        saveHistory();
        //mPreferences.edit().clear().apply();
        System.out.println("onStop!!!!!!!!!!!onStop!!!!!!!!!!!!!onStop!!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("onDestroy!!!!!!!!!!!onDestroy!!!!!!!!!!!!!onDestroy!!");
    }
}