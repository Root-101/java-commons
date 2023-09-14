[Ingles](README.md)

## Clean Core en español

###### (En proceso de renombrarlo 'utils' o algo asi)

Esta biblioteca pretende dar estándares y utilidades que faliciten el trabajo a la hora de hacer microservicio.

## Table of Contents
- [1 - Validations](#1)
    - [1.1 - Validation Exception](#1.1)
    - [1.2 - Simple object](#1.2)
    - [1.3 - Complex Object & Recursivity](#1.3)
    - [1.4 - Nombre de un campo personalizado](#1.4)
    - [1.5 - Validacion personalizada](#1.5)
    - [1.6 - Validaciones pre-echas:](#1.6)
        - [1.6.1 - Digit](#1.6.1)
        - [1.6.2 - Enum (Simple String y Lista de Strings)](#1.6.2)
        - [1.6.3 - Size Exact](#1.6.3)
- [2 - Exceptions](#exceptions)
- [3 - repo](#repo)
- [4 - rest](#rest)
    - [api response](#) 
    - [response extractor](#) TODO
    - [rest template utils](#) TODO
- [5 - utils](#jackson)
    - [JACKSON](#jackson)
    - [Enum mappeable](#jackson)
    - [Network](#jackson)
    - [Security Algos](#jackson)

## Validations <a name="1"></a>
- Todas las validaciones nativas se cargan del framework `jakarta.validations.*` (TODO: poner link al mvncentral)
- Para validar un objeto se utiliza la clase `dev.root101.clean.core.utils.validation.ValidationService` y sus metodos estaticos. Ejemplo:
`ValidationService.validateAndThrow(some_object);`
- Si todas las validaciones pasaron bien el codigo se ejecuta con normalidad. Si al menos una validacion falla, se lanzara una `ValidationException` (Mas detalles sobre esta excepcion adelante)
- TODOS los ejemplos de validaciones se encuentran en la carpeta de ejemplos `dev.root101.clean.core.examples.validation...`.

### 1.1 - Validation Exception <a name="1.1"></a>
Una vez que se ejecuten las validaciones sobre un objeto, y alguna falle, se lanzara una excepcion de tipo `dev.root101.clean.core.exceptions.ValidationException`.

Esta excepcion cuenta con el:
- `status_code`, que representa el codigo de respuesta http, siendo SIEMPRE el `422: UNPROCESSABLE_ENTITY`. Y
- Una lista de validaciones fallidas (`List<ValidationErrorMessage>`). Cada elemento de esta lista representa una validacion que fallo en algun campo del objeto.
- Cada validacion fallida (`ValidationErrorMessage`) cuenta:
  - `source`: El nombre del campo donde fallo la validacion.
  - `invalid_value`: El valor por el cual fallo la validacion.
  - `message`: El mensaje de error de la validacion fallida.

Para un objeto como:
```java
        record Child(
                @Size(min = 1, max = 5)
                String childName) {

        }

        record Parent(
                @Size(min = 1, max = 5)
                String parentName,
                @NotEmpty
                List<Child> childs) {

        }

        //validating the fields as a list
        ValidationService.validateAndThrow(
                parent,
                parent.childs().get(0),
                parent.childs().get(1)
        );    
```

Se genera una error de validacion:

```java
ValidationException{
    statusCode = 422 UNPROCESSABLE_ENTITY,
    messages = [
        ValidationErrorMessage[
            source = root[0].parentName,
            invalid_value = Pepito,
            message = el tamaño debe estar entre 1 y 5
        ],
        ValidationErrorMessage[
            source = root[1].childName,
            invalid_value = Pepito Junior,
            message = el tamaño debe estar entre 1 y 5
        ], 
        ValidationErrorMessage[
            source = root[2].childName,
            invalid_value = Pepito Junior 2,
            message = el tamaño debe estar entre 1 y 5
        ]
    ]
}
```
Note: the `root[i]` in the `source` indicate the position in the list of every element

### 1.2 - Simple Object <a name="1.2"></a>
De un objeto simple se validan todos sus campos:

```java
        //Simple object
        record Parent(
                @Size(min = 1, max = 5)
                String parentName) {

        }

        //instance of the simple object
        Parent parent = new Parent("Pepito Simple");
        
        //Validate
        ValidationService.validateAndThrow(parent);
```

Este codigo lanzara la excepcion:

```
ValidationException{
    statusCode = 422 UNPROCESSABLE_ENTITY, 
    messages = [
        ValidationErrorMessage[
            source = parentName, 
            invalid_value = Pepito Simple, 
            message = el tamaño debe estar entre 1 y 5
        ]
    ]
}
```

### 1.3 - Complex Object & Recursivity <a name="1.3"></a>
Si se tiene un objecto A con alguno de sus campos siendo otro objeto B, y se quiere validar los campos de A, y a la misma vez los campos de B, se ejecuta una recursividad por todos los campos de los objetos hasta validarlos todos. 
Incluyendo de una lista todos sus elementos

```java
        record Child(
                @Size(min = 1, max = 5)
                String childName) {

        }

        record Parent(
                @Size(min = 1, max = 5)
                String parentName,
                @NotEmpty
                List<Child> childrens) {

        }

        Parent parent = new Parent(
                "Pepito",
                List.of(
                        new Child(
                                "Pepito Junior"
                        ),
                        new Child(
                                "Pepito Junior 2"
                        )
                )
        );

        //validate the object and revursive every field
        ValidationService.validateRecursiveAndThrow(parent);
```

Este codigo lanzara la excepcion:
```
ValidationException{
    statusCode = 422 UNPROCESSABLE_ENTITY,
    messages = [
        ValidationErrorMessage[
            source = parentName,
            invalid_value = Pepito,
            message = el tamaño debe estar entre 1 y 5
        ],
        ValidationErrorMessage[
            source = childrens[0].childName,
            invalid_value = Pepito Junior,
            message = el tamaño debe estar entre 1 y 5
        ], 
        ValidationErrorMessage[
            source = childrens[1].childName,
            invalid_value = Pepito Junior 2,
            message = el tamaño debe estar entre 1 y 5
        ]
    ]
}
```

### 1.4 - Personalize fields names <a name="1.4"></a>
Si una validacion falla, el `source` del `ValidationErrorMessage` muestra el nombre del campo que fallo, pero en caso que se quiera que ese campo tenga un nombre diferente al que tiene en el campo (por una diferencia de tipado, Camel Case, Snake...).

Sin la anotacion la respuesta seria como el [1.2 - Simple object](#simple_object).

Con la anotacion quedaria:
```java
        record Parent(
                @ValidationFieldName("parent_name")
                @Size(min = 1, max = 5)
                String parentName) {

        }

        Parent parent = new Parent("Pepito Simple");

        ValidationService.validateAndThrow(parent);
```

Y la respuesta seria:
```
ValidationException{
    statusCode = 422 UNPROCESSABLE_ENTITY, 
    messages = [
        ValidationErrorMessage[
            source = parent_name, // => note here how the value change from `parentName` to `parent_name`
            invalid_value = Pepito Simple, 
            message = el tamaño debe estar entre 1 y 5
        ]
    ]
}
```

### 1.5 - Personalized annotation <a name="1.5"></a>
En caso que se necesite una validacion mas compleja o personalizada que no venga por defecto en jackson, se puede crear una manualmente, complatible con el `ValidationService`.

El ejemplo de una notacion para validar que le nombre de un Parent sea 'Pepito' quedaria asi:

Se crea la anotacion:
```java
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PersonalizedValidationRegister.class)
@interface PersonalizedValidation {

    String name();

    String message() default "Names don't match.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public String detailMessage() default "";

}
```

Se crea el registro/clase validadora.
```java
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PersonalizedValidationRegister implements ConstraintValidator<PersonalizedValidation, String> {

    //store the annotation for retrieve value later
    private PersonalizedValidation annotation;

    //initialize ConstraintValidator
    @Override
    public void initialize(PersonalizedValidation annotation) {
        ConstraintValidator.super.initialize(annotation);
        this.annotation = annotation;
    }

    //do the real validation
    @Override
    public boolean isValid(String target, ConstraintValidatorContext cvc) {
        //check is the target value is valid or not
        boolean valid = target != null && target.equalsIgnoreCase(annotation.name());

        //if its'n valid, add a custom message to return 
        if (!valid) {
            cvc.disableDefaultConstraintViolation();
            cvc.buildConstraintViolationWithTemplate(
                    String.format(
                            "'%s' don't match with given name".formatted(
                                    target,
                                    annotation.name()
                            )
                    )
            ).addConstraintViolation();
        }

        //if valid is true, no error will be throw
        return valid;
    }

}
```

Anotamos entonces el campo y ejecutamos las validaciones:
```java
        record Parent(
                @Size(min = 1, max = 5)
                String parentName) {

        }
        Parent parent = new Parent("Pepito Simple");

        ValidationService.validateAndThrow(parent);
```

Lo que nos daria una respuesta:
```
ValidationException{
    statusCode = 422 UNPROCESSABLE_ENTITY,
    messages = [
        ValidationErrorMessage[
            source = parentName,
            invalid_value = Pepito Simple,
            message = 'Pepito Simple' don't match with given name
        ]
    ]
}
```
NOTE: no puede estar el annotation y el register en el mismo fichero, se deben crear en ficheros/clases por separado.

### 1.6 - Pre-made validations <a name="1.6"></a>
El paquete `jakarta.validation.*` tiene las validaciones que se utilizan en la mayoria de los casos.

Pero en caso que no sean suficientes, se implementaron algunas que hicieron falta en su momento y que se pueden reutilizar, como:
- <a name="1.6.1"></a> `Digit`: validar que un `Character` sea un digito
```java
        record Parent(
                @Digit()
                Character sex) {

        }

        Parent parent = new Parent('0');
        ValidationService.validateAndThrow(parent);
```
Error:
```
ValidationException{
    statusCode = 422 UNPROCESSABLE_ENTITY,
    messages = [
        ValidationErrorMessage[
            source = sex, 
            invalid_value = a, 
            message = Must be a digit
        ]
    ]
}
```
- <a name="1.6.2"></a> `EnumValidator`: validar que un `String` pertenzca a alguno de los valores de un enum

Teniendo un enum:
```java
enum Age implements EnumValidatorComparator<String> {
    JOUNG("Joung"),
    OLD("Old");

    private final String name;

    private Age(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean test(String other) {
        return getName().equalsIgnoreCase(other);
    }

    @Override
    public String toString() {
        return getName();
    }

}
```
Se implementa la interfaz `EnumValidatorComparator<String>` que nos permite sobreescribir el metodo `test` y sera utilizado posteriormente en la validacion


Se crea el objeto y se anota el campo requerido y se corre la validacion:
```java
        record Parent(
                @EnumValidator(target = Age.class)
                String age) {
        }

        Parent parent = new Parent("other");
        ValidationService.validateAndThrow(parent);
```
Lo que da como resultado:
```
ValidationException{
    statusCode = 422 UNPROCESSABLE_ENTITY, 
    messages = [
        ValidationErrorMessage[
            source = age, 
            invalid_value = other,
            message = 'other' is not one of the one of allowable values: [Joung, Old]
        ]
    ]
}
```

Tambien se puede utilizar la anotacion para validar listas de String (`List<String>`) donde cada elemento de la lista es testeado a ver si conicide con algun elemento del `Enum`

Ademas de que en caso que no se quiera implementar la interfaz `EnumValidatorComparator<String>` se puede sobreescribir el comparador por defecto del validator:
```java
        EnumValidatorRegister_String.setDefaultEnumComparator((currentEnumValue, testValue) -> {
            return true;//never validate
            
            //default validator
            //return currentEnumValue.toString().equalsIgnoreCase(testValue);
        });
```

- <a name="1.6.3"></a> `SizeExact`: Valida que un `String`, `List`, `Array` or `Map` tengan un tamaño exacto. Validando por cada uno su length.
