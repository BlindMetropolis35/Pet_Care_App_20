package com.example.petcareapp20

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petcareapp20.R

class DetailActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        val url=intent.getStringExtra("CONTENT_URL")

        progressBar = findViewById(R.id.progress_bar_frag)

        if(url!=null){
            progressBar.visibility = View.VISIBLE
            Toast.makeText(this,"redirecting..",Toast.LENGTH_SHORT).show()
            val detailWebView=findViewById<WebView>(R.id.detailWebView)
            detailWebView.settings.javaScriptEnabled=true
            detailWebView.settings.userAgentString="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.246"
            detailWebView.webViewClient=object :WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    val progressBar=findViewById<ProgressBar>(R.id.progressBar)
                    progressBar.visibility=View.GONE
                    detailWebView.visibility=View.VISIBLE
                }
            }

            detailWebView.loadUrl(url)
        }
    }
}