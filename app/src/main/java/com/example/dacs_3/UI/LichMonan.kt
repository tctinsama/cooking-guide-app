package com.example.dacs_3.UI

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.aurelhubert.ahbottomnavigation.notification.AHNotification
import com.example.dacs_3.Action.InsertMonday
import com.example.dacs_3.Adapter.AdapterLichma
import com.example.dacs_3.Adapter.AdapterMonan
import com.example.dacs_3.Adapter.AdapterMonanyt
import com.example.dacs_3.Model.Lichmonan
import com.example.dacs_3.Model.Monan
import com.example.dacs_3.R
import com.example.dacs_3.RetrofitClient
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class LichMonan : AppCompatActivity() {
    private lateinit var ngay:TextView
    private lateinit var imagelich:ImageView
    private lateinit var btnaddmon:Button

    private lateinit var adapter: AdapterLichma

    private lateinit var recyclerView: RecyclerView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lich_monan)

        ngay = findViewById(R.id.edtNgay)
        imagelich = findViewById(R.id.imglich)
        btnaddmon = findViewById(R.id.btn_addma)


        imagelich.setOnClickListener{chonngay()}
        btnaddmon.setOnClickListener {
            if(ngay.text.equals("Ngày")) {
            Toast.makeText(this, "Vui lòng chọn ngày", Toast.LENGTH_SHORT).show()
            }else{
            val i = Intent(this, InsertMonday::class.java)
            i.putExtra("ngay", ngay.text.toString())
            startActivity(i)
            }
       }

        val bottomNavigation =
            findViewById<View>(com.example.dacs_3.R.id.bottom_navigation) as AHBottomNavigation

        bottomNavigation.setOnTabSelectedListener(object :
            AHBottomNavigation.OnTabSelectedListener {
            override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
                when (position) {
                    0 -> { /* Xử lý khi người dùng nhấn vào item 1 */
                        val intent = Intent(this@LichMonan, LichMonan::class.java)
                        startActivity(intent)
                    }

                    1 -> { /* Xử lý khi người dùng nhấn vào item 2 */
                        val intent = Intent(this@LichMonan, Home::class.java)
                        startActivity(intent)
                    }
                    2 -> { /* Xử lý khi người dùng nhấn vào item 3 */
                        val intent = Intent(this@LichMonan, Monanyt::class.java)
                        startActivity(intent)
                    }
                }
                return true
            }
        })

// Create items
        val item1 = AHBottomNavigationItem(
            R.string.tab_1,
            R.drawable.icon_home,
            R.color.white
        ).apply {
        }
        val item2 = AHBottomNavigationItem(
            R.string.tab_2,
            R.drawable.icon_favorite,
            R.color.white
        )
        val item3 = AHBottomNavigationItem(
            R.string.tab_3,
            R.drawable.icon_calender,
            R.color.white
        ).apply {
            setColorRes(R.color.white)
        }

// Add items to menu
        bottomNavigation.addItem(item3)
        bottomNavigation.addItem(item1)
        bottomNavigation.addItem(item2)

        bottomNavigation.defaultBackgroundColor
// Show title only when active
        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW

    }

    private fun chonngay() {
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        val datePickerDialog = DatePickerDialog(
            this,
            R.style.MyDatePickerStyle,
            { _, year, month, dayOfMonth ->
                val dateString = "$year-${month+1}-$dayOfMonth"
                ngay.text = dateString

                adapter = AdapterLichma()
                recyclerView = findViewById(R.id.rv_ngaymonan)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this)
                loadData(dateString)

            },
            currentYear,
            currentMonth,
            currentDay
        )

        datePickerDialog.show()

    }

    //
    private fun loadData(ngay:String) {
        val API = RetrofitClient.create()
        CoroutineScope(Dispatchers.IO).launch {
            val response = API.getlichma(ngay)
            val monans = RetrofitClient.gson.fromJson<List<Lichmonan>>(response.string(),
                object : TypeToken<List<Lichmonan>>() {}.type
            )
            withContext(Dispatchers.Main) {
                adapter.setMonans(monans)
            }
        }
    }

}