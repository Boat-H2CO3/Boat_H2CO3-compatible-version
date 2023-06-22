package cosine.boat;

import static cosine.boat.CHTools.h2co3Cfg;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class LauncherConfig extends HashMap<String, String> {


    public static void toFile(String filePath, LauncherConfig config) {
        try {
            //Log.i("Launcher", "Trying to save config to file.");
            Utils.writeFile(filePath, new Gson().toJson(config, LauncherConfig.class));

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }


    }

    public static LauncherConfig fromFile(String filePath) {
        try {
            return new Gson().fromJson(new String(Utils.readFile(filePath), StandardCharsets.UTF_8), LauncherConfig.class);

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String privateDir() {
        try {
            FileInputStream in = new FileInputStream(h2co3Cfg);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            return json.getString("allVerLoad");
        } catch (Exception e) {
            System.out.println(e);
        }
        return "false";
    }

    public static String loadgl() {
        try {
            FileInputStream in = new FileInputStream(h2co3Cfg);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            return json.getString("openGL");
        } catch (Exception e) {
            System.out.println(e);
        }
        return "libGL115.so";
    }

    public static String loadjava() {
        try {
            FileInputStream in = new FileInputStream(h2co3Cfg);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            return json.getString("java");
        } catch (Exception e) {
            System.out.println(e);
        }
        return "jre_8";
    }

    public static String api() {
        try {
            FileInputStream in = new FileInputStream(h2co3Cfg);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            return json.getString("LoginApi");
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Microsoft";
    }

    public String get(String key) {
        String value;

        if (super.get(key) == null) {
            value = "";
            Log.w("Boat", "Value required can not found: key=" + key);
        } else {
            value = super.get(key);
        }
        return value;
    }

}
