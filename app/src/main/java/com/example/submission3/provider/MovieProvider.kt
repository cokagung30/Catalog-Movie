package com.example.submission3.provider

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.example.submission3.sqlite.AppDatabase


@SuppressLint("Registered")
class MovieProvider : ContentProvider() {

    private val AUTHORITY = "com.example.submission3.provider"
    private val SCHEME = "content://"

    private val MOVIE = "$SCHEME$AUTHORITY/favorite"
    private val URI_MOVIE = Uri.parse(MOVIE)

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return p0
    }

    override fun query(
        uri: Uri,
        p1: Array<String>?,
        p2: String?,
        p3: Array<String>?,
        p4: String?
    ): Cursor? {
        var result: Cursor? = null
        if (URI_MOVIE == uri) {
            result = AppDatabase.getInstance().movieDao().fetchAllMovie()
            result.setNotificationUri(context.contentResolver, URI_MOVIE)
        }

        return result
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<String>?): Int {
        return 0
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<String>?): Int {
        return 0
    }

    override fun getType(p0: Uri): String? {
        return ""
    }

}