package com.example.bai1_3110

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var etNumber: EditText
    private lateinit var rbEven: RadioButton
    private lateinit var rbOdd: RadioButton
    private lateinit var rbSquare: RadioButton
    private lateinit var btnShow: Button
    private lateinit var lvResult: ListView
    private lateinit var tvError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo các view
        etNumber = findViewById(R.id.etNumber)
        rbEven = findViewById(R.id.rbEven)
        rbOdd = findViewById(R.id.rbOdd)
        rbSquare = findViewById(R.id.rbSquare)
        btnShow = findViewById(R.id.btnShow)
        lvResult = findViewById(R.id.lvResult)
        tvError = findViewById(R.id.tvError)

        // Xử lý sự kiện khi nhấn nút Show
        btnShow.setOnClickListener {
            showNumbers()
        }
    }

    private fun showNumbers() {
        val input = etNumber.text.toString()

        // Kiểm tra tính hợp lệ của đầu vào
        if (input.isEmpty()) {
            tvError.text = "Vui lòng nhập một số nguyên dương."
            tvError.visibility = View.VISIBLE
            return
        }

        val n = input.toIntOrNull()
        if (n == null || n <= 0) {
            tvError.text = "Vui lòng nhập một số nguyên dương hợp lệ."
            tvError.visibility = View.VISIBLE
            return
        }

        tvError.visibility = View.GONE

        // Lấy danh sách các số theo loại được chọn
        val numbers = when {
            rbEven.isChecked -> getEvenNumbers(n)
            rbOdd.isChecked -> getOddNumbers(n)
            rbSquare.isChecked -> getSquareNumbers(n)
            else -> listOf()
        }

        // Hiển thị danh sách trên ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, numbers)
        lvResult.adapter = adapter
    }

    // Hàm lấy danh sách số chẵn từ 0 đến n
    private fun getEvenNumbers(n: Int): List<Int> {
        val evenNumbers = ArrayList<Int>()
        for (i in 0..n step 2) {
            evenNumbers.add(i)
        }
        return evenNumbers
    }

    // Hàm lấy danh sách số lẻ từ 1 đến n
    private fun getOddNumbers(n: Int): List<Int> {
        val oddNumbers = ArrayList<Int>()
        for (i in 1..n step 2) {
            oddNumbers.add(i)
        }
        return oddNumbers
    }

    // Hàm lấy danh sách số chính phương từ 0 đến n
    private fun getSquareNumbers(n: Int): List<Int> {
        val squareNumbers = ArrayList<Int>()
        for (i in 0..n) {
            val sqrtVal = sqrt(i.toDouble()).toInt()
            if (sqrtVal * sqrtVal == i) {
                squareNumbers.add(i)
            }
        }
        return squareNumbers
    }
}
