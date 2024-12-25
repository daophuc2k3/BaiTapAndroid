package com.example.fileexplorerjavakotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val items : ArrayList<FileModel>) : RecyclerView.Adapter<MyAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.name.text = items[position].name
        holder.icon.setImageResource(items[position].icon)
//        holder.itemView.setOnClickListener {
//            val nextFolderFragment = NextFolderFragment()
//            val bundle = Bundle()
//            bundle.putString("address", items[position].address)
//            nextFolderFragment.arguments = bundle
//            (it.context as AppCompatActivity)
//                .supportFragmentManager
//                .beginTransaction()
//                .add(R.id.ListFile, nextFolderFragment)
//                .addToBackStack("Folder")
//                .commit()
//        }

//        holder.itemView.setOnLongClickListener {
//            it.showContextMenu()
//        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    class ItemViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon = itemView.findViewById<ImageView>(R.id.icon)
        val name = itemView.findViewById<TextView>(R.id.name)
    }
}