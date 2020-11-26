package com.example.selfdicwithjetpack.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.selfdicwithjetpack.databinding.DetailFragBinding
import com.example.selfdicwithjetpack.display.DisplayUIModel
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DetailFragBinding.inflate(inflater, container, false)
        context ?: return binding.root

        afterViewCreated(binding)
        return binding.root
    }

    private fun afterViewCreated(binding: DetailFragBinding) {
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "ReplaceSuccess", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.item = DisplayUIModel.DisplayItemModel(args.src, args.dst, args.sentence)
    }

}