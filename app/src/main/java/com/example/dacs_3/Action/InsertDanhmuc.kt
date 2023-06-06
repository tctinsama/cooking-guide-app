package com.example.dacs_3.Action

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.dacs_3.Model.Danhmuc
import com.example.dacs_3.R
import com.example.dacs_3.RetrofitClient
import com.example.dacs_3.UI.Home
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InsertDanhmuc : AppCompatActivity() {
    lateinit var edtTenDM : EditText
    lateinit var btnThemDM: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_danhmuc)
        edtTenDM = findViewById(R.id.edtTenDM)
        btnThemDM = findViewById(R.id.btnThemDM)


        btnThemDM.setOnClickListener { adddanhmuc() }
    }

    private fun adddanhmuc() {
        val tendm = edtTenDM.text.toString()

        if (tendm.isNotEmpty() ) {
            val danhmuc = Danhmuc(null, tendm)
            CoroutineScope(Dispatchers.IO).launch {
                val result = RetrofitClient.create().getInsert(danhmuc)

            }
            Toast.makeText(application, "Thêm thành công", Toast.LENGTH_SHORT).show()

            edtTenDM.setText("")


            val i = Intent(this, Home::class.java)
            startActivity(i)
        }else{
            Toast.makeText(application, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
        }
    }
}