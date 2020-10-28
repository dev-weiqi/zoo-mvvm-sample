package dev.weiqi.zoo.testtool.rule

import android.util.Log
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement


class LogRule : TestRule {

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {

                mockkStatic(Log::class)
                every { Log.v(any(), any()) } returns Log.VERBOSE
                every { Log.v(any(), any(), any()) } returns Log.VERBOSE
                every { Log.d(any(), any()) } returns Log.DEBUG
                every { Log.d(any(), any(), any()) } returns Log.DEBUG
                every { Log.i(any(), any()) } returns Log.INFO
                every { Log.i(any(), any(), any()) } returns Log.INFO
                every { Log.w(any(), any(), any()) } returns Log.WARN
                every { Log.w(any(), any<String>()) } returns Log.WARN
                every { Log.w(any(), any<Throwable>()) } returns Log.WARN
                every { Log.e(any(), any()) } returns Log.ERROR
                every { Log.e(any(), any(), any()) } returns Log.ERROR

                base?.evaluate()
            }
        }
    }

}