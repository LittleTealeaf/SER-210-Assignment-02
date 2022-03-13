package ser.quinnipiac.edu.harrypottercharacters.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Character implements Parcelable {

    private final String name;
    private final String[] alternateNames;
    private final String species;
    private final String gender;
    private final String house;
    private final String dateOfBirth;
    private final int yearOfBirth;
    private final boolean wizard;
    private final String ancestry;
    private final String eyeColour;
    private final String hairColour;
    private final Wand wand;
    private final String patronus;
    private final boolean hogwartsStudent;
    private final boolean hogwartsStaff;
    private final String actor;
    private final String[] alternateActors;
    private final boolean alive;
    private final String image;


/*
{"name":"Harry Potter","alternate_names":[],"species":"human","gender":"male","house":"Gryffindor","dateOfBirth":"31-07-1980","yearOfBirth":1980,
"wizard":true,"ancestry":"half-blood","eyeColour":"green","hairColour":"black","wand":{"wood":"holly","core":"phoenix feather","length":11},
"patronus":"stag","hogwartsStudent":true,"hogwartsStaff":false,"actor":"Daniel Radcliffe","alternate_actors":[],"alive":true,"image":"http://hp-api
.herokuapp.com/images/harry.jpg"}
 */

    public Character(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("name");
        alternateNames = convertJSONtoStringArray(jsonObject.getJSONArray("alternate_names"));
        species = jsonObject.getString("species");
        gender = jsonObject.getString("gender");
        house = jsonObject.getString("house");
        dateOfBirth = jsonObject.getString("dateOfBirth");
        int yearOfBirth = 0;
        try {
            yearOfBirth = jsonObject.getInt("yearOfBirth");
        } catch(Exception e) {}
        this.yearOfBirth = yearOfBirth;
        wizard = jsonObject.getBoolean("wizard");
        ancestry = jsonObject.getString("ancestry");
        eyeColour = jsonObject.getString("eyeColour");
        hairColour = jsonObject.getString("hairColour");
        wand = new Wand(jsonObject.getJSONObject("wand"));
        patronus = jsonObject.getString("patronus");
        hogwartsStudent = jsonObject.getBoolean("hogwartsStudent");
        hogwartsStaff = jsonObject.getBoolean("hogwartsStaff");
        actor = jsonObject.getString("actor");
        alternateActors = convertJSONtoStringArray(jsonObject.getJSONArray("alternate_actors"));
        alive = jsonObject.getBoolean("alive");
        image = jsonObject.getString("image").replace("http://","https://");
    }

    protected Character(Parcel in) {
        name = in.readString();
        alternateNames = in.createStringArray();
        species = in.readString();
        gender = in.readString();
        house = in.readString();
        dateOfBirth = in.readString();
        yearOfBirth = in.readInt();
        wizard = in.readByte() != 0;
        ancestry = in.readString();
        eyeColour = in.readString();
        hairColour = in.readString();
        wand = in.readParcelable(Wand.class.getClassLoader());
        patronus = in.readString();
        hogwartsStudent = in.readByte() != 0;
        hogwartsStaff = in.readByte() != 0;
        actor = in.readString();
        alternateActors = in.createStringArray();
        alive = in.readByte() != 0;
        image = in.readString();
    }

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    private static String[] convertJSONtoStringArray(JSONArray array) throws JSONException {
        String[] strings = new String[array.length()];
        for(int i = 0; i < strings.length; i++) {
            strings[i] = array.getString(i);
        }
        return strings;
    }

    public String getName() {
        return name;
    }

    public String[] getAlternateNames() {
        return alternateNames;
    }

    public String getSpecies() {
        return species;
    }

    public String getGender() {
        return gender;
    }

    public String getHouse() {
        return house;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public boolean isWizard() {
        return wizard;
    }

    public String getAncestry() {
        return ancestry;
    }

    public String getEyeColour() {
        return eyeColour;
    }

    public String getHairColour() {
        return hairColour;
    }

    public Wand getWand() {
        return wand;
    }

    public String getPatronus() {
        return patronus;
    }

    public boolean isHogwartsStudent() {
        return hogwartsStudent;
    }

    public boolean isHogwartsStaff() {
        return hogwartsStaff;
    }

    public String getActor() {
        return actor;
    }

    public String[] getAlternateActors() {
        return alternateActors;
    }

    public boolean isAlive() {
        return alive;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeStringArray(alternateNames);
        parcel.writeString(species);
        parcel.writeString(gender);
        parcel.writeString(house);
        parcel.writeString(dateOfBirth);
        parcel.writeInt(yearOfBirth);
        parcel.writeByte((byte) (wizard ? 1 : 0));
        parcel.writeString(ancestry);
        parcel.writeString(eyeColour);
        parcel.writeString(hairColour);
        parcel.writeParcelable(wand, i);
        parcel.writeString(patronus);
        parcel.writeByte((byte) (hogwartsStudent ? 1 : 0));
        parcel.writeByte((byte) (hogwartsStaff ? 1 : 0));
        parcel.writeString(actor);
        parcel.writeStringArray(alternateActors);
        parcel.writeByte((byte) (alive ? 1 : 0));
        parcel.writeString(image);
    }
}
