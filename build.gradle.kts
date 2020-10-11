buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.2")
        classpath(kotlin("gradle-plugin", "1.4.10"))
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.29.1-alpha")
        classpath(kotlin("serialization", "1.4.10"))
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }
    gradle.projectsEvaluated {
        tasks.withType(JavaCompile::class) {
            options.encoding = "UTF-8"
            options.compilerArgs.addAll(arrayOf("-Xlint:unchecked", "-Xlint:deprecation"))
        }
    }
}

tasks {
    withType(Delete::class) {
        delete(rootProject.buildDir)
    }
}
