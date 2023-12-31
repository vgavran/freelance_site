package ru.fl.retry

import org.testng.ITestContext
import org.testng.ITestListener
import org.testng.ITestResult

class Listener : ITestListener {
    override fun onTestStart(iTestResult: ITestResult) {
        println("Test " + getTestMethodName(iTestResult) + " start")
    }

    override fun onTestSuccess(iTestResult: ITestResult) {
        println("Test " + getTestMethodName(iTestResult) + " succeed")
    }

    override fun onTestFailure(iTestResult: ITestResult) {
        println("Test " + getTestMethodName(iTestResult) + " failed")
    }

    override fun onTestSkipped(iTestResult: ITestResult) {
        println("Test " + getTestMethodName(iTestResult) + " skipped")
    }

    override fun onTestFailedButWithinSuccessPercentage(iTestResult: ITestResult) {
        println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult))
    }

    override fun onStart(iTestContext: ITestContext) {
        println("I am in onStart method " + iTestContext.name)
    }

    override fun onFinish(iTestContext: ITestContext) {
        println("I am in onFinish method " + iTestContext.name)
    }

    companion object {
        private fun getTestMethodName(iTestResult: ITestResult): String {
            return iTestResult.method.constructorOrMethod.name
        }
    }
}