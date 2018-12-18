package com.example.asus.json


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.asus.json.data.remote.Moviee
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_main.view.*

class MainAdapter(private val movies: List<Moviee.Result>, val listener : onMovieListener) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    interface onMovieListener {
        fun onClick(m : Moviee.Result)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItem(movies[position],listener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_main, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = movies.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val txtTitle = itemView?.txtName
        val gambar1 = itemView?.gambar
        val deskrip = itemView?.txt_khg
        val release = itemView?.txt_date

        fun bindItem(movie: Moviee.Result, listener: onMovieListener) {

            txtTitle.text = movie.title
            deskrip.text=movie.overview
            release.text=movie.release_date

            val url_tambah: String = movie.poster_path
            val url: String = Upil.BASE_URL
            val url_lengkap : String = url + url_tambah

//            Log.d("CobaParserApi","asuk"+movie.poster)
//            Log.e("erornya","asuk"+Upil.BASE_URL+movie.poster)

            Picasso.get()
                    .load(url_lengkap)
                    .centerCrop().fit()
                    .into(gambar1)

            itemView.setOnClickListener{
                listener.onClick(movie)
            }


        }
    }
}


