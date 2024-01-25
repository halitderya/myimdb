package com.example.myimdb;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements DeleteAll.DeletionListener {
    DrawerLayout drawerlayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerlayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerlayout, R.string.open, R.string.close);
        drawerlayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MainFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.navhome);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.navhelp) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new HelpFragment())
                            .commit();
                } else if (id == R.id.navhome) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new MainFragment())
                            .commit();
                } else if (id == R.id.navabout) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new AboutFragment())
                            .commit();
                } else if (id == R.id.navadd) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new AddRecordFragment())
                            .commit();

                } else if (id == R.id.navedit) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new EditFragment())
                            .commit();

                } else if (id == R.id.navdelete) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new DeleteFragment())
                            .commit();
                } else if (id == R.id.navdeleteall) {
                    new DeleteAll(MainActivity.this).deleteAllRecords(MainActivity.this);
                }

                drawerlayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onDeletionComplete() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MainFragment())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerlayout.isDrawerOpen(navigationView)) {
            drawerlayout.closeDrawer(navigationView);
        } else {
            super.onBackPressed();
        }
    }
}
