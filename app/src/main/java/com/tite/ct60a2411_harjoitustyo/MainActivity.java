package com.tite.ct60a2411_harjoitustyo;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static TheatreArea.AreaId areaId = TheatreArea.AreaId.STRAND;
    private static ArrayList<TheatreArea> areas = new ArrayList<>();
    private static String[] tags = {"ID", "dttmShowStart", "dttmShowEnd", "EventID", "Title", "OriginalTitle", "ProductionYear", "LengthInMinutes", "Rating", "TheatreID", "Theatre", "TheatreAuditorium"};
    private static String url;
    private static int areaIndex = 0;
    public static MainActivity context;

    private static MovieArchive movieArchive;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        movieArchive = MovieArchive.getInstance();
        movieArchive.printArchiveInfo();
        readAllAreas();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Read XML for every area sequentially
    public static void readAllAreas() {
        if (areaIndex == TheatreArea.AreaId.values().length)
            return;

        TheatreArea.AreaId areaId = TheatreArea.AreaId.values()[areaIndex];

        url = "https://www.finnkino.fi/xml/Schedule/?area=" + areaId.getId();
        XMLReaderTask reader = new XMLReaderTask(context, url, "Show", tags);
        reader.setCallback(MainActivity::dataCallback);
        reader.execute();

        areaIndex++;
        movieArchive.saveArchive();
    }

    // Callback function for XMLReader
    public static void dataCallback(ArrayList<String[]> result) {
        TheatreArea area = new TheatreArea(areaId.getId());
        for (String[] entry : result) {
            Movie movie = new Movie(entry);
            area.addMovieToTheatre(movie.getTheatreId(), movie);
            movieArchive.addMovie(movie);
        }

        areas.add(area);
        //area.printAreaInfo();

        readAllAreas();
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
        switch(menuItem.getItemId()) {
            case R.id.nav_home_fragment:
                fragmentClass = HomeFragment.class;
                break;
            case R.id.nav_archive_fragment:
                fragmentClass = ArchiveFragment.class;
                break;
            case R.id.nav_showing_fragment:
                fragmentClass = ShowingFragment.class;
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
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
}