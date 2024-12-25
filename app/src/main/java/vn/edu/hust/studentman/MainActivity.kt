package vn.edu.hust.studentman

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
  lateinit var optionLauncher: ActivityResultLauncher<Intent>
  lateinit var contextLauncher: ActivityResultLauncher<Intent>
  lateinit var studentAdapter: StudentAdapter
  lateinit var db: SQLiteDatabase
  lateinit var students: MutableList<StudentModel>
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)

    val path = filesDir.path + "/mydb"
    Log.v("TAG", path)
    db = SQLiteDatabase.openDatabase(path,null, SQLiteDatabase.CREATE_IF_NECESSARY)
//    createTable()

    students = mutableListOf()

    val cs = db.query(
      "student_table",
      arrayOf("sid", "name"),
      null, null, null, null, null
    )
    cs.moveToFirst()
    do {
      val sid = cs.getString(0)
      val name = cs.getString(1)
      students.add(StudentModel(studentName = name, studentId = sid))
    } while (cs.moveToNext())
    cs.close()

    val parentView = findViewById<RecyclerView>(R.id.recycler_view_students)

    contextLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
      {
          it: ActivityResult ->
        if (it.resultCode == RESULT_OK) {
          val updatedName = it.data?.getStringExtra("updatedName")
          val updatedMssv = it.data?.getStringExtra("updatedMssv")
          val position = it.data?.getIntExtra("position", -1) ?: -1

          val oldName = students[position].studentName
          val oldSid = students[position].studentId

          if (updatedName != null) {
            students[position].studentName = updatedName
          }
          if (updatedMssv != null) {
            students[position].studentId = updatedMssv
          }

          var updatedRecID: Int = -1
          val cursor = db.rawQuery("SELECT recID FROM student_table WHERE sid = ? AND name = ?", arrayOf(oldSid, oldName))
          if (cursor.moveToFirst()) {
            updatedRecID = cursor.getInt(0) // Lấy giá trị recID
          }
          cursor.close()
          db.beginTransaction()
          try {
            db.execSQL(
              "UPDATE student_table SET name = ?, sid = ? WHERE recID = ?",
              arrayOf(updatedName, updatedMssv, updatedRecID.toString())
            )
            db.setTransactionSuccessful()
            Log.v("TAG", "DB updated successfully")
          } catch (ex: Exception) {
            ex.printStackTrace()
          } finally {
            db.endTransaction()
          }

          studentAdapter.notifyItemChanged(position)
        }
      }
    )

    studentAdapter = StudentAdapter(students, this@MainActivity, parentView, contextLauncher, db)

    parentView.run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    optionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
      {
        it: ActivityResult ->
        if (it.resultCode == RESULT_OK) {
          val hoten = it.data?.getStringExtra("hoten")
          val mssv = it.data?.getStringExtra("mssv")
          if (hoten != null && mssv != null) {
            students.add(StudentModel(studentName = hoten, studentId = mssv))
            db.beginTransaction()
            try {
              val values = ContentValues()
              values.put("sid", mssv)
              values.put("name", hoten)
              var result = db.insert("student_table", null, values)
              Log.v("TAG", "$result")
              db.setTransactionSuccessful()
            } catch (ex: Exception) {
              ex.printStackTrace()
            } finally {
              db.endTransaction()
            }
          }
          studentAdapter.notifyItemInserted(students.size - 1)
        }
      }
    )


  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_add_student -> {
        val intent = Intent(this, AddStudentActivity::class.java)
        optionLauncher.launch(intent)
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onContextItemSelected(item: MenuItem): Boolean {
    studentAdapter.handleContextMenuAction(item)
    return super.onContextItemSelected(item)
  }

  fun createTable() {
    db.beginTransaction()
    try {
      db.execSQL("create table student_table(" +
              "recID integer primary key autoincrement," +
              "sid text," +
              "name text)")
      db.execSQL("insert into student_table(sid, name) values ('SV001', 'Lê Minh Việt Anh')")
      db.setTransactionSuccessful()
    } catch (ex: Exception) {
      ex.printStackTrace()
    } finally {
      db.endTransaction()
    }
  }

//  override fun onStop() {
//    db.close()
//    super.onStop()
//  }
}