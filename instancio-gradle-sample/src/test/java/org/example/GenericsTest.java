package org.example;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.instancio.Instancio;
import org.instancio.TypeToken;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(InstancioExtension.class)
class GenericsTest {

    /**
     * Using TypeToken is the recommended way to create generic objects.
     */
    @Test
    void createPersonMapUsingTypeToken() {
        Map<UUID, Address> map = Instancio.create(new TypeToken<Map<UUID, Address>>() {});

        assertThat(map).isNotEmpty();
        System.out.println(map);
    }

    /**
     * Alternative way to create generic objects is using 'withTypeParameters'.
     * However, this approach generates an "unchecked assignment" warning.
     */
    @Test
    void createPersonMapUsingTypeParameters() {
        Map<UUID, Address> map = Instancio.of(Map.class)
                .withTypeParameters(UUID.class, Address.class)
                .create();

        assertThat(map).isNotEmpty();
        System.out.println(map);
    }

    /**
     * Using type token allows us to create almost any generic object,
     * including nested generics.
     */
    @Test
    void moreComplicatedGenerics() {
        List<Triplet<Integer, String, LocalDate>> list = Instancio.create(
                new TypeToken<List<Triplet<Integer, String, LocalDate>>>() {});

        assertThat(list)
                .isNotEmpty()
                .allSatisfy(triplet -> {
                    assertThat(triplet.getLeft()).isInstanceOf(Integer.class);
                    assertThat(triplet.getMiddle()).isInstanceOf(String.class);
                    assertThat(triplet.getRight()).isInstanceOf(LocalDate.class);
                });

        list.forEach(System.out::println);
    }

    /**
     * A generic triplet class.
     */
    static class Triplet<L, M, R> {
        private L left;
        private M middle;
        private R right;

        public L getLeft() {
            return left;
        }

        public M getMiddle() {
            return middle;
        }

        public R getRight() {
            return right;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

}
