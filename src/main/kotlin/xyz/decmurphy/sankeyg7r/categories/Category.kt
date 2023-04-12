package xyz.decmurphy.sankeyg7r.categories

enum class MatchType {
    MATCHES
    , CONTAINS
    , STARTS_WITH
    , ENDS_WITH
}

data class Match(
    val matchType: MatchType,
    val patterns: List<String>
) {
    infix fun matches(input: String): Boolean {
        return patterns.any {
            when (matchType) {
                MatchType.MATCHES -> Regex(it).containsMatchIn(input)
                MatchType.CONTAINS -> input.lowercase().contains(it.lowercase())
                MatchType.STARTS_WITH -> input.lowercase().startsWith(it.lowercase())
                MatchType.ENDS_WITH -> input.lowercase().endsWith(it.lowercase())
            }
        }
    }
}

data class Category(
    val name: String,
    val matches: List<Match>
) {
    fun matches(input: String): Boolean {
        return matches.any { it matches input }
    }
}

infix fun String.belongsTo(category: Category) = category.matches(this)