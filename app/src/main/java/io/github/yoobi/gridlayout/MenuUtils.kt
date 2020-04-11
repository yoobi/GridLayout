package io.github.yoobi.gridlayout

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.util.TypedValue
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


fun menuItemSelected(sharedPreferences: SharedPreferences, item: MenuItem){
    val editor = sharedPreferences.edit()
    when(item.itemId){
        R.id.col1 -> {
            editor.putInt("col", 1)
            item.isChecked = true
        }
        R.id.col2 -> {
            editor.putInt("col", 2)
            item.isChecked = true
        }
        R.id.col3 -> {
            editor.putInt("col", 3)
            item.isChecked = true
        }
    }
    editor.apply()
}

fun updateUI(recyclerView: RecyclerView, context: Context?) {
    val sharedPreferences = context?.getSharedPreferences("GRIDLAYOUT", Context.MODE_PRIVATE)
    val nbCol = sharedPreferences?.getInt("col", 1)!!
    val gridLayoutManager = (recyclerView.layoutManager as GridLayoutManager)

    // Set ItemDecoration
    if (nbCol > 1) {
        if (recyclerView.itemDecorationCount == 0) {
            val padding = context.resources.getDimensionPixelSize(R.dimen.spacing_item)
            val itemItemDecoration = ImageItemDecoration(padding)
            recyclerView.addItemDecoration(itemItemDecoration)
        }
        // Avoid flickering of layout if you select the same number of column again
        if(gridLayoutManager.spanCount != nbCol) {
            gridLayoutManager.spanCount = nbCol
            recyclerView.adapter?.notifyItemRangeChanged(0, recyclerView.adapter?.itemCount ?: 0)
        }
    }

    if (recyclerView.itemDecorationCount == 1 && nbCol == 1) {
        recyclerView.removeItemDecorationAt(0)
        gridLayoutManager.spanCount = nbCol
        recyclerView.adapter?.notifyItemRangeChanged(0, recyclerView.adapter?.itemCount ?: 0)

    }

}
