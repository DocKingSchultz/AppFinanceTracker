package com.example.appfinance.utilities;
import com.example.appfinance.model.BankReportInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONConverter {
    public static void writeToJson(List<BankReportInfo> bankReports, String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(bankReports, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
