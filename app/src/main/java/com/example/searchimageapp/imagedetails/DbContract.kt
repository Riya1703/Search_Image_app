package com.example.searchimageapp.imagedetails

import android.provider.BaseColumns

object DBContract {
    /**
     * Inner class that defines the table contents
     */
    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "images"
            val COLUMN_ID = "id"
            val COLUMN_COMMENT = "comment"
        }
    }
}