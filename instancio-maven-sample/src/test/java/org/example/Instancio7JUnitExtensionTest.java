package org.example;

import org.example.person.Address;
import org.example.person.Person;
import org.instancio.Instancio;
import org.instancio.Result;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.InstancioSource;
import org.instancio.junit.Seed;
import org.instancio.junit.WithSettings;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The Instancio extension for JUnit 5 provides a few additional features:
 *
 * <ul>
 *   <li>Reporting seed value when a test fails</li>
 *   <li>Seed annotation</li>
 *   <li>Settings injection</li>
 *   <li>Parameterized test arguments</li>
 * </ul>
 */
@ExtendWith(InstancioExtension.class)
class Instancio7JUnitExtensionTest {

    private static final int MIN_STRING_LENGTH = 20;
    private static final int MAX_STRING_LENGTH = 25;
    private static final int MIN_COLLECTION_SIZE = 5;
    private static final int MAX_COLLECTION_SIZE = 7;

    // Override default settings
    @WithSettings
    private static final Settings settings = Settings.create()
            .set(Keys.STRING_MIN_LENGTH, MIN_STRING_LENGTH)
            .set(Keys.STRING_MAX_LENGTH, MAX_STRING_LENGTH)
            .set(Keys.COLLECTION_MIN_SIZE, MIN_COLLECTION_SIZE)
            .set(Keys.COLLECTION_MAX_SIZE, MAX_COLLECTION_SIZE)
            .lock();

    @Test
    @DisplayName("Should use Settings overrides for all strings and collections")
    void withCustomSettings() {
        Person person = Instancio.create(Person.class);

        assertThat(person.getName().length()).isBetween(MIN_STRING_LENGTH, MAX_STRING_LENGTH);
        assertThat(person.getAddress().getCity().length()).isBetween(MIN_STRING_LENGTH, MAX_STRING_LENGTH);

        assertThat(person.getAddress().getPhoneNumbers().size())
                .as("All collections will also have specified sizes")
                .isBetween(MIN_COLLECTION_SIZE, MAX_COLLECTION_SIZE);
    }

    @ParameterizedTest
    @InstancioSource({UUID.class, Address.class})
    void parameterizedExample(UUID uuid, Address address) {
        assertThat(uuid).isNotNull();
        assertThat(address).isNotNull();
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class SeedAnnotationTest {
        private final Set<UUID> results = new HashSet<>();

        @RepeatedTest(5)
        @Seed(12345)
        void withSeedAnnotation() {
            UUID uuid = Instancio.create(UUID.class);
            System.out.println("Generated UUID: " + uuid);

            results.add(uuid);
            assertThat(results)
                    .as("Same value generated each time using given seed")
                    .hasSize(1);
        }

        @Test
        @Seed(12345)
        void overrideSeedAnnotation() {
            Result<UUID> result1 = Instancio.of(UUID.class).asResult(); // uses seed from annotation

            Result<UUID> result2 = Instancio.of(UUID.class)
                    .withSeed(234) // overrides seed from annotation
                    .asResult();

            assertThat(result1.get()).isNotEqualTo(result2.get());

            assertThat(result1.getSeed()).isEqualTo(12345);
            assertThat(result2.getSeed()).isEqualTo(234);
        }
    }

}
