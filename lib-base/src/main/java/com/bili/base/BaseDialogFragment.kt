package com.bili.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.bili.base.utils.ViewBindingUtil

/**
 * @description:
 * 
 * @author: Y.F
 * @e-mail: bilingchange@126.com
 * @date: on 2021/11/24    17:27.
 **/
abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment(){
    lateinit var binding: VB
    lateinit var mWindow: Window

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewBindingUtil.inflateWithGeneric(this,layoutInflater)
        setStyle(DialogFragment.STYLE_NORMAL,R.style.MyDialog)
    }

    override fun onStart() {
        super.onStart()
        isCancelable = true
        mWindow = dialog?.window!!

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        setListener()
    }

    abstract fun initData()

    abstract fun setListener()

}