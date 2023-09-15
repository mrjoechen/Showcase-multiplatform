import com.alpha.networkfile.rclone.entity.RcloneFileItem
import com.alpha.networkfile.rclone.entity.RemoteConfig
import com.alpha.networkfile.storage.StorageSources
import com.alpha.networkfile.storage.add
import com.alpha.networkfile.storage.remote.RemoteApiDefaultImpl
import com.alpha.networkfile.util.StorageSourceSerializer
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import okio.buffer
import okio.source
import okio.use
import java.io.File
import java.util.Properties

private val json = Json {
  ignoreUnknownKeys = true
}

fun main() {
  val execConfig = Runtime.getRuntime().exec("rclone config dump")
  execConfig.inputStream.source().use {
    val result = it.buffer().readUtf8()
    println(result)

    val parseToJsonElement = json.parseToJsonElement(result)
    parseToJsonElement.jsonObject.keys.forEach {
      println(it)
      val remote = json.decodeFromJsonElement<RemoteConfig>(parseToJsonElement.jsonObject[it]!!)
      println(remote)
    }
  }

  val execLs = Runtime.getRuntime().exec("rclone lsjson catnas:")
  execLs.inputStream.source().use {
    val result = it.buffer().readUtf8()
    println(result)

    val decodeFromString = Json.decodeFromString<ArrayList<RcloneFileItem>>(result)
    println(decodeFromString)
  }

  val execObsec = Runtime.getRuntime().exec("rclone obscure")
  execObsec.waitFor()
  val exitValue = execObsec.exitValue()
  println(exitValue)
  execObsec.inputStream.source().use {
    val result = it.buffer().readUtf8()
    println(result)
  }


  val file = File("E:\\workspace\\StudioProjects\\showcase\\remote.json")
  file.inputStream().source().use {
    val result = it.buffer().readUtf8()
    println(result)

    val storage = StorageSourceSerializer.sourceJson.decodeFromString<StorageSources>(result)
    storage.add(RemoteApiDefaultImpl("test", "test", mapOf("test" to "test")))
    println(StorageSourceSerializer.sourceJson.encodeToString(storage))
  }


  val configFile = File("/Users/eeo/Downloads/rclone111.conf")
  val reader = configFile.inputStream().reader()

  val properties = Properties()
  lateinit var section: String

  reader.readLines().forEach { line ->
    when {
      line.isEmpty() || line.startsWith(";") -> {
        // 忽略空行和注释行
      }

      line.startsWith("[") && line.endsWith("]") -> {
        // 解析 section
        section = line.substring(1, line.length - 1)
      }

      else -> {
        // 解析 key-value pair
        val (key, value) = line.split("=").map { it.trim() }
        properties.setProperty("$section.$key", value)
      }
    }
  }

  properties.forEach { t, u ->
    println("$t = $u")
  }
  // 输出解析结果
  println(properties.getProperty("YWxpc3Q.pass"))

  val updateIniContent = updateIniContent(configFile.path, "aG9tZTExMWdnZ2Y", "pass", "123123")
  configFile.outputStream().writer().use {
    it.write(updateIniContent)
  }



  runBlocking {
    val configManager = ConfigManager("/Users/eeo/Downloads/rclone111.conf")
    // 添加一个部分
    configManager.addSection(
      "newSection",
      mapOf("type" to "google photos", "client_id" to "some-id")
    )

    // 更新一个部分
    configManager.updateSection("newSection", mapOf("client_id" to "new-id"))
//
//  // 删除一个部分
    configManager.deleteSection("newSection")

    // 获取一个部分
    val section = configManager.getSection("b29vb29v")
    println(section)
  }
}


fun updateIniContent(path: String, section: String, key: String, value: String): String {
  val configFile = File(path)
  val lines = configFile.inputStream().reader().readLines()

  // Create a map to store sections and properties
  val map = LinkedHashMap<String, LinkedHashMap<String, String>>()
  var currentSection = ""

  // Loop through lines and parse sections and properties
  for (line in lines) {
    if (line.startsWith("[")) {
      // Found new section
      currentSection = line.substring(1, line.lastIndexOf("]"))
      map[currentSection] = LinkedHashMap()
    } else if (line.contains("=")) {
      // Found property
      val keyValuePair = line.split("=")
      val k = keyValuePair[0].trim()
      val v = keyValuePair[1].trim()
      map[currentSection]?.put(k, v)
    }
  }

  // Update property value
  map[section]?.put(key, value)

  // Convert map back to INI string
  val sb = StringBuilder()
  for ((sec, properties) in map) {
    sb.append("[$sec]\n")
    for ((k, v) in properties) {
      sb.append("$k = $v\n")
    }
    sb.append("\n")
  }
  return sb.toString()
}