package cn.hudp.shadyDemos;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import cn.hudp.shadyDemos.widget.LoadingDialog;

/**
 * Created by Administrator on 2016/6/30.
 */
public class JsInterface {
    Context context;
    LoadingDialog dialog;
    public JsInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void startFunction() {
        new LoadingDialog(context,false,R.style.loadingDialog).show();
    }

    //取消原生自定义的加载动画
    @JavascriptInterface
    public void	dismissInteractingProgressDialog(Context context) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @JavascriptInterface
    public void showToast(String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }
}
