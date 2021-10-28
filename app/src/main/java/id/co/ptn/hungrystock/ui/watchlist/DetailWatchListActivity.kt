package id.co.ptn.hungrystock.ui.watchlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import id.co.ptn.hungrystock.R

@AndroidEntryPoint
class DetailWatchListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_watch_list)
    }
}