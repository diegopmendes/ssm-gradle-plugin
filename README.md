# ssm-gradle-plugin

Projeto responsável por recuperar variáveis do parameter store e setar como `environments` na JVM

# Publicar local

* 1 - Acrescentar no build.gradle

 ```
 publishing {
    repositories {
        mavenLocal()
    }
 }
 ```

* 2 - Acrescentar no settings.gradle e do projeto que irá importar

```
pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}
```

* 3 - `./gradlew publish`

# Publicar Portal Publico Gradle

* 1 - Setar as credenciais do seu login no portal em: `$HOME/.gradle/gradle.properties`
* 2 - `./gradlew publishPlugins
