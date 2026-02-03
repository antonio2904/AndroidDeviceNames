package com.jaredrummler.androiddevicenames

import java.io.BufferedReader
import java.io.InputStreamReader

object Devices {

    private const val URL = "https://storage.googleapis.com/play_public/supported_devices.csv"

    fun get(url: String = URL) = mutableListOf<Device>().apply {
        val conn = java.net.URL(url).openConnection()
        val brands = arrayOf(
            "Samsung",
            "Google",
            "Xiaomi",
            "OnePlus",
            "Motorola",
            "OPPO",
            "Vivo",
            "Realme",
            "Nokia",
            "Sony",
            "Huawei",
            "ASUS",
            "LG"
        )
        BufferedReader(InputStreamReader(conn.getInputStream(), "UTF-16")).use { reader ->
            var numDevices = 0
            reader.readLine() // skip header
            reader.forEachLine { line ->
                val records = line.split(",").dropLastWhile(String::isEmpty).toTypedArray()
                if (records.size == 4) {
                    val manufacturer = records[0].trim('"')
                    val name = records[1].trim('"')
                    val code = records[2].trim('"')
                    val model = records[3].trim('"')
                    if (manufacturer in brands && name.isNotBlank() && code.isNotBlank()) {
                        add(Device(manufacturer, name, code, model))
                        numDevices += 1
                    }
                }
            }
            println("Total Devices added : $numDevices")
        }
    }
}