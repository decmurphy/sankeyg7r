package xyz.decmurphy.sankeyg7r.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import xyz.decmurphy.sankeyg7r.Category

@ConstructorBinding
@ConfigurationProperties(prefix = "app")
@Qualifier("AppConfig")
data class AppConfig(
    val categories: List<Category>
)