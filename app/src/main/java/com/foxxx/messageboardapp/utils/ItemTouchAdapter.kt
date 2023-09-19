package com.foxxx.messageboardapp.utils

interface ItemTouchAdapter {
    fun onMove(startPosition : Int, targetPosition : Int)
    fun onClear()
}