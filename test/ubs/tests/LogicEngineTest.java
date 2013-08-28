package ubs.tests;

import static org.junit.Assert.*;
import pokus.LogicEngine;

import org.junit.Test;

public class LogicEngineTest {

	@Test
	public void parseConditionTest() {
		assertEquals("NOW.setDate(NOW.getDate()+5)", LogicEngine.parseCondition("NOW#+5d"));
		assertEquals("NOW.setDate(NOW.getDate()+3)", LogicEngine.parseCondition("NOW#+3D"));
		assertEquals("NOW.setHours(NOW.getHours()+24)", LogicEngine.parseCondition("NOW#+24h"));
		assertEquals("NOW.setHours(NOW.getHours()-24)", LogicEngine.parseCondition("NOW#-24H"));
		assertEquals("NOW.setMinutes(NOW.getMinutes()+46546)", LogicEngine.parseCondition("NOW#46546m"));
		assertEquals("NOW.setMinutes(NOW.getMinutes()-46546)", LogicEngine.parseCondition("NOW#-46546M"));
		assertEquals("NOW.setMinutes(NOW.getMinutes()-46546) > NOW.setDate(NOW.getDate()+5)", LogicEngine.parseCondition("NOW#-46546M > NOW#+5d"));
	}

}
