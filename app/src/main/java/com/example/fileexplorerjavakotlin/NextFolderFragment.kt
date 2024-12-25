package com.example.fileexplorerjavakotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File


class NextFolderFragment : Fragment() {
    var address : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_next_folder, container, false)
        arguments?.let {
            address = it.getString("address")
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listFile = ArrayList<FileModel>()
        val directory = File(address.toString())
        for (entry in directory.listFiles()) {
            if (entry.isDirectory) {
                listFile.add(FileModel(R.drawable.folder, entry.name.toString(), entry.path, "folder"))
            }
            if (entry.isFile) {
                listFile.add(FileModel(R.drawable.file, entry.name.toString(), entry.path, "file"))
            }
        }

        val listInFolder = view.findViewById<RecyclerView>(R.id.listInFolder)
        listInFolder.layoutManager = LinearLayoutManager(requireContext())
        listInFolder.adapter = MyAdapter(listFile)
        //registerForContextMenu(listInFolder)
    }

}