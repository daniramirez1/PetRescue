package com.ucompensar.petrescue

import android.icu.util.Calendar
import android.text.format.DateFormat
import java.util.Locale

object Constantes {
    fun obtenerTiempoD(): Long{
        return System.currentTimeMillis()
    }
    fun formatoFecha (tiempo : Long): String{
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = tiempo

        return DateFormat.format("dd/MM/yyyy", calendar.timeInMillis).toString()

    }

}