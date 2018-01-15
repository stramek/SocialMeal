package com.marcinstramowski.socialmeal.account

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests for [DataValidator]
 */
class DataValidatorTest {

    private lateinit var dataValidator: DataValidator

    @Before
    fun prepareTest() {
        dataValidator = DataValidator()
    }

    @Test
    fun testPasswordValidator() {
        assertFalse(dataValidator.isPasswordValid("aaaaa")) // too short, no number
        assertFalse(dataValidator.isPasswordValid("aaaa1")) // too short
        assertFalse(dataValidator.isPasswordValid("11111")) // too short, no letter
        assertFalse(dataValidator.isPasswordValid("1111a")) // too short
        assertFalse(dataValidator.isPasswordValid("aaaaa!")) // no number
        assertFalse(dataValidator.isPasswordValid("@aaaaa")) // no number
        assertFalse(dataValidator.isPasswordValid("1111112")) // no letter
        assertTrue(dataValidator.isPasswordValid("1aaaaa"))
        assertTrue(dataValidator.isPasswordValid("@11111"))
    }

    @Test
    fun testEmailValidator() {
        assertFalse(dataValidator.isEmailValid("test.com"))
        assertFalse(dataValidator.isEmailValid("@test.com"))
        assertFalse(dataValidator.isEmailValid("a@test."))
        assertFalse(dataValidator.isEmailValid("test.test@"))
        assertFalse(dataValidator.isEmailValid("test@.pl"))
        assertTrue(dataValidator.isEmailValid("test@test.com"))
    }
}