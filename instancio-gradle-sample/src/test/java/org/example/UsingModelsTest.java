package org.example;

import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Examples of using {@link org.instancio.Model}.
 */
@ExtendWith(InstancioExtension.class)
class UsingModelsTest {

    private final Model<Person> simpsons = Instancio.of(Person.class)
            .set(Address_.city, "Springfield")
            .set(Address_.country, "US")
            .set(Phone_.countryCode, "+1")
            .generate(Phone_.number, gen -> gen.text().pattern("#d#d#d-#d#d-#d#d"))
            .toModel();

    @Test
    void simpsonsModel() {
        final Person homer = Instancio.of(simpsons)
                .set(Person_.name, "Homer")
                .create();

        final Person marge = Instancio.of(simpsons)
                .set(Person_.name, "Marge")
                .create();

        // Address based on the model
        assertAddress(homer);
        assertAddress(marge);

        assertThat(homer.getName()).isEqualTo("Homer");
        assertThat(marge.getName()).isEqualTo("Marge");
    }

    private void assertAddress(final Person simpsons) {
        assertThat(simpsons.getAddress().getCity()).isEqualTo("Springfield");
        assertThat(simpsons.getAddress().getCountry()).isEqualTo("US");
        assertThat(simpsons.getAddress().getPhoneNumbers())
                .isNotEmpty()
                .allSatisfy(phone -> assertThat(phone.getCountryCode()).isEqualTo("+1"));
    }

}
