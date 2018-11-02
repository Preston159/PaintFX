package com.ppetrie.paintfx.util;

import java.util.Stack;

@SuppressWarnings("serial")
public class FixedStack<T> extends Stack<T> {
	
	/**
	 * Default maximum stack size
	 */
	private static final int DEFAULT_MAX_SIZE = 15;
	
	private int max;
	
	/**
	 * Create a FixedStack with the {@link com.ppetrie.paintfx.util.FixedStack#DEFAULT_MAX_SIZE default maximum stack size}
	 */
	public FixedStack() {
		this(0);
	}
	
	/**
	 * Create a FixedStack with the given max stack size
	 * @param max	The maximum stack size
	 */
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
	
	/**
	 * Set the maximum size of this stack
	 * @param max	The new maximum stack size
	 * @return		The number of elements which were deleted in order to change the size
	 */
	public int setMax(int max) {
		this.max = max;
		int count = 0;
		while(this.size() >= max) {
			this.remove(0);
			count++;
		}
		return count;
	}
	
	/**
	 * @return	The current maximum size of this stack
	 */
	public int getMax() {
		return this.max;
	}
	
}
