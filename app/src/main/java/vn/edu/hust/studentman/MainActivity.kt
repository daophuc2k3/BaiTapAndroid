package vn.edu.hust.studentman

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val students = mutableListOf(
      StudentModel("Nguyễn Văn An", "SV001"),
      StudentModel("Trần Thị Bảo", "SV002"),
      StudentModel("Lê Hoàng Cường", "SV003"),
      StudentModel("Phạm Thị Dung", "SV004"),
      StudentModel("Đỗ Minh Đức", "SV005"),
      StudentModel("Vũ Thị Hoa", "SV006"),
      StudentModel("Hoàng Văn Hải", "SV007"),
      StudentModel("Bùi Thị Hạnh", "SV008"),
      StudentModel("Đinh Văn Hùng", "SV009"),
      StudentModel("Nguyễn Thị Linh", "SV010"),
      StudentModel("Phạm Văn Long", "SV011"),
      StudentModel("Trần Thị Mai", "SV012"),
      StudentModel("Lê Thị Ngọc", "SV013"),
      StudentModel("Vũ Văn Nam", "SV014"),
      StudentModel("Hoàng Thị Phương", "SV015"),
      StudentModel("Đỗ Văn Quân", "SV016"),
      StudentModel("Nguyễn Thị Thu", "SV017"),
      StudentModel("Trần Văn Tài", "SV018"),
      StudentModel("Phạm Thị Tuyết", "SV019"),
      StudentModel("Lê Văn Vũ", "SV020")
    )

    val studentAdapter = StudentAdapter(students)

    findViewById<RecyclerView>(R.id.recycler_view_students).run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      //show layout add_new_student
      val dialog= Dialog(this)
      dialog.setContentView(R.layout.add_new_student)
      val editFullname = dialog.findViewById<EditText>(R.id.edit_fullname)
      val editId = dialog.findViewById<EditText>(R.id.edit_id)
      dialog.findViewById<Button>(R.id.button_ok).setOnClickListener {
        val student = StudentModel(editFullname.text.toString(), editId.text.toString())
        students.add(student)
        studentAdapter.notifyItemInserted(students.size - 1)
        dialog.dismiss()
      }
      dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
        dialog.dismiss()
      }
      dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
      dialog.show()
    }

    //edit button


  }
}