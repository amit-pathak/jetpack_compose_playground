package com.playground.compose

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Model
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import com.playground.compose.entity.Item
import com.playground.compose.entity.ItemListingError
import com.playground.compose.views.ItemScrollable


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme {
            ItemScrollable(loadableContentRenderer = LoadableContentRenderer)
        } }
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            LoadableContentRenderer.showProgress = false
            LoadableContentRenderer.contentList.addAll(getItems())
        }, 2000)
    }

    private fun getItems(): MutableList<Item> {
        return mutableListOf<Item>().apply {
            this.add(Item("An introduction to Jetpack Compose for quick Android UI","Jetpack Compose is a new tool for designing Android app UIs, which could change the way that we handle layouts across devices.",R.drawable.cover_image))
            this.add(Item("Google launches Jetpack Compose developer preview","The company released expanded APIs for Android Jetpack, Jetpack Compose in developer preview",R.drawable.cover_image_two))
            this.add(Item("Android Studio 4 & Jetpack Compose","There is no doubt that the future of Android development is Kotlin",R.drawable.cover_image_three))
            this.add(Item("Build an app, but make it pretty – Android devs get new UI","Jetpack Compose is promoted as a “targeted answer to problems people have been having” when building Android apps. ",R.drawable.cover_image_four))
            this.add(Item("Android Dev Summit highlights","etpack Compose released to developer preview: First announced at I/O last year",R.drawable.cover_image))
            this.add(Item("Android Studio 4.0 backs native UI toolkit","Now entering a developer preview phase, Jetpack Compose is accessible in Android Studio.",R.drawable.cover_image_two))
            this.add(Item("Google Reaffirms Commitment To Kotlin Programming","Google is continuing to embrace Kotlin programming for Android, making more Android APIs accessible by Kotlin",R.drawable.cover_image_three))
            this.add(Item("Kotlin Vs Flutter: Who Will Rule the Cross-platform App Market?","So, app experts can employ the same tools and libraries they have been using during native development",R.drawable.cover_image_four))
           // this.forEach { it.image = R.mipmap.ic_launcher_round }

        }
    }
}


@Model
object LoadableContentRenderer {
    var showProgress = true
    var contentList = mutableListOf<Item>()
    var error: ItemListingError? = null
}
