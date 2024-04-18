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
fun formatTitle(text: String, maxLength: Int = 13) : String {
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

    override fun toString(): String {
        return "Note(created=$created, text='$text', title='$title')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (created != other.created) return false
        if (text != other.text) return false
        return title == other.title
    }

    override fun hashCode(): Int {
        var result = created.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + title.hashCode()
        return result
    }


}

fun LocalDateTime.toMilliseconds() : Long {
    return this.toEpochSecond(OffsetDateTime.now().offset) * 1000
}

fun Long.toLocalDateTime() : LocalDateTime {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(this), OffsetDateTime.now().offset)
}
