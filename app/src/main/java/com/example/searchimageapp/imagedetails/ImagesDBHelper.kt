package com.example.searchimageapp.imagedetails

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class ImagesDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun readComment(imageId: String?): String {
        var images:String = ""
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_ID + "='" + imageId + "'", null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return String.toString()
        }

        var id: String
        var comment: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_ID))
                comment = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_COMMENT))

                images = comment
                cursor.moveToNext()
            }
        }
        return images
    }

    fun delete(imageId: String?): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.UserEntry.COLUMN_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(imageId)

        db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)
        return true
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Images.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                        DBContract.UserEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
                        DBContract.UserEntry.COLUMN_COMMENT + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME
    }
}