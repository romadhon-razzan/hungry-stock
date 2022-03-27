package id.co.ptn.hungrystock.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

class EmailValidation {
    companion object {
        fun check(email: String): Boolean {
            val regex = "^(.+)@(.+)$"
            val pattern: Pattern = Pattern.compile(regex)
            val matcher: Matcher = pattern.matcher(email)
            return matcher.matches()
        }
    }
}