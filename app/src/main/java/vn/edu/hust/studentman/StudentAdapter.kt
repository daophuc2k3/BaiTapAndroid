package vn.edu.hust.studentman

import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(val students: MutableList<StudentModel>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
  class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    var textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
    val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
    val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(
      R.layout.layout_student_item,
      parent, false
    )
    return StudentViewHolder(itemView)
  }

  override fun getItemCount(): Int = students.size

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.textStudentName.text = student.studentName
    holder.textStudentId.text = student.studentId

    holder.imageEdit.setOnClickListener {
      val dialog = Dialog(holder.itemView.context)
      dialog.setContentView(R.layout.add_new_student)
      val editFullname = dialog.findViewById<EditText>(R.id.edit_fullname)
      val editId = dialog.findViewById<EditText>(R.id.edit_id)
      editFullname.setText(student.studentName)
      editId.setText(student.studentId)
      dialog.findViewById<Button>(R.id.button_ok).setOnClickListener {
        student.studentName = editFullname.text.toString()
        student.studentId = editId.text.toString()
        notifyItemChanged(position)
        dialog.dismiss()
      }
      dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
        dialog.dismiss()
      }
      dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
      dialog.show()
    }

    holder.imageRemove.setOnClickListener {
      val dialog = Dialog(holder.itemView.context)
      dialog.setContentView(R.layout.delete_or_not)
      dialog.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
        dialog.dismiss()
      }
      dialog.findViewById<Button>(R.id.button_yes).setOnClickListener {
        //xóa phần tử
        students.removeAt(position)
        //cập nhật lại position sau khi xóa
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, students.size - position)

        dialog.dismiss()

        //undo snackbar
        val snackbar = Snackbar.make(holder.itemView, "Deleted", Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo") {
          students.add(position, student)
          notifyItemInserted(position)
          notifyItemRangeChanged(position, students.size - position)
        }
        snackbar.show()

      }
      dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
      dialog.show()

    }

  }
}