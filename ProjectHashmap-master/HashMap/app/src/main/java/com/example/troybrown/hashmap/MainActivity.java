package com.example.troybrown.hashmap;

import android.content.Intent;
import android.database.Cursor;
import android.drm.DrmStore;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import static android.R.attr.fragment;
import static com.example.troybrown.hashmap.R.layout.activity_fragment;

public class MainActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener{

    private final static String POST_FEED_FRAGMENT_TAG = "test_list";
    private final static String FAB_FRAGMENT_TAG = "fab";
    private final static String TAB_FRAGMENT_TAG = "tab";
    private final static String ABOUT_FRAGMENT_TAG = "about";
    private final static String SELECTED_TAG = "selected_index";
    private final static int POST_FEED = 0;
    private final static int FAB = 1;
    private final static int TAB = 2;
    private final static int ABOUT = 3;
    static final int ADD_MARKER_REQUEST = 369;

    private static int selectedIndex;

    private Button mShow;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    private String[] myDataset = {"Clean my room",
            "Water the plants",
            "Get car washed",
            "Get my dry cleaning"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        selectedIndex = TAB;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new TabFragment(),TAB_FRAGMENT_TAG).commit();

        //selectedIndex = POST_FEED;
        //getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
        //new TestListFragment(),POST_FEED_FRAGMENT_TAG).commit();



        /*mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);//findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);*/


        /*Intent i = new Intent(getApplicationContext(),PostListActivity.class);
        startActivity(i);*/

        /*mShow = (Button) findViewById(R.id.btn_show);
        mShow.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(),PostListActivity.class);
                startActivity(i);
            }
        });*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_action_addmapmarker);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AddPostActivity.class);
                startActivityForResult(i, ADD_MARKER_REQUEST);
                Snackbar.make(view, "Post Added!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_postfeed) {
            // Handle the camera action
        } else if (id == R.id.nav_mypost) {
            selectedIndex = POST_FEED;
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    new TestListFragment(),POST_FEED_FRAGMENT_TAG).commit();

        } else if (id == R.id.nav_example) {
            Intent i = new Intent(getApplicationContext(),PostListActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static class PlaceholderFragment extends Fragment{
        private static final String ARG_SECTION_NUMBER = "section_number";


        public PlaceholderFragment(){

        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            /*FragmentManager fm = new FragmentManager() {
                @Override
                public FragmentTransaction beginTransaction() {
                    return null;
                }

                @Override
                public boolean executePendingTransactions() {
                    return false;
                }

                @Override
                public Fragment findFragmentById(@IdRes int id) {
                    return null;
                }

                @Override
                public Fragment findFragmentByTag(String tag) {
                    return null;
                }

                @Override
                public void popBackStack() {

                }

                @Override
                public boolean popBackStackImmediate() {
                    return false;
                }

                @Override
                public void popBackStack(String name, int flags) {

                }

                @Override
                public boolean popBackStackImmediate(String name, int flags) {
                    return false;
                }

                @Override
                public void popBackStack(int id, int flags) {

                }

                @Override
                public boolean popBackStackImmediate(int id, int flags) {
                    return false;
                }

                @Override
                public int getBackStackEntryCount() {
                    return 0;
                }

                @Override
                public BackStackEntry getBackStackEntryAt(int index) {
                    return null;
                }

                @Override
                public void addOnBackStackChangedListener(OnBackStackChangedListener listener) {

                }

                @Override
                public void removeOnBackStackChangedListener(OnBackStackChangedListener listener) {

                }

                @Override
                public void putFragment(Bundle bundle, String key, Fragment fragment) {

                }

                @Override
                public Fragment getFragment(Bundle bundle, String key) {
                    return null;
                }

                @Override
                public List<Fragment> getFragments() {
                    return null;
                }

                @Override
                public SavedState saveFragmentInstanceState(Fragment f) {
                    return null;
                }

                @Override
                public boolean isDestroyed() {
                    return false;
                }

                @Override
                public void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks cb, boolean recursive) {

                }

                @Override
                public void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks cb) {

                }

                @Override
                public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {

                }
            };
            Fragment fragment = fm.findFragmentById(R.id.fragment_container);
            if(fragment == null){
                fragment = PostListActivity.createFragment();

            }*/


            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

            ///View rootView = inflater.inflate(R.layout.activity_fragment, container, false);


            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            return rootView;
        }
    }

    /*
    public static class PostListActivity extends SingleFragmentActivity{
        private static final String TAG = "post_list_ACTIVITY";

        public Fragment createFragment () {
            Log.i(TAG, "--> createFragment");
            return new PostListFragment();
        }
        public Fragment populateListActivity() {
            return createFragment();
        }
    }*/



    public class SectionsPagerAdapter extends FragmentPagerAdapter{

        public SectionsPagerAdapter(FragmentManager fm) { super(fm);}

        @Override
        public Fragment getItem(int position) {

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount(){
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "TOP POSTS";
                case 1:
                    return "MAP VIEW";
            }
            return null;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ADD_MARKER_REQUEST){
            if(resultCode == RESULT_OK){
                //A marker has been added, so handle that here
                if(data.getStringExtra("title") != null && data.getStringExtra("description") != null){
                    String title = data.getStringExtra("title");
                    String description = data.getStringExtra("description");
                    boolean temporary = data.getBooleanExtra("temporary", true);
                    PostDatabase pd = PostDatabase.get(getBaseContext());
                    LatLng latLng = pd.getLocation();
                    Post np = new Post();
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(pd.getSize()+1);
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
                    np.setID(pd.getSize()+1);
                    np.setTitle(title);
                    np.setDescription(description);
                    np.setCoordinate(latLng);
                    np.setSolved(temporary);
                    pd.addPost(np);
                }
            }
        }
    }
}
