package com.neolink.ahoralotengo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import com.neolink.ahoralotengo.adapters.AdapterFI
import com.neolink.ahoralotengo.adapters.AdapterHI
import com.neolink.ahoralotengo.adapters.ItemFI
import com.neolink.ahoralotengo.adapters.ItemHI
import com.neolink.ahoralotengo.utils.GeneralUtils
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var linearLayoutManager2: LinearLayoutManager
    private lateinit var adapter: AdapterFI
    private lateinit var adapter2: AdapterHI
    private lateinit var items: ArrayList<ItemFI>
    private lateinit var items2: ArrayList<ItemHI>
    private lateinit var item: ItemFI
    private lateinit var item2: ItemHI
    private lateinit var gu: GeneralUtils
    private var sRes = ""; private var sMen = ""; private var s_pid = ""; private var s_tit = ""; private var s_pre = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        gu = GeneralUtils(this)
        gu.setRestServer("production")
        FuelManager.instance.basePath = gu.baseUrl

        initFeatureItemsLayout()
    }

    private fun initFeatureItemsLayout() {
        linearLayoutManager = LinearLayoutManager(application, LinearLayoutManager.HORIZONTAL, false)
        rv_featureitem.layoutManager = linearLayoutManager
        items = ArrayList<ItemFI>()
        adapter = AdapterFI(items, applicationContext)

        rv_featureitem.adapter = adapter

        initHomeItemsLayout()
    }

    private fun initHomeItemsLayout() {
        linearLayoutManager2 = LinearLayoutManager(application, LinearLayoutManager.VERTICAL, false)

        rv_homeitem.layoutManager = linearLayoutManager2
        items2 = ArrayList<ItemHI>()
        adapter2 = AdapterHI(items2, applicationContext)

        rv_homeitem.adapter = adapter2

        pullFeatureItems()
    }

    private fun pullFeatureItems() {
        if(gu.isNetworkAvailable) {
            try {
                items.clear()

                Fuel.post(gu.getUrlTarget("target"), listOf("v1" to "0")).response { request, response, result ->
                    val (bytes, error) = result
                    if (bytes != null) {
                        var json = JSONObject(String(bytes))
                        var jsdats = json.getJSONArray("dats")
                        var jsdtArrayObject = jsdats.getJSONObject(0)

                        sRes = jsdtArrayObject.getString("res")
                        sMen = jsdtArrayObject.getString("men")

                        if (sRes != "0") {
                            var jsdatarray = jsdtArrayObject.getJSONArray("dtarray")
                            for (i in 0 until jsdatarray!!.length()) {
                                s_pid = jsdatarray.getJSONObject(i).getString("pid")
                                s_tit = jsdatarray.getJSONObject(i).getString("tit")
                                s_pre = jsdatarray.getJSONObject(i).getString("pre")

                                item = ItemFI(s_pid, s_tit, s_pre);
                                items.add(item)
                            }

                            adapter.notifyDataSetChanged()
                            dummyData()
                        }
                    } else {
                        Log.i("ALTAPP", "wrong");
                    }
                }
            } catch (e: Exception) {
                Log.i("ALTAPP", e.toString());
            }
        } else {
            gu.displayAlert("SIN ACCESO A INTERNET", "AHORALOTENGO requiere acceso a Internet. Verifica tu conexion o revisa si esta App tiene Permisos para Internet")
        }
    }

    private fun dummyData() {
        item2 = ItemHI("VISTOS RECIENTEMENTE", "213", "207", "200", "188", "TITULO 1", "TITULO 2", "TITULO 3", "TITULO 4", "0.00", "0.00", "0.00", "0.00")
        items2.add(item2)
        item2 = ItemHI("RECOMENDACIONES PARA TI", "210", "203", "208", "197", "TITULO 1", "TITULO 2", "TITULO 3", "TITULO 4", "0.00", "0.00", "0.00", "0.00")
        items2.add(item2)

        adapter2.notifyDataSetChanged()
    }
}