package ru.fl.retry

import org.testng.IAnnotationTransformer
import org.testng.annotations.ITestAnnotation
import java.lang.reflect.Constructor
import java.lang.reflect.Method


class AnnotationTransformer : IAnnotationTransformer {
    override fun transform(
        annotation: ITestAnnotation,
        testClass: Class<*>?,
        testConstructor: Constructor<*>?,
        testMethod: Method
    ) {
        annotation.setRetryAnalyzer(Retry::class.java)
    }
}
