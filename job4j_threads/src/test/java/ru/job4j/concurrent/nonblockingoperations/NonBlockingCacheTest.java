package ru.job4j.concurrent.nonblockingoperations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NonBlockingCacheTest {
    NonBlockingCache cache;
    Base base;
    Base updated;

    @BeforeEach
    void init() {
        base = new Base(1, 1);
        base.setName("base");
        cache = new NonBlockingCache();
        updated = new Base(1, 1);
        updated.setName("updated");
    }

    @Test
    void shouldUpdateVersionOfValue() {
        updateCache();
        assertEquals(2, cache.get(1).getVersion());
    }

    @Test
    void shouldUpdateNameOfValue() {
        updateCache();
        assertEquals("updated", cache.get(1).getName());
    }

    @Test
    void shouldReturnTrueIfUpdated() {
        cache.add(base);
        assertTrue(cache.update(new Base(1, 1)));
    }

    @Test
    void shouldReturnFalseIfNotUpdatedWhenValueToUpdateDoesNotExistInCache() {
        cache.add(base);
        assertFalse(cache.update(new Base(3, 1)));
    }

    @Test
    void shouldThrowOptimisticExceptionWhenVersionsDidNotMatch() {
        cache.add(base);
        cache.update(base);
        assertThrows(OptimisticException.class, () -> cache.update(base));
    }

    @Test
    void shouldRemoveValue() {
        cache.add(base);
        cache.delete(base);
        assertNull(cache.get(1));
    }

    @Test
    void shouldReturnTrueIfValueWasRemoved() {
        cache.add(base);
        assertTrue(cache.delete(base));
    }

    @Test
    void shouldReturnFalseIfNotRemovedWhenValueDoesNotExistInCache() {
        cache.add(base);
        assertFalse(cache.delete(new Base(2, 1)));
    }

    void updateCache() {
        cache.add(base);
        cache.update(updated);
    }
}
