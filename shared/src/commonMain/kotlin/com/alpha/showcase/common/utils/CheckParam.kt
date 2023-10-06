package com.alpha.showcase.common.utils

import com.alpha.showcase.common.ui.StringResources
import java.net.URI


fun checkName(
    name: String?,
    showToast: Boolean = false,
    block: (() -> Unit)? = null
): Boolean {
    if (name == null || name.isEmpty()) {
        if (showToast) ToastUtil.error(StringResources.current.name_is_invalid)
        return false
    }

    if (name.contains("/") ||
        name.contains("\\") ||
        name.contains(":") ||
        name.contains("*") ||
        name.contains("?") ||
        name.contains("\"") ||
        name.contains("<") ||
        name.contains(">") ||
        name.contains("|") ||
        name.contains(" ")
    ) {
        if (showToast) ToastUtil.error(StringResources.current.name_is_invalid)
        return false
    }
    block?.invoke()
    return true
}

// check if the host is legal
fun checkHost(
    host: String?,
    showToast: Boolean = false,
    block: (() -> Unit)? = null
): Boolean {
    if (host == null || host.isEmpty()) {
        if (showToast) ToastUtil.error(StringResources.current.host_is_invalid)
        return false
    }

    if (host.contains("/") ||
        host.contains("\\") ||
        host.contains(":") ||
        host.contains("*") ||
        host.contains("?") ||
        host.contains("\"") ||
        host.contains("<") ||
        host.contains(">") ||
        host.contains("|")
    ) {
        if (showToast) ToastUtil.error(StringResources.current.host_is_invalid)
        return false
    }

    if (host.matches(Regex("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\$"))) {
        val ipSplit = host.split(".")
        var result = true
        for (i in ipSplit) {
            result = result && (i.toInt() in 0..255)
        }
        return if (result) {
            block?.invoke()
            true
        } else {
            if (showToast) ToastUtil.error(StringResources.current.host_is_invalid)
            false
        }
    }
    block?.invoke()
    return true
}

fun checkIp(ip: String?, showToast: Boolean = false, block: (() -> Unit)? = null): Boolean {
    if (ip == null || ip.isEmpty()) {
        if (showToast) ToastUtil.error(StringResources.current.ip_is_invalid)
        return false
    }
    if (ip.matches(Regex("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\$"))) {
        val ipSplit = ip.split(".")
        for (i in ipSplit) {
            if (i.toInt() !in 0..255) {
                if (showToast) ToastUtil.error(StringResources.current.ip_is_invalid)
                return false
            }
        }
    } else {
        if (showToast) ToastUtil.error(StringResources.current.ip_is_invalid)
        return false
    }
    block?.invoke()
    return true
}

fun checkPort(
    port: String,
    showToast: Boolean = false,
    block: (() -> Unit)? = null
): Boolean {
    if (port.isBlank()) {
        block?.invoke()
        return true
    }
    try {
        if (port.toInt() !in 1..65535) {
            if (showToast) ToastUtil.error(StringResources.current.port_is_invalid)
            return false
        }
    } catch (e: Exception) {
        if (showToast) ToastUtil.error(StringResources.current.port_is_invalid)
        return false
    }

    block?.invoke()
    return true
}

fun checkPath(
    path: String,
    showToast: Boolean = false,
    block: (() -> Unit)? = null
): Boolean {
    if (path.contains("\\") ||
        path.contains(":") ||
        path.contains("*") ||
        path.contains("?") ||
        path.contains("\"") ||
        path.contains("<") ||
        path.contains(">") ||
        path.contains("|")
    ) {
        if (showToast) ToastUtil.error(StringResources.current.path_is_invalid)
        return false
    }

    block?.invoke()
    return true
}

fun isBranchNameValid(
    branchName: String,
    showToast: Boolean = false,
    block: (() -> Unit)? = null
): Boolean {
    // 分支名称只能包含字母、数字、破折号和下划线
    val pattern = Regex("^[a-zA-Z0-9-_]+$")
    // 使用正则表达式进行匹配
    val matches = pattern.matches(branchName)
    if (!matches) {
        if (showToast) ToastUtil.error(StringResources.current.branch_name_is_invalid)
        return false
    }

    block?.invoke()
    return true
}


// check if url is legal
fun checkUrl(
    url: String?,
    showToast: Boolean = false,
    block: (() -> Unit)? = null
): Boolean {

    if (url == null || url.isEmpty()) {
        if (showToast) ToastUtil.error(StringResources.current.url_is_invalid)
        return false
    }
    val match = try {
        val uri = URI(url)
        val schemaMatches = uri.scheme.matches(Regex("^(http|https)\$"))
        val hostMatches =
            uri.host.matches(Regex("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\$"))
                    || uri.host.matches(Regex("^(?:[a-zA-Z0-9]+(?:-[a-zA-Z0-9]+)*\\.)+[a-zA-Z]+\$"))
        val portMatches = uri.port == -1 || uri.port in 1..65535
        val pathMatches = uri.path.isNullOrBlank() || uri.path.matches(Regex("^(?:/[^/]+)+\$"))

        schemaMatches && hostMatches && portMatches && pathMatches
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    return if (match) {
        block?.invoke()
        true
    } else {
        if (showToast) ToastUtil.error(StringResources.current.url_is_invalid)
        false
    }
}