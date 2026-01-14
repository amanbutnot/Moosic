package io.github.amanbutnot.moosic.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.github.amanbutnot.moosic.common.SongPlayer2
import io.github.amanbutnot.moosic.data.model.Song
import moosic.composeapp.generated.resources.Res
import moosic.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

data class SongPlayerScreen(val song: Song) : Screen {
    @Composable
    override fun Content() {
        val url1 =
            "https://music.hoelab.org/rest/stream.view?u=test&p=narayan7&v=1.16.1&c=feagegsag&f=json&id=${song.id}"
        SongScreenContent(url1)
    }
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview
@Composable
private fun SongScreenContent(url: String) {
    val player = remember { SongPlayer2(url) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            //TODO: Take color from the cover from previous screen
            .background(Color(0xffb4befe).copy(alpha = 0.2f))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    )
    {
        Box(
            modifier = Modifier.fillMaxWidth().clip(MaterialShapes.Puffy.toShape())
                .background(Color.Green)
        ) {
            Image(painterResource(Res.drawable.compose_multiplatform), contentDescription = "")
        }
        Text("Serafine", style = MaterialTheme.typography.displayLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FilledTonalIconButton(
                onClick = {
                    println("button click")
                    player.play()
                },
                modifier = Modifier.height(80.dp).weight(1f)
            ) {
                Text("PLAY", style = MaterialTheme.typography.displayMedium)
            }
            FilledTonalIconButton(
                onClick = { },
                modifier = Modifier.size(80.dp), shape = MaterialShapes.Square.toShape()
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Add to favorites",
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            FilledTonalIconButton(
                onClick = { },
                modifier = Modifier.size(80.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Add to favorites",
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
            LinearWavyProgressIndicator(
                modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 8.dp)
            )
        }
    }

}