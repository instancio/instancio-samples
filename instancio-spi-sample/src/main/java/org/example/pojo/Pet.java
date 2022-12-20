package org.example.pojo;

import org.example.util.PrettyToString;

public class Pet {
    private final String name;
    private final PetType petType;

    private Pet(final Builder builder) {
        name = builder.name;
        petType = builder.petType;
    }

    public String getName() {
        return name;
    }

    public PetType getPetType() {
        return petType;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return PrettyToString.toPrettyString(this);
    }

    public static final class Builder {
        private String name;
        private PetType petType;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder petType(final PetType petType) {
            this.petType = petType;
            return this;
        }

        public Pet build() {
            return new Pet(this);
        }
    }
}
