package com.alpha.networkfile.util

import TEST_IV
import TEST_KEY
import decodePass
import encodePass

object RConfig {

    var decrypt: ((String) -> String) = { it.decodePass(TEST_KEY, TEST_IV) }
        private set
    var encrypt: ((String) -> String) = { it.encodePass(TEST_KEY, TEST_IV) }
        private set

    fun initEnCryptAndDecrypt(encrypt: (String) -> String, decrypt: (String) -> String) {
        this.encrypt = encrypt
        this.decrypt = decrypt
    }

}