package com.tite.ct60a2411_harjoitustyo;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class ObjectSaveUtils {
    public static void saveObject(Object object, String filename) {
        try {
            ObjectOutput output = new ObjectOutputStream(MainActivity.context.openFileOutput(filename, Context.MODE_PRIVATE));
            output.writeObject(object);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readObject(String filename) {
        Object object;
        try {
            ObjectInput input = new ObjectInputStream(MainActivity.context.openFileInput(filename));
            object = input.readObject();
            input.close();
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }
}
