package org.example;

import org.instancio.Generator;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;

@ExtendWith(InstancioExtension.class)
class UsingCustomGeneratorTest {

    private static final int PHONES_SIZE = 5;

    @Test
    void customisedPerson() {
        // Using the 'random' instance supplied by Instancio allows us to generate reproducible data
        Generator<Phone> phoneGenerator = random -> {
            String countryCode = random.oneOf("+1", "+52");
            String phoneNumber = String.valueOf(random.intRange(1000000, 9999999));
            return new Phone(countryCode, phoneNumber);
        };

        final Person person = Instancio.of(Person.class)
                .supply(all(Phone.class), phoneGenerator)
                .generate(Address_.phoneNumbers, gen -> gen.collection().size(PHONES_SIZE))
                .create();

        assertThat(person.getAddress().getPhoneNumbers()).hasSize(PHONES_SIZE);

        System.out.println(person.getAddress().getPhoneNumbers());
    }

}
