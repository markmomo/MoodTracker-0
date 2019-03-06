package markmomo.com.myamazingviewpagertraining.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import markmomo.com.myamazingviewpagertraining.controllers.MoodsFragment;

/**
 * Created by markm ON 05/03/2019.
 */
public class PageAdapter extends FragmentPagerAdapter {

    private int[] colorsBank;
    private int mainIconsColor;

    public PageAdapter(FragmentManager mgr, int[] colors) {
        super(mgr);
        this.colorsBank = colors;
    }

    public int getMainIconsColor() {
        return mainIconsColor;
    }

    @Override
    public int getCount() {
        return(5);
    }

    @Override
    public Fragment getItem(int position) {
        mainIconsColor = this.colorsBank[position];
        return(MoodsFragment.newInstance(position, this.colorsBank[position]));
    }
}

