package com.example.dacs_3.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs_3.Model.Lichmonan
import com.example.dacs_3.Model.Monan
import com.example.dacs_3.R
import com.example.dacs_3.RetrofitClient
import com.example.dacs_3.UI.MtMonan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdapterLichma : RecyclerView.Adapter<AdapterLichma.MonanViewHolder>() {
    private var lichmonans: List<Lichmonan> = listOf()
    fun setMonans(lichmonans: List<Lichmonan>) {
        this.lichmonans = lichmonans
        notifyDataSetChanged()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ngaymonan, parent, false)
        val btnDeletema = view.findViewById<Button>(R.id.btnDeleteMonday)
        return MonanViewHolder(view, btnDeletema)
    }



    override fun onBindViewHolder(holder: MonanViewHolder, position: Int) {
        val lichmonan = lichmonans[position]
        holder.bind(lichmonan)

        holder.btnDeletema.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val id  = lichmonan.id
                    val result = RetrofitClient.create().getDeleteMonday(id)
            }


        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MtMonan::class.java)
            intent.putExtra("idma", lichmonan.idma)
            intent.putExtra("tenma", lichmonan.tenma)
            intent.putExtra("motama", lichmonan.motama)
            intent.putExtra("nguyenlieu", lichmonan.nguyenlieu)
            intent.putExtra("trangthai", lichmonan.trangthai)
            holder.itemView.context.startActivity(intent)

        }
    }
    override fun getItemCount(): Int = lichmonans.size

    inner class MonanViewHolder(
        itemView: View,
        btnDeletema: Button
    ) : RecyclerView.ViewHolder(itemView) {
        private val monanTextView: TextView = itemView.findViewById(R.id.tv_Ma)
        val btnDeletema: Button = btnDeletema

        fun bind(lichmonan: Lichmonan) {
            monanTextView.text = lichmonan.tenma.toString()
           // monanTextView.text = lichmonan.id.toString()

        }

    }
}