package com.bili.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.bili.base.utils.ViewBindingUtil.inflateWithGeneric

/**
 * @description:
 *
 * @author: bili
 * @e-mail: bilingchange@126.com
 * @date: on 2021/11/23    18:00.
 **/

abstract class BaseBindingActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateWithGeneric(this, layoutInflater)
        setContentView(binding.root)
    }
}