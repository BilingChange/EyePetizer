package com.bili.eyepetizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bili.eyepetizer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var  binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvContent.setText("测试")
        binding.tvTitle.setText("测试标题")
    }
}
