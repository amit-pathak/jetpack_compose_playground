package com.playground.compose.views

import androidx.compose.*
import androidx.core.graphics.drawable.toBitmap
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.graphics.ImageAsset
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.res.imageResource
import androidx.ui.unit.dp
import coil.Coil
import coil.api.newGetBuilder
import coil.request.GetRequest
import com.playground.compose.LoadableContentRenderer
import com.playground.compose.R
import com.playground.compose.entity.Item
import com.playground.compose.entity.ItemListingError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ItemScrollable(loadableContentRenderer: LoadableContentRenderer) {
    Recompose {
        loadableContentRenderer.error?.let {
            showError(loadableContentRenderer.error!!)
        } ?: run {
            if (loadableContentRenderer.showProgress || loadableContentRenderer.contentList.isEmpty()) {
                showLoading()
            } else if (loadableContentRenderer.contentList.isNotEmpty()) {
                showScrollableListing(loadableContentRenderer.contentList)
            }
        }
    }
}

@Composable
fun showError(error: ItemListingError) {

}

@Composable
fun showScrollableListing(contentList: List<Item>) {
    AdapterList(data = contentList, itemCallback = {
        Column {
            Card(modifier = Modifier.padding(10.dp) + Modifier.fillMaxWidth()) {
                Column {
                    /*val i = loadImageResource(id = it.image)
                    i.resource.resource?.let { coverImage(icon = it) }*/
                    coverImage()
                    //coverImage(icon = imageResource(id = it.image))
                    Spacer(modifier = Modifier.preferredWidth(4.dp))
                    titleText(text = it.title)
                    Spacer(modifier = Modifier.preferredWidth(4.dp))
                    subTitleText(text = it.description)
                    Spacer(modifier = Modifier.preferredWidth(4.dp))
                }
            }

        }
    })
}

@Composable
fun coverImage() {
    val url = "https://miro.medium.com/max/2968/1*KIpN-2QJGrifxZ4TREz5RQ.png"
    image(url)?.let {
        Image(it,contentScale = ContentScale.Crop)
    } ?: Image(asset = imageResource(id = R.drawable.placeholder_banner),contentScale = ContentScale.Crop)
}

@Composable
fun titleText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(10.dp)
    )
}

@Composable
fun subTitleText(text: String) {
    Text(
        text = "this is the description",
        style = MaterialTheme.typography.body1,
        modifier = Modifier.padding(10.dp)
    )
}


@Composable
fun showLoading() {
    CircularProgressIndicator(
        modifier = Modifier.preferredHeight(40.dp)
            .preferredWidth(40.dp)
    )
}

@Composable
fun image(data: String): ImageAsset? {
    // Positionally memoize the request creation so
    // it will only be recreated if data changes.
    val request = remember(data) {
        Coil.loader().newGetBuilder().data(data).build()
    }
    return image(request)
}
@Composable
fun image(request: GetRequest): ImageAsset? {
    val image = state<ImageAsset?> { null }

    // Execute the following code whenever the request changes.
    onCommit(request) {
        val job = CoroutineScope(Dispatchers.Main.immediate).launch {
            // Start loading the image and await the result.
            val drawable = Coil.loader().get(request)
            image.value = drawable.toBitmap().asImageAsset()
        }

        // Cancel the request if the input to onCommit changes or
        // the Composition is removed from the composition tree.
        onDispose { job.cancel() }
    }

    // Emit a null Image to start with.
    return image.value
}



