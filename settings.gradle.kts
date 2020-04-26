rootProject.name = "soffa-platform"

include(
    "soffa-platform-core",
    "soffa-platform-gateways",
    "soffa-platform-pubsub",
    "soffa-platform-tests"
)

for (project in rootProject.children) {
    project.apply {
        projectDir = file("src/$name")
    }
}


