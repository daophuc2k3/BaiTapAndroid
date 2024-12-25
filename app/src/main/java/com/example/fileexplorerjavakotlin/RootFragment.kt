package com.example.fileexplorerjavakotlin

import android.os.Bundle
import android.os.Environment
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RootFragment : Fragment() {

    val items = ArrayList<FileModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_root, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rootSD = Environment.getExternalStorageDirectory()
        for (entry in rootSD.listFiles()) {
            if (entry.isDirectory) {
                items.add(FileModel(R.drawable.folder, entry.name.toString(), entry.path, "folder"))
            } else if (entry.isFile) {
                items.add(FileModel(R.drawable.file, entry.name.toString(), entry.path, "file"))
            }
        }

        val listView = view.findViewById<RecyclerView>(R.id.listView)
        listView.layoutManager = LinearLayoutManager(requireContext())
        listView.adapter = MyAdapter(items)
        registerForContextMenu(listView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = requireActivity().menuInflater
        val menuContext = menuInfo as AdapterView.AdapterContextMenuInfo
        if (items[menuContext.position].type == "folder")
            inflater.inflate(R.menu.context_menu_folder, menu)
        else inflater.inflate(R.menu.context_menu_file, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when(item.itemId) {
            R.id.rename_file -> {
                true
            }
            R.id.delete_file -> {
                true
            }
            R.id.copy_file -> {
                true
            }
            R.id.rename_folder -> {
                true
            }
            R.id.delete_folder -> {
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

}