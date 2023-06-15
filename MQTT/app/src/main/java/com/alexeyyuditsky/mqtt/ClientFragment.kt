package com.alexeyyuditsky.mqtt

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.mqtt.databinding.ClientFragmentBinding
import com.google.android.material.snackbar.Snackbar
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken

class ClientFragment : Fragment(R.layout.client_fragment) {

    private lateinit var binding: ClientFragmentBinding

    var topic = ""
    var message = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ClientFragmentBinding.bind(view)

        binding.publishButton.setOnClickListener(onPublishButtonPressed)
    }

    private val onPublishButtonPressed = View.OnClickListener {
        MqttClientHolder.fetchMqttClient(requireContext())
            .publish(
                binding.topicEditText.text.toString().also { topic = it },
                binding.messageEditText.text.toString().also { message = it },
                1,
                false,
                mqttActionListener
            )
    }

    private val mqttActionListener = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Snackbar.make(requireView(), "$message to topic: $topic", Snackbar.LENGTH_SHORT).show()
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Snackbar.make(
                requireView(),
                "Failed to publish message to topic",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

}