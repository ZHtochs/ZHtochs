package com.example.drawer;

import android.os.Bundle;
import android.view.Menu;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.github.zhtouchs.Utils.ZHLog;
import com.github.zhtouchs.activity.BaseActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private AppBarConfiguration mAppBarConfiguration;

    private DrawerLayout drawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.menu.activity_main_drawer)
                .setOpenableLayout(drawer)
                .build();
        navigationView.setNavigationItemSelectedListener(item -> {
            ZHLog.d(TAG, item.getItemId());
            return true;
        });
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
//        DrawerLayoutUtil.setDrawerEdgeSize(this, drawer, 0.3);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void onBackPressed() {
        ZHLog.d(TAG, "onBackPressed");
        if (drawer.isOpen()) {
            ZHLog.d(TAG, "drawer isOpen");
            drawer.close();
        } else {
            super.onBackPressed();
        }
    }
}