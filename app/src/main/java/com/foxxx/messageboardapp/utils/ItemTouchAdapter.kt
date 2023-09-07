package com.foxxx.messageboardapp.utils

interface ItemTouchAdapter {
    fun onMove(startPos : Int, targetPos : Int)
    fun onClear()
}