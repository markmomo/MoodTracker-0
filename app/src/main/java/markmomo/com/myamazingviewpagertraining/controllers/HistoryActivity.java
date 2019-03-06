package markmomo.com.myamazingviewpagertraining.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import markmomo.com.myamazingviewpagertraining.R;

public class HistoryActivity extends AppCompatActivity implements HistoryFragment.OnButtonClickedListener {

    HistoryFragment mHistoryFragment;

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
        // A - Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mHistoryFragment = (HistoryFragment) getSupportFragmentManager().findFragmentById(R.id.activity_history_root);

        if (mHistoryFragment == null) {
            // B - Create new main fragment
            mHistoryFragment = new HistoryFragment();
            // C - Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_history_root, mHistoryFragment)
                    .commit();
        }
    }
}
