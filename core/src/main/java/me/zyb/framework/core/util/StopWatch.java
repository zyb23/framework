package me.zyb.framework.core.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class StopWatch {
	private long start;

    public StopWatch() {
        this.reset();
    }

    public void reset() {
        this.start = System.currentTimeMillis();
    }

    public long elapsedTime() {
        long end = System.currentTimeMillis();
        long elapsed = end - this.start;
        log.info(elapsed + "");
        return elapsed;
    }
}
