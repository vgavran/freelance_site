package ru.fl.appmanager

open class RandomUtils {
    companion object {
        fun randomNumberLetter(): String {
            val s = "qwertyuiopasdghjklzxcbnm0123456789"
            val newMail = StringBuffer()
            for (i in 0 until 12) {
                newMail.append(s.random())
            }
            return newMail.toString()
        }

        fun getRandomNumbers(count: Int): String {
            val s = "0123456789"
            val newMail = StringBuffer()
            for (i in 0 until count) {
                newMail.append(s.random())
            }
            return newMail.toString()
        }

        fun randomLetters(): String {
            val s = "qwertyuiopasdghjklzxcbnm"
            val newMail = StringBuffer()
            for (i in 0 until 12) {
                newMail.append(s.random())
            }
            return newMail.toString()
        }

        fun getRandomFee(): Int {
            val r = (1200..500000).random()
            return r
        }

        fun getRandomText(count: Int): String {
            val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') + (" ")
            return (1..count)
                .map { allowedChars.random() }
                .joinToString("")
        }
    }
}