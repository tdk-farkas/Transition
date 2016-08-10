package farkas.tdk.transition.util;

import android.os.Build;
import android.util.Log;

/**
 * @author tangdikun
 * Created by tangdikun on 16/1/13.
 */
public class Util {
    final private static String TAG ="Util";
    public static int getAndroidSDKVersion() {
        Log.e(TAG,Build.VERSION.SDK_INT+"");
        return Build.VERSION.SDK_INT;
    }
}
