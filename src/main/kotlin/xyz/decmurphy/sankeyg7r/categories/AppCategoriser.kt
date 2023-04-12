package xyz.decmurphy.sankeyg7r.categories

import org.springframework.stereotype.Service
import xyz.decmurphy.sankeyg7r.config.AppConfig

@Service
class AppCategoriser(
    val appConfig: AppConfig
) : Categoriser {

    override fun process(entry: String): Category? {
        return appConfig.categories.find { entry belongsTo it }
    }

}