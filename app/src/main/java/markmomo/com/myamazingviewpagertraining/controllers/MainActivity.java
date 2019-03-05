package markmomo.com.myamazingviewpagertraining.controllers;


import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import markmomo.com.myamazingviewpagertraining.adapters.PageAdapter;
import markmomo.com.myamazingviewpagertraining.R;

public class MainActivity extends AppCompatActivity {
    private ImageButton noteButtonIcon ;
    private ImageButton historyButtonIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteButtonIcon = findViewById(R.id.activity_main_note_icon);
        historyButtonIcon = findViewById(R.id.activity_main_history_icon);

        this.configureViewPager();
    }

    private void configureViewPager(){
        ViewPager viewPager;
        PageAdapter pageAdapter1;

        pageAdapter1 = new PageAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.colorPagesViewPager));
        viewPager = findViewById(R.id.activity_main_view_pager);

        viewPager.setAdapter(pageAdapter1);
        viewPager.setCurrentItem(2);

        noteButtonIcon.setBackgroundColor(pageAdapter1.getIconColor());
        historyButtonIcon.setBackgroundColor(pageAdapter1.getIconColor());
    }
}
