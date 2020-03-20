package com.example.pictoevents.Calendar

class CalendarObjectFormatter {
    // Raw values
    var yearFromDate = ""
    var monthFromDate = ""
    var dayFromDate = ""
    var hourFromTime = ""
    var minFromTime = ""
    var secFromTime = ""
    var ampm = ""
    var monthName = ""
    var dayOfMonth = ""
    var fullYear = ""
    var weekdayName = ""

    fun getFormattedMonth(): Int{
        var formattedMonth = ""
        if(monthName.isNotEmpty()){
            when (monthName) {
                "january", "jan" -> formattedMonth = "01"
                "february", "feb" -> formattedMonth = "02"
                "march", "mar" -> formattedMonth = "03"
                "april", "apr" -> formattedMonth = "04"
                "may" -> formattedMonth = "05"
                "june", "jun" -> formattedMonth = "06"
                "july", "jul" -> formattedMonth = "07"
                "august", "aug" -> formattedMonth = "08"
                "september", "sep" -> formattedMonth = "09"
                "october", "oct" -> formattedMonth = "10"
                "november", "nov" -> formattedMonth = "11"
                "december", "dec" -> formattedMonth = "12"
            }
        }
        if(monthFromDate.isNotEmpty()){
            formattedMonth = monthFromDate
        }
        return Integer.parseInt(formattedMonth) - 1
    }

    fun getFormattedDay(): Int{
        var formattedDay = ""
        if(dayOfMonth.isNotEmpty()){
            formattedDay = if(dayOfMonth.length == 4) dayOfMonth.replace("(st|nd|rd|th)".toRegex(), "") else dayOfMonth
        }
        if(dayFromDate.isNotEmpty()){
            formattedDay = dayFromDate
        }
        when (formattedDay) {
            "1" -> formattedDay = "01"
            "2" -> formattedDay = "02"
            "3" -> formattedDay = "03"
            "4" -> formattedDay = "04"
            "5" -> formattedDay = "05"
            "6" -> formattedDay = "06"
            "7" -> formattedDay = "07"
            "8" -> formattedDay = "08"
            "9" -> formattedDay = "09"
            else -> formattedDay = dayOfMonth
        }
        return Integer.parseInt(formattedDay)
    }

    fun getFormattedYear(): Int{
        var formattedYear = ""
        if(yearFromDate.isNotEmpty()){
            if(yearFromDate.length == 2){
                formattedYear = "20" + yearFromDate
            }
            if(yearFromDate.length == 4){
                formattedYear = yearFromDate
            }
        }
        if(fullYear.isNotEmpty()){
            formattedYear = fullYear
        }
        return Integer.parseInt(formattedYear)
    }

    fun getFormattedHour(): Int{
        var formattedHour = ""
        if(hourFromTime.isNotEmpty()){
            if(ampm.isNotEmpty()){
                when (ampm){
                    "am" -> formattedHour = hourFromTime
                    "pm" -> formattedHour = hourFromTime + 12
                }
            }
            else{
                formattedHour = hourFromTime
            }
        }
        return Integer.parseInt(formattedHour)
    }

    fun getFormattedMin(): Int{
        var formattedMin = ""
        if(minFromTime.isNotEmpty()){
            formattedMin = minFromTime
        }
        else{
            formattedMin = "00"
        }
        return Integer.parseInt(formattedMin)
    }

    fun getFormattedAMPM(): Int{
        var formattedAMPM = ""
        if(ampm.isNotEmpty()){
            when(ampm){
                "am"-> formattedAMPM = "0"
                "pm"-> formattedAMPM = "1"
            }
        }
        return Integer.parseInt(formattedAMPM)
    }
}