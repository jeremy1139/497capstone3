package com.example.pictoevents.Calendar

import com.example.pictoevents.Dictionary.MonthDictionary
import com.example.pictoevents.Dictionary.WeekDictionary
import com.example.pictoevents.Pattern.RegExPatterns
import java.util.regex.Matcher
import java.util.regex.Pattern

class CalendarObjectsGenerator(val ocrText: String)
{
    private var formatter = CalendarObjectFormatter()
    private fun tokenizeText(): List<String>
    {
        val tokens = this.ocrText.split(" ")
        return tokens
    }

    fun identifyCalendarComponents() {

        val iterator = this.tokenizeText().listIterator()
        for (words in iterator ){
            val wordSplit = words.split(",'")

            if (!this.isValid(wordSplit)){
                // No need to proceed with the other code if
                // the word isn't valid, so move on to the next
                continue
            }

            val word = wordSplit[0].toLowerCase()
            // Preset pattern to default/ false
            val hasDatePattern = this.findDatePattern(word)
            val hasAMPMPattern = this.findAMPMPattern(word)
            var hasTimePattern = false
            var hasYearPattern = false
            var hasDaysPattern = false
            var matchWeek = false
            var matchMonth = false

            // Find if the string matches the patterns
            if(!hasDatePattern){
                hasTimePattern = this.findTimePattern(word)
            }
            if(!hasDatePattern && !hasTimePattern){
                hasDaysPattern = this.findDayPattern(word)

                if(hasDatePattern && word.length > 4){
                    hasDaysPattern = false
                }
            }
            if(!hasDatePattern && !hasTimePattern && !hasDaysPattern){
                hasYearPattern = this.findYearPattern(word)
            }
            if(!hasDatePattern && !hasTimePattern && !hasYearPattern && !hasDaysPattern){
                matchWeek = WeekDictionary().weekDict().contains(word)
                matchMonth = MonthDictionary().getMonthDict().contains(word)
            }

            if(hasDatePattern){
                if(!formatter.monthFromDate.equals("") &&
                    !formatter.dayFromDate.equals("") &&
                    !formatter.yearFromDate.equals("")){
                    this.decomposeDate(word)
                }
            }
            else if(hasTimePattern){
                if(!formatter.hourFromTime.equals("") &&
                    !formatter.minFromTime.equals("")){
                    this.decomposeTime(word)
                }
            }
            else if(hasYearPattern){
                if(!formatter.fullYear.equals("")){
                    this.decomposeYear(word)
                }
            }
            else if(hasDaysPattern){
                if(!formatter.dayOfMonth.equals("")){
                    this.decomposeDay(word)
                }
            }
            else if(hasAMPMPattern){
                if(!formatter.ampm.equals("")){
                    this.decomposeAMPM(word)
                }
            }
            else if(matchWeek){
                if(!formatter.weekdayName.equals("")){
                    this.decomposeWeek(word)
                }
            }
            else if(matchMonth){
                if(!formatter.monthName.equals("")){
                    this.decomposeMonth(word)
                }
            }
        }
    }

    fun getObjectFormatter(): CalendarObjectFormatter{
        return formatter
    }
    private fun findDatePattern(word: String): Boolean{

        val datePattern = Pattern.compile(RegExPatterns.DATE)
        val dateMatcher = datePattern.matcher(word)

        return this.foundMatch(dateMatcher)
    }

    private fun findTimePattern(word: String): Boolean{

        val timePattern = Pattern.compile(RegExPatterns.TIME)
        val timeMatcher = timePattern.matcher(word)

        return this.foundMatch(timeMatcher)
    }

    private fun findYearPattern(word: String): Boolean{

        val yearPattern = Pattern.compile(RegExPatterns.YEAR)
        val yearMatcher = yearPattern.matcher(word)

        return this.foundMatch(yearMatcher)
    }

    private fun findDayPattern(word: String): Boolean{

        val dayPattern = Pattern.compile(RegExPatterns.DAY)
        val dayMatcher = dayPattern.matcher(word)

        return this.foundMatch(dayMatcher)
    }

    private fun findAMPMPattern(word: String): Boolean{

        val ampmPattern = Pattern.compile(RegExPatterns.AMPM)
        val ampmMatcher = ampmPattern.matcher(word)

        return this.foundMatch(ampmMatcher)
    }

    private fun foundMatch(_matcher: Matcher): Boolean {
        var foundMatch = false
        while (_matcher.find()) {
            if (_matcher.group().isNotEmpty()) {
                foundMatch = true
            }
        }
        return foundMatch
    }

    private fun isValid(word: List<String>): Boolean {
        if (word.isEmpty() || word[0] == "") {
            //Contains nothing

            return false
        }
        if (word.size <= 2 && word[0].matches("\\w".toRegex())) {
            //Is a word less than 2 chars
            return false
        }
        if (word.size > 7 && word[0].matches("[^0-9]".toRegex())) {
            //Is a word with more than 7 chars (contains no numbers)
            return false
        }
        return true
    }

    private fun decomposeDate(date: String){
        // Sample date pattern 3/5/2020
        // Assumption M-D-Y formats
        val dates = date.split("(/|-)".toRegex())
        if(dates.size == 3){
            formatter.monthFromDate = dates[0]
            formatter.dayFromDate = dates[1]
            formatter.yearFromDate = dates[2]
        }
    }

    private fun decomposeTime(time: String){
        //Sample time 12:01
        val stime = time.split(":")
        if(stime.size == 2){
            formatter.hourFromTime = stime[0]
            formatter.minFromTime = stime[1]
        }
    }

    private fun decomposeAMPM(ampm: String){
        formatter.ampm = ampm
    }

    private fun decomposeMonth(month: String){
        formatter.monthName = month
    }

    private fun decomposeDay(day: String){
        formatter.dayOfMonth = day
    }

    private fun decomposeYear(year: String){
        formatter.fullYear = year
    }

    private fun decomposeWeek(week: String){
        formatter.weekdayName = week
    }
}