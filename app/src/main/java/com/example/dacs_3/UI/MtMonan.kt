package com.example.dacs_3.UI

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.aurelhubert.ahbottomnavigation.notification.AHNotification
import com.example.dacs_3.R
import com.example.dacs_3.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MtMonan : AppCompatActivity() {
    lateinit var tvTenma: TextView
    lateinit var tvMotama: TextView
    lateinit var tvNl: TextView

    lateinit var btnyt: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mt_monan)

        tvTenma = findViewById(R.id.tvTenma)
        tvMotama = findViewById(R.id.tvMotama)
        tvNl = findViewById(R.id.tvNl)

        btnyt = findViewById(R.id.btnyt)

        tvMotama.movementMethod = ScrollingMovementMethod()
        tvNl.movementMethod = ScrollingMovementMethod()


        val bottomNavigation =
            findViewById<View>(com.example.dacs_3.R.id.bottom_navigation) as AHBottomNavigation

        bottomNavigation.setOnTabSelectedListener(object :
            AHBottomNavigation.OnTabSelectedListener {
            override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
                when (position) {
                    0 -> { /* Xử lý khi người dùng nhấn vào item 1 */
                        val intent = Intent(this@MtMonan, Home::class.java)
                        startActivity(intent)
                    }

                    1 -> { /* Xử lý khi người dùng nhấn vào item 2 */
                        val intent = Intent(this@MtMonan, Monanyt::class.java)
                        startActivity(intent)
                    }
                    2 -> { /* Xử lý khi người dùng nhấn vào item 3 */
                        val intent = Intent(this@MtMonan, LichMonan::class.java)
                        startActivity(intent)
                    }
                }
                return true
            }
        })

// Create items
        val item1 = AHBottomNavigationItem(
            com.example.dacs_3.R.string.tab_1,
            com.example.dacs_3.R.drawable.icon_home,
            com.example.dacs_3.R.color.white
        ).apply {
        }
        val item2 = AHBottomNavigationItem(
            com.example.dacs_3.R.string.tab_2,
            com.example.dacs_3.R.drawable.icon_favorite,
            com.example.dacs_3.R.color.white
        )
        val item3 = AHBottomNavigationItem(
            com.example.dacs_3.R.string.tab_3,
            com.example.dacs_3.R.drawable.icon_calender,
            com.example.dacs_3.R.color.white
        ).apply {
            setColorRes(R.color.white)
        }

// Add items to menu
        bottomNavigation.addItem(item1)
        bottomNavigation.addItem(item2)
        bottomNavigation.addItem(item3)

        bottomNavigation.defaultBackgroundColor
// Show title only when active
        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW

        if (intent != null) {
            val idma = intent.getIntExtra("idma", 0)
            val tenma = intent.getStringExtra("tenma")
            val motama = intent.getStringExtra("motama")
            val nguyenlieu = intent.getStringExtra("nguyenlieu")
            val trangthai = intent.getIntExtra("trangthai", 0)
            tvTenma.setText(tenma)
            tvMotama.setText(motama)
            tvNl.setText(nguyenlieu)

            if (trangthai == 0) {
                btnyt.setText(R.string.yt)
            } else {
                btnyt.setText(R.string.dyt)
            }

            btnyt.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val result = RetrofitClient.create().update_tt(idma)
                }
//                Toast.makeText(application, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show()

            }
        }
    }
}
