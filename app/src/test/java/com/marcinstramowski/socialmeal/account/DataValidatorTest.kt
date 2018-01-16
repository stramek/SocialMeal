package com.marcinstramowski.socialmeal.account

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests for [CredentialsValidator]
 */
class CredentialsValidatorTest {

    @Test
    fun testPasswordValidator() {
        assertFalse(CredentialsValidator.isPasswordValid("aaaaa")) // too short, no number
        assertFalse(CredentialsValidator.isPasswordValid("aaaa1")) // too short
        assertFalse(CredentialsValidator.isPasswordValid("11111")) // too short, no letter
        assertFalse(CredentialsValidator.isPasswordValid("1111a")) // too short
        assertFalse(CredentialsValidator.isPasswordValid("aaaaa!")) // no number
        assertFalse(CredentialsValidator.isPasswordValid("@aaaaa")) // no number
        assertFalse(CredentialsValidator.isPasswordValid("1111112")) // no letter
        assertTrue(CredentialsValidator.isPasswordValid("1aaaaa"))
        assertTrue(CredentialsValidator.isPasswordValid("@11111"))
    }

    @Test
    fun testEmailValidator() {
        assertFalse(CredentialsValidator.isEmailValid("test.com"))
        assertFalse(CredentialsValidator.isEmailValid("@test.com"))
        assertFalse(CredentialsValidator.isEmailValid("a@test."))
        assertFalse(CredentialsValidator.isEmailValid("test.test@"))
        assertFalse(CredentialsValidator.isEmailValid("test@.pl"))
        assertTrue(CredentialsValidator.isEmailValid("test@test.com"))
    }
}