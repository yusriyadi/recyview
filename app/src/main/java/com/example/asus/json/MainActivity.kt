package com.example.asus.json

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import android.graphics.Color
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.ParsedRequestListener
import kotlinx.android.synthetic.main.activity_main.*

import org.json.JSONObject

class MainActivity : AppCompatActivity(){

    var baseUrl:String? = null
    var jenis:String? = null
    //val movies = MutableList<Moviee.Result>
    lateinit var movies: List<Moviee.Result>

//    override fun onResume() {
//        super.onResume()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        bottom_navigation_view.setOnNavigationItemSelectedListener{
            when(it.itemId){

            }


        }


        val state = intent.extras.getString("pop")



        var page=1
        txt_cek.text=page.toString()

        if (page<=1) {

            //baseUrl = "https://api.themoviedb.org/3/discover/movie?api_key=$key&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=truepage=1"
            getMovie(page.toString(),state)
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        }


        btn_next.setOnClickListener {
            page=page+1
            txt_cek.text=page.toString()

            getMovie(page.toString(),state)
            recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        }

        btn_mundur.setOnClickListener {
            if(page>=2) {
                page = page - 1
                txt_cek.text = page.toString()
                getMovie(page.toString(),state)
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
        getMovie("1",state)
        page=1
        txt_cek.text=page.toString()
    }

    }





    fun getMovie(pages : String, state : String){

        //movies.clear()
        recyclerView.adapter = null

        val key = getString(R.string.api_key)
        //baseUrl = "https://api.themoviedb.org/3/discover/movie?api_key=$key&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=truepage=1"
        if (state.equals("1")) {
            jenis= "Popular Movie"
            baseUrl = "http://api.themoviedb.org/3/movie/popular?api_key=$key&language=en-US&page=${pages.toString()}"
        }else if(state.equals("2")) {
            jenis= "Comedy"

            baseUrl="https://api.themoviedb.org/3/discover/movie?api_key=$key&language=en-US&sort_by=popularity.desc&page=${pages.toString()}&with_genres=35"
            //baseUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=$key&language=en-US&page=${pages.toString()}"
            //baseUrl = "https://api.themoviedb.org/3/trending/movie/week?api_key=$key"

//            "http://api.themoviedb.org/3/movie/popular?api_key=add93344f04b57906509751217e8d180&language=en-US&video=true&page=1
        }


        AndroidNetworking.get(baseUrl)
                .setTag(this)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Moviee.Response::class.java,
                        object : ParsedRequestListener<Moviee.Response> {
                            override fun onResponse(response: Moviee.Response?) {

                                if (response!=null)
                                {

                                    // Log.d("response onject", response.results[0].title)
//                                    for (i in 0 until response.results.size) {
//
//                                        movies.add(
//                                                Movie(0,response.results[i].title,
//                                                        response.results[i].release_date,
//                                                        response.results[i].poster_path,
//                                                        response.results[i].overview,
//                                                        response.results[i].backdrop_path)
//                                        )
//
//                                    }

                         txt_state.text=jenis.toString()


                                    movies = response.results
                        recyclerView.adapter = MainAdapter(movies, object : MainAdapter.onMovieListener
                        {
                            override fun onClick(m: Moviee.Result) {
                                val intent = Intent(this@MainActivity,DetailActivity::class.java)
                                intent.putExtra("title",m.title)
                                intent.putExtra("deskripsi",m.overview)
                                intent.putExtra("path_gambar",m.backdrop_path)
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
//
//        AndroidNetworking.get(baseUrl)
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(object : JSONObjectRequestListener {
//                    override fun onResponse(response: JSONObject) {
//                        Log.e("_kotlinResponse", response.toString())
//
//                        val jsonArray = response.getJSONArray("results")
//                        for (i in 0 until jsonArray.length()) {
//                            val jsonObject = jsonArray.getJSONObject(i)
//
//                           // Log.e("title", jsonObject.optString("title"))
//
//                            movies.add(
//                                    Movie(0, jsonObject.optString("title"),
//                                            jsonObject.optString("release_date"),
//                                            jsonObject.optString("poster_path"),
//                                            jsonObject.optString("overview"),
//                                            jsonObject.optString("backdrop_path"))
//                            )
//
//                        }
//                        txt_state.text=jenis.toString()
//                        recyclerView.adapter = MainAdapter(movies, object : MainAdapter.onMovieListener {
//                            override fun onClick(m: Movie) {
//
//                                val intent = Intent(this@MainActivity,DetailActivity::class.java)
//                                intent.putExtra("title",m.title)
//                                intent.putExtra("deskripsi",m.deskripsi)
//                                intent.putExtra("path_gambar",m.banner)
//                                startActivity(intent)
//
//                                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                            }
//
//                        })
//                        swipeRefresh.isRefreshing = false
//
//                    }
//
//                    override fun onError(anError: ANError) {
//                        Log.i("_err", anError.toString())
//                    }
//                })
    }
}
