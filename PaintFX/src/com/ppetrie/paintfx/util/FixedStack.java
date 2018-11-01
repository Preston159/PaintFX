package com.ppetrie.paintfx.util;

import java.util.Stack;

@SuppressWarnings("serial")
public class FixedStack<T> extends Stack<T> {
	
	private static final int DEFAULT_MAX_SIZE = 15;
	
	private int max;
	
	public FixedStack() {
		this(0);
	}
	
	public FixedStack(int max) {
		super();
		if(max > 0) {
			this.max = max;
		} else {
			this.max = DEFAULT_MAX_SIZE;
		}
	}
	
	@Override
	public T push(T object) {
		while(this.size() >= max) {
			this.remove(0);
		}
		return super.push(object);
	}
	
	public int setMax(int max) {
		this.max = max;
		int count = 0;
		while(this.size() >= max) {
			this.remove(0);
			count++;
		}
		return count;
	}
	
	public int getMax() {
		return this.max;
	}
	
}
