package com.teaml.circulartimerview


enum class TimeFormatEnum {
    MILLIS, SECONDS, MINUTES, HOUR, DAY;

    fun canonicalForm(): String {
        return name
    }

    companion object {
        fun fromCanonicalForm(canonical: String): TimeFormatEnum {
            return valueOf(canonical)
        }
    }
}