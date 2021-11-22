package com.bili.eyepetizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bili.eyepetizer.databinding.ActivityMainBinding
/**
 * @description:
 *
 * @author: Y.F
 * @e-mail: bilingchange@126.com
 * @date: on 2021/4/21    14:05.
 **/
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
