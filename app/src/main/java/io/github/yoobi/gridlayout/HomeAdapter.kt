package io.github.yoobi.gridlayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager


class HomeAdapter(private val requestManager: RequestManager,
                  private val layoutManager: GridLayoutManager? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: List<ImageObject> = listOf()

    enum class ViewType {
        ONE_COLUMN,
        TWO_COLUMN,
        THREE_COLUMN
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.ONE_COLUMN.ordinal -> OneColumnViewHolder.from(parent)
            ViewType.TWO_COLUMN.ordinal -> TwoColumnViewHolder.from(parent)
            else -> ThreeColumnViewHolder.from(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(layoutManager?.spanCount){
            2 -> ViewType.TWO_COLUMN.ordinal
            3 -> ViewType.THREE_COLUMN.ordinal
            else -> ViewType.ONE_COLUMN.ordinal
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        (holder as HomeViewHolder).bind(item, requestManager)
    }

    class HomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.findViewById(R.id.title)
        private val description: TextView = itemView.findViewById(R.id.description)
        private val imageView: ImageView = itemView.findViewById(R.id.image)

        fun bind(imageObject: ImageObject, requestManager: RequestManager){
            title.text = imageObject.author
            description.text = imageObject.id
            requestManager.load(imageObject.url).into(imageView)
        }

    }
}

class OneColumnViewHolder(view: View): RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): HomeAdapter.HomeViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_1c, parent, false)
            return HomeAdapter.HomeViewHolder(view)
        }
    }
}

class TwoColumnViewHolder(view: View): RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): HomeAdapter.HomeViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_2c, parent, false)
            return HomeAdapter.HomeViewHolder(view)
        }
    }
}

class ThreeColumnViewHolder(view: View): RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): HomeAdapter.HomeViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image_3c, parent, false)
            return HomeAdapter.HomeViewHolder(view)
        }
    }
}
