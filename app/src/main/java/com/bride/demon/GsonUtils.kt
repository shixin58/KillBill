package com.bride.demon

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.lang.reflect.Type

object GsonUtils {
    private val stringMapType: Type = object : TypeToken<Map<String,Any>>(){}.type
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(stringMapType, StringMapDeserializer)
        .create()

    fun testFromJson() {
        val gson = Gson()

        val map = hashMapOf<String,Any>()
        map["price"] = 1.0
        map["age"] = 18
        map["name"] = "John"
        map["junior"] = true
        Timber.i("map:$map")

        // {"price":1.0,"name":"John","age":18}
        val json = gson.toJson(map, stringMapType)
        Timber.i("json:$json")

        // {price=1.0, name=John, age=18.0}
        val result = gson.fromJson<Map<String,Any>>(json, stringMapType)
//        val result = gson.fromJson(json, Map::class.java)
        Timber.i("result:$result")
    }

    fun stringMapFromJson(json: String): Map<String,Any> {
        return gson.fromJson(json, stringMapType)
    }
}

object StringMapDeserializer : JsonDeserializer<Map<String,Any>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Map<String, Any> {
        val map = hashMapOf<String,Any>()
        json?:return map
        json.asJsonObject
            .entrySet()
            .asSequence()
            .filter { it.value.isJsonPrimitive }
            .filter { it.value.asJsonPrimitive.let { p -> p.isString or p.isNumber or p.isBoolean } }
            .forEach {
                val p = it.value.asJsonPrimitive
                map[it.key] = when {
                    p.isNumber -> if (p.asNumber.isInteger()) p.asInt else p.asDouble
                    p.isBoolean -> p.asBoolean
                    else -> p.asString
                }
            }
        return map
    }

    private fun Number.isInteger(): Boolean {
        return toString().all { c -> c.isDigit() }
    }
}