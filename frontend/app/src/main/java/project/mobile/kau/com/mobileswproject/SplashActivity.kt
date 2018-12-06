package project.mobile.kau.com.mobileswproject

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var handler = Handler()

        handler.postDelayed({

            var intent = Intent (this, TimeTableActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
