package ser.quinnipiac.edu.harrypottercharacters.app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import ser.quinnipiac.edu.harrypottercharacters.R;
import ser.quinnipiac.edu.harrypottercharacters.async.LoadImageTask;
import ser.quinnipiac.edu.harrypottercharacters.data.Character;

public class CharacterDetailsActivity extends AppCompatActivity implements LoadImageTask.LoadImageListener {

    public static final String KEY_CHARACTER = "CHARACTER";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);

        imageView = findViewById(R.id.cd_image);

        Character character = getIntent().getParcelableExtra(KEY_CHARACTER);

        if(!character.getImage().equals("")) {
            new LoadImageTask(this).execute(character.getImage());
        }

        ((TextView) findViewById(R.id.cd_name)).setText(character.getName());

        if(character.getAlternateNames() != null && character.getAlternateNames().length > 0) {
            StringBuilder builder = new StringBuilder();
            for(String name : character.getAlternateNames()) {
                builder.append(", " + name);
            }

            ((TextView) findViewById(R.id.cd_name_alternates)).setText(builder.substring(1));
        }
    }

    @Override
    public void onLoadImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}