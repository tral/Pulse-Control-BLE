/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.perm.trubnikov.pulsecontrol;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * For a given BLE device, this Activity provides the user interface to connect, display data,
 * and display GATT services and characteristics supported by the device.  The Activity
 * communicates with {@code BluetoothLeService}, which in turn interacts with the
 * Bluetooth LE API.
 */

class DBHelper extends SQLiteOpenHelper {

    private String defSmsMsg;

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, "rupermtrubnikovpulsecontrolDB", null, 1);

    }

    public long getSettingsParamInt(String param) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query("settings", null, "param = '" + param + "'", null,
                null, null, null);

        if (c.moveToFirst()) {
            int idx = c.getColumnIndex("val_int");
            Long r = c.getLong(idx);
            return r;
        }

        return 0;
    }

    public void setSettingsParamInt(String param, long val) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("val_int", val);
        db.update("settings", cv, "param = ?", new String[] { param });
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        ContentValues cv = new ContentValues();

        db.execSQL("create table settings ("
                + "_id integer primary key autoincrement," + "param text,"
                + "val_txt text," + "val_int integer" + ");");

        cv.clear();
        cv.put("param", "low_p");
        cv.put("val_txt", "");
        cv.put("val_int", 60);
        db.insert("settings", null, cv);

        cv.clear();
        cv.put("param", "high_p");
        cv.put("val_txt", "");
        cv.put("val_int", 100);
        db.insert("settings", null, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}