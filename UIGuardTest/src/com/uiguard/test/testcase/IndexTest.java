package com.uiguard.test.testcase;

import java.lang.reflect.Method;

import org.testng.annotations.Test;

import com.uiguard.entity.driver.extension.UGDriverExtension;
import com.uiguard.entity.testcase.imp.UGTest;
import com.uiguard.exception.UiGuardException;
import com.uiguard.test.page.Detail;
import com.uiguard.test.page.Iframe;
import com.uiguard.test.page.Index;

public class IndexTest extends UGTest {

	@Test
	public void xx() throws UiGuardException {
		new Index(this).submitForm();
		new Detail(this).openIframePage();
		new Iframe(this).showAndBack();
		uiAssert.assertEquals("xx", "xx1");
	}

	public static void main(String[] args) {
		print(UGDriverExtension.class);
	}

	public static void print(Class<?> c) {

		if (c.getSuperclass() != null) {
			print(c.getSuperclass());
		}
		System.out.println("-------------");
		System.out.println(c.getName());
		System.out.println("-------------");
		for (Method method : c.getDeclaredMethods()) {
			System.out.println(method.getName() + ": "
					+ getCareModifier(method.getModifiers()));
		}

	}

	public static String getCareModifier(int m) {
		return m % 2 == 1 ? "public" : (m % 4 == 0) ? "protected" : "private";
	}

}
