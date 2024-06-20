rootProject.name = "url-shortener"

pluginManagement{
    val kotlinVersion : String by settings
    val springBootVersion : String by settings
    val springDependencyManagementVersion : String by settings

    resolutionStrategy{
        eachPlugin {
            when(requested.id.id) {
                "org.springframework.boot" -> useVersion(springBootVersion)
                "io.spring.dependency-management" -> useVersion(springDependencyManagementVersion)
                "org.jetbrains.kotlin.jvm","org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
            }
        }
    }
}
