package net.joe.systemtime

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import java.text.DateFormat
import java.util.*

class MainActivity : Activity() {

    private lateinit var time: TextView
    private lateinit var timeDetails: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        time = findViewById(R.id.time)
        timeDetails = findViewById(R.id.timeDetails)


    }

    override fun onStart() {
        super.onStart()

        Thread {
            var td:String
            while (!Thread.interrupted()) {
                runOnUiThread {
                    val rawTime =  System.currentTimeMillis()

                    time.text = ("ms: " + rawTime.toString())

                    val l:Long = 1000*60*60*24
                    td="sec: " +  (rawTime / 1000)+"\n"+
                            "min: " +  (rawTime / (1000*60))+"\n"+
                    "hours: " +  (rawTime / (1000*60*60))+"\n"+
                    "days: " +  (rawTime / (1000*60*60*24))+"\n"+
                    "weeks: " +  (rawTime / (1000*60*60*24*7))+"\n"+
                    "years: " +  (rawTime / (l*365))+"\n"


                    val date = Date()

                    val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.LONG, /*Locale("RU","ua")*/Locale.getDefault()).format(date)
                    } else {
                        TODO("VERSION.SDK_INT < LOLLIPOP")
                    }

                    val since=DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.LONG, /*Locale("RU","ua")*/Locale.getDefault()).format(0)

                    td+= "SINCE: $since"
                    td+= "\n\nAND NOW: $current"

                    val newYear = Calendar.getInstance()
                    newYear.set(newYear.get(Calendar.YEAR)+1,0,0,0,0,0)
                    newYear.set(Calendar.MILLISECOND,0)
                    val toNY=newYear.time.time-rawTime

                    td+= "\n\nFor New Year ms: $toNY"
                    td+="\nsec: " +  (toNY / 1000)+"\n"+
                            "min: " +  (toNY / (1000*60))+"\n"+
                            "hours: " +  (toNY / (1000*60*60))+"\n"+
                            "days: " +  (toNY / (1000*60*60*24))+"\n"+
                            "weeks: " +  (toNY / (1000*60*60*24*7))+"\n"+
                            "years: " +  (toNY / (l*365))+"\n"
                    timeDetails.text=td
                }
                Thread.sleep(3)
            }
        }.start()
    }
}
