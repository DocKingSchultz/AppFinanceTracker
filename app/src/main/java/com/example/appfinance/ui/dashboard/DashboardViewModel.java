package com.example.appfinance.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import com.example.appfinance.model.BankReportInfo;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<List<BankReportInfo>> mBankReportInfoList;

    public DashboardViewModel() {
        mBankReportInfoList = new MutableLiveData<>();
    }

    public LiveData<List<BankReportInfo>> getBankReportInfoList() {
        return mBankReportInfoList;
    }

    public void setBankReportInfoList(List<BankReportInfo> bankReportInfoList) {
        mBankReportInfoList.setValue(bankReportInfoList);
    }
}