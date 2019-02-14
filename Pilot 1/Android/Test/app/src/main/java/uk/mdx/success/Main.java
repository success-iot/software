package uk.mdx.success;

import android.app.Application;
import android.content.Context;

/**
 * Created by Josegines1 on 18/04/2018.
 */

public class Main extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        Main.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Main.context;
    }


}
