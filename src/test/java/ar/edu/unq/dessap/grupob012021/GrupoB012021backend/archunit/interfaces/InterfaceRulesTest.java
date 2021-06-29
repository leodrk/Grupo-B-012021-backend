package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.archunit.interfaces;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
//https://www.archunit.org/use-cases
//https://github.com/TNG/ArchUnit-Examples/tree/main/example-plain/src/test/java/com/tngtech/archunit/exampletest

@SpringBootTest
public class InterfaceRulesTest {
    private final JavaClasses classes = new ClassFileImporter().importPackages("ar.edu.unq.dessap.grupob012021.GrupoB012021backend");

    @Test
    public void interfaces_should_not_have_names_ending_with_the_word_interface() {
        noClasses().that().areInterfaces().should().haveNameMatching(".*Interface").check(classes);
    }

    @Test
    public void interfaces_should_not_have_simple_class_names_containing_the_word_interface() {
        noClasses().that().areInterfaces().should().haveSimpleNameContaining("Interface").check(classes);
    }

    @Test
    public void interfaces_must_not_be_placed_in_implementation_packages() {
        noClasses().that().resideInAPackage("..impl..").should().beInterfaces().check(classes);
    }
}
