package markmomo.com.myamazingviewpagertraining.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import markmomo.com.myamazingviewpagertraining.adapters.PageAdapter;
import markmomo.com.myamazingviewpagertraining.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton noteButtonIcon ;
    private ImageButton historyButtonIcon;
    ViewPager viewPager;
    SharedPreferences mPreferences;

    private EditText mEditTextBox;
    public static final String PREF_KEY_SAD_COMMENT = "PREF_KEY_SAD_COMMENT";
    public static final String PREF_KEY_DISAPPOINTED_COMMENT = "PREF_KEY_DISAPPOINTED_COMMENT";
    public static final String PREF_KEY_NORMAL_COMMENT = "PREF_KEY_NORMAL_COMMENT";
    public static final String PREF_KEY_HAPPY_COMMENT = "PREF_KEY_HAPPY_COMMENT";
    public static final String PREF_KEY_SUPER_HAPPY_COMMENT = "PREF_KEY_SUPER_HAPPY_COMMENT";

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

        mEditTextBox = new EditText(this);
        noteButtonIcon = findViewById(R.id.activity_main_note_icon_button);
        historyButtonIcon = findViewById(R.id.activity_main_history_icon_button);
        noteButtonIcon.setOnClickListener(this);
        historyButtonIcon.setOnClickListener(this);
        noteButtonIcon.setTag(1);
        historyButtonIcon.setTag(2);

        System.out.println(getPreferences(MODE_PRIVATE).getString(PREF_KEY_SAD_COMMENT, null));
        System.out.println(getPreferences(MODE_PRIVATE).getString(PREF_KEY_DISAPPOINTED_COMMENT, null));
        System.out.println(getPreferences(MODE_PRIVATE).getString(PREF_KEY_NORMAL_COMMENT, null));
        System.out.println(getPreferences(MODE_PRIVATE).getString(PREF_KEY_HAPPY_COMMENT, null));
        System.out.println(getPreferences(MODE_PRIVATE).getString(PREF_KEY_SUPER_HAPPY_COMMENT, null));

        this.configureViewPager();
    }

    private void configureViewPager(){

        PageAdapter pageAdapter1;

        pageAdapter1 = new PageAdapter(getSupportFragmentManager(), getResources().getIntArray(R.array.colorPagesViewPager));
        viewPager = findViewById(R.id.activity_main_view_pager);

        viewPager.setAdapter(pageAdapter1);
        viewPager.setCurrentItem(2);

        noteButtonIcon.setBackgroundColor(pageAdapter1.getMainIconsColor());
        historyButtonIcon.setBackgroundColor(pageAdapter1.getMainIconsColor());
    }
    private void myDisplayNoteBox(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Commentaire");
        alert.setView(mEditTextBox);

        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        alert.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                myStoreCommentOfTheMood();

                System.out.println(getPreferences(MODE_PRIVATE).getString(PREF_KEY_SAD_COMMENT, null));
                System.out.println(getPreferences(MODE_PRIVATE).getString(PREF_KEY_DISAPPOINTED_COMMENT, null));
                System.out.println(getPreferences(MODE_PRIVATE).getString(PREF_KEY_NORMAL_COMMENT, null));
                System.out.println(getPreferences(MODE_PRIVATE).getString(PREF_KEY_HAPPY_COMMENT, null));
                System.out.println(getPreferences(MODE_PRIVATE).getString(PREF_KEY_SUPER_HAPPY_COMMENT, null));
            }
        });
        alert.setCancelable(false);
        alert.create();
        if(mEditTextBox.getParent() != null) {
            ((ViewGroup)mEditTextBox.getParent()).removeView(mEditTextBox); // <- fix
        }
        alert.show();
    }





    private void myStoreCommentOfTheMood(){

        switch (viewPager.getCurrentItem()){
            case 0:
                mPreferences = getPreferences(MODE_PRIVATE);
                mPreferences.edit().putString(PREF_KEY_SAD_COMMENT, mEditTextBox.getText().toString()).apply();
                break;
            case 1:
                mPreferences = getPreferences(MODE_PRIVATE);
                mPreferences.edit().putString(PREF_KEY_DISAPPOINTED_COMMENT, mEditTextBox.getText().toString()).apply();
                break;
            case 2:
                mPreferences = getPreferences(MODE_PRIVATE);
                mPreferences.edit().putString(PREF_KEY_NORMAL_COMMENT, mEditTextBox.getText().toString()).apply();
                break;
            case 3:
                mPreferences = getPreferences(MODE_PRIVATE);
                mPreferences.edit().putString(PREF_KEY_HAPPY_COMMENT, mEditTextBox.getText().toString()).apply();
                break;
            case 4:
                mPreferences = getPreferences(MODE_PRIVATE);
                mPreferences.edit().putString(PREF_KEY_SUPER_HAPPY_COMMENT, mEditTextBox.getText().toString()).apply();
                break;
        }
    }
}