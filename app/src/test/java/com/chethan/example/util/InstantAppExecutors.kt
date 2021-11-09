package com.chethan.example.util

import com.chethan.common.AppExecutors
import java.util.concurrent.Executor

/**
 * Created by Chethan on 10/12/2021.
 */

class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}
