package com.example.selfdicwithjetpack.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.selfdicwithjetpack.R
import com.example.selfdicwithjetpack.databinding.DetailFragBinding
import com.example.selfdicwithjetpack.databinding.DisplayRvItemBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment : Fragment() {

    private binding:

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DetailFragBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.detail_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}