package com.example.newsapp

import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.kwabenaberko.newsapilib.models.Article


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(newsViewModel: NewsViewModel, navController: NavController) {
    val articles by newsViewModel.articles.observeAsState(emptyList())
    val backgroundColor = MaterialTheme.colorScheme.background

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        NewsButton(newsViewModel)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(articles) { article ->
                ShowData(article, navController)
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowData(article: Article,navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp),
        onClick = {
            navController.navigate(DescriptionPage(article.url))
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = article.urlToImage
                    ?: "https://en.ac-illust.com/clip-art/25480354/no-image-thumbnail-1-with-no-image",
                contentDescription = "Article Image",
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()) {
                Text(
                    text = article.title,
                    maxLines = 3,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = article.source.name, maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = formatPublishedDate(publishedAt = article.publishedAt),
                    maxLines = 1,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    textAlign = TextAlign.End,

                )
            }
        }


    }

}

@Composable
fun NewsButton(newsViewModel: NewsViewModel) {

    val categories = listOf("General", "Business", "Entertainment", "Health", "Science", "Sports", "Technology")
    var searchQuery by remember{
        mutableStateOf("")
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        )
    {
        if(isExpanded){
            OutlinedTextField(
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .border(
                        1.dp, Color.Gray,
                        CircleShape
                    )
                    .clip(CircleShape),
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                trailingIcon = {
                    IconButton(onClick = { isExpanded = false
                        if(searchQuery.isNotEmpty())
                        {
                            newsViewModel.GetEveryThing(searchQuery)
                        }

                    })
                    {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                    }
                }
            )
        }else
        {
            IconButton(onClick = {isExpanded = true})
            {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            }
        }
        categories.forEach {

                    Button(
                        onClick = {
                            newsViewModel.FetchNewHeadLines(it)
                        },
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .height(42.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1E88E5), // Vibrant blue
                            contentColor = Color.White
                        ),
                        shape = CircleShape, // Gives a pill-shaped rounded look
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
fun formatPublishedDate(publishedAt: String): String {
    val zonedDateTime = ZonedDateTime.parse(publishedAt)
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy HH:mm", Locale.ENGLISH)
    return zonedDateTime.format(formatter)
}

@Composable
fun DescriptionPage(article: Article) {

}