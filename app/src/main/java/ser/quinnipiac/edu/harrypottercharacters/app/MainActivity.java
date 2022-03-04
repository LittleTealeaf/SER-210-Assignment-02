package ser.quinnipiac.edu.harrypottercharacters.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import ser.quinnipiac.edu.harrypottercharacters.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final Map<Integer,String[]> BUTTON_ENDPOINTS;
    private static final int[] BUTTON_IDS;

    static {
        BUTTON_ENDPOINTS = new HashMap<Integer,String[]>() {{
            put(R.id.button_house_gryffindor, new String[] {"characters","house","gryffindor"});
        }};
        BUTTON_IDS = new int[] {
                R.id.button_house_gryffindor, R.id.button_house_hufflepuff,R.id.button_house_hufflepuff,R.id.button_house_ravenclaw,
                R.id.button_house_professors,R.id.button_house_nonwizards
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