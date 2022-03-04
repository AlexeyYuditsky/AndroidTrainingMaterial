package ua.cn.stu.foundation.sideeffects.dialogs.plugin

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event.*
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import ua.cn.stu.foundation.model.SuccessResult
import ua.cn.stu.foundation.sideeffects.SideEffectImplementation
import ua.cn.stu.foundation.sideeffects.dialogs.plugin.DialogsSideEffectMediator.DialogRecord
import ua.cn.stu.foundation.sideeffects.dialogs.plugin.DialogsSideEffectMediator.RetainedState

class DialogsSideEffectImpl(
    private val retainedState: RetainedState
) : SideEffectImplementation() {

    private var dialog: Dialog? = null

    private val lifecycleEventObserver = LifecycleEventObserver { _, event ->
        when (event) {
            ON_START -> {
                val record = retainedState.record ?: return@LifecycleEventObserver
                showDialog(record)
            }
            ON_STOP -> {
                removeDialog()
            }
            else -> {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireActivity().lifecycle.addObserver(lifecycleEventObserver)
    }

    fun showDialog(record: DialogRecord) {
        val config = record.config
        val emitter = record.emitter
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle(config.title)
            .setMessage(config.message)
            .setCancelable(config.cancellable)
        if (config.positiveButton.isNotBlank()) {
            builder.setPositiveButton(config.positiveButton) { _, _ ->
                emitter.emit(SuccessResult(true))
                dialog = null
            }
        }
        if (config.negativeButton.isNotBlank()) {
            builder.setNegativeButton(config.negativeButton) { _, _ ->
                emitter.emit(SuccessResult(false))
                dialog = null
            }
        }
        if (config.cancellable) {
            builder.setOnCancelListener {
                emitter.emit(SuccessResult(false))
                dialog = null
            }
        }
        val dialog = builder.create()
        dialog.show()
        this.dialog = dialog
    }

    fun removeDialog() {
        dialog?.dismiss()
        dialog = null
    }

}