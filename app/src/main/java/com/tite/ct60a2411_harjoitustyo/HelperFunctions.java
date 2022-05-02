package com.tite.ct60a2411_harjoitustyo;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class HelperFunctions {

    // Saving functions
    public static void saveObject(Object object, String filename) {
        try {
            ObjectOutput output = new ObjectOutputStream(MainActivity.getContext().openFileOutput(filename, Context.MODE_PRIVATE));
            output.writeObject(object);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readObject(String filename) {
        Object object;
        try {
            ObjectInput input = new ObjectInputStream(MainActivity.getContext().openFileInput(filename));
            object = input.readObject();
            input.close();
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Setting functions
    public static void setLanguage(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public static void setFontSize(Context context, int size) {
        switch (size) {
            case 0:
                context.setTheme(R.style.FontSmall);
                break;
            case 1:
                context.setTheme(R.style.FontNormal);
                break;
            case 2:
                context.setTheme(R.style.FontLarge);
                break;
            default:
                context.setTheme(R.style.FontNormal);
        }
    }


    // Image loading function
    public static Drawable LoadImageFromUrl(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(is, null);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
