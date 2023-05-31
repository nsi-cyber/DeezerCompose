package com.nsicyber.deezercompose.utils

import java.text.SimpleDateFormat
import java.util.Locale


fun convertDate(dateString: String?): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("d MMMM, yyyy", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date)
}