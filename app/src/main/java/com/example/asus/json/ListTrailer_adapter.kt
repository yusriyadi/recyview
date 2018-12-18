package com.example.asus.json

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.asus.json.R.id.txtName
import com.example.asus.json.data.remote.Trailer
import kotlinx.android.synthetic.main.adapter_trailer.view.*

class ListTrailer_adapter(private val trailers: List<Trailer.Result>, val listener: onTrailerListener) : RecyclerView.Adapter<ListTrailer_adapter.ViewHolder>() {

    interface onTrailerListener {
        fun onClick(trailer: Trailer.Result)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTrailer_adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_trailer, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = trailers.size

    override fun onBindViewHolder(holder: ListTrailer_adapter.ViewHolder, posisition: Int) {
        holder.bindItem(trailers[posisition], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nama = itemView?.txt_name
        val type = itemView?.txt_type
        val size1 = itemView?.txt_size


        fun bindItem(trailer: Trailer.Result, listener: ListTrailer_adapter.onTrailerListener) {
            nama.text = trailer.name
            type.text = trailer.type
            size1.text = trailer.size.toString()


            itemView.setOnClickListener {
                listener.onClick(trailer)
            }

        }


    }

}

