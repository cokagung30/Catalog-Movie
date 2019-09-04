package com.example.favoritemovie.utils

import android.net.Uri
import android.provider.BaseColumns


class DatabaseContract {

    companion object {
        private const val AUTHORITY = "com.example.submission3.provider"
        private const val SCHEME = "content"
    }

    class NoteColumns : BaseColumns {
        companion object {
            private val TABLE_NAME = "favorite"
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }

}