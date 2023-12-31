package ru.fl.retry

import org.redundent.kotlin.xml.xml
import org.testng.ITestContext
import org.testng.TestListenerAdapter
import ru.fl.retry.TestUtil.getTests
import java.io.File

class FixRetryListener : TestListenerAdapter() {
    override fun onFinish(testContext: ITestContext) {
        super.onFinish(testContext)
        val failedTests = getTests(failedTests)
        val failed = xml("suite") {
            doctype(systemId = "http://testng.org/testng-1.0.dtd")
            attribute("name", "Failed suite [Gradle suite]")
            attribute("verbose", 0)
            "listeners" {
                "listener"{
                    attribute("class-name", "ru.fl.retry.Listener")
                }
                "listener"{
                    attribute("class-name", "ru.fl.retry.AnnotationTransformer")
                }
                "listener"{
                    attribute("class-name", "ru.fl.retry.FixRetryListener")
                }
            }
                "test" {
                    attribute("thread-count", 5)
                    attribute("name", "Gradle test(failed)")
                    attribute("verbose", 0)
                    attribute("parallel", "methods")
                    "classes" {
                        for (classElement in failedTests) {
                            "class"{
                                attribute("name", classElement.key)
                                "methods" {
                                    "include"{
                                        attribute("name", "before")
                                    }
                                    for (test in classElement.value) {
                                        "include"{
                                            attribute("name", test)
                                        }
                                    }
                                    "include"{
                                        attribute("name", "tearDown")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        val failedAsString = failed.toString()
        val writer = File("src/test/resources/failed.xml").bufferedWriter()
        writer.write(failedAsString)
        writer.close()
    }
}