package com.pi.ProjectInclusion.android.auth
import android.annotation.SuppressLint
import android.app.*
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.android.gms.common.util.PlatformVersion
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONException
import org.json.JSONObject
import com.example.kmptemplate.logger.LoggerProvider.logger
import com.pi.ProjectInclusion.android.R
import com.pi.ProjectInclusion.android.navigation.AppRoute

const val channelId = "notification_channel"
const val channelName = "com.pi.projectinclusion"

private const val TAG = "MyFirebaseMessagingServ"

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

//        savePrefData(this, getString(R.string.key_fcm_token), token)
//        Logger.d(TAG, "onNewToken: token: " + getData(this, getString(R.string.key_fcm_token)))
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        logger.d("onMessageReceived: data: ${remoteMessage.data}")
        try {
            FirebaseMessaging.getInstance().subscribeToTopic("notifs")

            // Handle data payload
            if (remoteMessage.data.isNotEmpty()) {
                val data = remoteMessage.data
                val title = data["title"] ?: "Test"
                val message = data["body"] ?: "Abhishek"
//                sendNotifications(this, data)
                logger.d("Title-> $title  \n massage-> $message \n $data ")
                // Check for silent notification trigger
                if (message == "Please give us rate on Google play store..!") {
                    openRatingActivity()
                    return
                } else {
                    sendNotifications(this, data)
                }
            }

            // Handle notification payload
            remoteMessage.notification?.let {
                val title = it.title
                val message = it.body
                if (message == "Please give us rate on Google play store..!") {
                    openRatingActivity()
                    return
                } else if (title == "Case Transfer With Controls") {
                    val details = parseNotificationDetails(message ?: "")
                    val teacherName = details["FullName"]
                    val schoolName = details["SchoolMasterName"]
                    val parsedMassg =
                        "$teacherName from $schoolName, has sent a student transfer request"
                    sendNotification(this, title, parsedMassg, null)
                    logger.d( " teacher -> $teacherName; school-> $schoolName \n parsedMessg-> $parsedMassg")
                } else {
                    sendNotification(this, title, message, null)
                    logger.d("Title is-> $title  \n massage-> $message ")
                }
            }
        } catch (e: Exception) {
            logger.d( "onMessageReceived: error: ${e.message}")
            e.printStackTrace()
        }
    }

    /*
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Logger.d(TAG, "onMessageReceived: data: " + remoteMessage.data)
        try {
            FirebaseMessaging.getInstance().subscribeToTopic("notifs")
            val data = remoteMessage.data
            val title = data["title"]
            val message = data["body"]
//            val message = data["message"]
            val click_action = data["click_action"]
            sendNotifications(this, data)
//            sendNotification(this, title, message, click_action)
        } catch (e: Exception) {
            Logger.d(TAG, "onMessageReceived: error: " + e.message)
            e.printStackTrace()
        }
    }
*/
    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotifications(
        context: Context,
        data: MutableMap<String, String>,
    ) {
        val pendingIntent: PendingIntent
        var title: String = ""
        var message: String = ""
//            val message = data["message"]
//        val intent = Intent(data["click_action"])

//        val intent = Intent(Intent.ACTION_VIEW, "myapp://open/notifications".toUri())
        val intent = Intent(this, AppRoute.DashboardScreen::class.java) // Target your activity

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        pendingIntent = if (Build.VERSION.SDK_INT >= 26) {
            title = data["title"].toString()
            message = data["body"].toString()
            val notificationChannel = NotificationChannel(
                channelId, channelName, IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            title = data["title"].toString()
            message = data["message"].toString()
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        }

        val defaultSoundUri: Uri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder
        if (PlatformVersion.isAtLeastN()) {
            notificationBuilder =
                NotificationCompat.Builder(context, channelId).setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setSmallIcon(R.drawable.dummy_image)
                    .setPriority(IMPORTANCE_HIGH)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            context.resources, R.drawable.dummy_image
                        )
                    )

        } else {
            notificationBuilder = NotificationCompat.Builder(context).setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setSmallIcon(R.drawable.dummy_image)
                .setPriority(IMPORTANCE_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        context.resources, R.drawable.dummy_image
                    )
                )
        }

        notificationBuilder.setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)

        notificationManager.notify(0, notificationBuilder.build())

//        if (title != null && title!= "" && title!="null" && message!= null && message!="" && message!="null"){
//            val openRateIntent = Intent(this, RatingActivity::class.java)
//            openRateIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
//            startActivity(openRateIntent)
//        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun sendNotification(
        context: Context,
        title: String?,
        message: String?,
        click_action: String?,
    ) {
        val pendingIntent: PendingIntent
//        val intent = Intent(click_action)

//        val intent = Intent(Intent.ACTION_VIEW, "myapp://open/notifications".toUri())
        val intent = Intent(this, AppRoute.DashboardScreen::class.java) // Target your activity

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // intent.action = Intent.ACTION_VIEW
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        pendingIntent = if (Build.VERSION.SDK_INT >= 26) {
            val notificationChannel = NotificationChannel(
                channelId, channelName, IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        }

        val defaultSoundUri: Uri =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder
        if (PlatformVersion.isAtLeastN()) {
            notificationBuilder =
                NotificationCompat.Builder(context, channelId).setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setSmallIcon(R.drawable.dummy_image)
                    .setPriority(IMPORTANCE_HIGH)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            context.resources, R.drawable.dummy_image
                        )
                    )

        } else {
            notificationBuilder = NotificationCompat.Builder(context).setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setSmallIcon(R.drawable.dummy_image)
                .setPriority(IMPORTANCE_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        context.resources, R.drawable.dummy_image
                    )
                )
        }

        logger.d("sendNotification: click_action: $click_action")


        notificationBuilder.setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)

        notificationManager.notify(0, notificationBuilder.build())

//        val openRateIntent = Intent(this, RatingActivity::class.java)
//        openRateIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
//        startActivity(openRateIntent)
    }

    private fun parseNotificationDetails(body: String): Map<String, String> {
        return try {
            val jsonObject = JSONObject(body)

            // Extract the "StudentDetails" array
            val studentDetailsArray = jsonObject.getJSONArray("StudentDetails")
            val studentObject =
                if (studentDetailsArray.length() > 0) studentDetailsArray.getJSONObject(0) else null

            // Extract the "ControlDetails" array
            val controlDetailsArray = jsonObject.getJSONArray("ControlDetails")
            val controlObject =
                if (controlDetailsArray.length() > 0) controlDetailsArray.getJSONObject(0) else null

            // Extract the "FromUserDetails" array
            val fromUserDetailsArray = jsonObject.getJSONArray("FromUserDetails")
            val userObject =
                if (fromUserDetailsArray.length() > 0) fromUserDetailsArray.getJSONObject(0) else null

            // Collect the required fields
            mapOf(
                "StudentName" to (studentObject?.getString("StudentName") ?: "Unknown Student"),
                "Transfer" to (controlObject?.optBoolean("Transfer")?.toString() ?: "false"),
                "Cancel" to (controlObject?.optBoolean("Cancel")?.toString() ?: "false"),
                "GradeName" to (studentObject?.getString("GradeName") ?: "Unknown Grade"),
                "SchoolMasterName" to (userObject?.getString("SchoolMasterName")
                    ?: "Unknown School"),
                "FullName" to (userObject?.getString("FullName") ?: "Unknown Name")
            )
        } catch (e: JSONException) {
            e.printStackTrace()
            // Return a map with default values in case of error
            mapOf(
                "StudentName" to "Invalid Data",
                "Transfer" to "false",
                "Cancel" to "false",
                "GradeName" to "Invalid Data",
                "SchoolMasterName" to "Invalid Data",
                "FullName" to "Invalid Data"
            )
        }
    }

//    private fun navigateNotificationActivity() {
//        val intent = Intent(this, NotificationActivity::class.java)
//        startActivity(intent)
//    }

    private fun openRatingActivity() {
//        val intent = Intent(this, RatingActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//        }
//        startActivity(intent)
        logger.d( "Silent notification received: Opening RatingActivity")
    }


}