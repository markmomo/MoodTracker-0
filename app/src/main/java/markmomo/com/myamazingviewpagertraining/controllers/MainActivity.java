package markmomo.com.myamazingviewpagertraining.controllers;


import android.content.DialogInterface;
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

        if (buttonIndex == 1) displayNoteBox();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteButtonIcon = findViewById(R.id.activity_main_note_icon);
        historyButtonIcon = findViewById(R.id.activity_main_history_icon);
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

        noteButtonIcon.setBackgroundColor(pageAdapter1.getIconColor());
        historyButtonIcon.setBackgroundColor(pageAdapter1.getIconColor());
    }
    private void displayNoteBox(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Commentaire");
        alert.setMessage("Commentaire");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do something with value!
            }
        });
        alert.setCancelable(false);
        alert.create();
        alert.show();

    }


}
