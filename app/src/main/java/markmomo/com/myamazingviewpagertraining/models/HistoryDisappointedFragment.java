package markmomo.com.myamazingviewpagertraining.models;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import markmomo.com.myamazingviewpagertraining.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryDisappointedFragment extends Fragment implements View.OnClickListener {
    private OnButtonClickedListener mCallback;

    public HistoryDisappointedFragment() {

    }

    public interface OnButtonClickedListener {
        public void onButtonClicked(View view);
    }

    @Override
    public void onClick(View v) {

        mCallback.onButtonClicked(v);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_history_disappointed, container, false);
        result.findViewById(R.id.fragment_history_disappointed_button).setOnClickListener(this);

        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.createCallbackToParentActivity();
    }

    private void createCallbackToParentActivity(){
        try {
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }
}
