package com.example.sistemaprestamos;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.sistemaprestamos.ui.HomeFragment;
import com.example.sistemaprestamos.ui.InventoryFragment;
import com.example.sistemaprestamos.ui.LoansFragment;
import com.example.sistemaprestamos.ui.ReportsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);

        if (savedInstanceState == null) {
            switchTo(new HomeFragment());
            bottomNav.setSelectedItemId(R.id.nav_home);
        }

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                switchTo(new HomeFragment());
                return true;
            } else if (id == R.id.nav_loans) {
                switchTo(new LoansFragment());
                return true;
            } else if (id == R.id.nav_inventory) {
                switchTo(new InventoryFragment());
                return true;
            } else if (id == R.id.nav_reports) {
                switchTo(new ReportsFragment());
                return true;
            }

            return false;
        });
    }

    private void switchTo(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
