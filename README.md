# SS_GRADLE_PLUGIN
![technology java](https://img.shields.io/badge/technology-Java-blue.svg)
![technology Gradle](https://img.shields.io/badge/technology-Gradle-blue.svg)

## Plugin
- Essa é uma solução que facilita a utilização de credenciais ou qualquer variável no `build.gradle` sem termos a necessidade de informá-las utilizando o `gradle.properties` ou de forma exposta. Em alguns cenarios de segurança faz sentido manter a confidenciabilidade de algumas informações. Esse plugin acrescenta a possibilidade de utilizar parâmetros existentes no `Parameter Store de qualquer conta AWS` e setá-las como variáveis de ambiente `somente em tempo de execução do build.gradle`, ou seja, após o `gradle build` por exemplo, essas variáveis deixam de existir.  

## Pré-requisito
- Ter uma instalação `aws-cli` configurada para que as credenciais `{HOME}/.aws/credentials` possam ser utilizadas para acessar o AWS Parameter Store

## Compatibilidade
| Versão | Compatível |
| ------ | ------ |
| v7.0.0+ | YES |
| v7.0.0- | NOT TESTED |

## Plugin
- Página oficial do Plugin -> [ssm-gradle-plugin](https://plugins.gradle.org/plugin/io.github.diegopmendes.ssmgradle)

## Exemplo de utilização
##### 1 - Supondo que um `Projeto X` esteja utilizando o plugin:
* `build.gradle` do Projeto X

```gradle
plugins {
    .
    .
    .
    id "io.github.diegopmendes.ssmgradle" version "1.0.0"
}

apply plugin: "io.github.diegopmendes.ssmgradle"

environments {
    awsProfile = 'develop' //optional, caso não tenha especificado um profile ele irá pegar o `default` das credentials (`{HOME}/.aws/credentials`)
    environmentsNames = [ //no exemplo abaixo ele irá buscar no Parameter Store o paramêtro de nome `NAME_PARAMETER_STORE_1` e irá setar em uma variável de ambiente chamada `NAME_1_ENV_SYSTEM_TARGET`
            'NAME_PARAMETER_STORE_1' : 'NAME_1_ENV_SYSTEM_TARGET',
            'NAME_PARAMETER_STORE_2' : 'NAME_2_ENV_SYSTEM_TARGET'
    ]
}

tasks.create("Hello"){
    dependsOn(importEnvironments) //toda task que precisar das variáveis setadas precisa colocar esse `dependsOn` no início
    doLast {
        println "Hello ENV 1: " + System.getenv("NAME_1_ENV_SYSTEM_TARGET")// após setada podemos chamar e utilizar em qualquer task no build.gradle
        println "Hello ENV 2: " + System.getenv("NAME_2_ENV_SYSTEM_TARGET")// após setada podemos chamar e utilizar em qualquer task no build.gradle
    }
}
```

* Executar para testar:
```bash
./gradlew Hello
```

##### 2 - Existe um caso em particular quando queremos utilizar as variáveis como credentials em algum `repositorie`, nesse caso vamos acrecentar um bloco a mais, como no exemplo abaixo:
* `build.gradle` do Projeto X
 
```gradle
plugins {
    .
    .
    .
    id "io.github.diegopmendes.ssmgradle" version "1.0.0"
}

apply plugin: "io.github.diegopmendes.ssmgradle"

environments {
    awsProfile = 'develop' //optional, caso não tenha especificado um profile ele irá pegar o `default` das credentials (`{HOME}/.aws/credentials`)
    environmentsNames = [ //no exemplo abaixo ele irá buscar no Parameter Store o paramêtro de nome `NAME_PARAMETER_STORE_1` e irá setar em uma variável de ambiente chamada `NAME_1_ENV_SYSTEM_TARGET`
            'NAME_PARAMETER_STORE_USERNAME' : 'USERNAME_1',
            'NAME_PARAMETER_STORE_PASSWORD' : 'PASSWORD_1'
    ]
     repositories = //os repositories que forem aqui criados não devem ser colocados duplicados na tag `repositories{}`
    [
        ['name':'GitHubPackages',
         'url':'https://maven.pkg.github.com/ORGANIZATION/*',
         'username':'USERNAME_1',
         'password':'PASSWORD_1'
        ]
    ]
}

dependencies{
        implementation('com.commons:DEPENDENCY_FROM_GITHUB_PACKAGES:1.0.0')
}

```
* Executar para testar:
```bash
./gradlew clean build
```

### Deseja Contribuir?
- Envia um email para `diegu.jc@gmail.com` com assunto [SSM-GRADLE-PLUGIN]
