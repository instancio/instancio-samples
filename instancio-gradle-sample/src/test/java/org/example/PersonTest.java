package org.example;

import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.InstancioSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;
import static org.instancio.Select.field;

@ExtendWith(InstancioExtension.class)
class PersonTest {

    @Test
    @DisplayName("Hello world example: generate a Person filled with random data")
    void person() {
        Person person = Instancio.create(Person.class);
        assertThat(person).isNotNull();
        System.out.println(person);
    }

    @ParameterizedTest
    @InstancioSource({UUID.class, Address.class})
    @DisplayName("Generate UUID and Address via JUnit params")
    void personParams(final UUID uuid, final Address address) {
        assertThat(uuid).isNotNull();
        assertThat(address).isNotNull();
    }

    @Test
    @DisplayName("Customise Person using random and non-random values")
    void customisedPerson() {
        Person person = Instancio.of(Person.class)
                .set(field("name"), "Homer Simpson")
                .set(field(Address.class, "city"), "Springfield")
                .generate(field("age"), gen -> gen.ints().range(18, 80))
                .generate(all(LocalDateTime.class), gen -> gen.temporal().localDateTime().past())
                .create();

        assertThat(person.getName()).isEqualTo("Homer Simpson");
        assertThat(person.getAddress().getCity()).isEqualTo("Springfield");
        assertThat(person.getAge()).isBetween(18, 80);
        assertThat(person.getLastModified()).isBefore(LocalDateTime.now());
    }

    /**
     * Example of using metamodels.
     * <p>
     * Person_ and Address_ were auto-generated based on {@link ExampleMetamodel}  class.
     */
    @Test
    @DisplayName("Customise Person using Instancio metamodel classes")
    void customisedPersonUsingMetamodel() {
        Person person = Instancio.of(Person.class)
                .set(Person_.name, "Homer Simpson")
                .set(Address_.city, "Springfield")
                .generate(Person_.age, gen -> gen.ints().range(18, 80))
                .generate(Person_.lastModified, gen -> gen.temporal().localDateTime().past())
                .create();

        assertThat(person.getName()).isEqualTo("Homer Simpson");
        assertThat(person.getAddress().getCity()).isEqualTo("Springfield");
        assertThat(person.getAge()).isBetween(18, 80);
        assertThat(person.getLastModified()).isBefore(LocalDateTime.now());
    }
}
