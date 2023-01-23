package com.neolink.ahoralotengo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.kittinunf.fuel.core.FuelManager
import com.neolink.ahoralotengo.data.dbHandler
import com.neolink.ahoralotengo.utils.GeneralUtils
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException

class MainActivity : AppCompatActivity() {
    private lateinit var gu: GeneralUtils
    private lateinit var dbh: dbHandler
    private val ALL = 1
    private var aileron = false
    private var vc = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gu = GeneralUtils(this)
        gu.setRestServer("production")
        dbh = dbHandler(this, null)
        vc = BuildConfig.VERSION_CODE

        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            val sslSocketFactory = sslContext.socketFactory

            FuelManager.instance.socketFactory = sslSocketFactory
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        if(checkAndRequestPermissions()){
            aileron = true
            contineExecution()
        }
    }

    private fun displayLogo() {
        Handler().postDelayed({
            goToHome()
        }, 2000)
    }

    private fun goToHome() {
        val i = Intent(applicationContext, HomeActivity :: class.java)
        startActivity(i)
    }

    private fun checkAndRequestPermissions(): Boolean {
        val p1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
        val p2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
        val p3 = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)

        val listPermissionsNeeded = ArrayList<String>()

        if (p1 != PackageManager.PERMISSION_GRANTED) {listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE)}
        if (p2 != PackageManager.PERMISSION_GRANTED) {listPermissionsNeeded.add(Manifest.permission.ACCESS_WIFI_STATE)}
        if (p3 != PackageManager.PERMISSION_GRANTED) {listPermissionsNeeded.add(Manifest.permission.INTERNET)}

        if (listPermissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), ALL)
            return false
        }

        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val listPermissionsNeeded = ArrayList<String>()

        val p1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
        val p2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        val p3 = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)

        if (p1 != PackageManager.PERMISSION_GRANTED) {listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE)}
        if (p2 != PackageManager.PERMISSION_GRANTED) {listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE)}
        if (p3 != PackageManager.PERMISSION_GRANTED) {listPermissionsNeeded.add(Manifest.permission.INTERNET)}

        if (listPermissionsNeeded.isEmpty()) {
            aileron = true
            contineExecution()
        } else {
            aileron = false
        }
    }

    private fun contineExecution() {
        if(aileron){
            Handler().postDelayed({
                goToHome()
            }, 2000)
        }
    }
}