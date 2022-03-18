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

/**
 * @author Thomas Kwashnak
 */
public class CharacterDetailsActivity extends AppCompatActivity implements LoadImageTask.LoadImageListener {

    public static final String KEY_CHARACTER = "CHARACTER";

    private Character character;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);

        //sets background color from the intent
        int color = getIntent().getExtras().getInt(PickColorActivity.COLOR);
        //Finds the view by the root ID
        findViewById(android.R.id.content).getRootView().setBackgroundColor(color);


        imageView = findViewById(R.id.cd_image);

        //Pulls the character details from the intent
        character = getIntent().getParcelableExtra(KEY_CHARACTER);

        //If there is an image, send a load image async task
        if (!character.getImage().equals("")) {
            new LoadImageTask(this).execute(character.getImage());
        }

        setText(R.id.cd_name, character.getName());

        if (character.getAlternateNames() != null && character.getAlternateNames().length > 0) {
            StringBuilder builder = new StringBuilder();
            for (String name : character.getAlternateNames()) {
                builder.append(", ").append(name);
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

    /**
     * Sets a text with a value only if the value is present. Includes a label
     * @param id TextView ID
     * @param label The label to put in front of the value if it exists
     * @param value The value
     */
    private void setTextWithLabel(int id, String label, String value) {
        if(value != null && !value.equals("")) {
            setText(id,label + value);
        } else {
            hideElements(id);
        }
    }

    /**
     * Sets a text with a value only if it exists
     * @param id TextView ID
     * @param text The value
     */
    private void setText(int id, String text) {
        if (text != null && !text.equals("")) {
            ((TextView) findViewById(id)).setText(text);
        } else {
            hideElements(id);
        }
    }

    /**
     * Hides the given views
     * @param section Views to make hidden
     */
    private void hideElements(int... section) {
        for (int i : section) {
            findViewById(i).setVisibility(View.GONE);

            //Make text size 0 if it is a TextView
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
            //Put the character.toString() as the content
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

    /**
     * Loads the bitmap to the image view
     * @param bitmap Image to load
     */
    @Override
    public void onLoadImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}