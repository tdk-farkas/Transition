package farkas.tdk.transition.animation;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.transition.AutoTransition;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * @author tangdikun
 * Created by tangdikun on 16/1/13.
 */
public class Transition {
    final private static String TAG ="Transition";
    /**
     * 开始共享转场动画
     * @param context 当前activity
     * @param className 目标activity
     * @param view 共享视图
     * @param share 共享名字  // 该值 会用 className.getName() 作为key 通过bundle传递到 目标activity
     * @param mode ture:ImageView的Src图片 false:任何元素的背景图片  //该值 会用 "mode" 作为key 通过bundle传递到 目标activity
     * @return 结果 true:成功  false:出错了,请检查错误
     */
    public static boolean StartTransition(Activity context, Class<?> className,View view,String share,boolean mode){
        if(Build.VERSION.SDK_INT<21){
            Log.e(TAG,"手机版本太低,不支持共享转场动画");
            return false;
        }else {
            view.setTransitionName(share);//最重要的一句话  设置共享名称 不然没效果
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if (mode) {
                ((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
            } else {
                ((BitmapDrawable) view.getBackground()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
            }

            Intent intent = new Intent();
            intent.setClass(context, className);
            intent.putExtra(className.getName(), share);
            intent.putExtra("mode", mode);
            intent.putExtra(share, stream.toByteArray());
            ActivityOptions options;

            try {
                options = ActivityOptions.makeSceneTransitionAnimation(context, view, share);
            } catch (NullPointerException e) {
                Log.e("Transition", "空指针异常");
                return false;
            }

            if (options == null) {
                Log.e("Transition", "Options为空,请检查数据的准确性");
                return false;
            } else {
                context.startActivity(intent, options.toBundle());
                return true;
            }
        }
    }

    /**
     * 目标activity接收共享元素并执行动画
     * @param context 接收共享元素的activity
     * @param view 共享视图
     * @return 结果 true:成功 false:出错,请检查
     */
    public static boolean receiveTransition(Activity context,View view){
        if(Build.VERSION.SDK_INT<21){
            Log.e(TAG, "手机版本太低,不支持共享转场动画");
            return false;
        }else {

            Bundle bundle = context.getIntent().getExtras();
            String share = bundle.getString(context.getClass().getName());
            byte[] byteArray = bundle.getByteArray(share);
            view.setTransitionName(share);//最重要的一句话  设置共享名称 不然没效果
            if (byteArray != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                if (bundle.getBoolean("mode")) {
                    ((ImageView) view).setImageBitmap(bitmap);
                } else {
                    view.setBackground(new BitmapDrawable(context.getResources(), bitmap));
                }

                return true;
            } else {
                Log.e("Transition", "share为空,请检查共享数据的准确性");
                return false;
            }
        }
    }
}
