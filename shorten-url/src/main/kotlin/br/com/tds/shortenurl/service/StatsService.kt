package br.com.tds.shortenurl.service

import br.com.tds.shortenurl.model.StatsModel
import br.com.tds.shortenurl.repository.StatsRepository
import br.com.tds.shortenurl.repository.UrlRepository
import org.springframework.stereotype.Service

@Service
class StatsService(
    private var modelRepository: UrlRepository,
    private var statsRepository: StatsRepository,

    ) {

    fun countAccess(shortLink: String) {
        val statsModel = StatsModel(
            id = null,
            shortUrl = modelRepository.findByShortUrl(shortLink)!!.shortUrl,
            originalUrl = modelRepository.findByShortUrl(shortLink)!!.originalUrl,
            accessCounter = 1
        )

        if (statsRepository.findStatsByShortUrl(statsModel.shortUrl!!) == null) {
            statsRepository.save(statsModel)
        } else {
            val increaseStatsCounter = statsRepository.findStatsByShortUrl(statsModel.shortUrl!!)
            increaseStatsCounter!!.accessCounter += 1
            statsRepository.save(increaseStatsCounter!!)
        }

    }

}