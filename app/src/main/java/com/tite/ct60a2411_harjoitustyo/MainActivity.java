package com.tite.ct60a2411_harjoitustyo;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String[] tags = {"ID", "dttmShowStart", "dttmShowEnd", "EventID", "Title", "OriginalTitle", "ProductionYear", "LengthInMinutes", "Rating", "TheatreID", "Theatre", "TheatreAuditorium", "EventLargeImagePortrait"};
    private static final TheatreArea.AreaId areaId = TheatreArea.AreaId.STRAND;
    private static int areaIndex = 0;
    private static MainActivity context;

    private static MovieArchive movieArchive;
    private SettingsManager settings;

    private Calendar date;
    private int dateOffset = 0;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    public static String[] getTags() {
        return tags;
    }

    public static MainActivity getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = this;


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);

        // Setup drawer view
        setupDrawerContent(nvDrawer);

        drawerToggle = setupDrawerToggle();

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        // Open home fragment as default
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContent, new HomeFragment()).commit();

        movieArchive = MovieArchive.getInstance();
        movieArchive.printArchiveInfo();

        settings = SettingsManager.getInstance();

        date = Calendar.getInstance();
        Toast.makeText(getApplicationContext(), getString(R.string.archive_updating) + "...", Toast.LENGTH_SHORT).show();
        updateArchive();
        setLanguage("fi");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Update movie archive for time length given in settings
    public void updateArchive() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        if (areaIndex == TheatreArea.AreaId.values().length) {
            date.add(Calendar.DATE, 1);
            dateOffset++;
            areaIndex = 0;
            if (dateOffset >= settings.getUpdateArchiveLength()) {
                Toast.makeText(getApplicationContext(), getString(R.string.archive_updated), Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getApplicationContext(), getString(R.string.archive_updating) + " " + dateOffset + "/" + settings.getUpdateArchiveLength(), Toast.LENGTH_SHORT).show();
        }

        TheatreArea.AreaId areaId = TheatreArea.AreaId.values()[areaIndex];

        String url = "https://www.finnkino.fi/xml/Schedule/?area=" + areaId.getId() + "&dt=" + format.format(date.getTime());
        XMLReaderTask reader = new XMLReaderTask(this, url, "Show", tags);
        reader.setCallback(this::dataCallback);
        reader.setShowDialog(false);
        reader.execute();
        System.out.println(url);

        areaIndex++;
        movieArchive.saveArchive();
    }

    // Callback function for XMLReader
    public void dataCallback(ArrayList<String[]> result) {
        if (result == null) {
            updateArchive();
            return;
        }
        TheatreArea area = new TheatreArea(areaId.getId());
        for (String[] entry : result) {
            Movie movie = new Movie(entry);
            area.addMovieToTheatre(movie.getTheatreId(), movie);
            movieArchive.addMovie(movie);
        }

        updateArchive();
    }

    public void setLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        nvDrawer.getMenu().clear();
        nvDrawer.inflateMenu(R.menu.drawer_view);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_home_fragment:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_archive_fragment:
                fragmentClass = ArchiveFragment.class;
                break;
            case R.id.nav_showing_fragment:
                fragmentClass = ShowingFragment.class;
                break;
            case R.id.nav_settings_fragment:
                fragmentClass = SettingsFragment.class;
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);

        // Set action bar title
        setTitle(menuItem.getTitle());
        if (menuItem.getItemId() == R.id.nav_home_fragment)
            setTitle(R.string.app_name);

        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }
}