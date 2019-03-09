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

        for (int i = 0; i < 7; i++){
            displayFragmentOnPosition(mMoodHistory.get(i+1), i+1);
        }
    }

    @Override
    public void onButtonClicked(View view) {


        Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        Log.e(getClass().getSimpleName(), "Button clicked!");

    }

    private void displayFragmentOnPosition(int currentMood, int position) {

        HistorySadFragment historySadFragment;
        HistoryDisappointedFragment historyDisappointedFragment;
        HistoryNormalFragment historyNormalFragment;
        HistoryHappyFragment historyHappyFragment;
        HistorySuperHappyFragment historySuperHappyFragment;
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
                historySadFragment = (HistorySadFragment) getSupportFragmentManager().findFragmentById(mDayPositionLayout);
                if (historySadFragment == null) {
                    historySadFragment = new HistorySadFragment();
                    myDisplayFragment(historySadFragment);
                }
                break;
            case 1:
                historyDisappointedFragment = (HistoryDisappointedFragment) getSupportFragmentManager().findFragmentById(mDayPositionLayout);
                if (historyDisappointedFragment == null) {
                    historyDisappointedFragment = new HistoryDisappointedFragment();
                    myDisplayFragment(historyDisappointedFragment);
                }
                break;
            case 2:
                historyNormalFragment = (HistoryNormalFragment) getSupportFragmentManager().findFragmentById(mDayPositionLayout);
                if (historyNormalFragment == null) {
                    historyNormalFragment = new HistoryNormalFragment();
                    myDisplayFragment(historyNormalFragment);
                }
                break;
            case 3:
                historyHappyFragment = (HistoryHappyFragment) getSupportFragmentManager().findFragmentById(mDayPositionLayout);
                if (historyHappyFragment == null) {
                    historyHappyFragment = new HistoryHappyFragment();
                    myDisplayFragment(historyHappyFragment);
                }
                break;
            case 4:
                historySuperHappyFragment = (HistorySuperHappyFragment) getSupportFragmentManager().findFragmentById(mDayPositionLayout);
                if (historySuperHappyFragment == null) {
                    historySuperHappyFragment = new HistorySuperHappyFragment();
                    myDisplayFragment(historySuperHappyFragment);
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