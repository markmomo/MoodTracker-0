package markmomo.com.myamazingviewpagertraining.controllers;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

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


    ArrayList<Integer> mMoodHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mMoodHistory = new ArrayList<>();



        Intent intent = getIntent();

        mMoodHistory = intent.getIntegerArrayListExtra("moodHistoryArray");

        MyDisplayFragmentOnPosition(mMoodHistory.get(7), 7);
        MyDisplayFragmentOnPosition(mMoodHistory.get(6), 6);
        MyDisplayFragmentOnPosition(mMoodHistory.get(5), 5);
        MyDisplayFragmentOnPosition(mMoodHistory.get(4), 4);
        MyDisplayFragmentOnPosition(mMoodHistory.get(3), 3);
        MyDisplayFragmentOnPosition(mMoodHistory.get(2), 2);
        MyDisplayFragmentOnPosition(mMoodHistory.get(1), 1);

    }

    @Override
    public void onButtonClicked(View view) {


        Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
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