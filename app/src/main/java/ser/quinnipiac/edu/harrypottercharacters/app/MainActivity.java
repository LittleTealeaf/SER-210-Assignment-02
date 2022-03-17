package ser.quinnipiac.edu.harrypottercharacters.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import ser.quinnipiac.edu.harrypottercharacters.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final Map<Integer,String[]> BUTTON_ENDPOINTS;
    private static final int[] BUTTON_IDS;

    public static boolean DARK_MODE = false;

    static {
        BUTTON_ENDPOINTS = new HashMap<Integer,String[]>() {{
            put(R.id.button_house_gryffindor, new String[] {"characters","house","gryffindor"});
            put(R.id.button_house_hufflepuff, new String[] {"characters","house","hufflepuff"});
            put(R.id.button_house_ravenclaw, new String[] {"characters","house","ravenclaw"});
            put(R.id.button_house_slytherin, new String[] {"characters","house","slytherin"});
            put(R.id.button_house_professors, new String[] {"characters","staff"});
            put(R.id.button_house_all, new String[] {"characters"});
        }};
        BUTTON_IDS = new int[] {
                R.id.button_house_gryffindor, R.id.button_house_slytherin,R.id.button_house_hufflepuff,R.id.button_house_ravenclaw,
                R.id.button_house_professors,R.id.button_house_all
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        for(int id : BUTTON_IDS) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()) {
//            case R.id.menu_invert_colors:
////                https://stackoverflow.com/a/61891471/12206859
//                AppCompatDelegate.setDefaultNightMode(DARK_MODE ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
//
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public void onClick(View view) {
        if(BUTTON_ENDPOINTS.containsKey(view.getId())) {
            String[] endpoint = BUTTON_ENDPOINTS.get(view.getId());
            Intent intent = new Intent(this,CharactersActivity.class);
            intent.putExtra(CharactersActivity.API_ENDPOINT,endpoint);
            startActivity(intent);
        }
    }
}