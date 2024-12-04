package com.example.studentmanagerw12

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
  private val context: Context,
  private val students: List<StudentModel>
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

  inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val studentNameTextView: TextView = itemView.findViewById(R.id.text_student_name)
    val studentIdTextView: TextView = itemView.findViewById(R.id.text_student_id)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.layout_student_item, parent, false)
    return StudentViewHolder(view)
  }

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]
    holder.studentNameTextView.text = student.studentName
    holder.studentIdTextView.text = student.studentId
  }

  override fun getItemCount(): Int {
    return students.size
  }
}
