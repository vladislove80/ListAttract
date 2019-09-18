package com.group.listattract.data.repos

import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import com.group.listattract.data.DataCallback
import com.group.listattract.data.rest.ImageLoadTask
import com.group.listattract.data.rest.RestTask
import com.group.listattract.model.Item
import java.util.concurrent.ExecutorService

class RepositoryImpl private constructor() : Repository {
    private val executorService: ExecutorService
    private val uiHandler: Handler

    init {
        val taskExecutor = TaskExecutor()
        executorService = taskExecutor.getThreadPoolExecutor()
        uiHandler = Handler(Looper.getMainLooper())
    }

    override fun getItems(callback: DataCallback<Item>) {
        executorService.execute(RestTask(uiHandler, callback))
    }

    fun loadImage(url: String, onLoaded: (Bitmap?) -> Unit) {
        executorService.execute(ImageLoadTask(uiHandler, url, onLoaded))
    }

    /*    override fun getItemDescription(callback: DataCallback<ItemDescription>) {
            executorService.execute(RestTask(uiHandler, callback))
        }*/
    companion object {
        private var INSTANCE: RepositoryImpl? = null

        @JvmStatic
        fun getInstance() = INSTANCE ?: synchronized(RepositoryImpl::class.java) {
            INSTANCE ?: RepositoryImpl().also {
                INSTANCE = it
            }
        }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}