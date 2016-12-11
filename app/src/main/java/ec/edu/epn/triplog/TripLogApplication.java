package ec.edu.epn.triplog;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by santi on 11-Dec-16.
 */

public class TripLogApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
