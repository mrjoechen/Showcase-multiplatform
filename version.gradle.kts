
// 定义一个函数来获取 Git 提交数
fun getGitCommitCount(): Int {
    return try {
        val process = Runtime.getRuntime().exec("git rev-list --count HEAD")
        val output = process.inputStream.reader(Charsets.UTF_8).readText()
        output.trim().toInt()
    } catch (e: Exception) {
        0 // 如果不能获取 Git 提交数，则返回 0
    }
}

fun getGitHash(): String {
    return try {
        val process = Runtime.getRuntime().exec("git rev-parse --short HEAD")
        val output = process.inputStream.reader(Charsets.UTF_8).readText()
        output.trim()
    } catch (e: Exception) {
        "error"
    }
}

project.extra["gitCommitCount"] = getGitCommitCount()
project.extra["gitHash"] = getGitHash()
