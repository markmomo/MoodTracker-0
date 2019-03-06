package markmomo.com.myamazingviewpagertraining.controllers;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import markmomo.com.myamazingviewpagertraining.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements View.OnClickListener {

    //2 - Declare callback
    private OnButtonClickedListener mCallback;
    private ImageButton commentIcon;


    public HistoryFragment() {
        // Required empty public constructor
    }

    // 1 - Declare our interface that will be implemented by any container activity
    public interface OnButtonClickedListener {
        public void onButtonClicked(View view);
    }

    @Override
    public void onClick(View v) {
        // 5 - Spread the click to the parent activity
        mCallback.onButtonClicked(v);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_history, container, false);
        //result.findViewById(R.id.activity_main_history_icon).setOnClickListener(this);
        commentIcon = result.findViewById(R.id.fragment_page_root_view);
        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // 4 - Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }

    // 3 - Create callback to parent activity
    private void createCallbackToParentActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }

}
