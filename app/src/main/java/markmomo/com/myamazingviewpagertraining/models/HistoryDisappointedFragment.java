package markmomo.com.myamazingviewpagertraining.models;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import markmomo.com.myamazingviewpagertraining.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryDisappointedFragment extends Fragment {


    public HistoryDisappointedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_disappointed, container, false);
    }

}
