package com.example.dacs_3.Adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dacs_3.Action.UpdateMonan

import com.example.dacs_3.Model.Monan
import com.example.dacs_3.R
import com.example.dacs_3.RetrofitClient
import com.example.dacs_3.UI.MtMonan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdapterMonan: RecyclerView.Adapter<AdapterMonan.MonanViewHolder>() {
    private var monans: List<Monan> = listOf()
    private var currentMonan: Monan? = null

    fun setMonans(monans: List<Monan>) {
        this.monans = monans
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_monan, parent, false)
        val btnDeleteMA= view.findViewById<Button>(R.id.btnDeleteMA)
        val btnUpdateMA= view.findViewById<Button>(R.id.btnUpdateMA)

        return MonanViewHolder(view, btnUpdateMA, btnDeleteMA)
    }

    private fun openFeedbackDialog(gravity: Int, context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dilog_delete)
        val window = dialog.window
        if (window == null) {
            return
        }
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val windowAttributes = window.attributes
        windowAttributes.gravity = gravity
        window.attributes = windowAttributes

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true)
        } else {
            dialog.setCancelable(false)
        }
        dialog.show()

        val btnKhong: Button = dialog.findViewById(R.id.btnKhong)
        val btnCo: Button = dialog.findViewById(R.id.btnCo)

        btnKhong.setOnClickListener(){
            dialog.dismiss()
        }

        btnCo.setOnClickListener(){

            CoroutineScope(Dispatchers.IO).launch {
                val id  = currentMonan?.idma
                val monan = Monan( id, null,null,null,null, null)
                if(id != null) {

                    val result = RetrofitClient.create().getDeleteMA(monan)
                }

            }
            val id = currentMonan?.idma

            Toast.makeText(context, "Xóa thành công ", Toast.LENGTH_SHORT).show()
//            val intent = Intent(context, DsMonan::class.java)

//            context.startActivity(intent)
            dialog.dismiss()
        }

    }


    override fun onBindViewHolder(holder: MonanViewHolder, position: Int) {
        val monan = monans[position]
        holder.bind(monan)

        holder.btnUpdateMA.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateMonan::class.java)
            intent.putExtra("idma", monan.idma)
            intent.putExtra("tenma", monan.tenma)
            intent.putExtra("motama", monan.motama)
            intent.putExtra("nguyenlieu", monan.nguyenlieu)

            holder.itemView.context.startActivity(intent)
        }

        holder.btnDeleteMA.setOnClickListener {
            currentMonan = monan
            openFeedbackDialog(Gravity.CENTER, holder.itemView.context)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MtMonan::class.java)
            intent.putExtra("idma", monan.idma)
            intent.putExtra("tenma", monan.tenma)
            intent.putExtra("motama", monan.motama)
            intent.putExtra("nguyenlieu", monan.nguyenlieu)

            intent.putExtra("iddm", monan.iddm)

            intent.putExtra("trangthai", monan.trangthai)
            holder.itemView.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int = monans.size

    inner class MonanViewHolder(
        itemView: View,
        btnUpdateMA: Button,
        btnDeleteMA: Button
    ) : RecyclerView.ViewHolder(itemView) {
        private val monanTextView: TextView = itemView.findViewById(R.id.tvMonan)
        val btnUpdateMA: Button = btnUpdateMA
        val btnDeleteMA: Button = btnDeleteMA

        fun bind(monan: Monan) {
            monanTextView.text = monan.tenma
        }
    }
}