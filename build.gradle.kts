buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.29.1-alpha")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.4.10")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
    gradle.projectsEvaluated {
        tasks.withType(JavaCompile::class) {
            options.compilerArgs.addAll(arrayOf("-Xlint:unchecked", "-Xlint:deprecation"))
        }
    }
}

tasks {
    withType(Delete::class) {
        delete(rootProject.buildDir)
    }
}
