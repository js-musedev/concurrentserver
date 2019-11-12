package com.jsbg.concurrentserver.logging;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

class DuplicateHandler {
    private static Logger logger = Logger.getLogger();
    private static final int expectedInsertions = 1000 * 1000 * 115;
    private static final float desiredFalsePositiveProbability = 0.001f;

    volatile private BloomFilter<Integer> bloomFilter = BloomFilter.create(
            Funnels.integerFunnel(),
            expectedInsertions,
            desiredFalsePositiveProbability
    );

    boolean isDuplicate(int n) {
        if (bloomFilter.mightContain(n)) {
            return logger.contains(n);
        } else {
            bloomFilter.put(n);
            return false;
        }
    }

}
