package com.example.submission3.service

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.submission3.R
import com.example.submission3.activity.MainActivity
import java.util.*

class UpcomingService : BroadcastReceiver() {

    companion object {
        const val TYPE_RELEASE = "ReleaseTodayAlarm"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"
    }

    private val NOTIF_ID_RELEASE = 100


    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra(EXTRA_MESSAGE)
        val type = intent?.getStringExtra(EXTRA_TYPE)
        val title = context?.resources?.getString(R.string.app_name)

        if (type == TYPE_RELEASE)
            showAlarmNotification(context, title, message)
    }

    private fun showAlarmNotification(context: Context?, title: String?, message: String?) {
        val CHANNEL_ID = "Channel_2"
        val CHANNEL_NAME = "MovieUpcoming channel"

        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                NOTIF_ID_RELEASE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_movie_white_24dp)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarm)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        builder.setContentIntent(pendingIntent)
        builder.setAutoCancel(true)
        val notification = builder.build()
        notificationManager.notify(NOTIF_ID_RELEASE, notification)
    }

    fun setRepeatingUpcoming(
        context: Context?,
        type: String,
        time: String,
        message: String
    ) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, UpcomingService::class.java)

        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        val timeArray = time.split(":")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, timeArray[0].toInt())
        calendar.set(Calendar.MINUTE, timeArray[1].toInt())

        if (calendar.before(Calendar.getInstance())) calendar.add(Calendar.DATE, 1)

        val pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_RELEASE, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, UpcomingService::class.java)

        val pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID_RELEASE, intent, 0)
        alarmManager.cancel(pendingIntent)

    }

}