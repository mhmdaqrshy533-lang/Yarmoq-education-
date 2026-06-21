package com.example.utils

import java.util.Locale

/**
 * Built-in Linguistic Editor: الممحص والمدقق اللغوي المدمج
 *
 * Provides real-time spell-checking for official memos and notes.
 * This operates entirely offline using an intelligent heuristic rule system.
 */
object LinguisticEditorEngine {

    // Common Arabic spelling mistakes database (Offline Ruleset)
    private val commonMistakes = mapOf(
        "لكن" to listOf("لاكن"),
        "هذا" to listOf("هاذا"),
        "إن شاء الله" to listOf("انشاء الله", "ان شاءلله"),
        "الرحمان" to listOf("الرحمن"), // Correct is الرحمن
        "إلى" to listOf("الي"),
        "على" to listOf("علي"), // (Context sensitive, simplified for here)
        "مدير" to listOf("مديرر", "مذير")
    )

    data class SpellCheckResult(
        val isCorrect: Boolean,
        val suggestions: List<String> = emptyList(),
        val word: String
    )

    fun checkDocument(text: String): List<SpellCheckResult> {
        val words = text.split("\\s+".toRegex())
        val issues = mutableListOf<SpellCheckResult>()

        for (word in words) {
            val cleanWord = word.replace(Regex("[.,!؟]"), "")
            
            // Reverse lookup: if cleanWord is in the *values* of our map, it's a mistake.
            for ((correct, mistakes) in commonMistakes) {
                if (mistakes.contains(cleanWord)) {
                    issues.add(
                        SpellCheckResult(
                            isCorrect = false,
                            suggestions = listOf(correct),
                            word = cleanWord
                        )
                    )
                }
            }
        }
        return issues
    }
}
