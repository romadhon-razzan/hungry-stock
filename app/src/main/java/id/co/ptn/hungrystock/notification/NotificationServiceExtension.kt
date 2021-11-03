package id.co.ptn.hungrystock.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.onesignal.OSNotification
import com.onesignal.OSNotificationReceivedEvent
import com.onesignal.OneSignal
import id.co.ptn.hungrystock.R
import id.co.ptn.hungrystock.ui.general.auth.AuthActivity

class NotificationServiceExtension : OneSignal.OSRemoteNotificationReceivedHandler {
    private val id_channel = "guruku_id"
    private val name_channel: CharSequence = "guruku"

    override fun remoteNotificationReceived(
        context: Context,
        notificationReceivedEvent: OSNotificationReceivedEvent
    ) {

        OneSignal.onesignalLog(
            OneSignal.LOG_LEVEL.VERBOSE, "OSRemoteNotificationReceivedHandler fired!" +
                    " with OSNotificationReceived: " + notificationReceivedEvent.toString()
        )
        val notification = notificationReceivedEvent.notification
        if (notification.actionButtons != null) {
            for (button in notification.actionButtons) {
                OneSignal.onesignalLog(OneSignal.LOG_LEVEL.VERBOSE, "ActionButton: $button")
            }
        }
//        val mutableNotification = notification.mutableCopy()
//        mutableNotification.setExtender { builder: NotificationCompat.Builder ->
//            builder.setColor(
//                ContextCompat.getColor(context, R.color.colorPrimary)
//            )
//        }

        // If complete isn't call within a time period of 25 seconds, OneSignal internal logic will show the original notification
        notificationReceivedEvent.complete(null)
        route(context, notification)
    }

    private fun route(context: Context, notification: OSNotification) {
        val data = notification.additionalData
        when {
            data.get("type") == "V" -> { // Verification
                sendVerifyNotification(context, notification)
            }
            else -> {
                sendNotification(context, notification)
            }
        }
    }

    private fun sendVerifyNotification(context: Context, notification: OSNotification) {
        val intent = Intent(context, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            context,
            100000,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        send(context, notificationBuilder(context, notification, pendingIntent))
    }

    private fun sendNotification(context: Context, notification: OSNotification) {
        send(context, notificationBuilder(context, notification, null))
    }

    private fun notificationBuilder(context: Context, notification: OSNotification, intent: PendingIntent?): NotificationCompat.Builder {
        return NotificationCompat.Builder(context)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.icon_launcher_hungry_stock)
            .setChannelId(id_channel)
            .setContentIntent(intent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(notification.body))
    }

    private fun send(context: Context, notificationBuilder: NotificationCompat.Builder) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(
                id_channel,
                name_channel,
                importance
            )
            notificationManager.createNotificationChannel(mChannel)
        }

        notificationManager.notify(100, notificationBuilder.build())
    }
}