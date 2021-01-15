package com.example.anyang_linker.fragments.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.anyang_linker.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    var lastTimeBackPressed = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url = intent.getStringExtra("url")

        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }

    override fun onBackPressed() {
        if(webView.canGoBack()) webView.goBack()
        else {
            if(System.currentTimeMillis() - lastTimeBackPressed < 1500){
                finish()
                return
            }
            lastTimeBackPressed = System.currentTimeMillis()
            Toast.makeText(this,"'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
