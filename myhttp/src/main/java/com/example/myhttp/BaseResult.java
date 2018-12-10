package com.example.myhttp;

public class BaseResult {
	/**
	 * 返回状态说明
	 */
	private String err;
	/**
	 * 服务器时间（东八区时间戳）
	 */
	private String _time;

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public String get_time() {
		return _time;
	}

	public void set_time(String _time) {
		this._time = _time;
	}

}
