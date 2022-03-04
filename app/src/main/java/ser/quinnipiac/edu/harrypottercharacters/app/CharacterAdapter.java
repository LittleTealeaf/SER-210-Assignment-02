package ser.quinnipiac.edu.harrypottercharacters.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ser.quinnipiac.edu.harrypottercharacters.R;
import ser.quinnipiac.edu.harrypottercharacters.data.Character;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private final LayoutInflater mInflater;
    private final List<Character> mCharacterList;

    public CharacterAdapter(Context context, List<Character> characterList) {
        mInflater = LayoutInflater.from(context);
        mCharacterList = characterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.list_character_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTo(mCharacterList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCharacterList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            textName = itemView.findViewById(R.id.character_text_name);
        }

        @Override
        public void onClick(View view) {

        }

        public void bindTo(Character person) {
            textName.setText(person.getName());
        }
    }
}
