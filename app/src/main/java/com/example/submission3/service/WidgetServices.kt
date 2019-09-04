package com.example.submission3.service

import android.content.Intent
import android.widget.RemoteViewsService
import com.example.submission3.widget.StackRemoteViewFactory

class WidgetServices : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StackRemoteViewFactory(applicationContext)
    }

}