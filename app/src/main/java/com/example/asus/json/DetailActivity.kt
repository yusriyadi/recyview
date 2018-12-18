package com.example.asus.json

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val title = intent.extras.getString("title")
        val deskrip = intent.extras.getString("deskripsi")
        val id_mv = intent.extras.getString("id_movie")
        val gambar = intent.extras.getString("path_gambar")



       // Toast.makeText(this@DetailActivity, Upil.BASE_URL+gambar, 1).show()

        txt_title.text = title
        txt_deskripsi.text = deskrip


        Picasso.get()
                .load(Upil.BASE_URL+gambar)
                .centerCrop().fit()
                .into(iv_gambar)

btn_trailer.setOnClickListener {
    val intent = Intent(this,ListTrailerActivity::class.java)
    intent.putExtra("id",id_mv)
    startActivity(intent)
}



    }



}
