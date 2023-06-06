package com.example.dacs_3.UI

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.aurelhubert.ahbottomnavigation.notification.AHNotification
import com.example.dacs_3.Action.InsertDanhmuc
import com.example.dacs_3.Adapter.AdapterDanhmuc
import com.example.dacs_3.Model.Danhmuc
import com.example.dacs_3.R
import com.example.dacs_3.RetrofitClient
import com.example.dacs_3.RetrofitClient.gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Home : AppCompatActivity() {
    lateinit var btnThemDM: Button
    private lateinit var adapter: AdapterDanhmuc
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.dacs_3.R.layout.activity_home)

        btnThemDM = findViewById(R.id.btnThem)
        adapter = AdapterDanhmuc()
        recyclerView = findViewById(R.id.recycler_view_danhmuc)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        loadData()

        btnThemDM.setOnClickListener() {
            var i = Intent(this, InsertDanhmuc::class.java)
            startActivity(i)
        }


        //MenuBottom
        val bottomNavigation =
            findViewById<View>(com.example.dacs_3.R.id.bottom_navigation) as AHBottomNavigation

        bottomNavigation.setOnTabSelectedListener(object :
            AHBottomNavigation.OnTabSelectedListener {
            override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
                when (position) {
                    0 -> { /* Xử lý khi người dùng nhấn vào item 1 */
                        val intent = Intent(this@Home, Home::class.java)
                        startActivity(intent)
                    }

                    1 -> { /* Xử lý khi người dùng nhấn vào item 2 */
                        val intent = Intent(this@Home, Monanyt::class.java)
                        startActivity(intent)
                    }
                    2 -> { /* Xử lý khi người dùng nhấn vào item 3 */
                        val intent = Intent(this@Home, LichMonan::class.java)
                        startActivity(intent)
                    }
                }
                return true
            }
        })

// Create items
        val item1 = AHBottomNavigationItem(
            com.example.dacs_3.R.string.tab_1,
            com.example.dacs_3.R.drawable.icon_home,
            com.example.dacs_3.R.color.white
        ).apply {
        }
        val item2 = AHBottomNavigationItem(
            com.example.dacs_3.R.string.tab_2,
            com.example.dacs_3.R.drawable.icon_favorite,
            com.example.dacs_3.R.color.white
        )
        val item3 = AHBottomNavigationItem(
            com.example.dacs_3.R.string.tab_3,
            com.example.dacs_3.R.drawable.icon_calender,
            com.example.dacs_3.R.color.white
        ).apply {
            setColorRes(R.color.white)
        }

// Add items to menu
        bottomNavigation.addItem(item1)
        bottomNavigation.addItem(item2)
        bottomNavigation.addItem(item3)

        bottomNavigation.defaultBackgroundColor
// Show title only when active
        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW


    }


    private fun loadData() {
        val API = RetrofitClient.create()
        CoroutineScope(Dispatchers.IO).launch {
            val response = API.getDanhmuc()
            val danhmucs = gson.fromJson<List<Danhmuc>>(
                response.string(),
                object : TypeToken<List<Danhmuc>>() {}.type
            )
            withContext(Dispatchers.Main) {
                adapter.setDanhmucs(danhmucs)
            }
        }
    }


}