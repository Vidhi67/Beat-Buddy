package com.example.beat_buddy


import android.app.Application
class PostApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PostRepository.initialize(this)
    }
}