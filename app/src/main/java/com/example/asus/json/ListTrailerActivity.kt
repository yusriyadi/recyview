package com.example.asus.json

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.asus.json.data.remote.Trailer
import com.example.asus.json.utils.FeedbackDialog
import com.google.android.youtube.player.YouTubeStandalonePlayer
import kotlinx.android.synthetic.main.activity_list_trailer.*
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.contentView
import org.jetbrains.anko.ctx


class ListTrailerActivity : AppCompatActivity() {

    lateinit var trailers: List<Trailer.Result>
    lateinit var loading: FeedbackDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_trailer)

        val id = intent.extras.getString("id")

        showList(id.toString())
        recyclerTrailer.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

    }

    fun showList(id_movie: String) {

        load(1)
        val baseUrl = "https://api.themoviedb.org/3/movie/$id_movie/videos?api_key=add93344f04b57906509751217e8d180&language=en-US"
        AndroidNetworking.get(baseUrl)
                .setTag(this)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Trailer.Response::class.java,
                        object : ParsedRequestListener<Trailer.Response> {
                            override fun onResponse(response: Trailer.Response?) {
                                if (response != null) {
                                    load(0)
                                    trailers = response.results


                                    recyclerTrailer.adapter = ListTrailer_adapter(trailers, object : ListTrailer_adapter.onTrailerListener {
                                        override fun onClick(trailer: Trailer.Result) {

                                            load(1)
                                            val intent = YouTubeStandalonePlayer.createVideoIntent(this@ListTrailerActivity, "AIzaSyBpadENLE69eEX4Llvw9Dz_c421buhi9N8", trailer.key, 0, true, false)
                                            startActivity(intent)

                                        }

                                    })


                                }
                            }

                            override fun onError(anError: ANError?) {
                                Log.e("error", anError?.message)
                            }


                        })
    }

    fun load(stat: Int) {

        if (stat == 1) {
            contentView?.let {
                loading = FeedbackDialog(AnkoContext.create(ctx, it))
            }
        } else if (stat == 0) {
            loading.dialog.dismiss()
        }
    }
}
