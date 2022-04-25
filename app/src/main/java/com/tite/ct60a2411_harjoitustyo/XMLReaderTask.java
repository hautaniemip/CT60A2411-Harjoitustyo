package com.tite.ct60a2411_harjoitustyo;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.function.Consumer;


// TODO: Add comments to me
public class XMLReaderTask extends AsyncTask<String[], Void, ArrayList<String[]>> {
    private final MainActivity activity;
    private final String url;
    private final String[] tags;
    private final String mainTag;
    private XmlPullParserFactory parserFactory;
    private ProgressDialog progressDialog;
    private Consumer<ArrayList<String[]>> callback;

    // XMLReaderTask parameters
    // activity:    reference MainActivity for showing ProgressDialog
    // url:         URL address for wanted XML file
    // mainTag:     the base which is used to spilt XML into multiple objects
    // tags:        list of tags whose value to get from inside of mainTag
    public XMLReaderTask(MainActivity activity, String url, String mainTag, String[] tags) {
        this.activity = activity;
        this.url = url;
        this.mainTag = mainTag;
        this.tags = tags;
    }

    public void setCallback(Consumer<ArrayList<String[]>> callback) {
        this.callback = callback;
    }

    @Override
    // Setups and opens progress dialog
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Getting data from XML");
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    protected ArrayList<String[]> doInBackground(String[]... strings) {
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            InputStream stream = connection.getInputStream();

            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            ArrayList<String[]> result = parseXML(parser);
            stream.close();

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String[]> result) {
        progressDialog.dismiss();
        this.callback.accept(result);
    }

    public ArrayList<String[]> parseXML(XmlPullParser parser) {
        int event;
        String text = null;
        String[] items = new String[tags.length];
        ArrayList<String[]> result = new ArrayList<>();

        try {
            event = parser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (name.equals(mainTag)) {
                            result.add(items);
                            items = new String[tags.length];
                        } else {

                            for (int i = 0; i < tags.length; i++) {
                                if (name.equals(tags[i]))
                                    items[i] = text;
                            }
                        }
                        break;
                }
                event = parser.next();
            }

            return result;
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
