package com.example.asus.json

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_depan.*
import com.google.android.youtube.player.YouTubeStandalonePlayer



class DepanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_depan)



    Picasso.get().
            load("https://image.tmdb.org/t/p/w500//VuukZLgaCrho2Ar8Scl9HtV3yD.jpg")
            .centerCrop()
            .fit().into(iv_banner)

    btn_pop.setOnClickListener {

        val intent = Intent(this@DepanActivity,MainActivity::class.java)
        intent.putExtra("pop","1")
        startActivity(intent)
        //https://api.themoviedb.org/3/discover/movie?api_key=add93344f04b57906509751217e8d180&language=en-US&sort_by=popularity.asc&include_adult=true&include_video=truepage=2
    }
        btn_play.setOnClickListener{
            val intent=Intent(this@DepanActivity,MainActivity::class.java)
            intent.putExtra("pop","2")
            startActivity(intent)

        }



        btn_cari.setOnClickListener {
            val intent=Intent(this@DepanActivity,MainActivity::class.java)
            intent.putExtra("pop","3")
            intent.putExtra("keyword",txt_cari.text.toString())
            startActivity(intent)

        }


    }


}
