package farkas.tdk.transition;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import farkas.tdk.transition.animation.Transition;

/**
 * @author tangdikun
 * Created by tangdikun on 16/1/12.
 */
public class NextActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        ImageView imageView = (ImageView) findViewById(R.id.image);

        if(!Transition.receiveTransition(this,imageView)){
            Bundle bundle = getIntent().getExtras();
            byte[] byteArray = bundle.getByteArray(this.getClass().getName());
            if(byteArray!=null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                imageView.setImageBitmap(bitmap);
            }else{
                Toast.makeText(this,"载入图片出错",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(Build.VERSION.SDK_INT>=21)
        finishAfterTransition();
    }
}