package com.example.asus.json

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.asus.json.data.remote.Moviee
import com.example.asus.json.utils.FeedbackDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.contentView
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    var baseUrl: String? = null
    var jenis: String? = null
    lateinit var loading : FeedbackDialog
    lateinit var movies: List<Moviee.Result>
    var kata_kunci: String? =null
//    override fun onResume() {
//        super.onResume()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val state = intent.extras.getString("pop")



        //kata_kunci = crud_keyword.replace("//s".toRegex(), "%20")
        //Log.e("coba", kata_kunci)
//            Toast.makeText(this,kata_kunci.toString(),Toast.LENGTH_SHORT).show()


        var page = 1
        txt_cek.text = page.toString()

        if (page <= 1) {

            //baseUrl = "https://api.themoviedb.org/3/discover/movie?api_key=$key&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=truepage=1"
            getMovie(page.toString(), state)
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        }


        btn_next.setOnClickListener {
            page = page + 1
            txt_cek.text = page.toString()

            getMovie(page.toString(), state)
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        }

        btn_mundur.setOnClickListener {
            if (page >= 2) {
                page = page - 1
                txt_cek.text = page.toString()
                getMovie(page.toString(), state)
                recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            }

        }

//
//        baseUrl="http://api.themoviedb.org/3/movie/popular?api_key=$key&language=en-US&page=${page.toString()}"
//        //baseUrl = "https://api.themoviedb.org/3/discover/movie?api_key=$key&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=truepage=1"
//        getMovie()
//        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
//       refresh()

        swipeRefresh.setOnRefreshListener {
            getMovie("1", state)
            page = 1
            txt_cek.text = page.toString()
        }

    }


    fun getMovie(pages: String, state: String) {

        //movies.clear()
        recyclerView.adapter = null

        val key = getString(R.string.api_key)
        //baseUrl = "https://api.themoviedb.org/3/discover/movie?api_key=$key&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=truepage=1"
        if (state.equals("1")) {
            jenis = "Popular Movie"
            baseUrl = "http://api.themoviedb.org/3/movie/popular?api_key=$key&language=en-US&page=${pages.toString()}"
        } else if (state.equals("2")) {
            jenis = "Top Rated"


            baseUrl = "https://api.themoviedb.org/3/movie/top_rated?api_key=add93344f04b57906509751217e8d180&language=en-US&page=${pages.toString()}"
            //baseUrl = "https://api.themoviedb.org/3/discover/movie?api_key=$key&language=en-US&sort_by=popularity.desc&page=${pages.toString()}&with_genres=35"
            //baseUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=$key&language=en-US&page=${pages.toString()}"
            //baseUrl = "https://api.themoviedb.org/3/trending/movie/week?api_key=$key"

//            "http://api.themoviedb.org/3/movie/popular?api_key=add93344f04b57906509751217e8d180&language=en-US&video=true&page=1
        } else if (state.equals("3")) {

            kata_kunci = intent.extras.getString("keyword")
            jenis = "Hasil Pencarian"
            baseUrl = "https://api.themoviedb.org/3/search/movie?api_key=add93344f04b57906509751217e8d180&language=en-US&query=$kata_kunci&page=1&include_adult=true&page=${pages.toString()}"

        }

load(1)
        AndroidNetworking.get(baseUrl)
                .setTag(this)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Moviee.Response::class.java,
                        object : ParsedRequestListener<Moviee.Response> {
                            override fun onResponse(response: Moviee.Response?) {

                                if (response != null) {


                                        load(0)
                                    txt_state.text = jenis.toString()
                                    movies = response.results
                                    recyclerView.adapter = MainAdapter(movies, object : MainAdapter.onMovieListener {
                                        override fun onClick(m: Moviee.Result) {

                                            val intent = Intent(this@MainActivity, DetailActivity::class.java)
                                            intent.putExtra("title", m.title)
                                            intent.putExtra("deskripsi", m.overview)
                                            intent.putExtra("id_movie", m.id.toString())
                                            Log.e("cokkkk", m.id.toString())
                                            intent.putExtra("path_gambar", m.backdrop_path)
                                            startActivity(intent)



                                        }


                                    })
                                    swipeRefresh.isRefreshing = false

                                }

                            }

                            override fun onError(anError: ANError?) {
                                Log.e("error", anError?.message)
                            }


                        })

    }


    fun load(stat : Int) {

        if (stat==1) {
            contentView?.let {
                loading = FeedbackDialog(AnkoContext.create(ctx, it))
            }
        }
        else if (stat==0) {
            loading.dialog.dismiss()
        }
    }
}
