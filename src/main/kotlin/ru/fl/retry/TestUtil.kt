package ru.fl.retry

import org.testng.ITestResult
import java.util.*

object TestUtil {
    fun getTests(iTestResult: MutableList<ITestResult>): HashMap<String, MutableList<String>> {
        val failedMethods = mutableListOf<String>()
        val failedTests = HashMap<String, MutableList<String>>()
        for (test in iTestResult) {
            val methodName = test.method.methodName.toString()
            val className = test.testClass.name.toString()
            failedMethods.add(methodName)
            failedTests[className] = failedMethods
        }
        return failedTests
    }
}