package com.cloniamix.lesson10engurazovkotlin.screens.screenbridgeinmap

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cloniamix.lesson10engurazovkotlin.R
import com.cloniamix.lesson10engurazovkotlin.screens.screenbridgelist.MainActivity
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {

    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, MapActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        toolbarMap.inflateMenu(R.menu.list_menu)
        toolbarMap.setOnMenuItemClickListener {
            if (it.itemId == R.id.itemList){
                startActivity(MainActivity.createStartIntent(this).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
            }
            true
        }
    }

}
