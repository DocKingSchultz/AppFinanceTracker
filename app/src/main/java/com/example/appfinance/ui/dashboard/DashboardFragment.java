package com.example.appfinance.ui.dashboard;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.activity.result.ActivityResultLauncher;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfinance.MainActivity;
import com.example.appfinance.R;

import java.util.ArrayList;
import java.util.List;

import com.example.appfinance.adapter.BankReportAdapter;
import com.example.appfinance.mocks.MockBankReportInfo;
import com.example.appfinance.model.BankReportInfo;
import com.example.appfinance.utilities.SMSReader;

public class DashboardFragment extends Fragment {

    private static final int SMS_PERMISSION_REQUEST_CODE = 100;
    private TextView textView;
    private DashboardViewModel dashboardViewModel;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    private List<BankReportInfo> extractedMessages = new ArrayList<>();
    private RecyclerView recyclerView;
    private BankReportAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = root.findViewById(R.id.recycler_view);
        adapter = new BankReportAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Create mock data
        List<BankReportInfo> mockData = MockBankReportInfo.getMockBankReportInfoList(requireContext());


        // Initialize ViewModel
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        // Set mock data to ViewModel
        dashboardViewModel.setBankReportInfoList(mockData);

        dashboardViewModel.getBankReportInfoList().observe(getViewLifecycleOwner(), bankReportInfos -> {
            // Update RecyclerView adapter with new data
            SMSReader smsReader = new SMSReader(requireContext(), requestPermissionLauncher);
            List<BankReportInfo> reports = smsReader.readNLBKBMessages();
            adapter.setBankReportInfoList(reports);
        });
        return root;
    }




}
