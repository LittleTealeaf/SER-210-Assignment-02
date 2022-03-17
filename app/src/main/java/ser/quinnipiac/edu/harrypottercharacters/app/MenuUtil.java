package ser.quinnipiac.edu.harrypottercharacters.app;

import android.app.Activity;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import ser.quinnipiac.edu.harrypottercharacters.R;

public class MenuUtil {

    public static Map<Integer, Function<Activity, Boolean>> MENU_FUNCTIONS;

    static {
        MENU_FUNCTIONS = new HashMap<Integer, Function<Activity, Boolean>>() {{
            put(R.id.menu_about, (activity) -> {
                activity.startActivity(new Intent(activity, AppInfoActivity.class));
                return true;
            });
        }};
    }
}
