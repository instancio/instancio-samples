/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.example;

import org.example.pojo.Address;
import org.example.pojo.Person;
import org.example.pojo.Pet;
import org.example.pojo.PetType;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.generator.AfterGenerate;
import org.instancio.junit.InstancioExtension;
import org.instancio.junit.Seed;
import org.instancio.junit.WithSettings;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;
import static org.instancio.Select.field;

@ExtendWith(InstancioExtension.class)
class SampleTest {

    private static final String COUNTRY_CODE_REGEX = "\\+\\d\\d";

    /**
     * {@code PopulateAction.NULLS} tells Instancio to populate null fields with random data.
     */
    @WithSettings
    private static final Settings settings = Settings.create()
            .set(Keys.AFTER_GENERATE_HINT, AfterGenerate.POPULATE_NULLS)
            .lock();

    /**
     * @see org.example.generators.SampleGeneratorProvider
     */
    @Test
    @DisplayName("Should use custom generators registered via GeneratorProvider SPI")
    void customGenerators() {
        final Person person = Instancio.create(Person.class);

        System.out.println(person);

        // Verify some of the fields we set manually
        // using Data Faker and org.instancio.Random
        assertThat(person.getName()).isNotBlank();
        assertThat(person.getPassportNumber()).matches("\\w\\w\\d{6}");
        assertThat(person.getAddress().getPhoneNumbers()).isNotEmpty()
                .allSatisfy(phone -> {
                    assertThat(phone.getCountryCode()).matches(COUNTRY_CODE_REGEX);
                    assertThat(phone.getPhoneType()).isNotNull();
                });

        // Fields we didn't manually set in the generator
        // should be populated automatically by Instancio
        assertThat(person.getId()).isNotNull();
        assertThat(person.getHobbies()).isNotEmpty().doesNotContainNull();
        assertThat(person.getPets()).isNotEmpty().doesNotContainNull();
        assertThat(person.getAddress().getCountry()).isNotNull();
    }

    @Test
    @Seed(123456)
    @DisplayName("Should produce identical data on each run")
    void populateWithSameSeed() {
        final Person person = Instancio.create(Person.class);
        System.out.println(person);
    }

    @Test
    @DisplayName("Should allow overriding generated values")
    void customisingGeneratedData() {
        final Pet simbaTheTiger = Pet.builder()
                .name("Simba")
                .petType(PetType.TIGER)
                .build();

        final Person person = Instancio.of(Person.class)
                .set(field("name"), "Joe Exotic")
                .generate(field("pets"), gen -> gen.array().with(simbaTheTiger))
                .create();

        System.out.println(person);

        assertThat(person.getName()).isEqualTo("Joe Exotic");

        assertThat(person.getPets())
                .as("Should contain Simba mixed with other random Pets")
                .hasSizeGreaterThan(1)
                .contains(simbaTheTiger);
    }

    @Test
    @DisplayName("Should be able to create a common Model template")
    void usingModelsWithCustomGenerators() {
        final int minNumberOfPets = 10;
        final int passportNumberLength = 9;

        final Model<Person> exoticModel = Instancio.of(Person.class)
                .generate(all(Pet[].class), gen -> gen.array().minLength(minNumberOfPets))
                .generate(all(PetType.class), gen -> gen.oneOf(PetType.TIGER, PetType.LION))
                .generate(field("dateOfBirth"), gen -> gen.temporal().localDate().past())
                .generate(field("hobbies"), gen -> gen.collection().with(""))
                .set(field(Address.class, "country"), "USA")
                .generate(field("passportNumber"), gen -> gen.string().digits().length(passportNumberLength))
                .toModel();

        // create persons using the model
        final Person joe = Instancio.of(exoticModel)
                .set(field("name"), "Joe Exotic")
                .create();

        final Person carole = Instancio.of(exoticModel)
                .set(field("name"), "Carole Baskin")
                .create();

        System.out.println(joe);
        System.out.println(carole);

        assertExoticPerson(joe, "Joe Exotic", minNumberOfPets, passportNumberLength);
        assertExoticPerson(carole, "Carole Baskin", minNumberOfPets, passportNumberLength);
    }

    private void assertExoticPerson(final Person person,
                                    final String expectedName,
                                    final int minNumberOfPets,
                                    final int passportNumberLength) {
        assertThat(person.getName()).isEqualTo(expectedName);

        assertThat(person.getPets())
                .hasSizeGreaterThanOrEqualTo(minNumberOfPets)
                .extracting(Pet::getPetType)
                .containsOnly(PetType.TIGER, PetType.LION);

        assertThat(person.getDateOfBirth()).isBefore(LocalDate.now());
        assertThat(person.getPassportNumber()).hasSize(passportNumberLength).containsOnlyDigits();
        assertThat(person.getAddress().getCountry()).isEqualTo("USA");
    }
}
