package com.bride.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task("greetings") {
            println("What's the weather like today?")
        }
    }
}