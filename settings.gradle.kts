rootProject.name = "soffa-platform"

include(
    "soffa-platform-core",
    "soffa-platform-springboot",
    "soffa-platform-springboot-pubsub",
    "soffa-platform-springboot-tests"
)

for (project in rootProject.children) {
    project.apply {
        projectDir = file("src/$name")
    }
}


