package com.example.studentmanagerw12

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {

    private lateinit var editTextStudentName: EditText
    private lateinit var editTextStudentId: EditText
    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        editTextStudentName = findViewById(R.id.edit_text_student_name)
        editTextStudentId = findViewById(R.id.edit_text_student_id)
        buttonSave = findViewById(R.id.button_save)

        buttonSave.setOnClickListener {
            val resultIntent = intent.apply {
                putExtra(MainActivity.EXTRA_STUDENT_NAME, editTextStudentName.text.toString())
                putExtra(MainActivity.EXTRA_STUDENT_ID, editTextStudentId.text.toString())
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
