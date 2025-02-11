pluginManagement {
    repositories {
        google {
//            content {
//                includeGroupByRegex("com/.android.*")
//                includeGroupByRegex("com/.google.*")
//                includeGroupByRegex("androidx.*")
//            }
        }

        mavenLocal()
        gradlePluginPortal()
        //noinspection JcenterRepositoryObsolete
        jcenter()
        maven { url = uri("https://jitpack.io") }

        mavenCentral()
        maven { url = uri("https://www.jitpack.io") }
        maven { url = uri("https://maven.google.com") }
    }
}
dependencyResolutionManagement {
    //repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google{

        }

        mavenLocal()
        gradlePluginPortal()
        //noinspection JcenterRepositoryObsolete
        jcenter()
        maven { url = uri("https://jitpack.io") }

        mavenCentral()
        maven { url = uri("https://www.jitpack.io") }
        maven { url = uri("https://maven.google.com") }
    }
}


rootProject.name = "Meccamedina"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app1")
include(":app2")
include(":data")



// include(":UnderratedTube")
include(":features:permissionResolver")
project(":features:permissionResolver").projectDir = file("D:\\walhalla\\sdk\\android\\ui\\features\\permissionResolver")

include(":threader")
project(":threader").projectDir = file("D:\\walhalla\\sdk\\android\\multithreader\\threader")

include(":features:ui")
project(":features:ui").projectDir = file("D:\\walhalla\\sdk\\android\\UI\\features\\ui")

include(":features:wads")
project(":features:wads").projectDir = file("D:\\walhalla\\sdk\\android\\ui\\features\\wads")

include(":pdfViewer")
project(":pdfViewer").projectDir = file("E:\\src\\Med\\HealthCalculator\\pdf-viewer\\")

include(":ytlib")
//include(":ytlib2")

include(":scrapper")

include(":intentresolver")
project(":intentresolver").projectDir = file("D:\\walhalla\\TTDwn\\AndroidStudioSourceCode\\intentresolver")
include(":mylibrary")
