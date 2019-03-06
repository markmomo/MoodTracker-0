package markmomo.com.myamazingviewpagertraining.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import markmomo.com.myamazingviewpagertraining.adapters.PageAdapter;
import markmomo.com.myamazingviewpagertraining.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton noteButtonIcon ;
    private ImageButton historyButtonIcon;

    @Override
    public void onClick(View v) {
        int buttonIndex;
        buttonIndex = (int)v.getTag();

        if (buttonIndex == 1) {
            myDisplayNoteBox();
        }
        if (buttonIndex == 2){
            Intent historyActivityIntent = new Intent(MainActivity.this,HistoryActivity.class);
            startActivity(historyActivityIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteButtonIcon = findViewById(R.id.activity_main_note_icon_button);
        historyButtonIcon = findViewById(R.id.activity_main_history_icon_button);
        noteButtonIcon.setOnClickListener(this);
        historyButtonIcon.setOnClickListener(this);
        noteButtonIcon.setTag(1);
        historyButtonIcon.setTag(2);

        this.configureViewPager();
    }

    private void configureViewPager(){
        ViewPager viewPager;
        PageAdapter pageAdapter1;

        pageAdapter1 = new PageAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.colorPagesViewPager));
        viewPager = findViewById(R.id.activity_main_view_pager);

        viewPager.setAdapter(pageAdapter1);
        viewPager.setCurrentItem(2);

        noteButtonIcon.setBackgroundColor(pageAdapter1.getMainIconsColor());
        historyButtonIcon.setBackgroundColor(pageAdapter1.getMainIconsColor());
    }
    private void myDisplayNoteBox(){
        final EditText input = new EditText(this);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Commentaire");
        alert.setView(input);

        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alert.setCancelable(false);
        alert.create();
        alert.show();
    }
}