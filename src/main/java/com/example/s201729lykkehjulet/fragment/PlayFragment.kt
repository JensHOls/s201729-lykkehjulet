package com.example.s201729lykkehjulet.fragment

import com.example.s201729lykkehjulet.model.data.Datasource
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.s201729lykkehjulet.GameViewModel
import com.example.s201729lykkehjulet.R
import com.example.s201729lykkehjulet.databinding.PlayFragmentBinding
import com.example.s201729lykkehjulet.model.INCORRECT_LETTER
import com.example.s201729lykkehjulet.model.TYPE_LETTER
import com.example.s201729lykkehjulet.model.SPIN_WHEEL
import com.example.s201729lykkehjulet.model.adapter.ItemAdapter

class PlayFragment : Fragment() {
    /*
     Delegating the initiation of the ViewModel to activityViewModels.
     If ViewModel already exists the fragment will use that one.
     When other fragments in the same activity use "by activityViewModels()"
     to initiate a ViewModel they will receive the same ViewModel effectively
     sharing information between fragments.
     */
    private val viewModel: GameViewModel by activityViewModels()

    // Instance of binding object with access to play_fragment.xml layout
    private lateinit var binding: PlayFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PlayFragmentBinding.inflate(inflater, container, false)
        // Next two lines of code is slightly altered code from
        // "https://developer.android.com/codelabs/basic-android-kotlin-training-affirmations-app#3"
        val myDataset = Datasource().loadBonusDescriptions()
        binding.recyclerView.adapter = ItemAdapter(this, myDataset)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup a click listener for the submit letter and wheel of fortune buttons
        binding.submit.setOnClickListener { onSubmitLetter(view) }

        binding.wheelOfFortune.setOnClickListener { onClickWheelOfFortune(view) }

        binding.category.text = getString(R.string.category, viewModel.category)

        //further commenting
        // Observe different LiveData from viewModel
        viewModel.wordToDisplay.observe(viewLifecycleOwner,
            { newWord -> binding.textViewWord.text = newWord })

        viewModel.score.observe(viewLifecycleOwner,
            { newScore -> binding.score.text = getString(R.string.score, newScore) })

        viewModel.lives.observe(viewLifecycleOwner,
            { newLives -> binding.lives.text = getString(R.string.lives, newLives) })

        viewModel.bonusMessage.observe(viewLifecycleOwner,
            { newBonusMessage -> binding.bonus.text = newBonusMessage })
    }

    /*
     Function to handle events related to the user clicking the submit button to submit a letter.
     User can win, lose, get an error message or gain points when guessing correctly.
     */
    private fun onSubmitLetter(view: View) {
        val playerLetter = binding.textInputEditText.text.toString()
        // Returns false if the user has an untriggered bonus effect from the wheel of fortune
        if (!viewModel.bonusTriggered) {
            // Checks if the user typed a letter before submitting
            if (playerLetter.isNotBlank()) {
                // Returns true if user correctly guessed a letter
                if (viewModel.handleLetterGuess(playerLetter)) {
                    setErrorTextField(false, INCORRECT_LETTER)
                    // Returns true if user has no lives left, if so navigate to loss screen
                    if (viewModel.noLives()) {
                        navigateToWinOrLossFragment(view, false)
                      // Returns true if the user guessed the entire word, if so navigate to win screen
                    } else if (viewModel.isWordGuessed()) {
                        navigateToWinOrLossFragment(view, true)
                    }
                } else {
                    setErrorTextField(true, INCORRECT_LETTER)
                }
            } else {
                setErrorTextField(true, TYPE_LETTER)
            }
        } else {
            setErrorTextField(true, SPIN_WHEEL)
        }
    }

    // Function to handle events related to when the use clicks the wheel
    // of fortune button
    private fun onClickWheelOfFortune(view: View) {
        if (viewModel.bonusTriggered) {
            viewModel.handleBonus()
            if (viewModel.noLives()) {
                navigateToWinOrLossFragment(view, false)
            }
        } else {
            setErrorTextField(true, TYPE_LETTER)
        }
    }

    // Navigate to finish or loss screen
    private fun navigateToWinOrLossFragment(view: View, win: Boolean) {
        if (win) {
            val action =
                PlayFragmentDirections
                    .actionPlayFragmentToWinFragment()
            view.findNavController().navigate(action)
        } else {
            val action =
                PlayFragmentDirections
                    .actionPlayFragmentToLossFragment()
            view.findNavController().navigate(action)
        }
    }


    // Sets and resets various error messages for the user input textField
    private fun setErrorTextField(error: Boolean, type: Int) {
        if (error) {
            when (type) {
                1 -> {
                    binding.textField.isErrorEnabled = true
                    binding.textField.error = getString(R.string.incorrect)
                }
                2 -> {
                    binding.textField.isErrorEnabled = true
                    binding.textField.error = getString(R.string.type_letter)
                }
                3 -> {
                    binding.textField.isErrorEnabled = true
                    binding.textField.error = getString(R.string.spin_wheel)
                }
            }
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }
}