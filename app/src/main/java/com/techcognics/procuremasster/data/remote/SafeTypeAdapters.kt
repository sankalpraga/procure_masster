package com.techcognics.procuremasster.data.remote

import com.google.gson.*
import java.lang.reflect.Type

class SafeDoubleAdapter : JsonDeserializer<Double> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Double {
        return try {
            json?.asDouble ?: 0.0
        } catch (e: Exception) {
            try { json?.asString?.toDoubleOrNull() ?: 0.0 } catch (e: Exception) { 0.0 }
        }
    }
}

class SafeIntAdapter : JsonDeserializer<Int> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Int {
        return try {
            json?.asInt ?: 0
        } catch (e: Exception) {
            try { json?.asString?.toIntOrNull() ?: 0 } catch (e: Exception) { 0 }
        }
    }
}
