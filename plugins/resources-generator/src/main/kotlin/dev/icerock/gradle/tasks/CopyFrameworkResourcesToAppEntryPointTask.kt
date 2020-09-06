/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.jetbrains.kotlin.konan.target.KonanTarget

open class CopyFrameworkResourcesToAppEntryPointTask : DefaultTask() {

    val konanTarget: KonanTarget?
    val configuration: String?

    init {
        group = "moko-resources"

        val platformName =
            project.findProperty("moko.resources.PLATFORM_NAME") as? String
        configuration =
            (project.findProperty("moko.resources.CONFIGURATION") as? String)?.toLowerCase()

        konanTarget = when (platformName) {
            "iphonesimulator" -> KonanTarget.IOS_X64
            "iphoneos" -> KonanTarget.IOS_ARM64
            else -> null
        }
    }

    @TaskAction
    fun action() {
        if (dependsOn.isEmpty()) {
            throw IllegalStateException(
                """
framework link task with konanTarget $konanTarget and buildType $configuration not found!
"""
            )
        }
    }
}
