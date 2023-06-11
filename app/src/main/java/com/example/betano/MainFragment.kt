package com.example.betano

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MainFragment : Fragment() {
    private var onPlayClickListener: OnPlayClickListener? = null
    fun setOnPlayClickListener(listener: OnPlayClickListener?) {
        onPlayClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.activity_main, container, false)

        // Set up any views or listeners in the fragment layout
        // For example, you can set up a button click listener
        rootView.findViewById<View>(R.id.start_game_layout).setOnClickListener {
            if (onPlayClickListener != null) {
                onPlayClickListener!!.onPlayClick()
            }
        }
        return rootView
    }

    interface OnPlayClickListener {
        fun onPlayClick()
    }
}