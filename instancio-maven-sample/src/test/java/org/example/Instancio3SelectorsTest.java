package org.example;

import org.example.person.Address;
import org.example.person.Person;
import org.example.person.Phone;
import org.instancio.Instancio;
import org.instancio.SelectorGroup;
import org.instancio.TypeToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.all;
import static org.instancio.Select.allStrings;
import static org.instancio.Select.field;
import static org.instancio.Select.fields;
import static org.instancio.Select.root;
import static org.instancio.Select.scope;
import static org.instancio.Select.types;

/**
 * Demonstrates how to use different types of selectors.
 *
 * <ul>
 *   <li>field(), all() - regular selectors</li>
 *   <li>fields(), types() - predicate selectors</li>
 *   <li>all(...) - selector groups</li>
 *   <li>root() - selecting the root object</li>
 *   <li>within() and scope() - to narrow down selector targets</li>
 * </ul>
 */
class Instancio3SelectorsTest {

    //
    // regular selectors: field(), all()
    //

    @Test
    @DisplayName("Selecting a field of the class being created")
    void selectFieldInRootClass() {
        Person person = Instancio.of(Person.class)
                .set(field("name"), "Homer") // selects Person.name
                .create();

        assertThat(person.getName()).isEqualTo("Homer");
    }

    @Test
    @DisplayName("Selecting a field of the specified class")
    void selectFieldInAnyClass() {
        Person person = Instancio.of(Person.class)
                .set(field(Address.class, "city"), "Springfield")
                .create();

        assertThat(person.getAddress().getCity()).isEqualTo("Springfield");
    }

    @Test
    @DisplayName("Selecting all instances of a class")
    void selectClass() {
        Phone phone = Instancio.of(Phone.class)
                .set(allStrings(), "foo") // shorthand for: all(String.class)
                .create();

        assertThat(phone.getNumber()).isEqualTo("foo");
        assertThat(phone.getCountryCode()).isEqualTo("foo");
    }

    //
    // Predicate selectors: fields(), types()
    //

    @Test
    @DisplayName("Using a predicate to target fields")
    void fieldPredicate() {
        Predicate<Field> fieldNamesStartingWithC = field -> field.getName().startsWith("c");

        Address address = Instancio.of(Address.class)
                .set(fields(fieldNamesStartingWithC), "foo")
                .create();

        assertThat(address.getCity()).isEqualTo("foo");
        assertThat(address.getCountry()).isEqualTo("foo");
        // non-match
        assertThat(address.getStreet()).isNotEqualTo("foo");
    }

    @Test
    @DisplayName("Using a class to target types")
    void classPredicate() {
        Predicate<Class<?>> allEnums = Class::isEnum;

        Person person = Instancio.of(Person.class)
                .ignore(types(allEnums))
                .create();

        assertThat(person.getGender()).isNull();
    }

    //
    // Predicate selector builders
    //

    @Test
    @DisplayName("Using predicate builder to target fields")
    void fieldPredicateBuilder() {
        Person person = Instancio.of(Person.class)
                .generate(fields().ofType(LocalDateTime.class), gen -> gen.temporal().localDateTime().past())
                .create();

        assertThat(person.getLastModified()).isBefore(LocalDateTime.now());
    }

    @Test
    @DisplayName("Using predicate builder to target classes")
    void classPredicateBuilder() {
        Address address = Instancio.of(Address.class)
                .generate(types().of(Collection.class), gen -> gen.collection().size(5))
                .create();

        assertThat(address.getPhoneNumbers()).hasSize(5);
    }

    //
    // Selector groups; only regular (non-predicate) selectors can be grouped
    //

    @Test
    void selectorGroup() {
        final SelectorGroup genderAndId = all(
                field(Person.class, "id"),
                field(Person.class, "gender"));

        Person person = Instancio.of(Person.class)
                .ignore(genderAndId)
                .create();

        assertThat(person.getId()).isNull();
        assertThat(person.getGender()).isNull();
    }

    //
    // Root selector
    //

    @Test
    @DisplayName("Root is the object being created; in this example, it's the outer list")
    void selectRoot() {
        final int outerListSize = 1;
        final int innerListSize = 3;

        List<List<String>> result = Instancio.of(new TypeToken<List<List<String>>>() {})
                .generate(root(), gen -> gen.collection().size(outerListSize))
                .generate(all(List.class), gen -> gen.collection().size(innerListSize))
                .create();

        assertThat(result)
                .hasSize(outerListSize)
                .allSatisfy(innerList -> assertThat(innerList).hasSize(innerListSize));

    }

    @Test
    @DisplayName("Using scope() to narrow down selector targets")
    void selectAllStringsInThePhoneClass() {
        Address address = Instancio.of(Address.class)
                .set(allStrings().within(scope(Phone.class)), "bar")
                .create();

        assertThat(address.getPhoneNumbers()).allSatisfy(phone -> {
            assertThat(phone.getCountryCode()).isEqualTo("bar");
            assertThat(phone.getNumber()).isEqualTo("bar");
        });
    }
}
