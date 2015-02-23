package com.shamanland.fonticon.example;

import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SoundEffectConstants;

public class MainActivity extends ActionBarActivity implements MenuListener {
    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.a_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                new Toolbar(MainActivity.this), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (state == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.menu, new MenuFragment())
                    .commit();

            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
    }

    @Override
    protected void onPostCreate(Bundle state) {
        super.onPostCreate(state);

        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            // override hardware menu key to open/close menu
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }

            mDrawerLayout.playSoundEffect(SoundEffectConstants.CLICK);
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onMenuClicked(int menuViewId, String menuText, boolean selectionChanged) {
        if (selectionChanged) {
            changeContent(menuViewId);
        } else {
            // user clicks already selected menu item
            // custom behavior can be implemented here
        }

        getSupportActionBar().setTitle(menuText);

        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    protected void changeContent(int menuViewId) {
        final Fragment fragment;

        switch (menuViewId) {
            case R.id.scaled:
                fragment = new ScaledIconsFragment();
                break;

            case R.id.glowing:
                fragment = new GlowingIconsFragment();
                break;

            case R.id.compound:
                fragment = new CompoundIconsFragment();
                break;

            default:
                throw new IllegalStateException();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
