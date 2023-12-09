package de.andrena.tools.altn8th.domain.settings.types

fun MutableList<out PatternSetting>.patterns() = this.map { it.pattern }
