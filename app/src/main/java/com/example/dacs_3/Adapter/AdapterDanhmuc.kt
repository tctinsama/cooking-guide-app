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
import com.example.dacs_3.Action.UpdateDanhmuc
import com.example.dacs_3.Model.Danhmuc
import com.example.dacs_3.R
import com.example.dacs_3.RetrofitClient
import com.example.dacs_3.UI.DsMonan
import com.example.dacs_3.UI.Home
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AdapterDanhmuc : RecyclerView.Adapter<AdapterDanhmuc.DanhmucViewHolder>() {
    private var danhmucs: List<Danhmuc> = listOf()
    private var currentDanhmuc: Danhmuc? = null

    fun setDanhmucs(danhmucs: List<Danhmuc>) {
        this.danhmucs = danhmucs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DanhmucViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_danhmuc, parent, false)
        val btnDeleteDM = view.findViewById<Button>(R.id.btnDeleteDM)
        val btnUpdateDM = view.findViewById<Button>(R.id.btnUpdateDM)

        return DanhmucViewHolder(view, btnUpdateDM, btnDeleteDM)
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
                val iddm  = currentDanhmuc?.iddm
                val danhmuc = Danhmuc( iddm, null)
                if(iddm != null) {

                        val result = RetrofitClient.create().getDelete(danhmuc)
                }

            }
            val iddm = currentDanhmuc?.iddm

            Toast.makeText(context, "Xóa thành công ", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, Home::class.java)

            context.startActivity(intent)
            dialog.dismiss()
        }

    }


    override fun onBindViewHolder(holder: DanhmucViewHolder, position: Int) {
        val danhmuc = danhmucs[position]
        holder.bind(danhmuc)

        holder.btnUpdateDM.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateDanhmuc::class.java)
            intent.putExtra("iddm", danhmuc.iddm)
            intent.putExtra("tendm", danhmuc.tendm)
            holder.itemView.context.startActivity(intent)
        }

        holder.btnDeleteDM.setOnClickListener {
            currentDanhmuc = danhmuc
            openFeedbackDialog(Gravity.CENTER, holder.itemView.context)
        }

        holder.itemView.setOnClickListener{
            var i = Intent(holder.itemView.context, DsMonan::class.java)
            i.putExtra("iddm", danhmuc.iddm)
            holder.itemView.context.startActivity(i)
        }

    }

    override fun getItemCount(): Int = danhmucs.size

    inner class DanhmucViewHolder(
        itemView: View,
        btnUpdateDM: Button,
        btnDeleteDM: Button
    ) : RecyclerView.ViewHolder(itemView) {
        private val danhmucTextView: TextView = itemView.findViewById(R.id.tvDanhmuc)
        val btnUpdateDM: Button = btnUpdateDM
        val btnDeleteDM: Button = btnDeleteDM

        fun bind(danhmuc: Danhmuc) {
            danhmucTextView.text = danhmuc.tendm
        }
    }
}