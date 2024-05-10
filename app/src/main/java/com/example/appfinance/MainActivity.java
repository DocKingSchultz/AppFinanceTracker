package com.example.appfinance;

import android.os.Bundle;

import com.example.appfinance.model.BankReportInfo;
import com.example.appfinance.model.Category;
import com.example.appfinance.utilities.DatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.appfinance.databinding.ActivityMainBinding;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<BankReportInfo> bankReports;
    private List<Category> categories;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        bankReports = new ArrayList<>();
        categories = new ArrayList<>();

        Stetho.initializeWithDefaults(this);
    }
    public List<BankReportInfo> getBankReports() {
        return bankReports;
    }
    public void addBankReport(BankReportInfo bi)
    {
        this.bankReports.add(bi);
    }
    public void addCategory(Category c)
    {
        this.categories.add(c);
    }
    public void setBankReports(List<BankReportInfo> bankReports) {
        this.bankReports = bankReports;
    }
    public List<Category> getCategories() {
        return categories;
    }
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}