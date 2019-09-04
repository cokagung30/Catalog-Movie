package com.example.submission3.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.submission3.R
import com.example.submission3.fragment.SettingFragment
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        toolbar_setting_movie?.title = "Setting"
        setSupportActionBar(toolbar_setting_movie)
        val fragment = SettingFragment()
        supportFragmentManager.beginTransaction().replace(R.id.setting_content, fragment).commit()
        toolbar_setting_movie.setTitleTextColor(
            ContextCompat.getColor(
                applicationContext,
                R.color.white
            )
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_setting_movie.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }
}
