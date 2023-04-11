package xyz.decmurphy.sankeyg7r

import org.springframework.stereotype.Service
import xyz.decmurphy.sankeyg7r.config.AppConfig

@Service
class Categoriser(
    val appConfig: AppConfig
) {
    fun process(entry: String, isIncome: Boolean): AppCategory? {
        return appConfig.categories.find { entry belongsTo it }
    }
}