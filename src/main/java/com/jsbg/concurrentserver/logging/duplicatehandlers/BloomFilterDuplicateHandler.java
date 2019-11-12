package com.jsbg.concurrentserver.logging.duplicatehandlers;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.jsbg.concurrentserver.logging.Logger;

class BloomFilterDuplicateHandler implements DuplicateHandler {
    private static Logger logger = Logger.getLogger();
    private static final int expectedInsertions = 1000 * 1000 * 115;
    private static final float desiredFalsePositiveProbability = 0.001f;

    volatile private BloomFilter<Integer> bloomFilter = BloomFilter.create(
            Funnels.integerFunnel(),
            expectedInsertions,
            desiredFalsePositiveProbability
    );

    @Override
    public boolean isDuplicate(int n) {
        if (bloomFilter.mightContain(n)) {
            return logger.contains(n);
        } else {
            bloomFilter.put(n);
            return false;
        }
    }

}
