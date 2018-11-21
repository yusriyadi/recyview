package com.example.asus.json.movie

interface MovieContract {


    interface Presenter
    {
        fun getData()
        fun onAttachView()
        fun onDetachView()
    }

    interface View{
        fun showLoading()
        fun hideLoading()

    }

}