package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.background.KEY_IMAGE_URI

class BlurWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        try {
            makeStatusNotification("Blurring image", applicationContext)
            sleep()
            val resourceUri = inputData.getString(KEY_IMAGE_URI)
            if (TextUtils.isEmpty(resourceUri)) {
                Log.e("BlurOMatic", "Invalid input uri")
            }

            val resolver = applicationContext.contentResolver
            val picture = BitmapFactory.decodeStream(resolver.openInputStream
                (Uri.parse(resourceUri)))
            val output = blurBitmap(picture, applicationContext)
            val outputUri = writeBitmapToFile(applicationContext, output)

            val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())
            return Result.success(outputData)
        } catch (e: Throwable) {
            Log.e("BlurOMatic", "Error applying blur", e)
            return Result.failure()
        }
    }
}