package vn.edu.hust.studentman

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        val editName = findViewById<EditText>(R.id.edit2Hoten)
        val editMssv = findViewById<EditText>(R.id.edit2Mssv)

        val name = intent.getStringExtra("hoten")
        val mssv = intent.getStringExtra("mssv")
        val position = intent.getIntExtra("position", -1)

        editName.setText(name)
        editMssv.setText(mssv)

        findViewById<Button>(R.id.button_save).setOnClickListener {
            val updatedName = editName.text.toString()
            val updatedMssv = editMssv.text.toString()

            val resultIntent = intent.apply {
                putExtra("updatedName", updatedName)
                putExtra("updatedMssv", updatedMssv)
                putExtra("position", position)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        findViewById<Button>(R.id.button_cancle_edit).setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}