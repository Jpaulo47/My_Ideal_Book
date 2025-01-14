package com.joaorodrigues.myidealbook.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.joaorodrigues.myidealbook.R
import com.joaorodrigues.myidealbook.domain.model.BookModel
import com.joaorodrigues.myidealbook.presentation.Dimens.ArticleCardSize
import com.joaorodrigues.myidealbook.presentation.Dimens.ArticleCardSize2
import com.joaorodrigues.myidealbook.presentation.Dimens.ExtraSmallPadding2
import com.joaorodrigues.myidealbook.presentation.Dimens.SmallIconSize

@Composable
fun BooksCard(
    modifier: Modifier = Modifier,
    book: BookModel,
    onClick: () -> Unit
    ) {

    Row(modifier = modifier.clickable { onClick() }) {

        LoadImageWithCoil(book = book)

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(horizontal = SmallIconSize)
                .height(ArticleCardSize)

        ) {
            Text(
                text = book.volumeInfo.title,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.black),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(verticalAlignment = Alignment.CenterVertically) {

                val authorsText = book.volumeInfo.authors?.joinToString(", ") ?: "Autor Desconhecido"
                Text(
                    text = authorsText,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body)
                )

                Spacer(modifier = Modifier.width(ExtraSmallPadding2))

                val publishedDateText = book.volumeInfo.publishedDate
                Text(
                    text = "• $publishedDateText",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body)
                )
            }


            val descriptionText = book.volumeInfo.description ?: "Descrição não disponível"
            Text(
                text = descriptionText,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.body),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

        }
    }

}

@Composable
fun LoadImageWithCoil(book: BookModel) {

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
                .data(imageUrl)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(ArticleCardSize, ArticleCardSize2)
                .clip(shape = MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
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
//fun showBooks() {
//    BooksCard(
//        modifier = Modifier.padding(16.dp),
//        book = BookModel(
//            etag = "f0zKg75Mx/I",
//            id = "zyTCAlFPjgYC",
//            kind = "books#volume",
//            selfLink = "https://www.googleapis.com/books/v1/volumes/zyTCAlFPjgYC",
//            accessInfo = AccessInfo(
//                country = "BR",
//                viewability = "PARTIAL",
//                embeddable = true,
//                publicDomain = false,
//                textToSpeechPermission = "ALLOWED",
//                epub = Epub(
//                    isAvailable = true,
//                    acsTokenLink = "https://books.google.com.br/books/download/The_Google_story-sample-epub.acsm?id=zyTCAlFPjgYC&format=epub&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api"
//                ),
//                pdf = Pdf(
//                    isAvailable = true ),
//                accessViewStatus = "SAMPLE"
//            ),
//            volumeInfo = VolumeInfo(
//                title = "The Google story",
//                authors = listOf("David A. Vise", "Mark Malseed"),
//                publisher = "Random House Digital, Inc.",
//                publishedDate = "2005-11-15",
//                description = "\"Here is the story behind one of the most remarkable Internet successes of our time. Based on scrupulous research and extraordinary access to Google, ...\"",
//                industryIdentifiers = listOf(
//                    IndustryIdentifier(
//                        type = "ISBN_10",
//                        identifier = "055380457X"
//                    ),
//                    IndustryIdentifier(
//                        type = "ISBN_13",
//                        identifier = "9780553804577"
//                    )
//                ),
//                pageCount = 207,
//                dimensions = Dimensions(
//                    height = "24.00 cm",
//                    width = "16.03 cm",
//                    thickness = "2.74 cm"
//                ),
//                printType = "BOOK",
//                mainCategory = "Business & Economics / Entrepreneurship",
//                categories = listOf("Browsers (Computer programs)"),
//                averageRating = 3.5,
//                ratingsCount = 136,
//                contentVersion = "1.1.0.0.preview.2",
//                imageLinks = ImageLinks(
//                    smallThumbnail = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
//                    thumbnail = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
//                    small = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=2&edge=curl&source=gbs_api",
//                    medium = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=3&edge=curl&source=gbs_api",
//                    large = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=4&edge=curl&source=gbs_api",
//                    extraLarge = "https://books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=6&edge=curl&source=gbs_api"
//                ),
//                language = "en",
//                infoLink = "https://books.google.com/books?id=zyTCAlFPjgYC&ie=ISO-8859-1&source=gbs_api",
//                canonicalVolumeLink = "https://books.google.com/books/about/The_Google_story.html?id=zyTCAlFPjgYC"
//            )
//        )
//    )
//}


