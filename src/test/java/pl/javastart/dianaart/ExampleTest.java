package pl.javastart.dianaart;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExampleTest {

    public void example() {
        Assertions.assertThat(1).isEqualTo(1);
    }
    @Test
    public void nameShouldEndWithLetterA(){
        String name = "Dorota";
        org.junit.jupiter.api.Assertions.assertFalse(name.endsWith("a"));
        org.assertj.core.api.Assertions.assertThat(name).doesNotEndWith("a");
    }
}
