package com.example.s201729lykkehjulet.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.s201729lykkehjulet.databinding.LossFragmentBinding
import com.example.s201729lykkehjulet.GameViewModel
import com.example.s201729lykkehjulet.R

class LossFragment : Fragment() {
    private val viewModel: GameViewModel by activityViewModels()

    private lateinit var binding: LossFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //get a binding to the loss fragment xml file
        binding = LossFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // sets onClick listeners for buttons to exit the gae and restart it.
        binding.exitGame.setOnClickListener { exitGame() }

        binding.restart.setOnClickListener { restartGame(view) }

        binding.lossMessage.text = getString(R.string.loss_message, viewModel.score.value)
    }


    // Reinitialize ViewModels data and navigate to playFragment
    private fun restartGame(view: View) {
        viewModel.reinitializeData()
        val action =
            LossFragmentDirections
                .actionLossFragmentToPlayFragment()
        view.findNavController().navigate(action)
    }

    // Exits game
    private fun exitGame() {
        activity?.finish()
    }
}