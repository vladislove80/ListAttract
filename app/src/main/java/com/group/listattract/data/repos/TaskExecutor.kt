package com.group.listattract.data.repos

import java.util.concurrent.*

private const val SCALE_POOL_SIZE = 4
private const val KEEP_ALIVE_TIME = 15

class TaskExecutor : Executor {

    private val processors = Runtime.getRuntime().availableProcessors()
    private val initialPoolSize = if (processors > 1) processors else 2
    private val maxPoolSize = initialPoolSize * SCALE_POOL_SIZE
    private val keepAliveTimeUnit = TimeUnit.SECONDS
    private val threadPoolExecutor: ThreadPoolExecutor

    init {
        val workQueue = LinkedBlockingQueue<Runnable>()
        val threadFactory = JobThreadFactory()
        this.threadPoolExecutor = ThreadPoolExecutor(
            initialPoolSize, maxPoolSize,
            KEEP_ALIVE_TIME.toLong(), keepAliveTimeUnit, workQueue, threadFactory
        )
    }

    fun getThreadPoolExecutor(): ThreadPoolExecutor {
        return threadPoolExecutor
    }

    override fun execute(command: Runnable) {
        this.threadPoolExecutor.execute(command)
    }
}

private class JobThreadFactory : ThreadFactory {
    private var counter = 0

    override fun newThread(runnable: Runnable): Thread {
        return Thread(runnable, THREAD_NAME + counter++)
    }

    companion object {
        private const val THREAD_NAME = "listattract-thread-"
    }
}