package markmomo.com.myamazingviewpagertraining.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import markmomo.com.myamazingviewpagertraining.R;
import markmomo.com.myamazingviewpagertraining.models.HistoryDisappointedFragment;
import markmomo.com.myamazingviewpagertraining.models.HistoryHappyFragment;
import markmomo.com.myamazingviewpagertraining.models.HistoryNormalFragment;
import markmomo.com.myamazingviewpagertraining.models.HistorySadFragment;
import markmomo.com.myamazingviewpagertraining.models.HistorySuperHappy;

public class HistoryActivity extends AppCompatActivity implements HistoryNormalFragment.OnButtonClickedListener {

    HistorySadFragment mHistorySadFragment;
    HistoryDisappointedFragment mHistoryDisappointedFragment;
    HistoryNormalFragment mHistoryNormalFragment;
    HistoryHappyFragment mHistoryHappyFragment;
    HistorySuperHappy mHistorySuperHappy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        configureAndShowSevenDaysFragment();
        configureAndShowSixDaysFragment();
        configureAndShowFiveDaysFragment();
        configureAndShowFourDaysFragment();
        configureAndShowThreeDaysFragment();
        configureAndShowTwoDaysFragment();
        configureAndShowOneDaysFragment();
    }

    @Override
    public void onButtonClicked(View view) {

        Log.e(getClass().getSimpleName(), "Button clicked!" );
    }

    private void configureAndShowSevenDaysFragment(){

        mHistorySadFragment = (HistorySadFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_history_seven_day_frame_layout);

        if (mHistorySadFragment == null) {

            mHistorySadFragment = new HistorySadFragment();getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_history_seven_day_frame_layout, mHistorySadFragment)
                    .commit();
        }
    }

    private void configureAndShowSixDaysFragment(){

        mHistoryDisappointedFragment = (HistoryDisappointedFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_history_six_day_frame_layout);

        if (mHistoryDisappointedFragment == null) {

            mHistoryDisappointedFragment = new HistoryDisappointedFragment();getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_history_six_day_frame_layout, mHistoryDisappointedFragment)
                    .commit();
        }
    }

    private void configureAndShowFiveDaysFragment(){

        mHistoryNormalFragment = (HistoryNormalFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_history_five_day_frame_layout);

        if (mHistoryNormalFragment == null) {

            mHistoryNormalFragment = new HistoryNormalFragment();getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_history_five_day_frame_layout, mHistoryNormalFragment)
                    .commit();
        }
    }
    private void configureAndShowFourDaysFragment(){

        mHistoryHappyFragment = (HistoryHappyFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_history_four_day_frame_layout);

        if (mHistoryHappyFragment == null) {

            mHistoryHappyFragment = new HistoryHappyFragment();getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_history_four_day_frame_layout, mHistoryHappyFragment)
                    .commit();
        }
    }

    private void configureAndShowThreeDaysFragment(){

        mHistorySuperHappy = (HistorySuperHappy) getSupportFragmentManager()
                .findFragmentById(R.id.activity_history_three_day_frame_layout);

        if (mHistorySuperHappy == null) {

            mHistorySuperHappy = new HistorySuperHappy();getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_history_three_day_frame_layout, mHistorySuperHappy)
                    .commit();
        }
    }

    private void configureAndShowTwoDaysFragment(){

        mHistoryHappyFragment = (HistoryHappyFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_history_two_day_frame_layout);

        if (mHistoryHappyFragment == null) {

            mHistoryHappyFragment = new HistoryHappyFragment();getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_history_two_day_frame_layout, mHistoryHappyFragment)
                    .commit();
        }
    }

    private void configureAndShowOneDaysFragment(){

        mHistoryNormalFragment = (HistoryNormalFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_history_one_day_frame_layout);

        if (mHistoryNormalFragment == null) {

            mHistoryNormalFragment = new HistoryNormalFragment();getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_history_one_day_frame_layout, mHistoryNormalFragment)
                    .commit();
        }
    }
}
