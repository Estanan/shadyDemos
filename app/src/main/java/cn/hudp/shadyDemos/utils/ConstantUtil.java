package cn.hudp.shadyDemos.utils;

import android.view.Gravity;
import android.widget.Toast;

import cn.hudp.shadyDemos.MyApplication;

/**
 * Created by Administrator on 2016/7/11.
 */
public class ConstantUtil {


    /**
     * show Toast
     */
    public static void showToast(int strId){
        showToast(MyApplication.getAppContext().getString(strId));
    }

    private static Toast mToast = null;
    public static void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getAppContext(), text, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(text);
        }

        mToast.show();
    }

}
