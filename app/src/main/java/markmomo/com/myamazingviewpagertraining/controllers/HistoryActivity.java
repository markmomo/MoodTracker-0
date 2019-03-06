package markmomo.com.myamazingviewpagertraining.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import markmomo.com.myamazingviewpagertraining.R;
import markmomo.com.myamazingviewpagertraining.models.HistoryNormalFragment;

public class HistoryActivity extends AppCompatActivity implements HistoryNormalFragment.OnButtonClickedListener {

    HistoryNormalFragment mHistoryNormalFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        configureAndShowMainFragment();
    }

    @Override
    public void onButtonClicked(View view) {

        Log.e(getClass().getSimpleName(), "Button clicked!" );
    }

    private void configureAndShowMainFragment(){

        mHistoryNormalFragment = (HistoryNormalFragment) getSupportFragmentManager()
                .findFragmentById(R.id.activity_history_seven_day_frame_layout);

        if (mHistoryNormalFragment == null) {

            mHistoryNormalFragment = new HistoryNormalFragment();getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_history_seven_day_frame_layout, mHistoryNormalFragment)
                    .commit();
        }
    }
}
