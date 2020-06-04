package dev.aayushgupta.recipecookbook.utils

import android.app.Activity
import android.app.Application
import android.content.Context

object PermissionFlow {
    fun triggerPermissionFlow(
        app: Application,
        ctx: Context,
        activity: Activity,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {

    }
}