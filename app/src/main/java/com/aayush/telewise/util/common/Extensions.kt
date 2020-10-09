package com.aayush.telewise.util.common

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

fun String.toFormattedDate(): String = LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    .format(
        DateTimeFormatter.ofPattern("dd MMMM yyyy")
    )
