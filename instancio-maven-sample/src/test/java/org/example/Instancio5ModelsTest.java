package org.example;

import org.example.person.Address;
import org.example.person.Gender;
import org.example.person.Person;
import org.example.person.Phone;
import org.instancio.Instancio;
import org.instancio.Model;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;
import static org.instancio.Select.field;

/**
 * Examples of using {@link org.instancio.Model}.
 */
class Instancio5ModelsTest {

    // A model is a template for creating objects.
    // Define a model and create objects from it base on the template.
    // Objects created from Models can be customised further or even turned into other models.
    private static Model<Person> simpsonsModel() {
        return Instancio.of(Person.class)
                .set(field(Address.class, "city"), "Springfield")
                .set(field(Address.class, "country"), "US")
                .set(field(Phone.class, "countryCode"), "+1")
                .generate(field(Phone.class, "number"), gen -> gen.text().pattern("#d#d#d-#d#d-#d#d"))
                .toModel();
    }

    @Test
    void createSimpsonsFromModel() {
        Person homer = Instancio.of(simpsonsModel())
                .set(field(Person.class, "name"), "Homer")
                .set(all(Gender.class), Gender.MALE)
                .create();

        Person marge = Instancio.of(simpsonsModel())
                .set(field(Person.class, "name"), "Marge")
                .set(all(Gender.class), Gender.FEMALE)
                .create();

        assertSimpson(homer, "Homer", Gender.MALE);
        assertSimpson(marge, "Marge", Gender.FEMALE);
    }

    @Test
    @DisplayName("Models can be created from other Models")
    void createModelFromModel() {
        Model<Person> simpsonsKid = Instancio.of(simpsonsModel())
                .generate(field("age"), gen -> gen.ints().range(5, 10))
                .toModel();

        Person bart = Instancio.of(simpsonsKid)
                .set(field(Person.class, "name"), "Bart")
                .set(all(Gender.class), Gender.MALE)
                .create();

        assertSimpson(bart, "Bart", Gender.MALE);
        assertThat(bart.getAge()).isBetween(5, 10);
    }

    private static void assertSimpson(Person simpson, String name, Gender gender) {
        assertThat(simpson.getName()).isEqualTo(name);
        assertThat(simpson.getGender()).isEqualTo(gender);

        // Address is based on the model and was not modified
        assertAddress(simpson.getAddress());
    }

    private static void assertAddress(Address address) {
        assertThat(address.getCity()).isEqualTo("Springfield");
        assertThat(address.getCountry()).isEqualTo("US");
        assertThat(address.getPhoneNumbers())
                .isNotEmpty()
                .allSatisfy(phone -> assertThat(phone.getCountryCode()).isEqualTo("+1"));
    }

}
