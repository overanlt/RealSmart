package com.interview.realsmart

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.telephony.TelephonyManager
import com.google.gson.Gson
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private var mProgressDialogLoader: ProgressDialog? = null

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etUser =findViewById<EditText>(R.id.etUser)
        val etPassword =findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        if (mProgressDialogLoader == null) {
            mProgressDialogLoader = ProgressDialog(this)
            mProgressDialogLoader!!.setMessage("Loading")
            mProgressDialogLoader!!.setCancelable(false)
        }

        btnLogin.setOnClickListener {
            mProgressDialogLoader!!.show()
            var uuid:String ;
            try {
                val tManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                uuid = tManager.deviceId
            }catch (e:Exception){
                uuid = ""
            }
            Log.d("TAG", "uuid: $uuid")
            Api().loginApi(LoginModel(etUser.text.toString(),etPassword.text.toString(),uuid),object : Handler.Callback,
                Api.ResponseResult {
                override fun handleMessage(p0: Message): Boolean {
                    TODO("Not yet implemented")
                }

                override fun response(
                    httpCode: Int?,
                    res: ResponseLoginModel?
                ) {
                    mProgressDialogLoader!!.dismiss()
                    Log.d("TAG", "response: ${res?.responseCode}")
                    if (res?.responseCode == 200) {
                        val intent = Intent(this@MainActivity,PostLoginActivity::class.java)
                        intent.putExtra("data", Gson().toJson(res))
                        startActivity(intent)
                    }else {
                        val builder = AlertDialog.Builder(this@MainActivity)
                        builder.setTitle("error")
                        builder.setMessage(res?.responseMessage)
                        builder.setNegativeButton("ok", DialogInterface.OnClickListener { dialog, _ ->
                            dialog.dismiss()
                        })
                        builder.show()
                    }
                }

            })
        }

    }
}