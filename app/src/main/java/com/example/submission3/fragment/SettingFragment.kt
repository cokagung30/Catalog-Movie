package com.example.submission3.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.submission3.R
import com.example.submission3.model.Movie
import com.example.submission3.service.AlarmService
import com.example.submission3.service.UpcomingService
import com.example.submission3.viewmodel.MovieViewModel
import java.text.SimpleDateFormat
import java.util.*

class SettingFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener,
    Preference.OnPreferenceClickListener {

    private val alarmService = AlarmService()
    private val upcomingService = UpcomingService()
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_settings, rootKey)
        val prefDaily = preferenceManager.findPreference(getString(R.string.reminder_daily))
        val prefUpcoming = preferenceManager.findPreference(getString(R.string.reminder_upcoming))
        val locale = preferenceManager.findPreference(getString(R.string.key_setting_locale))

        prefDaily.onPreferenceChangeListener = this
        prefUpcoming.onPreferenceChangeListener = this
        locale.onPreferenceClickListener = this

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        val key = preference?.key

        if (key == getString(R.string.reminder_daily)) {
            if (newValue as Boolean) {
                val message =
                    "Selamat Pagi, cek yuk film terbaru di ${getString(R.string.app_name)}"
                alarmService.setRepeatingAlarm(
                    activity,
                    AlarmService.TYPE_REPEATING,
                    "07:00",
                    message
                )
            } else {
                alarmService.cancelAlarm(activity)
            }

            Toast.makeText(
                activity,
                if (newValue) getString(R.string.label_daily_reminder_on) else getString(R.string.label_daily_reminder_off),
                Toast.LENGTH_SHORT
            ).show()
            return true
        }

        if (key == getString(R.string.reminder_upcoming)) {
            if (newValue as Boolean) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = Date()
                val currentDate = dateFormat.format(date)
                movieViewModel.upComingMovie(currentDate)
                movieViewModel.getMovie().observe(this, getMovie)

            } else {
                upcomingService.cancelAlarm(activity)
            }
            Toast.makeText(
                activity,
                if (newValue) getString(R.string.label_upcoming_reminder_on) else getString(R.string.label_upcoming_reminder_off),
                Toast.LENGTH_SHORT
            ).show()
            return true
        }


        return false
    }


    private val getMovie = Observer<List<Movie>> { movies ->
        val message =
            "Hari ini terdapat ${movies.size} terbaru, yuk cek ${getString(R.string.app_name)}"
        if (movies.isNotEmpty()) {
            upcomingService.setRepeatingUpcoming(
                activity,
                UpcomingService.TYPE_RELEASE,
                "08:00",
                message
            )
        }
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(intent)
        return true
    }
}