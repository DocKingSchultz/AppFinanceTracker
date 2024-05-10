package com.example.appfinance.adapter;

import com.example.appfinance.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.appfinance.model.BankReportInfo;

public class BankReportAdapter extends RecyclerView.Adapter<BankReportAdapter.ViewHolder> {

    private List<BankReportInfo> bankReportInfoList;

    public BankReportAdapter(List<BankReportInfo> bankReportInfoList) {
        this.bankReportInfoList = bankReportInfoList;
    }
    public BankReportAdapter() {
        this.bankReportInfoList = new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_report_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BankReportInfo bankReportInfo = bankReportInfoList.get(position);
        holder.textDescr.setText("Date: " + bankReportInfo.getDescription());
        holder.textAmount.setText("Amount: " + bankReportInfo.getAmount());
        holder.textId.setText("Date: " + bankReportInfo.getAccountID());
        holder.textDate.setText("Date: " + bankReportInfo.getDate());
        // Bind other attributes here if needed
    }

    @Override
    public int getItemCount() {
        return bankReportInfoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textAmount;
        TextView textDate;
        TextView textId;
        TextView textDescr;
        // Add other TextViews here as needed

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textAmount = itemView.findViewById(R.id.text_amount);
            textDate = itemView.findViewById(R.id.text_date);
            textId = itemView.findViewById(R.id.text_id);
            textDescr = itemView.findViewById(R.id.text_descr);
            // Initialize other TextViews here as needed
        }
    }

    public void setBankReportInfoList(List<BankReportInfo> bankReportInfoList) {
        this.bankReportInfoList = bankReportInfoList;
        notifyDataSetChanged(); // Notify the adapter that the data set has changed
    }
}
