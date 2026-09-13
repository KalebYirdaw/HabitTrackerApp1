package com.example.habittrackerapp.ui.habits

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.habittrackerapp.R

class ColorPickerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_color_picker,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.colorBlue).setOnClickListener {
            selectColor("#90CAF9")
        }

        view.findViewById<View>(R.id.colorSky).setOnClickListener {
            selectColor("#81D4FA")
        }

        view.findViewById<View>(R.id.colorPurple).setOnClickListener {
            selectColor("#CE93D8")
        }

        view.findViewById<View>(R.id.colorLavender).setOnClickListener {
            selectColor("#B39DDB")
        }

        view.findViewById<View>(R.id.colorPink).setOnClickListener {
            selectColor("#F48FB1")
        }

        view.findViewById<View>(R.id.colorRed).setOnClickListener {
            selectColor("#EF9A9A")
        }

        view.findViewById<View>(R.id.colorOrange).setOnClickListener {
            selectColor("#FFCC80")
        }

        view.findViewById<View>(R.id.colorYellow).setOnClickListener {
            selectColor("#FFF59D")
        }

        view.findViewById<View>(R.id.colorGreen).setOnClickListener {
            selectColor("#A5D6A7")
        }

        view.findViewById<View>(R.id.colorMint).setOnClickListener {
            selectColor("#80CBC4")
        }

        view.findViewById<View>(R.id.colorBrown).setOnClickListener {
            selectColor("#BCAAA4")
        }

        view.findViewById<View>(R.id.colorGrey).setOnClickListener {
            selectColor("#CFD8DC")
        }
    }

    private fun selectColor(color: String) {

        parentFragmentManager.setFragmentResult(
            "color_picker_result",
            Bundle().apply {
                putString("selectedColor", color)
            }
        )

        findNavController().navigateUp()
    }
}