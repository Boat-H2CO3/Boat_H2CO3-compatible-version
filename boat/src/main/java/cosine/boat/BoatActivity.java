package cosine.boat;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.Vector;

import cosine.boat.function.BoatCallback;
import cosine.boat.function.BoatLaunchCallback;

public class BoatActivity extends AppCompatActivity implements TextureView.SurfaceTextureListener {

	public TextureView mainTextureView;
	public BoatCallback boatCallback;
	public float scaleFactor = 1.0F;

	public Timer timer;
	public RelativeLayout base;
	public int initialX;
	public int initialY;
	public int baseX;
	public int baseY;

	int output = 0;

	public void init(){
		nOnCreate();
		timer = new Timer();
		mainTextureView = findViewById(R.id.main_surface);
		mainTextureView.setSurfaceTextureListener(this);
	}
	
	public static native void setBoatNativeWindow(Surface surface);

	public native void nOnCreate();
	
	static {
		System.loadLibrary("boat");
	}
	
	public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
		System.out.println("SurfaceTexture is available!");
		boatCallback.onSurfaceTextureAvailable(surface,width,height);
	}

	@Override
	public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {
		boatCallback.onSurfaceTextureSizeChanged(surface,width,height);
	}

	@Override
	public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
		return false;
	}

	@Override
	public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {
		if (output == 1) {
			boatCallback.onPicOutput();
			output++;
		}
		if (output < 1) {
			output++;
		}
	}

	public void startGame(final String javaPath,final String home,final boolean highVersion,final Vector<String> args,String renderer,String gameDir){
		Handler handler = new Handler();
		new Thread(() -> LoadMe.launchMinecraft(handler, BoatActivity.this, javaPath, home, highVersion, args, renderer, gameDir, new BoatLaunchCallback() {
			@Override
			public void onStart() {
				boatCallback.onStart();
			}

			@Override
			public void onError(Exception e) {
				boatCallback.onError(e);
			}
		})).start();
	}

	public void setCursorMode(int mode) {
		boatCallback.onCursorModeChange(mode);
	}

	public static void onExit(Context ctx, int code) {
		((BoatActivity) ctx).boatCallback.onExit(code);
	}

	public void setBoatCallback(BoatCallback callback) {
		this.boatCallback = callback;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			getWindow().getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		if (mainTextureView != null && mainTextureView.getSurfaceTexture() != null) {
			mainTextureView.post(() -> {
				boatCallback.onSurfaceTextureSizeChanged(mainTextureView.getSurfaceTexture(),mainTextureView.getWidth(),mainTextureView.getHeight());
			});
		}
	}
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
}



