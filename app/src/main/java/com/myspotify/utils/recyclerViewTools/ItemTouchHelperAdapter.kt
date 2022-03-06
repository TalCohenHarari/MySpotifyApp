package com.myspotify.utils.recyclerViewTools

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemSwiped(position: Int)
}