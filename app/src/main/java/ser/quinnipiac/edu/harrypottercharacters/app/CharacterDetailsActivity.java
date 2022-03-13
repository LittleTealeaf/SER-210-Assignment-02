package ser.quinnipiac.edu.harrypottercharacters.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ser.quinnipiac.edu.harrypottercharacters.R;

public class CharacterDetailsActivity extends AppCompatActivity {

    public static final String KEY_CHARACTER = "CHARACTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);
    }
}