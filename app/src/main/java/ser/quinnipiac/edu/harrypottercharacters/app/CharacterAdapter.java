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

import org.w3c.dom.Text;

import java.util.List;

import ser.quinnipiac.edu.harrypottercharacters.R;
import ser.quinnipiac.edu.harrypottercharacters.async.LoadImageTask;
import ser.quinnipiac.edu.harrypottercharacters.data.Character;

/**
 * Adapts a Character to a View Holder
 * @author Thomas Kwashnak
 */
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private final LruCache<String,Bitmap> imageCache;
    private final Context context;

    private final int color;

    private final LayoutInflater mInflater;
    private final List<Character> mCharacterList;

    public CharacterAdapter(Context context, List<Character> characterList, int color) {
        //Initializes the color, which needs to be passed since this does not use an intent
        this.color = color;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mCharacterList = characterList;

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;


        /*
        Initialize the LRU Cache
        Googling brought me to use https://developer.android.com/reference/android/util/LruCache
         */
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

        final CharacterAdapter adapter;

        final TextView[] textViews;

        final TextView textName;
        final TextView textHouse;
        final ImageView imageView;

        public ViewHolder(CharacterAdapter adapter, @NonNull View itemView) {
            super(itemView);
            this.adapter = adapter;

            //Updates background color based on the adapter's set background color (pulled from constructor)
            itemView.setBackgroundColor(color);

            itemView.setOnClickListener(this);

            textName = itemView.findViewById(R.id.character_text_name);
            imageView = itemView.findViewById(R.id.character_image);
            textHouse = itemView.findViewById(R.id.character_text_house);

            //Initializes the textview list
            textViews = new TextView[] {textName, textHouse};
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(adapter.context,CharacterDetailsActivity.class);
            intent.putExtra(CharacterDetailsActivity.KEY_CHARACTER, character);
            intent.putExtra(PickColorActivity.COLOR,color);
            adapter.context.startActivity(intent);

        }

        /**
         * Updates values based on a provided character
         * @param character
         */
        public void bindTo(Character character) {
            this.character = character;
            textName.setText(character.getName());
            textHouse.setText(character.getHouse());

            //Clears image
            imageView.setImageBitmap(null);

            if(!character.getImage().equals("")) {
                //If there is an image, try to grab it from cache
                Bitmap cache = adapter.imageCache.get(character.getImage());
                if(cache != null) {
                    //If there was a value in cache, set that as the image
                    imageView.setImageBitmap(cache);
                } else {
                    //Create a new LoadImageTask that uses this as a listener
                    new LoadImageTask(this).execute(character.getImage());
                }
                //Adjusts the view bounds
                imageView.setAdjustViewBounds(true);
                //Update the image texts to have a white background to be visible on image
                for(TextView view : textViews) {
                    view.setTextColor(Color.WHITE);
                }
            } else {
                //If no image, Set the view bounds to false, and set text color to black
                imageView.setAdjustViewBounds(false);
                for(TextView view : textViews) {
                    view.setTextColor(Color.BLACK);
                }
            }
        }

        @Override
        public void onLoadImage(Bitmap bitmap) {
            //When the async task loads the image, set it as the imageView and put it in the cache
            imageView.setImageBitmap(bitmap);
            adapter.imageCache.put(character.getImage(),bitmap);
        }
    }
}
