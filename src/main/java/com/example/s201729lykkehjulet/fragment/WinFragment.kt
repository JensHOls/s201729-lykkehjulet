package com.example.s201729lykkehjulet.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.s201729lykkehjulet.GameViewModel
import com.example.s201729lykkehjulet.R
import com.example.s201729lykkehjulet.databinding.WinFragmentBinding

class WinFragment : Fragment() {
    private val viewModel: GameViewModel by activityViewModels()

    private lateinit var binding: WinFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WinFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exitGame.setOnClickListener { exitGame() }

        binding.restart.setOnClickListener { restartGame(view) }

        binding.winMessage.text = getString(R.string.win_message, viewModel.score.value)
    }

    //reinitialize ViewModels data and navigate to playFragment
    private fun restartGame(view: View) {
        viewModel.reinitializeData()
        val action =
            WinFragmentDirections
                .actionWinFragmentToPlayFragment()
        view.findNavController().navigate(action)
    }


    // Exits game
    private fun exitGame() {
        activity?.finish()
    }
}