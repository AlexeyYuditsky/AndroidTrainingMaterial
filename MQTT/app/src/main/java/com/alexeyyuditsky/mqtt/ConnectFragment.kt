package com.alexeyyuditsky.mqtt

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.mqtt.databinding.ConnectFragmentBinding
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage

class MqttClientHolder {
    companion object {
        private var mqttClient: MQTTClient? = null
        fun fetchMqttClient(context: Context): MQTTClient {
            return mqttClient ?: MQTTClient(context, "tcp://mqtt-dashboard.com:1883", "333")
                .also { mqttClient = it }
        }
    }
}

class ConnectFragment : Fragment(R.layout.connect_fragment) {

    private lateinit var binding: ConnectFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ConnectFragmentBinding.bind(view)

        binding.connectButton.setOnClickListener(onConnectionButtonPressed)
    }

    private val onConnectionButtonPressed = View.OnClickListener {
        MqttClientHolder.fetchMqttClient(requireContext())
            .connect("111", "222", mqttActionListener, mqttCallback)
    }

    private val mqttActionListener = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            Toast.makeText(context, "Connection success", Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ClientFragment())
                .addToBackStack(null)
                .commit()
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            Toast.makeText(context, "Connection fails", Toast.LENGTH_SHORT).show()
        }
    }

    private val mqttCallback = object : MqttCallback {
        override fun connectionLost(cause: Throwable?) {
            Toast.makeText(context, "Connection lost ${cause.toString()}", Toast.LENGTH_SHORT)
                .show()
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            val msg = "Receive message: ${message.toString()} from topic: $topic"
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            Toast.makeText(context, "Delivery complete", Toast.LENGTH_SHORT).show()
        }
    }

}