package com.app.weatherdemo.ui.webview

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.app.weatherdemo.databinding.FragmentHomeBinding
import com.app.weatherdemo.databinding.FragmentWebViewBinding
import dagger.hilt.android.AndroidEntryPoint
import android.content.res.AssetManager
import android.view.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder


@AndroidEntryPoint
class WebViewFragment : Fragment() {

    lateinit var webViewBinding: FragmentWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        webViewBinding = FragmentWebViewBinding.inflate(inflater, container, false)
        return webViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webViewBinding.webView
            .addJavascriptInterface(KotlinClassInterface(requireContext()), "AndroidFunction");
        webViewBinding.webView.getSettings().setJavaScriptEnabled(true);
        //getHTMLData()?.let { webViewBinding.webView.loadData(it, "text/html", "UTF-8") };
        webViewBinding.webView.loadUrl("file:///android_asset/index.html");

    }

    private fun getHTMLData(): String? {
        val html = StringBuilder()
        try {
            val assetManager: AssetManager = requireContext()!!.getAssets()
            val input: InputStream = assetManager.open("index.html")
            val br = BufferedReader(InputStreamReader(input))
            var line: String?
            while (br.readLine().also { line = it } != null) {
                html.append(line)
            }
            br.close()
        } catch (e: Exception) {
                e.printStackTrace()
        }
        return html.toString()
    }

}