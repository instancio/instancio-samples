package org.example;

import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.WithSettings;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(InstancioExtension.class)
class UsingCustomSettingsTest {

    private static final int MIN_STRING_LENGTH = 20;
    private static final int MAX_STRING_LENGTH = 25;
    private static final int MIN_COLLECTION_SIZE = 5;
    private static final int MAX_COLLECTION_SIZE = 7;

    // Override default settings
    @WithSettings
    private final Settings settings = Settings.create()
            .set(Keys.STRING_MIN_LENGTH, MIN_STRING_LENGTH)
            .set(Keys.STRING_MAX_LENGTH, MAX_STRING_LENGTH)
            .set(Keys.COLLECTION_MIN_SIZE, MIN_COLLECTION_SIZE)
            .set(Keys.COLLECTION_MAX_SIZE, MAX_COLLECTION_SIZE);

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

}
