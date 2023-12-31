package ru.fl.retry

import org.testng.IRetryAnalyzer
import org.testng.ITestResult

class Retry : IRetryAnalyzer {
    private var count = 0
    override fun retry(iTestResult: ITestResult): Boolean {
        if (!iTestResult.isSuccess) {
            if (count < maxTry) {
                count++
                iTestResult.status = ITestResult.SKIP
                return true
            } else {
                iTestResult.status = ITestResult.FAILURE
            }
        } else {
            iTestResult.status = ITestResult.SUCCESS
        }
        return false
    }

    companion object {
        private const val maxTry = 1
    }
}