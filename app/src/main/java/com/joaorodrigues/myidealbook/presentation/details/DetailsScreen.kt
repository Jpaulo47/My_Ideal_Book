package com.joaorodrigues.myidealbook.presentation.details

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.joaorodrigues.myidealbook.R
import com.joaorodrigues.myidealbook.domain.model.BookModel
import com.joaorodrigues.myidealbook.util.UIComponent
import com.joaorodrigues.myidealbook.presentation.details.components.DetailsTopBar
import com.joaorodrigues.myidealbook.presentation.Dimens.ArticleCardSize
import com.joaorodrigues.myidealbook.presentation.Dimens.ArticleCardSize2
import com.joaorodrigues.myidealbook.presentation.Dimens.ArticleDetaisSize
import com.joaorodrigues.myidealbook.presentation.Dimens.ArticleDetaisSize2
import com.joaorodrigues.myidealbook.presentation.Dimens.ExtraSmallPadding2
import com.joaorodrigues.myidealbook.presentation.Dimens.MediumPadding1


@Composable
fun DetailsScreen(
    book: BookModel,
    event: (DetailsEvent) -> Unit,
    sideEffect: UIComponent?,
    navigateUp: () -> Unit,
    existedBook: Boolean
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = sideEffect) {
        sideEffect?.let {
            when (sideEffect) {
                is UIComponent.Toast -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                    event(DetailsEvent.RemoveSideEffect)
                }

                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        val query = Uri.encode("livro ${book.volumeInfo.title}")
        val searchUrl = "https://www.google.com/search?q=$query"

        DetailsTopBar(
            onBrowsingClick = {

                Intent(Intent.ACTION_VIEW).also { intent ->
                    intent.data = Uri.parse(searchUrl)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    }
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, searchUrl)
                    it.type = "text/plain"
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onBookMarkClick = {
                event(DetailsEvent.UpsertDeleteArticle(book))

            },
            onBackClick = {
                navigateUp()
            },
            existedBook = existedBook
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = MediumPadding1,
                end = MediumPadding1,
                top = MediumPadding1
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                LoadImage(book = book)
                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = book.volumeInfo.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(
                        id = R.color.text_title
                    ),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Start
                )

                Row(modifier = Modifier.fillMaxWidth()) {

                    val authorsText =
                        book.volumeInfo.authors?.joinToString(", ") ?: "Autor Desconhecido"
                    Text(
                        text = authorsText,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = colorResource(id = R.color.body),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Start
                    )

                    Spacer(modifier = Modifier.width(ExtraSmallPadding2))

                    val publishedDateText = book.volumeInfo.publishedDate
                    Text(
                        text = "• $publishedDateText",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = colorResource(id = R.color.body),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Start
                    )
                }

                val publisher = book.volumeInfo.publisher
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Editora: $publisher",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Start
                )

                Text(
                    text = book.volumeInfo.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(
                        id = R.color.body
                    )
                )
            }
        }
    }
}

@Composable
fun LoadImage(book: BookModel) {

    if (book.volumeInfo.imageLinks == null) {
        Image(
            painter = painterResource(id = R.drawable.ic_network_error),
            contentDescription = "Erro ao carregar imagem",
            modifier = Modifier.size(ArticleCardSize, ArticleCardSize2),
            contentScale = ContentScale.Crop
        )
        return
    }

    val imageUrl = book.volumeInfo.imageLinks.thumbnail

    if (imageUrl.isNotEmpty()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl) // URL da imagem
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(ArticleDetaisSize, ArticleDetaisSize2)
                .clip(shape = MaterialTheme.shapes.medium), // Forma
            contentScale = ContentScale.Crop // Ajuste de escala
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.ic_network_error),
            contentDescription = "Erro ao carregar imagem",
            modifier = Modifier.size(ArticleCardSize, ArticleCardSize2),
            contentScale = ContentScale.Crop
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DetailsScreenPreview() {
//    val book = BookModel(
//        etag = "f0zKg75Mx/I",
//        id = "zyTCAlFPjgYC",
//        kind = "books#volume",
//        selfLink = "https://www.googleapis.com/books/v1/volumes/zyTCAlFPjgYC",
//        volumeInfo = VolumeInfo(
//            title = "The Google story",
//            authors = listOf("David A. Vise", "Mark Malseed"),
//            publisher = "Random House Digital, Inc.",
//            publishedDate = "2005-11-15",
//            subtitle = "The Google Story takes you inside the creation and growth of a company that has become a part of our lives.",
//            description = "\"Here is the story behind one of the most remarkable Internet successes of our time. Based on scrupulous research and extraordinary access to Google, ...\"",
//            industryIdentifiers = listOf(
//                IndustryIdentifier(
//                    type = "ISBN_10",
//                    identifier = "055380457X"
//                ),
//                IndustryIdentifier(
//                    type = "ISBN_13",
//                    identifier = "9780553804577"
//                )
//            ),
//            pageCount = 207,
//            printType = "BOOK",
//            categories = listOf("Browsers (Computer programs)"),
//            contentVersion = "1.1.0.0.preview.2",
//            imageLinks = ImageLinks(
//                smallThumbnail = "https://books.google.com/books/content?id=AfXZzQEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
//                thumbnail = "https://books.google.com/books/content?id=AfXZzQEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
//
//                ),
//            language = "en",
//            previewLink = "http://books.google.com.br/books?id=zyTCAlFPjgYC&printsec=frontcover&dq=isbn:055380457X&hl=&cd=1&source=gbs_api",
//            allowAnonLogging = false,
//            canonicalVolumeLink = "https://books.google.com/books/about/The_Google_story.html?hl=&id=zyTCAlFPjgYC",
//            readingModes = ReadingModes(
//                text = false,
//                image = false
//            ),
//            maturityRating = "NOT_MATURE",
//            panelizationSummary = PanelizationSummary(
//                containsEpubBubbles = false,
//                containsImageBubbles = false
//            ),
//            infoLink = "https://books.google.com/books?id=zyTCAlFPjgYC&ie=ISO-8859-1&source=gbs_api",
//        )
//    )
//    DetailsScreen(
//        book = book,
//        event = {},
//        sideEffect = null,
//        navigateUp = {}
//    )
//}
