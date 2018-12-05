package project.mobile.kau.com.mobileswproject

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class ScheduleActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_main)

        val button : Button = findViewById(R.id.button)
        button.setOnClickListener {

            val intent = Intent(this,ScheduleSelectActivity::class.java)
            startActivity(intent)

        }
    }
}
