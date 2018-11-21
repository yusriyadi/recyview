package com.example.asus.json.movie

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.asus.json.ListPostingan
import com.example.asus.json.Moviee
import com.example.asus.json.R

class CobaParserApi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coba)

        //getPosts()

        //post()
        getMovie()
    }



    fun getMovie()
    {
        AndroidNetworking.get("http://api.themoviedb.org/3/movie/popular?api_key=add93344f04b57906509751217e8d180&language=en-US&video=true&page=1")
                .setTag(this)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Moviee.Response::class.java,
                        object : ParsedRequestListener<Moviee.Response>{
                    override fun onResponse(response: Moviee.Response?) {
                        if (response!=null)
                        {
                           // Log.d("response onject", response.results[0].title)
                            for (i in 0 until response.results.size) {
                                Log.d("response onject", response.results[i].title)
                            }
                            }

                    }

                    override fun onError(anError: ANError?) {
                        Log.e("error", anError?.message)
                       }


                })
    }

    fun post()
    {
        AndroidNetworking.get("https://jsonplaceholder.typicode.com/posts/{id}")
                .addPathParameter("id","1")
                .setTag(this)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(ListPostingan::class.java, object : ParsedRequestListener<ListPostingan>{
                    override fun onResponse(response: ListPostingan?) {
                              if (response!=null)
                        {
                            Log.d("response onject",response.title)
                        }


                    }

                    override fun onError(anError: ANError?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }


                })





    }

    fun getPosts()
    {
        AndroidNetworking.get("https://jsonplaceholder.typicode.com/posts")
                .setTag(this)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(ListPostingan::class.java, object : ParsedRequestListener<List<ListPostingan>>{
                    override fun onResponse(response: List<ListPostingan>?) {


                        if (response != null) {
                            Log.d("testingan ", response[0].id.toString())
                        }
                    }


                    override fun onError(anError: ANError?) {

                    }

                })


    }

}
