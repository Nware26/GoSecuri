package teamfrite.com.gosecuri

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import teamfrite.com.gosecuri.dao.Dao

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Dao().test()
        //Dao().add()
    }
}
