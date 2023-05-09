package com.alexeyyuditsky.test

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.alexeyyuditsky.test.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::onGotCameraPermissionResult
    )

    private var requestRecordAudioAndLocationPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        ::onGotRecordAudioAndLocationPermissionsResult
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.requestCameraPermissionButton.setOnClickListener {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        binding.requestRecordAudioAndLocationPermissionsButton.setOnClickListener {
            requestRecordAudioAndLocationPermissionsLauncher.launch(
                arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }

        return binding.root
    }

    private fun onGotCameraPermissionResult(granted: Boolean) {
        if (granted) {
            onCameraPermissionGranted()
        } else {
            // ветка выполняется, если мы отменили разрешение первый раз
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(requireContext(), "Если отмените разрешения, функционал не будет работать", Toast.LENGTH_SHORT).show()
            // ветка выполянется, если мы отменили разрешение второй раз
            } else {
                askUserForOpeningAppSettings()
            }
        }
    }

    private fun onGotRecordAudioAndLocationPermissionsResult(grantResult: Map<String, Boolean>) {
        if (grantResult.values.all { it }) {
            Toast.makeText(requireContext(), "Все разрешения предоставлены", Toast.LENGTH_SHORT).show()
        } else {
            // ветка выполняется, если мы отменили разрешение первый раз
            if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO) ||
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
            ) {
                Toast.makeText(requireContext(), "Вы предоставили не все разрешения", Toast.LENGTH_SHORT).show()
            // ветка выполянется, если мы отменили разрешение второй раз
            } else {
                askUserForOpeningAppSettings()
            }
        }
    }

    private fun onCameraPermissionGranted() {
        Toast.makeText(requireContext(), "Разрешение предоставлено", Toast.LENGTH_SHORT).show()
    }

    private fun askUserForOpeningAppSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", requireContext().packageName, null)
        )
        // проверка на то, что если не существует активити отвечающей за настройки приложения, то показываем тоаст
        if (requireContext().packageManager.resolveActivity(appSettingsIntent, PackageManager.MATCH_DEFAULT_ONLY) == null) {
            Toast.makeText(requireContext(), "Разрешение отменено навсегда", Toast.LENGTH_SHORT).show()
        // если такая активити есть, то показываем диалог с возможность перейти в настройки приложения для активации разрешения
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("Permission denied")
                .setMessage(
                    "You have denied permissions forever. You can change your decision in app settings\n\n" +
                            "Would you like to open app settings?"
                )
                .setPositiveButton("Open") { _, _ -> startActivity(appSettingsIntent) }
                .create()
                .show()
        }
    }

}