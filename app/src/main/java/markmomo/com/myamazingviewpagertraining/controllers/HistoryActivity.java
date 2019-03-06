package markmomo.com.myamazingviewpagertraining.controllers;

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

    @IdRes int DaysBeforeLayout;
    HistorySadFragment mHistorySadFragment;
    HistoryDisappointedFragment mHistoryDisappointedFragment;
    HistoryNormalFragment mHistoryNormalFragment;
    HistoryHappyFragment mHistoryHappyFragment;
    HistorySuperHappyFragment mHistorySuperHappyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        MyConfigureAndShowHistory(5, 7);
        MyConfigureAndShowHistory(4, 6);
        MyConfigureAndShowHistory(3, 5);
        MyConfigureAndShowHistory(2, 4);
        MyConfigureAndShowHistory(1, 3);
        MyConfigureAndShowHistory(2, 2);
        MyConfigureAndShowHistory(3, 1);
    }
    @Override
    public void onButtonClicked(View view) {
        Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        Log.e(getClass().getSimpleName(), "Button clicked!");
    }

    private void MyConfigureAndShowHistory(int endOfDayMood, int daysBefore) {

        DaysBeforeLayout = 0;

        switch (daysBefore) {
            case 1:
                DaysBeforeLayout = R.id.activity_history_one_day_frame_layout;
                break;
            case 2:
                DaysBeforeLayout = R.id.activity_history_two_day_frame_layout;
                break;
            case 3:
                DaysBeforeLayout = R.id.activity_history_three_day_frame_layout;
                break;
            case 4:
                DaysBeforeLayout = R.id.activity_history_four_day_frame_layout;
                break;
            case 5:
                DaysBeforeLayout = R.id.activity_history_five_day_frame_layout;
                break;
            case 6:
                DaysBeforeLayout = R.id.activity_history_six_day_frame_layout;
                break;
            case 7:
                DaysBeforeLayout = R.id.activity_history_seven_day_frame_layout;
                break;
        }
        switch (endOfDayMood) {
            case 1:
                mHistorySadFragment = (HistorySadFragment) getSupportFragmentManager().findFragmentById(DaysBeforeLayout);
                if (mHistorySadFragment == null) {
                    mHistorySadFragment = new HistorySadFragment();
                    myDisplayFragment(mHistorySadFragment);
                }
                break;
            case 2:
                mHistoryDisappointedFragment = (HistoryDisappointedFragment) getSupportFragmentManager().findFragmentById(DaysBeforeLayout);
                if (mHistoryDisappointedFragment == null) {
                    mHistoryDisappointedFragment = new HistoryDisappointedFragment();
                    myDisplayFragment(mHistoryDisappointedFragment);
                }
                break;
            case 3:
                mHistoryNormalFragment = (HistoryNormalFragment) getSupportFragmentManager().findFragmentById(DaysBeforeLayout);
                if (mHistoryNormalFragment == null) {
                    mHistoryNormalFragment = new HistoryNormalFragment();
                    myDisplayFragment(mHistoryNormalFragment);
                }
                break;
            case 4:
                mHistoryHappyFragment = (HistoryHappyFragment) getSupportFragmentManager().findFragmentById(DaysBeforeLayout);
                if (mHistoryHappyFragment == null) {
                    mHistoryHappyFragment = new HistoryHappyFragment();
                    myDisplayFragment(mHistoryHappyFragment);
                }
                break;
            case 5:
                mHistorySuperHappyFragment = (HistorySuperHappyFragment) getSupportFragmentManager().findFragmentById(DaysBeforeLayout);
                if (mHistoryHappyFragment == null) {
                    mHistorySuperHappyFragment = new HistorySuperHappyFragment();
                    myDisplayFragment(mHistorySuperHappyFragment);
                    break;
                }
        }
    }

    public void myDisplayFragment (Fragment fragment){

        getSupportFragmentManager()
                .beginTransaction()
                .add(DaysBeforeLayout, fragment)
                .commit();
    }
}