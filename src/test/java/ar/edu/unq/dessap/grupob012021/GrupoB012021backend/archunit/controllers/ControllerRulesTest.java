package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.archunit.controllers;
import static com.tngtech.archunit.core.domain.JavaClass.Functions.GET_PACKAGE_NAME;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.base.PackageMatchers;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMember;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.Test;
import org.springframework.security.access.annotation.Secured;

import static com.tngtech.archunit.core.domain.JavaMember.Predicates.declaredIn;
import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.annotatedWith;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class ControllerRulesTest {
    private final JavaClasses classes = new ClassFileImporter().importPackages("ar.edu.unq.dessap.grupob012021.GrupoB012021backend");

//    @Test
//    public void controllers_should_only_call_secured_methods() {
//        classes()
//                .that().haveNameMatching(".*Controller")
//                .should().onlyCallMethodsThat(areDeclaredInController())
//                .check(classes);
//    }
    @Test
    public void controllers_should_have_their_own_package() {
        classes()
                .that().haveNameMatching(".*Controller")
                .should().resideInAPackage("..controllers..")
                .check(classes);
    }
    private DescribedPredicate<JavaMember> areDeclaredInController() {
        DescribedPredicate<JavaClass> aPackageController = GET_PACKAGE_NAME.is(PackageMatchers.of("..controller..", "java.."))
                .as("a package '..controller..'");
        return are(declaredIn(aPackageController));
    }
}