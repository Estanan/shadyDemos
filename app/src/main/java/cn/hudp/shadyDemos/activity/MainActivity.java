package cn.hudp.shadyDemos.activity;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.hudp.shadyDemos.JsInterface;
import cn.hudp.shadyDemos.R;
import cn.hudp.shadyDemos.bean.Courses;
import cn.hudp.shadyDemos.bean.Student;
import cn.hudp.shadyDemos.utils.ConstantUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private WebView wv;
    private ImageView image;
    /**
     * 是否退出
     */
    private static boolean isExit=false;

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit=false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();

        wv.getSettings().setJavaScriptEnabled(true);//开启Js支持
        wv.loadUrl("file:///android_asset/test.html");
        //New 一个接口类 后面的是名称
        wv.addJavascriptInterface(new JsInterface(MainActivity.this),"shady");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxDemo();
            }
        });
    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        wv = (WebView) findViewById(R.id.wv);
        image= (ImageView) findViewById(R.id.image);
    }


    //RXdemo
    Student li = new Student();
    Student zhang = new Student();
    public void RxDemo(){
        String[] names={"shady","stan"};
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d("RX",s);
            }
        });
        final int drawableRes=R.mipmap.ic_launcher;
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable=getTheme().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ConstantUtil.showToast("Error!");
            }

            @Override
            public void onNext(Drawable drawable) {
                image.setImageDrawable(drawable);
            }
        });

        Observable.just(1,2,3,4)
                .subscribeOn(Schedulers.io())//指定subscibe()发生在IO线程
                .observeOn(AndroidSchedulers.mainThread())//指定Subscriber的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d("RX","Num:"+integer);
                    }
                });
        setData();
        Student[] student = {li,zhang};
        //获取学生姓名
        Subscriber<String> subscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d("RX",s);
            }
        };
        Observable.from(student)
                .map(new Func1<Student, String>() {
                    @Override
                    public String call(Student student) {
                        return student.getName();
                    }
                })
                .subscribe(subscriber);

        //获取学生课程
        /***
        Subscriber<Student> studentSubscriber=new Subscriber<Student>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Student student) {
                List<Courses> courses=student.getCourses();
                for (int i=0;i<courses.size();i++){
                    Courses course=courses.get(i);
                    Log.d("RX",course.getName());
                }
            }
        };
        Observable.from(student)
                .subscribe(studentSubscriber);
         */
        Subscriber<Courses> subscriber1=new Subscriber<Courses>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Courses courses) {
                Log.d("RX",courses.getName());
            }
        };
        Observable.from(student)
                .flatMap(new Func1<Student, Observable<Courses>>() {
                    @Override
                    public Observable<Courses> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                }).subscribe(subscriber1);
    }

    public void setData(){
        li.setName("李");
        List<Courses> courseList=new ArrayList<>();

        Courses courses=new Courses();
        courses.setName("化学");
        courseList.add(courses);

        Courses course2=new Courses();
        course2.setName("物理");
        courseList.add(course2);

        li.setCourses(courseList);



        zhang.setName("张");

//        li.setCourses(list);
//        zhang.setCourses(lzhang);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit){
            isExit=true;
            ConstantUtil.showToast(R.string.twiceExit);
            //利用handler延迟发送改变更改状态信息
            mHandler.sendEmptyMessageDelayed(0,2000);
        }else {
            finish();
            System.exit(0);
        }
    }

    private long exitTime=0;
    private void exit1(){
        if (System.currentTimeMillis()-exitTime>2000){
//            Toast.makeText(getApplicationContext(),R.string.twiceExit,Toast.LENGTH_SHORT).show();
            ConstantUtil.showToast(R.string.twiceExit);
            exitTime=System.currentTimeMillis();
        }else {
            finish();
            System.exit(0);
        }
    }
}
