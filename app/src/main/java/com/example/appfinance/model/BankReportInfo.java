package com.example.appfinance.model;

import android.content.Context;

import com.example.appfinance.MainActivity;
import com.example.appfinance.utilities.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BankReportInfo {
    private int ID;
    private double amount;
    private Date date;
    private String accountID;
    private String description;

    private boolean isInflow = false;

    public BankReportInfo(String message, Context context) {
        // Extract amount
        try {
            String priceString = "Iznos: ";
            if(message.contains("Priliv"))priceString = "Priliv: ";
            else if(message.contains("Odliv"))
            {
                priceString = "Odliv: ";
            }
            String amountString = extractValue(message, priceString);
            if (amountString != null) {
                this.amount = parseAmount(amountString);
            }
            // Extract date
            String dateString = extractValue(message, "Datum: ");
            if (dateString != null) {
                if(dateString.contains(","))dateString = dateString.trim();
                this.date = parseDate(dateString);
            }

            // Extract account ID
            String accountIDString = extractID(message, "REF: ");
            if (accountIDString != null) {
                this.accountID = accountIDString;
            }


            if (message.contains("Priliv")) {
                this.isInflow = true;
                // Extract descr
                this.description = "Priliv + " + extractDescr( message);
            }
            else if(message.contains("Odliv"))
            {
                // Extract descr
                this.description = "Odliv + " + extractDescr( message);
            }
            else if(message.contains("Inicirali")){
                // Extract place
                this.description = "Specific transaction";
            }
            else {
                this.description = extractPlace(message);
            }

            DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
            dbHelper.addBankReport(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    private String extractValue(String message, String key) {
        int startIndex = message.indexOf(key);
        if (startIndex != -1) {
            startIndex += key.length();
            int endIndex = message.indexOf(" ", startIndex);
            if (endIndex != -1) {
                return message.substring(startIndex, endIndex);
            }
        }
        return null;
    }

    private String extractID(String message, String key) {
        int startIndex = message.indexOf(key);
        if (startIndex != -1) {
            startIndex += key.length();
            int endIndex = message.indexOf(" ", startIndex);
            if (endIndex != -1) {
                return message.substring(startIndex, endIndex);
            }
        }
        return null;
    }

    private String extractPlace(String message) {
        String key = "Mesto:";
        int startIndex = message.indexOf(key);
        if (startIndex != -1) {
            startIndex += key.length();
            return message.substring(startIndex).trim();
        }
        return null;
    }

    private String extractDescr(String message) {
        String key = "Opis:";
        int startIndex = message.indexOf(key);
        if (startIndex != -1) {
            startIndex += key.length();
            return message.substring(startIndex).trim();
        }
        return null;
    }

    private double parseAmount(String amountString) {
        if(!amountString.contains(","))return Double.parseDouble(amountString);
        return Double.parseDouble(amountString.substring(0, amountString.indexOf(",")).replaceAll("[^0-9]", ""));
    }

    private Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Getters and setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String place) {
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
