package ru.tikskit.imin.services.geocode.here;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Билдер адреса для запроса должен")
class QueryBuilderImplTest {

    @Test
    @DisplayName("не выбрасывать исключений, если в метод addPart передается null ")
    public void shouldNotThrowWhenAddressPartIsNull() {
        new QueryBuilderImpl()
                .addPart("not null")
                .addPart(null)
                .build();
    }

    @Test
    @DisplayName("должен позволять добавлять одинаковые части несколько раз и они должны присутствовать в результате в этом количестве раз")
    public void shouldAllowDuplicates() {
        String result = new QueryBuilderImpl()
                .addPart("diff one")
                .addPart("same one")
                .addPart("same one")
                .build();

        assertThat(substrOccurrencesCount(result, "same one")).isEqualTo(2);
    }
    @Test
    @DisplayName("должен позволять добавлять одинаковые части несколько раз и они должны присутствовать в результате в этом количестве раз")
    public void shouldAddOnePartPerEveryAddPartCall() {
        String result = new QueryBuilderImpl()
                .addPart("one")
                .addPart("two")
                .addPart("three")
                .build();

        assertThat(substrOccurrencesCount(result, "one")).isEqualTo(1);
        assertThat(substrOccurrencesCount(result, "two")).isEqualTo(1);
        assertThat(substrOccurrencesCount(result, "three")).isEqualTo(1);
    }

    private static int substrOccurrencesCount(String str, String substr) {
        return (str.length() - str.replace(substr, "").length()) / substr.length();
    }
}