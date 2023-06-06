package com.example.dacs_3.Action

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dacs_3.Model.Danhmuc
import com.example.dacs_3.Model.Monan
import com.example.dacs_3.R
import com.example.dacs_3.RetrofitClient
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateMonan : AppCompatActivity() {
    lateinit var edtupdateTenMA: EditText
    lateinit var edtUpdateDec: EditText
    lateinit var edtUpdateNl: EditText
    lateinit var btnupdateMA: Button
    private var danhmuclist: List<Danhmuc> = emptyList()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_monan)

        edtupdateTenMA = findViewById(R.id.edtUpdateTenMA)
        btnupdateMA = findViewById(R.id.btnUpdateMA)
        edtUpdateDec = findViewById(R.id.edtUpdateDec)
        edtUpdateNl = findViewById(R.id.edtUpdateNl)

        val APIUpdate = RetrofitClient.create()
        CoroutineScope(Dispatchers.IO).launch {
            val response = APIUpdate.getDanhmuc()
            val danhmucs = RetrofitClient.gson.fromJson<List<Danhmuc>>(
                response.string(), object : TypeToken<List<Danhmuc>>() {}.type
            )
            withContext(Dispatchers.Main) {
                danhmuclist = danhmucs

                if (intent != null) {
                    val idma = intent.getIntExtra("idma", 0)
                    val tenma = intent.getStringExtra("tenma")
                    val mota = intent.getStringExtra("motama")
                    val nguyenlieu = intent.getStringExtra("nguyenlieu")

                    edtupdateTenMA.setText(tenma)
                    edtUpdateDec.setText(mota)
                    edtUpdateNl.setText(nguyenlieu)

                    btnupdateMA.setOnClickListener {
                        if (edtupdateTenMA.text.isNotEmpty()) {
                            val monan = Monan(
                                idma,
                                edtupdateTenMA.text.toString(),
                                edtUpdateDec.text.toString(),
                                edtUpdateNl.text.toString(),
                                null,
                                null
                            )
                            CoroutineScope(Dispatchers.IO).launch {
                                val result = RetrofitClient.create().getUpdateMA(monan)
                            }
                            Toast.makeText(
                                application,
                                "Chỉnh sửa thành công",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                application,
                                "Vui lòng điền đầy đủ thông tin",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}
