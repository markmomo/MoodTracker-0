package markmomo.com.myamazingviewpagertraining.controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import markmomo.com.myamazingviewpagertraining.R;
import markmomo.com.myamazingviewpagertraining.models.HistoryDisappointedFragment;
import markmomo.com.myamazingviewpagertraining.models.HistoryHappyFragment;
import markmomo.com.myamazingviewpagertraining.models.HistoryNormalFragment;
import markmomo.com.myamazingviewpagertraining.models.HistorySadFragment;
import markmomo.com.myamazingviewpagertraining.models.HistorySuperHappyFragment;

public class HistoryActivity extends AppCompatActivity implements HistoryNormalFragment.OnButtonClickedListener,
        HistoryDisappointedFragment.OnButtonClickedListener,
        HistoryHappyFragment.OnButtonClickedListener, HistorySadFragment.OnButtonClickedListener,
        HistorySuperHappyFragment.OnButtonClickedListener {

    @IdRes
    private int mDayPositionLayout;
    private SharedPreferences mPreferences;

    //MOOD

    public static final String PREF_KEY_ONE_BEFORE_COMMENT_MOOD = "PREF_KEY_ONE_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_TWO_BEFORE_COMMENT_MOOD = "PREF_KEY_TWO_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_THREE_BEFORE_COMMENT_MOOD = "PREF_KEY_THREE_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_FOUR_BEFORE_COMMENT_MOOD = "PREF_KEY_FOUR_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_FIVE_BEFORE_COMMENT_MOOD = "PREF_KEY_FIVE_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_SIX_BEFORE_COMMENT_MOOD = "PREF_KEY_SIX_BEFORE_COMMENT_MOOD";
    public static final String PREF_KEY_SEVEN_BEFORE_COMMENT_MOOD = "PREF_KEY_SEVEN_BEFORE_COMMENT_MOOD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        int YesterdayMood;
        int twoDaysLeftMood;
        int threeDaysLeftMood;
        int fourDaysLeftMood;
        int fiveDaysLeftMood;
        int sixDaysLeftMood;
        int sevenDaysLeftMood;

        mPreferences = getPreferences(MODE_PRIVATE);
        Intent intent = getIntent();
        YesterdayMood = intent.getIntExtra(PREF_KEY_ONE_BEFORE_COMMENT_MOOD,-1);
        twoDaysLeftMood = intent.getIntExtra(PREF_KEY_TWO_BEFORE_COMMENT_MOOD,-1);
        threeDaysLeftMood = intent.getIntExtra(PREF_KEY_THREE_BEFORE_COMMENT_MOOD,-1);
        fourDaysLeftMood = intent.getIntExtra(PREF_KEY_FOUR_BEFORE_COMMENT_MOOD,-1);
        fiveDaysLeftMood = intent.getIntExtra(PREF_KEY_FIVE_BEFORE_COMMENT_MOOD,-1);
        sixDaysLeftMood = intent.getIntExtra(PREF_KEY_SIX_BEFORE_COMMENT_MOOD,-1);
        sevenDaysLeftMood = intent.getIntExtra(PREF_KEY_SEVEN_BEFORE_COMMENT_MOOD,-1);

        MyDisplayFragmentOnPosition(sevenDaysLeftMood, 7);
        MyDisplayFragmentOnPosition(sixDaysLeftMood, 6);
        MyDisplayFragmentOnPosition(fiveDaysLeftMood, 5);
        MyDisplayFragmentOnPosition(fourDaysLeftMood, 4);
        MyDisplayFragmentOnPosition(threeDaysLeftMood, 3);
        MyDisplayFragmentOnPosition(twoDaysLeftMood, 2);
        MyDisplayFragmentOnPosition(YesterdayMood, 1);

    }

    @Override
    public void onButtonClicked(View view) {


        Toast.makeText(this, "Correct"+ mPreferences.getInt(PREF_KEY_ONE_BEFORE_COMMENT_MOOD, -1), Toast.LENGTH_SHORT).show();
        Log.e(getClass().getSimpleName(), "Button clicked!");

    }

    private void MyDisplayFragmentOnPosition(int currentMood, int position) {

        HistorySadFragment mHistorySadFragment;
        HistoryDisappointedFragment mHistoryDisappointedFragment;
        HistoryNormalFragment mHistoryNormalFragment;
        HistoryHappyFragment mHistoryHappyFragment;
        HistorySuperHappyFragment mHistorySuperHappyFragment;
        mDayPositionLayout = 0;

        switch (position) {
            case 1:
                mDayPositionLayout = R.id.activity_history_one_day_frame_layout;
                break;
            case 2:
                mDayPositionLayout = R.id.activity_history_two_day_frame_layout;
                break;
            case 3:
                mDayPositionLayout = R.id.activity_history_three_day_frame_layout;
                break;
            case 4:
                mDayPositionLayout = R.id.activity_history_four_day_frame_layout;
                break;
            case 5:
                mDayPositionLayout = R.id.activity_history_five_day_frame_layout;
                break;
            case 6:
                mDayPositionLayout = R.id.activity_history_six_day_frame_layout;
                break;
            case 7:
                mDayPositionLayout = R.id.activity_history_seven_day_frame_layout;
                break;
        }
        switch (currentMood) {
            case 0:
                mHistorySadFragment = (HistorySadFragment) getSupportFragmentManager().findFragmentById(mDayPositionLayout);
                if (mHistorySadFragment == null) {
                    mHistorySadFragment = new HistorySadFragment();
                    myDisplayFragment(mHistorySadFragment);
                }
                break;
            case 1:
                mHistoryDisappointedFragment = (HistoryDisappointedFragment) getSupportFragmentManager().findFragmentById(mDayPositionLayout);
                if (mHistoryDisappointedFragment == null) {
                    mHistoryDisappointedFragment = new HistoryDisappointedFragment();
                    myDisplayFragment(mHistoryDisappointedFragment);
                }
                break;
            case 2:
                mHistoryNormalFragment = (HistoryNormalFragment) getSupportFragmentManager().findFragmentById(mDayPositionLayout);
                if (mHistoryNormalFragment == null) {
                    mHistoryNormalFragment = new HistoryNormalFragment();
                    myDisplayFragment(mHistoryNormalFragment);
                }
                break;
            case 3:
                mHistoryHappyFragment = (HistoryHappyFragment) getSupportFragmentManager().findFragmentById(mDayPositionLayout);
                if (mHistoryHappyFragment == null) {
                    mHistoryHappyFragment = new HistoryHappyFragment();
                    myDisplayFragment(mHistoryHappyFragment);
                }
                break;
            case 4:
                mHistorySuperHappyFragment = (HistorySuperHappyFragment) getSupportFragmentManager().findFragmentById(mDayPositionLayout);
                if (mHistorySuperHappyFragment == null) {
                    mHistorySuperHappyFragment = new HistorySuperHappyFragment();
                    myDisplayFragment(mHistorySuperHappyFragment);
                    break;
                }
        }
    }

    private void myDisplayFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .add(mDayPositionLayout, fragment)
                .commit();
    }
}