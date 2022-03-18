package ser.quinnipiac.edu.harrypottercharacters.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;

import ser.quinnipiac.edu.harrypottercharacters.R;

/**
 * Activity representing the Application Information
 * @author Thomas Kwashnak
 */
public class AppInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Update colors based on color stored in intent
        int color = getIntent().getExtras().getInt(PickColorActivity.COLOR);
        findViewById(android.R.id.content).getRootView().setBackgroundColor(color);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        //Hide the about menu option
        menu.findItem(R.id.menu_about).setVisible(false);


        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        //Go back on navigate up
        finish();
        return true;
    }


}