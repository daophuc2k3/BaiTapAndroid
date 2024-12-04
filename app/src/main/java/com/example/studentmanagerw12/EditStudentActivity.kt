package com.example.studentmanagerw12

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {

    private lateinit var editTextStudentName: EditText
    private lateinit var editTextStudentId: EditText
    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        editTextStudentName = findViewById(R.id.edit_text_student_name)
        editTextStudentId = findViewById(R.id.edit_text_student_id)
        buttonSave = findViewById(R.id.button_save)

        val studentName = intent.getStringExtra(MainActivity.EXTRA_STUDENT_NAME)
        val studentId = intent.getStringExtra(MainActivity.EXTRA_STUDENT_ID)
        val position = intent.getIntExtra(MainActivity.EXTRA_STUDENT_POSITION, -1)

        if (studentName != null) {
            editTextStudentName.setText(studentName)
        }
        if (studentId != null) {
            editTextStudentId.setText(studentId)
        }

        buttonSave.setOnClickListener {
            val resultIntent = intent.apply {
                putExtra(MainActivity.EXTRA_STUDENT_NAME, editTextStudentName.text.toString())
                putExtra(MainActivity.EXTRA_STUDENT_ID, editTextStudentId.text.toString())
                if (position != -1) putExtra(MainActivity.EXTRA_STUDENT_POSITION, position)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
