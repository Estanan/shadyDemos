package cn.hudp.shadyDemos.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import cn.hudp.shadyDemos.R;

/**
 * 加载中Dialog
 * 
 * @author xm
 */
public class LoadingDialog extends AlertDialog {

	private Context context;
    public LoadingDialog(Context context,boolean cancelAble) {
        super(context);
        this.context = context;
        this.setCanceledOnTouchOutside(cancelAble);
    }

    public LoadingDialog(Context context, boolean cancelAble,int theme) {
        super(context, theme);
        this.context = context;
        this.setCanceledOnTouchOutside(cancelAble);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.loading_layout);
        
    }

}
