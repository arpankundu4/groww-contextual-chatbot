package com.groww.chatbot;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * Test suite
 * for Groww Chatbot Application
 */

@Suite
@SuiteDisplayName("Groww Chatbot Test Suite")
@SelectPackages({
		"com.groww.chatbot.repository",
		"com.groww.chatbot.service",
		"com.groww.chatbot.controller"
		})
class ChatbotApplicationTests {

}
