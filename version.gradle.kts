

// 定义一个函数来获取 Git 提交数
fun retriveGitCommitCount(): Int {
    return try {
        val process = Runtime.getRuntime().exec("git rev-list --count HEAD")
        val output = process.inputStream.reader(Charsets.UTF_8).readText()
        output.trim().toInt()
    } catch (e: Exception) {
        0 // 如果不能获取 Git 提交数，则返回 0
    }
}

fun retriveGitHash(): String {
    return try {
        val process = Runtime.getRuntime().exec("git rev-parse --short HEAD")
        val output = process.inputStream.reader(Charsets.UTF_8).readText()
        output.trim()
    } catch (e: Exception) {
        "error"
    }
}
val gitCommitCount = retriveGitCommitCount()
val gitHash = retriveGitHash()
val versionCode = gitCommitCount + 10000
val versionName = findProperty("showcase.versionName") as String

project.extra["gitCommitCount"] = gitCommitCount
project.extra["gitHash"] = gitHash
project.extra["versionCode"] = versionCode
project.extra["versionName"] = versionName

