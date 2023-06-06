package com.example.dacs_3.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs_3.Model.Lichmonan
import com.example.dacs_3.Model.Monan
import com.example.dacs_3.R
import com.example.dacs_3.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdapterMonday(private val context: Context): RecyclerView.Adapter<AdapterMonday.MonanViewHolder>() {
    private var monans: List<Monan> = listOf()

    fun setMonans(monans: List<Monan>) {
        this.monans = monans
        notifyDataSetChanged()
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chonmon, parent, false)
        val btnChonmon= view.findViewById<Button>(R.id.btn_chonMa)

        return MonanViewHolder(view, btnChonmon)
    }



    override fun onBindViewHolder(holder: MonanViewHolder, position: Int, ) {
        val monan = monans[position]
        holder.bind(monan)



        holder.btnChonmon.setOnClickListener {
            val intent = (context as Activity).intent
            val ngay = intent.getStringExtra("ngay")
            val lichmonan = Lichmonan(null, monan.idma, ngay,null,null,null,null)
            CoroutineScope(Dispatchers.IO).launch {
                val result = RetrofitClient.create().InsertMonday(lichmonan)
                (context as Activity).runOnUiThread {
                    Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show()
                }            }

        }
    }
    override fun getItemCount(): Int = monans.size

    inner class MonanViewHolder(
        itemView: View,
        btnChonmon: Button
    ) : RecyclerView.ViewHolder(itemView) {
        private val monanTextView: TextView = itemView.findViewById(R.id.tv_chonMa)
        val btnChonmon: Button = btnChonmon

        fun bind(monan: Monan) {
            monanTextView.text = monan.tenma
        }
    }
}