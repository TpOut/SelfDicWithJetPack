package com.example.selfdicwithjetpack.random

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.example.selfdicwithjetpack.R
import com.example.selfdicwithjetpack.databinding.RandomFragBinding
import com.example.selfdicwithjetpack.display.DisplayUIModel

/**
 * Created by TpOut on 2020/10/14.<br>
 * Email address: 416756910@qq.com<br>
 */

const val MAX_RANDOM_SIZE = 5

class RandomFrag(val layout: Int = R.layout.random_frag) : Fragment(layout), RandomHandler {

    private lateinit var binding: RandomFragBinding

    private var mList: ArrayList<DisplayUIModel> = arrayListOf()
    private var mIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.image)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RandomFragBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.handler = this
        afterViewCreated(binding)
        return binding.root
    }

    private fun afterViewCreated(binding: RandomFragBinding) {

    }

    override fun onConfigClick(v: View) {
        val bundle = Bundle().apply {
            putBoolean("order", true)
        }
        findNavController().navigate(
            R.id.action_RandomFrag_to_RandomConfigFrag,
            bundle
        )
    }
}