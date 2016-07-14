package cn.hudp.shadyDemos;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/7/11.
 */
public class MyApplication extends Application {
    public Context applicationContext;
    private static MyApplication app = null;

    public static MyApplication getInstance() {
        if (app == null) {
            app = new MyApplication();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        applicationContext=this.getApplicationContext();
    }

    public static Context getAppContext(){
        return app;
    }
}
