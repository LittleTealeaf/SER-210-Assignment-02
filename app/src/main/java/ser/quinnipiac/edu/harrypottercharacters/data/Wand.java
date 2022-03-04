package ser.quinnipiac.edu.harrypottercharacters.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Wand implements Parcelable {
//    {"wood":"holly","core":"phoenix feather","length":11}

    private String wood;
    private String core;
    private double length;

    public Wand(JSONObject jsonObject) throws JSONException {
        wood = jsonObject.getString("wood");
        core = jsonObject.getString("core");
        try {
            length = jsonObject.getDouble("length");
        } catch(Exception e) {
            length = 0;
        }
    }

    protected Wand(Parcel in) {
        wood = in.readString();
        core = in.readString();
        length = in.readDouble();
    }

    public static final Creator<Wand> CREATOR = new Creator<Wand>() {
        @Override
        public Wand createFromParcel(Parcel in) {
            return new Wand(in);
        }

        @Override
        public Wand[] newArray(int size) {
            return new Wand[size];
        }
    };

    public String getWood() {
        return wood;
    }

    public String getCore() {
        return core;
    }

    public double getLength() {
        return length;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(wood);
        parcel.writeString(core);
        parcel.writeDouble(length);
    }
}
