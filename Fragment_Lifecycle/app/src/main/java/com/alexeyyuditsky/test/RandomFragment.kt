package com.alexeyyuditsky.test

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.databinding.FragmentRandomBinding
import com.github.javafaker.Faker
import kotlin.properties.Delegates
import kotlin.random.Random

class RandomFragment : Fragment(), HasUuid, NumberListener {
    private lateinit var binding: FragmentRandomBinding

    private var backgroundColor by Delegates.notNull<Int>()
    private lateinit var chuckNorrisFact: String

    private val textColor: Int
        get() = if (Color.luminance(backgroundColor) > 0.3) Color.BLACK else Color.WHITE

    private var uuidArgument: String
        get() = requireArguments().getString(ARG_UUID)!!
        set(value) = requireArguments().putString(ARG_UUID, value)

    private val permissionRequestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {

        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getUuid()
        Log.d(TAG, "$uuidArgument: onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "$uuidArgument: onCreate")

        backgroundColor = savedInstanceState?.getInt(KEY_BACKGROUND_COLOR) ?: getRandomColor()
        chuckNorrisFact =
            savedInstanceState?.getString(KEY_CHUCK_NORRIS_FACT) ?: getNextChuckNorrisFact()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRandomBinding.inflate(inflater, container, false)

        setupUI()
        updateUI()

        Log.d(TAG, "$uuidArgument: onCreateView")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "$uuidArgument: onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "$uuidArgument: onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "$uuidArgument: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "$uuidArgument: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "$uuidArgument: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "$uuidArgument: onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "$uuidArgument: onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "$uuidArgument: onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "$uuidArgument: onDetach")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_BACKGROUND_COLOR, backgroundColor)
        outState.putString(KEY_CHUCK_NORRIS_FACT, chuckNorrisFact)
        Log.d(TAG, "$uuidArgument: onSaveInstanceState")
    }

    override fun getUuid(): String = uuidArgument

    override fun onNewScreenNumber(number: Int) {
        binding.numberTextView.text = getString(R.string.number, number)
    }

    private fun getRandomColor(): Int = -Random.nextInt(0xFFFFFF)

    private fun getNextChuckNorrisFact(): String = Faker.instance().chuckNorris().fact()

    private fun setupUI() {
        binding.apply {
            changeUuidButton.setOnClickListener {
                uuidArgument = navigator().generateUuid()
                navigator().update()
            }
            changeChuckNorrisFactButton.setOnClickListener {
                chuckNorrisFact = getNextChuckNorrisFact()
                updateUI()
            }
            changeBackgroundButton.setOnClickListener {
                backgroundColor = getRandomColor()
                updateUI()
            }
            launchNextButton.setOnClickListener {
                navigator().launchNext()
            }
            launchPermission.setOnClickListener {
                permissionRequestLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun updateUI() {
        binding.apply {
            uuidTextView.text = uuidArgument
            chuckNorrisFactTextView.text = chuckNorrisFact

            root.setBackgroundColor(backgroundColor)
            uuidTextView.setTextColor(textColor)
            chuckNorrisFactTextView.setTextColor(textColor)
            numberTextView.setTextColor(textColor)
        }
    }

    companion object {
        private val TAG = RandomFragment::class.java.simpleName
        private const val ARG_UUID = "ARG_UUID"
        private const val KEY_BACKGROUND_COLOR = "KEY_BACKGROUND_COLOR"
        private const val KEY_CHUCK_NORRIS_FACT = "KEY_CHUCK_NORRIS_FACT"

        fun newInstance(uuid: String): RandomFragment = RandomFragment().apply {
            arguments = bundleOf(ARG_UUID to uuid)
        }
    }
}