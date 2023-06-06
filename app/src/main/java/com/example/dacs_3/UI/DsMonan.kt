package com.example.dacs_3.UI

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.aurelhubert.ahbottomnavigation.notification.AHNotification
import com.example.dacs_3.Action.InsertDanhmuc
import com.example.dacs_3.Action.InsertMonan
import com.example.dacs_3.Adapter.AdapterDanhmuc
import com.example.dacs_3.Adapter.AdapterMonan
import com.example.dacs_3.Model.Danhmuc
import com.example.dacs_3.Model.Monan
import com.example.dacs_3.R
import com.example.dacs_3.RetrofitClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DsMonan : AppCompatActivity() {
    lateinit var btnThemMA: Button
    private lateinit var adapter: AdapterMonan
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.dacs_3.R.layout.activity_ds_monan)

        btnThemMA = findViewById(R.id.btnThem)
        adapter = AdapterMonan()
        recyclerView = findViewById(R.id.recycler_view_monan)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        if(intent!=null){
            val iddm= intent.getIntExtra("iddm",0)
            loadData(iddm)
        }


        btnThemMA.setOnClickListener() {
            var i = Intent(this, InsertMonan::class.java)
            startActivity(i)
        }


        val bottomNavigation =
            findViewById<View>(com.example.dacs_3.R.id.bottom_navigation) as AHBottomNavigation

        bottomNavigation.setOnTabSelectedListener(object :
            AHBottomNavigation.OnTabSelectedListener {
            override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
                when (position) {
                    0 -> { /* Xử lý khi người dùng nhấn vào item 1 */
                        val intent = Intent(this@DsMonan, Home::class.java)
                        startActivity(intent)
                    }

                    1 -> { /* Xử lý khi người dùng nhấn vào item 2 */
                        val intent = Intent(this@DsMonan, Monanyt::class.java)
                        startActivity(intent)
                    }
                    2 -> { /* Xử lý khi người dùng nhấn vào item 3 */
                        val intent = Intent(this@DsMonan, LichMonan::class.java)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (intent != null) {
                    val iddm = intent.getIntExtra("iddm", 0)

                    if (!query.isNullOrEmpty()) {
                        Search(query, iddm)
                    } else {
                        loadData(iddm)
                    }
                }
                return true

        }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (intent != null) {
                    val iddm = intent.getIntExtra("iddm", 0)

                    if (!newText.isNullOrEmpty()) {
                        Search(newText, iddm)
                    } else {
                        loadData(iddm)
                    }
                }
                return true
            }
        })


        return true
    }

    private fun loadData(iddm:Int) {
        val API = RetrofitClient.create()
        CoroutineScope(Dispatchers.IO).launch {
            val response = API.getMonan(iddm)
            val monans = RetrofitClient.gson.fromJson<List<Monan>>(response.string(),
                object : TypeToken<List<Monan>>() {}.type
            )
            withContext(Dispatchers.Main) {
                adapter.setMonans(monans)
            }
        }
    }

    private fun Search(text:String?, id:Int?) {
        val API = RetrofitClient.create()
        CoroutineScope(Dispatchers.IO).launch {
            val response = API.search(text, id)
            val monans = RetrofitClient.gson.fromJson<List<Monan>>(response.string(),
                object : TypeToken<List<Monan>>() {}.type
            )
            withContext(Dispatchers.Main) {
                adapter.setMonans(monans)
            }
        }
    }
}