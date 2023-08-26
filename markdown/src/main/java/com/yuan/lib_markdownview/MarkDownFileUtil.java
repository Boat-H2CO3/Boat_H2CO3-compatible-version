package com.yuan.lib_markdownview;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * create by water.yuan
 * on 2020-10-19
 */
public class MarkDownFileUtil {

    public static String getString(Context context) throws IOException {
        return getString(Objects.requireNonNull(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)).getParent(), "test.md");
    }

    public static String getString(String parentPath, String fileName) throws UnsupportedEncodingException {
        File mdFile = new File(parentPath, fileName);
        return getString(mdFile);
    }

    public static String getString(File mdFile) throws UnsupportedEncodingException {
        ByteBuffer stream = ByteBuffer.create(mdFile);

        int length = 6 * 1024;
        length = Math.min(Objects.requireNonNull(stream).size(), length);
        String charset = CharsetUtils.getCharset(stream.getData(), length, "utf-8");

        String str;
        str = new String(stream.getData(), 0, stream.size(), charset);

        return str;
    }
}
