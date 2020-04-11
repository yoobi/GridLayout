package io.github.yoobi.gridlayout

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions

class HomeFragment: Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        sharedPreferences = activity!!.getSharedPreferences("GRIDLAYOUT", Context.MODE_PRIVATE)
        recyclerView = view.findViewById(R.id.rv_image)

        val nbCol = sharedPreferences.getInt("col",1)
        val layoutManager = GridLayoutManager(context, nbCol, GridLayoutManager.VERTICAL, false)
        val adapter = HomeAdapter(initGlide(), layoutManager)

        recyclerView.layoutManager = layoutManager

        adapter.data = Resources.imageList
        recyclerView.adapter = adapter
        updateUI(recyclerView, context)
        return view
    }

    private fun initGlide(): RequestManager {
        val options: RequestOptions = RequestOptions()
            .error(R.drawable.img_error)

        return Glide.with(this)
            .setDefaultRequestOptions(options)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        when(sharedPreferences.getInt("col", 1)){
            1 -> menu.findItem(R.id.col1)?.isChecked = true
            2 -> menu.findItem(R.id.col2)?.isChecked = true
            3 -> menu.findItem(R.id.col3)?.isChecked = true
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        menuItemSelected(sharedPreferences, item)
        updateUI(recyclerView, context)

        return super.onOptionsItemSelected(item)
    }

}