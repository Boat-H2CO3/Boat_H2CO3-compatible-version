package org.koishi.launcher.h2co3.launcher.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;

import com.yuan.lib_markdownview.MarkDownFileUtil;
import com.yuan.lib_markdownview.MarkdownWebView;

import org.koishi.launcher.h2co3.R;
import org.koishi.launcher.h2co3.component.H2CO3Activity;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

import cosine.boat.utils.CHTools;

public class InstructionActivity extends H2CO3Activity {

    public LinearLayout layout;
    public MarkdownWebView markdownWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        CHTools.loadPaths(this);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        layout = findViewById(R.id.markdown_layout);
        markdownWebView = findViewById(R.id.markdown_view);

        try {
            markdownWebView.setText(MarkDownFileUtil.getString(CHTools.PUBLIC_FILE_PATH + "/markdown", "info.md"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void startBili(View v) {
        CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
        intent.launchUrl(InstructionActivity.this, Uri.parse("https://114514.cn\n"));
    }

}