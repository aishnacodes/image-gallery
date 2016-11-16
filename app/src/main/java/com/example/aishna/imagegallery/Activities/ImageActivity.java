package com.example.aishna.imagegallery.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.aishna.imagegallery.DrawerItemCustomAdapter;
import com.example.aishna.imagegallery.R;
import com.example.aishna.imagegallery.fragments.ImageSearchFragment;
import com.example.aishna.imagegallery.fragments.LoginFragment;

import timber.log.Timber;

public class ImageActivity extends AppCompatActivity {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBar mActionBar;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        mActionBar = getSupportActionBar();
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, myToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        String[] drawerItem = new String[3];
        drawerItem[0] = getResources().getString(R.string.search);
        drawerItem[1] = getResources().getString(R.string.user);
        drawerItem[2] = getResources().getString(R.string.help);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setAdapter(adapter);

        mFragmentManager = getFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.content_frame, new ImageSearchFragment()).commit();
        mDrawerList.setItemChecked(0, true);
        mDrawerList.setSelection(0);
        mActionBar.setTitle(mNavigationDrawerItemTitles[0]);
    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new ImageSearchFragment();
                break;
            case 1:
                fragment = new LoginFragment();
                break;
            case 2:
                fragment = new ImageSearchFragment();
                break;
            default:
                fragment = new ImageSearchFragment();
                break;
        }
        if (fragment != null) {

            mFragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            mActionBar.setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Timber.e("Error in creating fragment");
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
