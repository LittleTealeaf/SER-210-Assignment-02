package ser.quinnipiac.edu.harrypottercharacters.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import ser.quinnipiac.edu.harrypottercharacters.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final Map<Integer, String[]> BUTTON_ENDPOINTS;
    private static final int[] BUTTON_IDS;

    public static boolean DARK_MODE = false;

    private Color backgroundColor;

    static {
        BUTTON_IDS = new int[]{
                R.id.button_house_gryffindor,
                R.id.button_house_slytherin,
                R.id.button_house_hufflepuff,
                R.id.button_house_ravenclaw,
                R.id.button_house_professors,
                R.id.button_house_all
        };

        BUTTON_ENDPOINTS = new HashMap<Integer, String[]>() {{
            put(R.id.button_house_gryffindor, new String[]{"characters", "house", "gryffindor"});
            put(R.id.button_house_hufflepuff, new String[]{"characters", "house", "hufflepuff"});
            put(R.id.button_house_ravenclaw, new String[]{"characters", "house", "ravenclaw"});
            put(R.id.button_house_slytherin, new String[]{"characters", "house", "slytherin"});
            put(R.id.button_house_professors, new String[]{"characters", "staff"});
            put(R.id.button_house_all, new String[]{"characters"});
        }};
    }

    private ActivityResultLauncher<Intent> colorLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        for (int id : BUTTON_IDS) {
            findViewById(id).setOnClickListener(this);
        }

        if(savedInstanceState != null) {
            backgroundColor = PickColorActivity.fromBundle(savedInstanceState);
        } else {
            backgroundColor = Color.valueOf(Color.WHITE);
        }

        findViewById(android.R.id.content).getRootView().setBackgroundColor(backgroundColor.toArgb());
        colorLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->  {
           if(result.getResultCode() == Activity.RESULT_OK) {
               assert result.getData() != null;
               backgroundColor = PickColorActivity.fromBundle(result.getData().getExtras());
               findViewById(android.R.id.content).getRootView().setBackgroundColor(backgroundColor.toArgb());
           }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.menu_change_colors).setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_change_colors) {
            Intent intent = new Intent(this,PickColorActivity.class);
            Bundle bundle = new Bundle();
            PickColorActivity.storeColor(bundle,backgroundColor);
            intent.putExtras(bundle);
            colorLauncher.launch(intent);
        } else if (id ==  R.id.menu_about) {

            startActivityWithBackground(new Intent(this,AppInfoActivity.class));
        } else if (id == R.id.menu_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "I'm using the Harry Potter Characters App by Thomas Kwashnak!");
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, null));
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    protected void startActivityWithBackground(Intent intent) {
        Bundle bundle = new Bundle();
        PickColorActivity.storeColor(bundle,backgroundColor);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (BUTTON_ENDPOINTS.containsKey(view.getId())) {
            String[] endpoint = BUTTON_ENDPOINTS.get(view.getId());
            Intent intent = new Intent(this, CharactersActivity.class);
            intent.putExtra(CharactersActivity.API_ENDPOINT, endpoint);
            intent.putExtra(PickColorActivity.COLOR,backgroundColor.toArgb());
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        PickColorActivity.storeColor(outState,backgroundColor);
    }
}