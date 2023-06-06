package com.example.dacs_3.UI

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import com.example.dacs_3.Model.Account
import com.example.dacs_3.R
import com.example.dacs_3.RetrofitClient


class RegisterAndLogin : AppCompatActivity() {
    val key:String = "ABCDEF1234567890"

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonRegister: TextView
    private lateinit var buttonLogin: Button
    private lateinit var textViewErrorMessage: TextView
    private lateinit var login: RelativeLayout


    private lateinit var editTextUsername_rg: EditText
    private lateinit var editPhone_rg: EditText
    private lateinit var editTextPassword_rg: EditText
    private lateinit var editTextConfirmPassword_rg: EditText
    private lateinit var textViewErrorMessage_rg: TextView
    private lateinit var buttonRegister_rg: Button
    private lateinit var buttnonLogin_rg: Button
    private lateinit var register: RelativeLayout
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextUsername = findViewById(R.id.editText_username)
        editTextUsername = findViewById(R.id.editText_username)
        editTextPassword = findViewById(R.id.editText_password)
        buttonRegister = findViewById(R.id.button_register)
        buttonLogin = findViewById(R.id.button_login)
        textViewErrorMessage = findViewById(R.id.textView_error_message)
        login = findViewById(R.id.login)



        editTextUsername_rg = findViewById(R.id.editText_username_rg)
        editPhone_rg = findViewById(R.id.editText_phone_rg)
        editTextPassword_rg = findViewById(R.id.editText_password_rg)
        editTextConfirmPassword_rg = findViewById(R.id.editText_confirm_password_rg)
        textViewErrorMessage_rg = findViewById(R.id.textView_error_message_rg)
        buttonRegister_rg = findViewById(R.id.button_register_rg)
        buttnonLogin_rg = findViewById(R.id.button_login_rg)
        register = findViewById(R.id.register)

        buttonRegister.setOnClickListener {
            register()
        }

        buttonLogin.setOnClickListener {
            login()
        }

        buttonRegister_rg.setOnClickListener{
            register_rg()
        }

        buttnonLogin_rg.setOnClickListener { login_rg() }

    }


    private fun register() {
        editTextUsername.setText("")
        editTextPassword.setText("")
        textViewErrorMessage.setText("")
        register.visibility = View.VISIBLE
        login.visibility = View.GONE
//        val i = Intent(this, javaClass register::class.java)
//        startActivity(i)
    }

    private fun login() {
        val username = editTextUsername.text.toString()
        val password = editTextPassword.text.toString()
        val account = Account(null, username, null, mahoa(password,key))
        val Dacs3Api = RetrofitClient.create()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                withContext(Dispatchers.Main) {
                    val result = Dacs3Api.Login(account)
                    if (result.user == null || result.pass == null) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(applicationContext, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                        editTextUsername.setText("")
                        editTextPassword.setText("")
                        var i  = Intent(this@RegisterAndLogin, Home::class.java)
                        startActivity(i)

                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
                }
                e.printStackTrace()
            }
        }

    }

    private fun register_rg() {
        val username = editTextUsername_rg.text.toString()
        val phone = editPhone_rg.text.toString()
        var password = editTextPassword_rg.text.toString()
        val passwordconfirm = editTextConfirmPassword_rg.text.toString()

        if (username.isEmpty() || phone.isEmpty() || password.isEmpty() || passwordconfirm.isEmpty()) {
            textViewErrorMessage_rg.text = "Vui lòng nhập đầy đủ thông tin"
        } else {
            if (password != passwordconfirm) {
                textViewErrorMessage_rg.text = "Cần điền pass giống nhau"
            } else {
                if (isValidUsername(username)) {
                    if (isValidPhoneNumber(phone)) {
                        if (isValidPassword(password)) {
                            // Toast.makeText(applicationContext, "Đăng kí thành công", Toast.LENGTH_SHORT).show()
                            val account = Account(null, username, phone,mahoa(password,key))
                            CoroutineScope(Dispatchers.IO).launch {
                                val result = RetrofitClient.create().Register(account)
                            }
                            Toast.makeText(
                                applicationContext,
                                "Đăng kí thành công",
                                Toast.LENGTH_SHORT
                            ).show()
                            editTextUsername_rg.setText("")
                            editPhone_rg.setText("")
                            editTextPassword_rg.setText("")
                            editTextConfirmPassword_rg.setText("")
                        } else {
                            textViewErrorMessage_rg.text = "Pass phải có 1 chứ itexta, 1 kí tự đặc biệt, trên 8 kí tự"
                        }
                    } else {
                        textViewErrorMessage_rg.text = "Số điện thoại không hợp lệ"
                    }
                } else {
                    textViewErrorMessage_rg.text = "Tên đăng nhập phải tối thiểu 6 kí tự"
                }
            }
        }
    }

    private fun login_rg(){
        editTextUsername_rg.setText("")
        editPhone_rg.setText("")
        editTextPassword_rg.setText("")
        editTextConfirmPassword_rg.setText("")
        textViewErrorMessage_rg.setText("")
        login.visibility = View.VISIBLE
        register.visibility = View.GONE
    }
    fun isValidPassword(password: String): Boolean {
        val pattern = Regex("^(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=.{8,})\\S+$")
        return pattern.matches(password)
    }
    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.matches("^0[0-9]{9}\$".toRegex())
    }
    fun isValidUsername(username: String): Boolean {
        val pattern = Regex("^[a-zA-Z0-9]{6,}$")
        return pattern.matches(username)
    }
    @SuppressLint("NewApi")
    fun mahoa(kitu: String, key: String): String {
        val skeySpec = SecretKeySpec(key.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec)
        val byteEncrypted = cipher.doFinal(kitu.toByteArray())
        val encrypted = Base64.getEncoder().encodeToString(byteEncrypted)
        return encrypted
    }

    @SuppressLint("NewApi")
    fun giaima(matma: String, key: String): String {
        val skeySpec = SecretKeySpec(key.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, skeySpec)
        return String(cipher.doFinal(Base64.getDecoder().decode(matma)))
    }


}