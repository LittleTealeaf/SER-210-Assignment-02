package ser.quinnipiac.edu.harrypottercharacters.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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