package org.example.pojo;

import org.example.util.PrettyToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Person {

    private final UUID id;
    private final String name;
    private final String passportNumber;
    private final LocalDate dateOfBirth;
    private final Address address;
    private final List<String> hobbies;
    private final Pet[] pets;

    private Person(final Builder builder) {
        id = builder.id;
        name = builder.name;
        passportNumber = builder.passportNumber;
        dateOfBirth = builder.dateOfBirth;
        address = builder.address;
        hobbies = builder.hobbies;
        pets = builder.pets;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public Pet[] getPets() {
        return pets;
    }

    @Override
    public String toString() {
        return PrettyToString.toPrettyString(this);
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UUID id;
        private String name;
        private String passportNumber;
        private LocalDate dateOfBirth;
        private Address address;
        private List<String> hobbies;
        private Pet[] pets;

        private Builder() {
        }

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder passportNumber(final String passportNumber) {
            this.passportNumber = passportNumber;
            return this;
        }

        public Builder dateOfBirth(final LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder address(final Address address) {
            this.address = address;
            return this;
        }

        public Builder hobbies(final List<String> hobbies) {
            this.hobbies = hobbies;
            return this;
        }

        public Builder pets(final Pet[] pets) {
            this.pets = pets;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
