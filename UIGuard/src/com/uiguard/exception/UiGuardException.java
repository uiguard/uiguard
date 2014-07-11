package com.uiguard.exception;

/**
 * <b>描述：</b> 自动化测试引擎异常类
 * @author kfzx-chensy04
 * @version 1.0 2012-12-3 下午01:08:39
 */
public class UiGuardException extends Exception {
	private static final long serialVersionUID = 6258242003794190934L;

	public UiGuardException() {
		super();
	}

	public UiGuardException(String message, Throwable cause) {
		super(message, cause);
	}

	public UiGuardException(String message) {
		super(message);
	}

	public UiGuardException(Throwable cause) {
		super(cause);
	}

}
