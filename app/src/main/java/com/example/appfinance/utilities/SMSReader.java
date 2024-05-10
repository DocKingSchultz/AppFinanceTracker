package com.example.appfinance.utilities;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

import com.example.appfinance.model.BankReportInfo;

import java.util.ArrayList;
import java.util.List;

public class SMSReader {

    private final Context context;
    private final ActivityResultLauncher<String> requestPermissionLauncher;

    public SMSReader(Context context, ActivityResultLauncher<String> requestPermissionLauncher) {
        this.context = context;
        this.requestPermissionLauncher = requestPermissionLauncher;
    }

    public boolean checkSMSPermission() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestSMSPermission() {
        requestPermissionLauncher.launch(Manifest.permission.READ_SMS);
    }

    public List<BankReportInfo> readNLBKBMessages() {
        List<BankReportInfo> nlbkbMessages = new ArrayList<>();
        if (!checkSMSPermission()) {
            Log.e("SMSReader", "READ_SMS permission not granted");
            return nlbkbMessages;
        }

        Uri uriSMS = Telephony.Sms.CONTENT_URI;
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(uriSMS, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int senderIndex = cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS);
            int bodyIndex = cursor.getColumnIndexOrThrow(Telephony.Sms.BODY);
            do {
                String sender = cursor.getString(senderIndex);
                if (!sender.equals("NLBKB")) {
                    continue; // Skip messages not from NLBKB sender
                }
                String message = cursor.getString(bodyIndex);
                BankReportInfo bankReportInfo = new BankReportInfo(message,context);
                nlbkbMessages.add(bankReportInfo);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Log.e("SMSReader", "No SMS messages found.");
        }
        return nlbkbMessages;
    }
}
