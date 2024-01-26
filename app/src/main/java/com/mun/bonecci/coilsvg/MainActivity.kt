package com.mun.bonecci.coilsvg

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import com.mun.bonecci.coilsvg.ui.theme.CoilSvgTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoilSvgTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CoilPNGTest()
                        CoilSVGTest()
                        CoilGifTest()
                    }
                }
            }
        }
    }
}

/**
 * Composable function to test loading a PNG image using Coil.
 */
@Composable
fun CoilPNGTest() {
    // Create an instance of ImageLoader for loading PNG images
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .build()

    // Display the PNG image using ImageComponent
    ImageComponent(
        imageUrl = IMAGE_PNG_URL,
        imageLoader = imageLoader,
        contentDescription = "PNG for testing"
    )
}

/**
 * Composable function to test loading an SVG image using Coil.
 */
@Composable
fun CoilSVGTest() {
    // Create an instance of ImageLoader with an SVG decoder for loading SVG images
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            add(SvgDecoder.Factory()) // Add SVG decoder to support SVG images
        }
        .build()

    // Display the SVG image using ImageComponent
    ImageComponent(
        imageUrl = IMAGE_SVG_URL,
        imageLoader = imageLoader,
        contentDescription = "SVG for testing"
    )
}

/**
 * Composable function to test loading a GIF image using Coil.
 */
@Composable
fun CoilGifTest() {
    // Create an instance of ImageLoader with a GIF decoder for loading GIF images
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            // Add appropriate decoder based on the Android version
            if (SDK_INT >= Build.VERSION_CODES.P) {
                add(ImageDecoderDecoder.Factory()) // Use ImageDecoder for Android P and above
            } else {
                add(GifDecoder.Factory()) // Use GifDecoder for versions below Android P
            }
        }
        .build()

    // Display the GIF image using ImageComponent
    ImageComponent(
        imageUrl = GIF_URL,
        imageLoader = imageLoader,
        contentDescription = "GIF for testing"
    )
}

/**
 * Composable function to display an image using Image component.
 *
 * @param imageUrl The URL of the image to be loaded.
 * @param imageLoader The ImageLoader instance to use for loading the image.
 * @param contentDescription The content description for accessibility.
 */
@Composable
fun ImageComponent(imageUrl: String, imageLoader: ImageLoader, contentDescription: String) {
    // Use Coil's Image component with rememberAsyncImagePainter for asynchronous image loading
    Image(
        painter = rememberAsyncImagePainter(model = imageUrl, imageLoader = imageLoader),
        contentDescription = contentDescription,
        modifier = Modifier
            .padding(all = 16.dp)
            .size(200.dp)
    )
}


const val IMAGE_PNG_URL = "https://upload.wikimedia.org/wikipedia/commons/4/4c/Android_Marshmallow.png"
const val IMAGE_SVG_URL =
    "https://upload.wikimedia.org/wikipedia/commons/e/e0/Android_robot_%282014-2019%29.svg"
const val GIF_URL =
    "https://upload.wikimedia.org/wikipedia/commons/c/cb/Android_easter_eggs_%28version_2.3_to_10%29.gif"

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoilSvgTheme {
        CoilSVGTest()
    }
}