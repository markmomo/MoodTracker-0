package markmomo.com.myamazingviewpagertraining.models;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import markmomo.com.myamazingviewpagertraining.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoodsFragment extends Fragment {

    private LinearLayout linearLayoutRoot;
    private ImageView imageViewFragment;
    private static final String KEY_POSITION = "page number";
    private static final String KEY_COLOR = "background color";

    public MoodsFragment() {
    }

    public static MoodsFragment newInstance(int position, int color) {

        MoodsFragment moodsFragmentInstance = new MoodsFragment();

        Bundle bundleArgs = new Bundle();
        bundleArgs.putInt(KEY_POSITION, position);
        bundleArgs.putInt(KEY_COLOR, color);
        moodsFragmentInstance.setArguments(bundleArgs);

        return (moodsFragmentInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_moods, container, false);

        linearLayoutRoot = result.findViewById(R.id.fragment_moods_root);
        imageViewFragment = result.findViewById(R.id.fragment_moods_image);

        this.configureFragment();
        return result;
    }

    private void configureFragment() {
        int pageNumb;
        int backColor;
        pageNumb = getArguments().getInt(KEY_POSITION, -1);
        backColor = getArguments().getInt(KEY_COLOR, -1);

        linearLayoutRoot.setBackgroundColor(backColor);

        switch (pageNumb) {
            case 0:
                imageViewFragment.setImageResource(R.drawable.smiley_sad);
                break;
            case 1:
                imageViewFragment.setImageResource(R.drawable.smiley_disappointed);
                break;
            case 2:
                imageViewFragment.setImageResource(R.drawable.smiley_normal);
                break;
            case 3:
                imageViewFragment.setImageResource(R.drawable.smiley_happy);
                break;
            case 4:
                imageViewFragment.setImageResource(R.drawable.smiley_super_happy);
                break;
        }
        Log.e(getClass().getSimpleName(), "onCreateView called for fragment number " + pageNumb);
    }
}

