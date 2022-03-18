package ser.quinnipiac.edu.harrypottercharacters.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ser.quinnipiac.edu.harrypottercharacters.R;

/**
 * @author Thomas Kwashnak
 */
public class PickColorActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {


    public static final String COLOR = "color";

    /**
     * Current RGB values
     */
    private float r, g, b;

    public static void storeColor(Bundle bundle, Color color) {
        //Stores the color value into the bundle
        bundle.putInt(COLOR, color.toArgb());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_color);

        //Pulls the color from either the savedInstanceState or the intent
        if (savedInstanceState != null) {
            Color color = fromBundle(savedInstanceState);
            r = color.red();
            g = color.green();
            b = color.blue();
        } else {
            Intent intent = getIntent();
            Color color = fromBundle(intent.getExtras());
            r = color.red();
            g = color.green();
            b = color.blue();
        }

        findViewById(R.id.color_confirm).setOnClickListener(this::confirm);

        //Loads the individual SeekBars
        SeekBar bar_r = ((SeekBar) findViewById(R.id.color_bar_r));
        SeekBar bar_g = ((SeekBar) findViewById(R.id.color_bar_g));
        SeekBar bar_b = ((SeekBar) findViewById(R.id.color_bar_b));

        //Sets the bar's change listener to this
        bar_r.setOnSeekBarChangeListener(this);
        bar_g.setOnSeekBarChangeListener(this);
        bar_b.setOnSeekBarChangeListener(this);

        //Sets the values of each bar
        bar_r.setProgress((int) (r * 100));
        bar_g.setProgress((int) (g * 100));
        bar_b.setProgress((int) (b * 100));

        //Updates backgrounds
        updateColorView();
    }


    public static Color fromBundle(Bundle bundle) {
        //Returns a color object from a bundle.
        return Color.valueOf(bundle.getInt(COLOR));

    }

    protected void updateColorView() {
        //Creates the current color, then sets the activity's background color to that
        int col = Color.valueOf(r, g, b).toArgb();
        findViewById(android.R.id.content).getRootView().setBackgroundColor(col);
    }

    protected void confirm(View view) {
        //Saves the current color as the result, and then finishes the activity
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        storeColor(bundle,Color.valueOf(r,g,b));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //Saves the current color
        storeColor(outState,Color.valueOf(r,g,b));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean bool) {

        //Switches between each of the ids, and updates that color value accordingly
        if (seekBar.getId() == R.id.color_bar_r) {
            r = (float) (i / 100.0);
        } else if (seekBar.getId() == R.id.color_bar_b) {
            b = (float) (i / 100.0);
        } else if (seekBar.getId() == R.id.color_bar_g) {
            g = (float) (i / 100.0);
        }
        //Updates the color view
        updateColorView();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //required stub
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //required stub
    }
}