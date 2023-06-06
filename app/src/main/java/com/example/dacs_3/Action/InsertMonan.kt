package com.example.dacs_3.Action

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dacs_3.Model.Danhmuc
import com.example.dacs_3.R
import com.example.dacs_3.RetrofitClient
import com.example.dacs_3.UI.DsMonan
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InsertMonan : AppCompatActivity() {
    lateinit var edtTenMA: EditText
    lateinit var btnThemMA: Button
    lateinit var edtdm: Spinner
    lateinit var edtmota: EditText
    lateinit var edtNl: EditText
    private var danhmuclist: List<Danhmuc> = emptyList()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_monan)
        edtTenMA = findViewById(R.id.edtTenMA)
        btnThemMA = findViewById(R.id.btnThemMA)
        edtdm = findViewById(R.id.spinner_danhmuc)
        edtmota = findViewById(R.id.edtDec)
        edtNl = findViewById(R.id.edtNl)


        btnThemMA.setOnClickListener { addmonan() }


        val API = RetrofitClient.create()
        CoroutineScope(Dispatchers.IO).launch {
            val response = API.getDanhmuc()
            val danhmucs = RetrofitClient.gson.fromJson<List<Danhmuc>>(
                response.string(), object : TypeToken<List<Danhmuc>>() {}.type
            )
            withContext(Dispatchers.Main) {
                danhmuclist = danhmucs
                val spinner1Adapter = ArrayAdapter(applicationContext,
                    R.layout.spinner, // layout cho item trong spinner
                    R.id.textspinner, // ID của TextView trong layout item
                    danhmuclist.map { it.tendm })
                spinner1Adapter.setDropDownViewResource(R.layout.spinner) // layout cho dropdown item
                val spinner1 = findViewById<Spinner>(R.id.spinner_danhmuc)
                spinner1.adapter = spinner1Adapter
            }
        }


    }

    private fun addmonan() {

        val tenma = edtTenMA.text.toString()
        val tendm = edtdm.selectedItem.toString()
        val mota = edtmota.text.toString()
        val nguyenlieu = edtNl.text.toString()


        if (tenma.isNotEmpty()) {
            // val monan = Monan(null, tenma , null,null)
            CoroutineScope(Dispatchers.IO).launch {
                val result = RetrofitClient.create().getInsertMA(tenma, mota,nguyenlieu, tendm)

            }
            Toast.makeText(application, "Thêm thành công", Toast.LENGTH_SHORT).show()


//            val i = Intent(this, DsMonan::class.java)
//            startActivity(i)
        } else {
            Toast.makeText(application, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
        }
    }

}