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
import com.example.dacs_3.UI.DsMonan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateDanhmuc : AppCompatActivity() {
    lateinit var tvupdate: EditText
    lateinit var btnupdate: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_danhmuc)

        tvupdate = findViewById(R.id.tvupdate)
        btnupdate = findViewById(R.id.btnupdate)

        if(intent!=null){

            val id = intent.getIntExtra("id",0)
            val tendm = intent.getStringExtra("tendm")


            tvupdate.setText(tendm)


            btnupdate.setOnClickListener {
                if (tvupdate.text.isNotEmpty() ) {
                    val danhmuc = Danhmuc( id, tvupdate.text.toString())
                    CoroutineScope(Dispatchers.IO).launch {
                        val result = RetrofitClient.create().getUpdate(danhmuc)
                    }
                    Toast.makeText(application, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show()
                    val i = Intent(this, DsMonan::class.java)
                    startActivity(i)
                }else{
                    Toast.makeText(application, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}