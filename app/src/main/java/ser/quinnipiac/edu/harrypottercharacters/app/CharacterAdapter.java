package ser.quinnipiac.edu.harrypottercharacters.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ser.quinnipiac.edu.harrypottercharacters.R;
import ser.quinnipiac.edu.harrypottercharacters.async.LoadImageTask;
import ser.quinnipiac.edu.harrypottercharacters.data.Character;


public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private final LruCache<String,Bitmap> imageCache;
    private final Context context;

    private final LayoutInflater mInflater;
    private final List<Character> mCharacterList;

    public CharacterAdapter(Context context, List<Character> characterList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mCharacterList = characterList;

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        imageCache = new LruCache<String,Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(this,mInflater.inflate(R.layout.list_character_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTo(mCharacterList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCharacterList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, LoadImageTask.LoadImageListener {

        Character character;

        CharacterAdapter adapter;

        final TextView textName;
        final ImageView imageView;

        public ViewHolder(CharacterAdapter adapter, @NonNull View itemView) {
            super(itemView);
            this.adapter = adapter;

            itemView.setOnClickListener(this);

            textName = itemView.findViewById(R.id.character_text_name);
            imageView = itemView.findViewById(R.id.character_image);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(adapter.context,CharacterDetailsActivity.class);
            intent.putExtra(CharacterDetailsActivity.KEY_CHARACTER, character);
            adapter.context.startActivity(intent);
        }

        public void bindTo(Character character) {
            this.character = character;
            textName.setText(character.getName());

            imageView.setImageBitmap(null);

            if(!character.getImage().equals("")) {

                Bitmap cache = adapter.imageCache.get(character.getImage());
                if(cache != null) {
                    imageView.setImageBitmap(cache);
                } else {
                    new  LoadImageTask(this).execute(character.getImage());
                }
                imageView.setAdjustViewBounds(true);
                textName.setTextColor(Color.WHITE);
            } else {
                imageView.setAdjustViewBounds(false);
                textName.setTextColor(Color.BLACK);
            }
        }

        @Override
        public void onLoadImage(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
            adapter.imageCache.put(character.getImage(),bitmap);
        }
    }
}
