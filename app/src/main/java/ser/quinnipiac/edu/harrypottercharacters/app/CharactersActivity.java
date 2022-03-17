package ser.quinnipiac.edu.harrypottercharacters.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collection;

import ser.quinnipiac.edu.harrypottercharacters.R;
import ser.quinnipiac.edu.harrypottercharacters.async.FetchCharactersTask;
import ser.quinnipiac.edu.harrypottercharacters.data.Character;

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

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.characters_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new CharacterAdapter(this, characterList);

        mRecyclerView.setAdapter(mAdapter);

        if(savedInstanceState != null) {
            onFetchCharacters(savedInstanceState.getParcelableArrayList(CHARACTER_LIST));
        } else {
            String[] endpoint = getIntent().getStringArrayExtra(API_ENDPOINT);
            new FetchCharactersTask(this).execute(endpoint);
        }


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (MenuUtil.MENU_FUNCTIONS.getOrDefault(item.getItemId(),(n) -> false).apply(this)) {
            return true;
        } else if (id == R.id.menu_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, "I'm using the Harry Potter Characters App by Thomas Kwashnak!");
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, null));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFetchCharacters(Collection<Character> characters) {
        characterList.clear();
        characterList.addAll(characters);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(CHARACTER_LIST,characterList);
    }
}