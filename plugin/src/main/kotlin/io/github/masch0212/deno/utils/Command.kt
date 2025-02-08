package io.github.masch0212.deno.utils

private val COMMAND_ARGUMENT_REGEX = Regex("""([^\s"']+)|"((?:\\.|[^"\\])*)"|'((?:\\.|[^'\\])*)'""")

/**
 * Parses a shell-like command string into separate arguments, respecting quotes. Supports both
 * single (' ') and double (" ") quotes.
 */
internal fun parseCommandArgs(command: String) =
    COMMAND_ARGUMENT_REGEX.findAll(command)
        .mapNotNull { it.groupValues.drop(1).firstOrNull { part -> part.isNotEmpty() } }
        .toList()
