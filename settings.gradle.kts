rootProject.name = "soffa-platform"

include(
    "soffa-core",
    "soffa-web",
    "soffa-data-jpa",
    "soffa-rabbitmq",
    "soffa-security",
    "soffa-tests"
)

for (project in rootProject.children) {
    project.apply {
        projectDir = file("src/$name")
    }
}


