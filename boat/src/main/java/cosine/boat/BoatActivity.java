package cosine.boat;

import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Surface;
import android.view.TextureView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.koishi.h2co3.mclauncher.gamecontroller.codes.BoatKeycodes;

import java.util.Timer;

//import com.koishi.launcher.h2o2.MainActivity;


public class BoatActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

    static {
        System.loadLibrary("boat");

    }

    public Timer timer;
    public RelativeLayout base;
    public TextureView mainTextureView;
    public MyHandler mHandler;
    public int initialX;
    public int initialY;
    public int baseX;
    public int baseY;

    public static native void setBoatNativeWindow(Surface surface);

    public static void sendKeyPress(int keyCode, int modifiers, boolean status) {
        //sendKeyPress(keyCode, 0, modifiers, status);
        BoatInput.setKey(keyCode, 0, status);
        System.out.print(keyCode);
    }

    public static void sendKeyPress(int keyCode, char keyChar, int scancode, int modifiers, boolean status) {
        BoatInput.setKey(keyCode, keyChar, status);
        System.out.print(keyCode);
    }

    public static void sendKeyPress(int keyCode) {
        BoatInput.setKey(keyCode, 0, true);
        BoatInput.setKey(keyCode, 0, false);
        System.out.print(keyCode);
    }

    public static void sendMouseButton(int button, boolean status) {
        BoatInput.setMouseButton(button, status);
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {

        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        //mainSurfaceView = new SurfaceView(this);
        //mainSurfaceView.getHolder().addCallback(this);
        timer = new Timer();
    }

    @Override
    protected void onPause() {
        // TODO: Implement this method
        super.onPause();

        //popupWindow.dismiss();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO: Implement this method
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onBackPressed() {
        BoatInput.setKey(BoatKeycodes.BOAT_KEYBOARD_Escape, 0, true);
        // TODO: Implement this method
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        // TODO: Implement this method
        System.out.println("SurfaceTexture is available!");
        BoatActivity.setBoatNativeWindow(new Surface(surface));

        new Thread() {
            @Override
            public void run() {

                LauncherConfig config = LauncherConfig.fromFile(getIntent().getExtras().getString("config"));
                LoadMe.exec(config);
                Message msg = new Message();
                msg.what = -1;
                mHandler.sendMessage(msg);
            }
        }.start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture p1, int p2, int p3) {
        // TODO: Implement this method
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture p1) {
        // TODO: Implement this method
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture p1) {
        // TODO: Implement this method
    }

    public void setCursorMode(int mode) {

        Message msg = new Message();
        msg.what = mode;
        mHandler.sendMessage(msg);
    }

    public void setCursorPos(int x, int y) {
    }

    @SuppressLint("HandlerLeak")
    private static class MyHandler extends Handler {
    }


}



