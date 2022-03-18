package ser.quinnipiac.edu.harrypottercharacters.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ser.quinnipiac.edu.harrypottercharacters.R;
import ser.quinnipiac.edu.harrypottercharacters.async.LoadImageTask;
import ser.quinnipiac.edu.harrypottercharacters.data.Character;

public class CharacterDetailsActivity extends AppCompatActivity implements LoadImageTask.LoadImageListener {

    public static final String KEY_CHARACTER = "CHARACTER";

    private static final int[] SECTION_WAND;

    static {
        SECTION_WAND = new int[]{
//                R.id.cd_wand_title
        };
    }

    private Character character;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);

        int color = getIntent().getExtras().getInt(PickColorActivity.COLOR);
        findViewById(android.R.id.content).getRootView().setBackgroundColor(color);

        imageView = findViewById(R.id.cd_image);

        character = getIntent().getParcelableExtra(KEY_CHARACTER);

        if (!character.getImage().equals("")) {
            new LoadImageTask(this).execute(character.getImage());
        }

        setText(R.id.cd_name, character.getName());

        if (character.getAlternateNames() != null && character.getAlternateNames().length > 0) {
            StringBuilder builder = new StringBuilder();
            for (String name : character.getAlternateNames()) {
                builder.append(", " + name);
            }

            setText(R.id.cd_name_alternates, builder.substring(2));
        } else {
            hideElements(R.id.cd_name_alternates);
        }

        setText(R.id.cd_house,character.getHouse());

        setTextWithLabel(R.id.cd_actor, "Played by: " ,character.getActor());

        setTextWithLabel(R.id.cd_birthdate,"Birthday: ",character.getDateOfBirth());
        setTextWithLabel(R.id.cd_ancestry,"Ancestry: ",character.getAncestry());
        setTextWithLabel(R.id.cd_patronus,"Patronus: ",character.getPatronus());


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setTextWithLabel(int id, String label, String value) {
        if(value != null && !value.equals("")) {
            setText(id,label + value);
        } else {
            hideElements(id);
        }
    }

    private void setText(int id, String text) {
        if (text != null && !text.equals("")) {
            ((TextView) findViewById(id)).setText(text);
        } else {
            hideElements(id);
        }
    }

    private void hideElements(int... section) {
        for (int i : section) {
            findViewById(i).setVisibility(View.GONE);
            if(findViewById(i) instanceof TextView) {
                ((TextView) findViewById(i)).setTextSize(0);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_about) {
            Intent intent = new Intent(this, AppInfoActivity.class);
            intent.putExtra(PickColorActivity.COLOR, getIntent().getExtras().getInt(PickColorActivity.COLOR));
            startActivity(intent);
        } else if (id == R.id.menu_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "Harry Potter Info!\n" + character.toString());
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, null));
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onLoadImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}