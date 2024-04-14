package com.example.mynotes.data

import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime


/**
 * Creates a title for the [text] provided.
 * Gets the subtext before the first line separator symbol '\n'
 * and limits it to the first [maxLength] characters.
 *
 * If there are more than [maxLength] characters,
 * an ellipsis (...) will be appended to the end of the limited title.
 * @return A new title based on the provided [text]
 */
fun formatTitle(text: String, maxLength: Int = 15) : String {
    val firstLine = text.substringBefore('\n')
    if (firstLine.length < maxLength) {
        return firstLine
    }

    return firstLine.substring(0, maxLength - 3) + "..."
}

class Note {
    var created: Long = LocalDateTime.now().toMilliseconds()
    var text: String = ""

    var title: String = ""
        set(newTitle) { field = formatTitle(newTitle) }

    constructor(text: String) {
        this.text = text
        this.title = formatTitle(text)
    }

    constructor(text: String, title: String = formatTitle(text), created: LocalDateTime = LocalDateTime.now()) {
        this.text = text
        this.title = formatTitle(title)
        this.created = created.toMilliseconds()
    }
}

fun LocalDateTime.toMilliseconds() : Long {
    return this.toEpochSecond(OffsetDateTime.now().offset) * 1000
}

fun Long.toLocalDateTime() : LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), OffsetDateTime.now().offset)
}
