package ser.quinnipiac.edu.harrypottercharacters.app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ser.quinnipiac.edu.harrypottercharacters.R;
import ser.quinnipiac.edu.harrypottercharacters.async.LoadImageTask;
import ser.quinnipiac.edu.harrypottercharacters.data.Character;

public class CharacterDetailsActivity extends AppCompatActivity implements LoadImageTask.LoadImageListener {

    public static final String KEY_CHARACTER = "CHARACTER";

    private static final int[] SECTION_WAND;

    static {
        SECTION_WAND = new int[] {
//                R.id.cd_wand_title
        };
    }

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

        setText(R.id.cd_name,character.getName());

        if(character.getAlternateNames() != null && character.getAlternateNames().length > 0) {
            StringBuilder builder = new StringBuilder();
            for(String name : character.getAlternateNames()) {
                builder.append(", " + name);
            }

            setText(R.id.cd_name_alternates,builder.substring(2));
        }

        setText(R.id.cd_actor,"Played by: " + character.getActor());
//
//        if(!character.getWand().exists()) {
//            hideElements(SECTION_WAND);
//        }
//
////        setText(R.id.cd_house,character.getHouse());
    }

    private void hideElements(int... section) {
        for(int i : section) {
            findViewById(i).setVisibility(View.GONE);
        }
    }

    private void setText(int id, String text) {
        if(text != null && !text.equals("")) {
            ((TextView) findViewById(id)).setText(text);
        } else {
            hideElements(id);
        }
    }

    @Override
    public void onLoadImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}