package com.example.dacs_3.Action

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs_3.Adapter.AdapterLichma
import com.example.dacs_3.Adapter.AdapterMonday
import com.example.dacs_3.Model.Monan
import com.example.dacs_3.R
import com.example.dacs_3.RetrofitClient
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InsertMonday : AppCompatActivity() {

    private lateinit var adapter: AdapterMonday
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_monday)

        adapter = AdapterMonday(this)
        recyclerView = findViewById(R.id.rv_chonmon)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        loadData()
    }
    private fun loadData() {
        val API = RetrofitClient.create()
        CoroutineScope(Dispatchers.IO).launch {
            val response = API.getChonmon()
            val monans = RetrofitClient.gson.fromJson<List<Monan>>(response.string(),
                object : TypeToken<List<Monan>>() {}.type
            )
            withContext(Dispatchers.Main) {
                adapter.setMonans(monans)
            }
        }
    }
}