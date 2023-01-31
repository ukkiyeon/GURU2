package com.example.guru2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBManager (context: Context,
                 name: String?,
                 factory: SQLiteDatabase.CursorFactory?,
                 version: Int
): SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE plogging (sec INTEGER, milli INTEGER, distance INTEGER)")
        db!!.execSQL("INSERT INTO plogging VALUES('"+0+"','"+0+"','"+0+"');")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS plogging")
        onCreate(db)
    }
}