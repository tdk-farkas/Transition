package farkas.tdk.transition;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import farkas.tdk.transition.animation.Transition;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final ImageView imageView = (ImageView)findViewById(R.id.image);
        imageView.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.mipmap.pic_10));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Transition.StartTransition(MainActivity.this, NextActivity.class, imageView, "image", true)) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    ((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, NextActivity.class);
                    intent.putExtra(NextActivity.class.getName(), stream.toByteArray());
                    startActivity(intent);
                }
            }
        });
    }
}