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

public class PickColorActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    public static final String COLOR = "color";

    private float r, g, b;

    public static void storeColor(Bundle bundle, Color color) {
        bundle.putInt(COLOR, color.toArgb());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_color);

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

        SeekBar bar_r = ((SeekBar) findViewById(R.id.color_bar_r));
        SeekBar bar_g = ((SeekBar) findViewById(R.id.color_bar_g));
        SeekBar bar_b = ((SeekBar) findViewById(R.id.color_bar_b));

        bar_r.setOnSeekBarChangeListener(this);
        bar_g.setOnSeekBarChangeListener(this);
        bar_b.setOnSeekBarChangeListener(this);

        bar_r.setProgress((int) (r * 100));
        bar_g.setProgress((int) (g * 100));
        bar_b.setProgress((int) (b * 100));

        updateColorView();
    }

    public static Color fromBundle(Bundle bundle) {
        return Color.valueOf(bundle.getInt(COLOR));
    }

    protected void updateColorView() {
        int col = Color.valueOf(r, g, b).toArgb();
        findViewById(R.id.color_view_preview).setBackgroundColor(col);
        findViewById(R.id.color_bar_r).setBackgroundColor(col);
        findViewById(R.id.color_bar_g).setBackgroundColor(col);
        findViewById(R.id.color_bar_b).setBackgroundColor(col);

    }

    protected void confirm(View view) {
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
        storeColor(outState,Color.valueOf(r,g,b));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean bool) {
        if (seekBar.getId() == R.id.color_bar_r) {
            r = (float) (i / 100.0);
        } else if (seekBar.getId() == R.id.color_bar_b) {
            b = (float) (i / 100.0);
        } else if (seekBar.getId() == R.id.color_bar_g) {
            g = (float) (i / 100.0);
        }
        updateColorView();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}