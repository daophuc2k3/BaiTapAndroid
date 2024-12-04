package com.example.studentmanagerw12

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var studentListView: ListView
    private lateinit var studentAdapter: ArrayAdapter<String>
    private lateinit var addButton: Button
    private val students = mutableListOf(
        StudentModel("Đỗ Đào Phúc", "20210684"),
        StudentModel("Nguyễn Văn A", "SV002")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentListView = findViewById(R.id.list_view_students)
        addButton = findViewById(R.id.btn_add_student)
        studentAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students.map { it.toString() })
        studentListView.adapter = studentAdapter

        // Đăng ký context menu cho ListView
        registerForContextMenu(studentListView)

        // Sự kiện click vào item để chỉnh sửa
        studentListView.setOnItemClickListener { _, _, position, _ ->
            editStudent(position)
        }

        // Mở AddStudentActivity khi nhấn nút thêm sinh viên
        addButton.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivityForResult(intent, REQUEST_ADD_STUDENT)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_new -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD_STUDENT)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.menu_edit -> {
                editStudent(info.position)
                true
            }
            R.id.menu_remove -> {
                removeStudent(info.position)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun editStudent(position: Int) {
        val intent = Intent(this, EditStudentActivity::class.java).apply {
            putExtra(EXTRA_STUDENT_NAME, students[position].studentName)
            putExtra(EXTRA_STUDENT_ID, students[position].studentId)
            putExtra(EXTRA_STUDENT_POSITION, position)
        }
        startActivityForResult(intent, REQUEST_EDIT_STUDENT)
    }

    private fun removeStudent(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete this student?")
            .setPositiveButton("Yes") { _, _ ->
                students.removeAt(position)
                studentAdapter.clear()
                studentAdapter.addAll(students.map { it.toString() })
                studentAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("No", null)
            .create()
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val studentName = data.getStringExtra(EXTRA_STUDENT_NAME) ?: return
            val studentId = data.getStringExtra(EXTRA_STUDENT_ID) ?: return
            when (requestCode) {
                REQUEST_ADD_STUDENT -> {
                    students.add(StudentModel(studentName, studentId))
                    studentAdapter.clear()
                    studentAdapter.addAll(students.map { it.toString() })
                    studentAdapter.notifyDataSetChanged()
                }
                REQUEST_EDIT_STUDENT -> {
                    val position = data.getIntExtra(EXTRA_STUDENT_POSITION, -1)
                    if (position != -1) {
                        students[position] = StudentModel(studentName, studentId)
                        studentAdapter.clear()
                        studentAdapter.addAll(students.map { it.toString() })
                        studentAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    companion object {
        const val REQUEST_ADD_STUDENT = 1
        const val REQUEST_EDIT_STUDENT = 2
        const val EXTRA_STUDENT_NAME = "EXTRA_STUDENT_NAME"
        const val EXTRA_STUDENT_ID = "EXTRA_STUDENT_ID"
        const val EXTRA_STUDENT_POSITION = "EXTRA_STUDENT_POSITION"
    }
}
