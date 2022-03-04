package ser.quinnipiac.edu.harrypottercharacters.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class HarryPotterCharacter implements Serializable {

    public static String NAME, ALTERNATE_NAMES, SPECIES, GENDER, HOUSE, DATE_OF_BIRTH, YEAR_OF_BIRTH, WIZARD, ANCESTRY, EYE_COLOUR, HAIR_COLOUR,
            WAND, PATRONUS, HOGWARTS_STUDENT, HOGWARTS_STAFF, ACTOR, ALTERNATE_ACTORS, ALIVE, IMAGE;

    private String name;
    private String[] alternateNames;
    private String species;
    private String gender;
    private String house;
    private String dateOfBirth;
    private int yearOfBirth;
    private boolean wizard;
    private String ancestry;
    private String eyeColour;
    private String hairColour;
    private Wand wand;
    private String patronus;
    private boolean hogwartsStudent;
    private boolean hogwartsStaff;
    private String actor;
    private String[] alternateActors;
    private boolean alive;
    private String image;


/*
{"name":"Harry Potter","alternate_names":[],"species":"human","gender":"male","house":"Gryffindor","dateOfBirth":"31-07-1980","yearOfBirth":1980,
"wizard":true,"ancestry":"half-blood","eyeColour":"green","hairColour":"black","wand":{"wood":"holly","core":"phoenix feather","length":11},
"patronus":"stag","hogwartsStudent":true,"hogwartsStaff":false,"actor":"Daniel Radcliffe","alternate_actors":[],"alive":true,"image":"http://hp-api
.herokuapp.com/images/harry.jpg"}
 */

    public HarryPotterCharacter(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("name");
        alternateNames = convertJSONtoStringArray(jsonObject.getJSONArray("alternate_names"));
        species = jsonObject.getString("species");
        gender = jsonObject.getString("gender");
        house = jsonObject.getString("house");
        dateOfBirth = jsonObject.getString("dateOfBirth");
        yearOfBirth = jsonObject.getInt("yearOfBirth");
        wizard = jsonObject.getBoolean("wizard");
        ancestry = jsonObject.getString("ancestry");
        eyeColour = jsonObject.getString("eyeColour");
        hairColour = jsonObject.getString("hairColour");
//        wand = new Wand(jsonObject.getJSONObject("wand"));
        patronus = jsonObject.getString("patronus");
        hogwartsStudent = jsonObject.getBoolean("hogwartsStudent");
        hogwartsStaff = jsonObject.getBoolean("hogwartsStaff");
        actor = jsonObject.getString("actor");
        alternateActors = convertJSONtoStringArray(jsonObject.getJSONArray("alternate_actors"));
        alive = jsonObject.getBoolean("alive");
        image = jsonObject.getString("image");
    }

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
}
