package ser.quinnipiac.edu.harrypottercharacters.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

import ser.quinnipiac.edu.harrypottercharacters.R;
import ser.quinnipiac.edu.harrypottercharacters.async.FetchCharactersTask;
import ser.quinnipiac.edu.harrypottercharacters.data.Character;

/**
 * @author Thomas Kwashnak
 */
public class CharactersActivity extends AppCompatActivity implements FetchCharactersTask.FetchCharactersListener {

    public static final String CHARACTER_LIST, API_ENDPOINT;

    static {
        CHARACTER_LIST = "characterList";
        API_ENDPOINT = "apiEndpoint";
    }

    private final ArrayList<Character> characterList = new ArrayList<>();
    private CharacterAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);

        //Sets up recycler view
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.characters_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Sets background colors from intent
        int color = getIntent().getExtras().getInt(PickColorActivity.COLOR);
        findViewById(android.R.id.content).getRootView().setBackgroundColor(color);
        mRecyclerView.setBackgroundColor(color);

        mAdapter = new CharacterAdapter(this, characterList,color);

        mRecyclerView.setAdapter(mAdapter);

        //If a previous save state is present, pull characters from there, otherwise pull from a new FetchCharactersTask
        if (savedInstanceState != null) {
            onFetchCharacters(savedInstanceState.getParcelableArrayList(CHARACTER_LIST));
        } else {
            String[] endpoint = getIntent().getStringArrayExtra(API_ENDPOINT);
            new FetchCharactersTask(this).execute(endpoint);
        }



        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Clears and repopulates the character list, and then notifies the adapter of a dataset change
     * @param characters
     */
    @Override
    public void onFetchCharacters(Collection<Character> characters) {
        characterList.clear();
        characterList.addAll(characters);
        mAdapter.notifyDataSetChanged();
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
            intent.putExtra(PickColorActivity.COLOR,getIntent().getExtras().getInt(PickColorActivity.COLOR));
            startActivity(intent);
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the list of characters in the outState so another fetch isn't needed
        outState.putParcelableArrayList(CHARACTER_LIST, characterList);
    }
}