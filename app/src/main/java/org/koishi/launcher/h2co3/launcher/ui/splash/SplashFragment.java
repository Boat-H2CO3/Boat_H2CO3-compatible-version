package org.koishi.launcher.h2co3.launcher.ui.splash;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;

import org.koishi.launcher.h2co3.R;
import org.koishi.launcher.h2co3.component.H2CO3Fragment;
import org.koishi.launcher.h2co3.launcher.ui.SplashActivity;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import cosine.boat.utils.CHTools;
import cosine.boat.utils.FileUtils;
import cosine.boat.utils.LocaleUtils;
import cosine.boat.utils.RuntimeUtils;

public class SplashFragment extends H2CO3Fragment {

    boolean boat = false;
    boolean java8 = false;
    boolean java17 = false;
    boolean keyboard = false;

    private ProgressBar boatProgress;

    private ProgressBar java8Progress;
    private ProgressBar java17Progress;
    private ProgressBar keyboardProgress;
    private ImageView boatState;
    private ImageView java8State;

    private ImageView java17State;
    private ImageView keyboardState;
    private MaterialButton install;
    private boolean installing = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        boatProgress = findViewById(view, R.id.boat_progress);
        java8Progress = findViewById(view, R.id.java8_progress);
        java17Progress = findViewById(view, R.id.java17_progress);
        keyboardProgress = findViewById(view,R.id.keyboard_progress);

        boatState = findViewById(view, R.id.boat_state);
        java8State = findViewById(view, R.id.java8_state);
        java17State = findViewById(view, R.id.java17_state);
        keyboardState = findViewById(view, R.id.keyboard_state);

        initState();

        refreshDrawables();

        check();

        return view;

    }

    private void initState() {
        try {
            boat = RuntimeUtils.isLatest(CHTools.BOAT_LIBRARY_DIR, "/assets/app_runtime/boat");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            java8 = RuntimeUtils.isLatest(CHTools.JAVA_8_PATH, "/assets/app_runtime/jre_8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            java17 = RuntimeUtils.isLatest(CHTools.JAVA_17_PATH, "/assets/app_runtime/jre_17");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            keyboard = RuntimeUtils.isLatest(CHTools.CONTROLLER_DIR, "/assets/keyboards");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            install();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshDrawables() {
        requireContext();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable stateUpdate = requireContext().getDrawable(R.drawable.ic_baseline_update_24);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable stateDone = requireContext().getDrawable(R.drawable.ic_baseline_done_24);

        stateUpdate.setTint(Color.GRAY);
        stateDone.setTint(Color.GRAY);

        boatState.setBackgroundDrawable(boat ? stateDone : stateUpdate);
        java8State.setBackgroundDrawable(java8 ? stateDone : stateUpdate);
        java17State.setBackgroundDrawable(java17 ? stateDone : stateUpdate);
        keyboardState.setBackgroundDrawable(keyboard ? stateDone : stateUpdate);
    }

    private boolean isLatest() {
        return boat && java8 && java17 && keyboard ;
    }

    private void check() {
        if (isLatest()) {
            if (getActivity() != null) {
                ((SplashActivity) getActivity()).enterLauncher();
            }
        }
    }

    private void install() {
        if (installing)
            return;
        installing = true;
        if (!boat) {
            boatState.setVisibility(View.GONE);
            boatProgress.setVisibility(View.VISIBLE);
            new Thread(() -> {
                try {
                    RuntimeUtils.install(requireContext(), CHTools.BOAT_LIBRARY_DIR, "app_runtime/boat");
                    boat = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        boatState.setVisibility(View.VISIBLE);
                        boatProgress.setVisibility(View.GONE);
                        refreshDrawables();
                        check();
                    });
                }
            }).start();
        }
        if (!java8) {
            java8State.setVisibility(View.GONE);
            java8Progress.setVisibility(View.VISIBLE);
            new Thread(() -> {
                try {
                    RuntimeUtils.installJava(requireContext(), CHTools.JAVA_8_PATH, "app_runtime/jre_8");
                    java8 = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        java8State.setVisibility(View.VISIBLE);
                        java8Progress.setVisibility(View.GONE);
                        refreshDrawables();
                        check();
                    });
                }
            }).start();
        }
        if (!java17) {
            java17State.setVisibility(View.GONE);
            java17Progress.setVisibility(View.VISIBLE);
            new Thread(() -> {
                try {
                    RuntimeUtils.installJava(requireContext(), CHTools.JAVA_17_PATH, "app_runtime/jre_17");
                    if (!LocaleUtils.getSystemLocale().getDisplayName().equals(Locale.CHINA.getDisplayName())) {
                        FileUtils.writeText(new File(CHTools.JAVA_17_PATH + "/resolv.conf"), "nameserver 1.1.1.1\n" + "nameserver 1.0.0.1");
                    } else {
                        FileUtils.writeText(new File(CHTools.JAVA_17_PATH + "/resolv.conf"), "nameserver 8.8.8.8\n" + "nameserver 8.8.4.4");
                    }
                    java17 = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        java17State.setVisibility(View.VISIBLE);
                        java17Progress.setVisibility(View.GONE);
                        refreshDrawables();
                        check();
                    });
                }
            }).start();
        }
        if (!keyboard) {
            keyboardState.setVisibility(View.GONE);
            keyboardProgress.setVisibility(View.VISIBLE);
            new Thread(() -> {
                try {
                    RuntimeUtils.install(requireContext(), CHTools.CONTROLLER_DIR, "keyboards");
                    keyboard = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        keyboardState.setVisibility(View.VISIBLE);
                        keyboardProgress.setVisibility(View.GONE);
                        refreshDrawables();
                        check();
                    });
                }
            }).start();
        }
    }
}
