package org.koishi.launcher.h2co3.ui.version;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.koishi.launcher.h2co3.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VersionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class VersionFragment extends Fragment {

    public static VersionFragment newInstance(String param1, String param2) {
        VersionFragment fragment = new VersionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_versions, container, false);
    }
}