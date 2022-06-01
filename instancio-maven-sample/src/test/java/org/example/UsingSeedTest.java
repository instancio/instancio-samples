package org.example;

import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.Seed;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(InstancioExtension.class)
class UsingSeedTest {

    private static final Set<UUID> results = new HashSet<>();

    @RepeatedTest(5)
    @Seed(12345)
    void withSeed() {
        UUID uuid = Instancio.create(UUID.class);
        System.out.println("Generated UUID: " + uuid);

        results.add(uuid);
        assertThat(results)
                .as("Same value generated each time using given seed")
                .hasSize(1);
    }
}
