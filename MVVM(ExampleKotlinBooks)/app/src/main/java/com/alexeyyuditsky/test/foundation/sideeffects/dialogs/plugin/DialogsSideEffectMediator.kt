package com.alexeyyuditsky.test.foundation.sideeffects.dialogs.plugin

import com.alexeyyuditsky.test.foundation.model.ErrorResult
import com.alexeyyuditsky.test.foundation.model.coroutines.Emitter
import com.alexeyyuditsky.test.foundation.model.coroutines.toEmitter
import kotlinx.coroutines.suspendCancellableCoroutine
import com.alexeyyuditsky.test.foundation.sideeffects.SideEffectMediator
import com.alexeyyuditsky.test.foundation.sideeffects.dialogs.Dialogs

class DialogsSideEffectMediator : SideEffectMediator<DialogsSideEffectImpl>(), Dialogs {

    var retainedState = RetainedState()

    override suspend fun show(dialogConfig: DialogConfig): Boolean = suspendCancellableCoroutine { continuation ->
        val emitter = continuation.toEmitter()
        if (retainedState.record != null) {
            // for now allowing only 1 active dialog at a time
            emitter.emit(ErrorResult(IllegalStateException("Can't launch more than 1 dialog at a time")))
            return@suspendCancellableCoroutine
        }

        val wrappedEmitter = Emitter.wrap(emitter) {
            retainedState.record = null
        }

        val record = DialogRecord(wrappedEmitter, dialogConfig)
        wrappedEmitter.setCancelListener {
            target { implementation ->
                implementation.removeDialog()
            }
        }

        target { implementation ->
            implementation.showDialog(record)
        }

        retainedState.record = record
    }

    class DialogRecord(
        val emitter: Emitter<Boolean>,
        val config: DialogConfig
    )

    class RetainedState(
        var record: DialogRecord? = null
    )
}