package com.joaorodrigues.myidealbook.presentation.home

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joaorodrigues.myidealbook.R

@Preview
@Composable
fun DrawerContent() {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(0.dp, 16.dp, 16.dp, 0.dp)
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(50))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_app_menu),
                    contentDescription = "Imagem do Drawer",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }

            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = stringResource(R.string.welcome_text),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = androidx.compose.ui.text.style.TextAlign.Justify
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.linkedin.com/in/jo%C3%A3o-paulo-rodrigues-silva/")
                        )
                        context.startActivity(intent)
                    }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.linkedin),
                        contentDescription = "Ícone do LinkedIn",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(25.dp)
                    )
                    Text(
                        text = "LinkedIn ➚",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Jpaulo47"))
                        context.startActivity(intent)
                    }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.github),
                        contentDescription = "Ícone do GitHub",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(25.dp)
                    )
                    Text(
                        text = "GitHub ➚",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.clickable {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(
                            android.content.ClipData.newPlainText(
                                "Email",
                                "j.paulosil49@gmail.com"
                            ))
                        Toast.makeText(context, "Email copiado para a área de transferência", Toast.LENGTH_SHORT).show()
                    },
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.gmail),
                        contentDescription = "Ícone do GitHub",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(25.dp)
                    )
                    Text(
                        text = "j.paulosil49@gmail.com",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
