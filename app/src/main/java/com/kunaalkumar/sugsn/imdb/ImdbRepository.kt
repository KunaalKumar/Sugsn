package com.kunaalkumar.sugsn.imdb

import com.kunaalkumar.sugsn.util.ListItem
import com.kunaalkumar.sugsn.util.RetrofitFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

object ImdbRepository {
    val TAG: String = "ImdbRepository"
    private val imdbService = RetrofitFactory.makeImdbRetrofitService()

    fun getTopRatedMovies(): Observable<ArrayList<ListItem>> {
        return imdbService.getTopRatedMovies().map { response ->
            val doc = Jsoup.parse(response)
            val listOfMovies = ArrayList<ListItem>()

            doc.select("tbody.lister-list").select("tr").forEach { listItem ->
                listOfMovies.add(
                    parseMovie(listItem)
                )
            }

            return@map listOfMovies
        }.subscribeOn(Schedulers.io())
    }

    fun getPopularMovies(): Observable<ArrayList<ListItem>> {
        return imdbService.getMostPopularMovies().map { response ->
            val doc = Jsoup.parse(response)
            val listOfMovies = ArrayList<ListItem>()

            doc.select("tbody.lister-list").select("tr").forEach { listItem ->
                listOfMovies.add(
                    parseMovie(listItem)
                )
            }
            return@map listOfMovies
        }.subscribeOn(Schedulers.io())
    }

    // Parse movie list jsoup element and return as ListItem
    private fun parseMovie(movie: Element): ListItem {
        val ratingElement = movie.selectFirst("td.ratingColumn.imdbRating").selectFirst("strong")
        val imdbRating =
            if (ratingElement == null) null else
                ratingElement.text()

        return ListItem(
            movie.selectFirst("td.titleColumn").selectFirst("a").text(),
            movie.selectFirst("td.titleColumn > span.secondaryInfo").text().removeSurrounding(
                "(",
                ")"
            ),
            movie.selectFirst("td.posterColumn")
                .selectFirst("a")
                .selectFirst("img")
                .attr("src")
                .replace(".jpg", "#\$1.jpg"), // Replace to increase poster resolution
            imdbRating,
            null,
            movie.selectFirst("td.titleColumn").selectFirst("a").attr("href")
        )
    }
}