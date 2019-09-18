package com.group.listattract.data

interface DataCallback<T> {
    fun onCompleted(data: MutableList<T>)
    fun onError(throwable: Throwable)
}