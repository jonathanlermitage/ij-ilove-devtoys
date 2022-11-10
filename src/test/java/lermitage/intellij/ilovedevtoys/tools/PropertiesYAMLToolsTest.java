package lermitage.intellij.ilovedevtoys.tools;

import lermitage.intellij.ilovedevtoys.tools.PropertiesYAMLTools.PropertiesType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertiesYAMLToolsTest {

    @Test
    void should_propertiesToYaml_convert_spring_properties_with_profiles() {
        assertEquals(
            """
                logging:
                  file:
                    name: myapplication.log
                xxx:
                  property: health, info, refresh, shutdown, selftest, listProcessors, foo, bar
                ---
                spring:
                  config:
                    activate:
                      on-profile: dev
                  datasource:
                    password: password
                    url: jdbc:h2:dev
                    username: SA
                xxx:
                  property: devValue
                ---
                spring:
                  config:
                    activate:
                      on-profile: prod
                  datasource:
                    password: password
                    url: jdbc:h2:prod
                    username: prodUser
                xxx:
                  property: prodValue""",
            PropertiesYAMLTools.propertiesToYaml("""
                logging.file.name=myapplication.log
                xxx.property=health, info, refresh, shutdown, selftest, listProcessors, foo, bar
                #---
                spring.config.activate.on-profile=dev
                spring.datasource.password=password
                spring.datasource.url=jdbc:h2:dev
                spring.datasource.username=SA
                xxx.property=devValue
                #---
                spring.config.activate.on-profile=prod
                spring.datasource.password=password
                spring.datasource.url=jdbc:h2:prod
                spring.datasource.username=prodUser
                xxx.property=prodValue""", PropertiesType.SPRING));
    }

    @Test
    void should_propertiesToYaml_convert_spring_properties_without_profiles() {
        assertEquals(
            """
                spring:
                  config:
                    activate:
                      on-profile: dev
                  datasource:
                    password: password
                    url: jdbc:h2:dev
                    username: SA
                xxx:
                  property: devValue3""",
            PropertiesYAMLTools.propertiesToYaml("""
                spring.config.activate.on-profile=dev
                spring.datasource.password=password
                spring.datasource.url=jdbc:h2:dev
                spring.datasource.username=SA
                xxx.property=devValue1
                xxx.property=devValue2
                xxx.property=devValue3""", PropertiesType.SPRING));
    }

    @Test
    void should_propertiesToYaml_convert_regular_properties_ignoring_spring_profiles() {
        assertEquals(
            """
                logging:
                  file:
                    name: myapplication.log
                spring:
                  config:
                    activate:
                      on-profile: prod
                  datasource:
                    password: password
                    url: jdbc:h2:prod
                    username: prodUser
                xxx:
                  property: prodValue""",
            PropertiesYAMLTools.propertiesToYaml("""
                logging.file.name=myapplication.log
                xxx.property=health, info, refresh, shutdown, selftest, listProcessors, foo, bar
                #---
                spring.config.activate.on-profile=dev
                spring.datasource.password=password
                spring.datasource.url=jdbc:h2:dev
                spring.datasource.username=SA
                xxx.property=devValue
                #---
                spring.config.activate.on-profile=prod
                spring.datasource.password=password
                spring.datasource.url=jdbc:h2:prod
                spring.datasource.username=prodUser
                xxx.property=prodValue""", PropertiesType.REGULAR));
    }
}
