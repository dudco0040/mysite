package com.poscodx.mysite.dto;

public class JsonResult {
	private String result;		// "success" of "fail"
	private String message;		// if fail set/ null of "error message(exception message)"
	private Object data;		// if success set.
}
