[Español](README.es.md)

## Clean Core en spanglish por el momento

###### (En proceso de renombrarlo 'utils' o algo asi)

This library aims to provide standards and utilities that make work easier when creating microservices.

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
- [2 - Exceptions](#2)
- [3 - Repo](#3)
- [4 - Rest](#4)
    - [4.1 - Api response](#4.1) 
    - [4.2 - Response Extractor (Next)](#4.2)
    - [4.3 - Rest Template utils (Next)](#4.3)
- [5 - Utils](#5)
    - [5.1 - JACKSON](#5.1)
    - [5.2 - Enum mappeable](#5.2)
    - [5.3 - Network](#5.3)
    - [5.4 - Security Algos](#5.4)

## Validations <a name="1"></a>
- All native validations are loaded from the [`jakarta.validations.*` framework](https://mvnrepository.com/artifact/jakarta.validation/jakarta.validation-api).
- To validate an object, the `dev.root101.clean.core.utils.validation.ValidationService` class and its static methods are used. Example: `ValidationService.validateAndThrow(some_object);`.
- If all validations passed correctly, the code runs normally. If at least one validation fails, a `ValidationException` will be thrown or **a `List` in case of need** (`ValidationService.validate(some_object);`, without `andThrow`).
- ALL validation examples are located in the examples folder `dev.root101.clean.core.examples.validation...`.
- **NOTE**: ALL the objects used are `record` to reduce the example code, but everything explained here works EXACTLY the same with standard Java classes.

### 1.1 - Validation Exception <a name="1.1"></a>
Once validations are executed on an object, and some fail, an exception of type `dev.root101.clean.core.exceptions.ValidationException` will be thrown.

This exception has the:
- `status_code`, which represents the http response code, ALWAYS being `422: UNPROCESSABLE_ENTITY`. AND,
- A list of failed validations (`List<ValidationErrorMessage>`). Each element in this list represents a validation that failed in some field of the object.
- Each failed validation (`ValidationErrorMessage`) has:
   - `source`: The name of the field where validation failed.
   - `invalid_value`: The value by which the validation failed.
   - `message`: The error message for the failed validation.

For an object like:

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

A validation error is generated:

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
All its fields are validated for a simple object:

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

This code will throw the exception:

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
If you have an object A with one of its fields being another object B, and you want to validate the fields of A, and at the same time the fields of B, a recursion is executed through all the fields of the objects until all of them are validated.
Including all its elements in a list.

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

This code will throw the exception:

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
If a validation fails, the `source` of the `ValidationErrorMessage` shows the name of the field that failed, but in case you want that field to have a different name than the one it has by default (due to a typing difference or similar: Camel Case , Snake...).

Without the annotation the answer would be like [1.2 - Simple object](#simple_object).

With the annotation:

```java
        record Parent(
                @ValidationFieldName("parent_name")
                @Size(min = 1, max = 5)
                String parentName) {

        }

        Parent parent = new Parent("Pepito Simple");

        ValidationService.validateAndThrow(parent);
```

And response:

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
In case a more complex or customized validation is needed that does not come by default in `jakarta`, one can be created manually, compatible with the `ValidationService`.

The example of an annotation to validate that a Parent's name is 'Pepito' would look like this:

The annotation is created:

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

The registry/validator class is created.

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

We then write down the field and execute the validations:

```java
        record Parent(
                @Size(min = 1, max = 5)
                String parentName) {

        }
        Parent parent = new Parent("Pepito Simple");

        ValidationService.validateAndThrow(parent);
```

Answer will be:

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
**NOTE**: the annotation and the register cannot be in the same file, they must be created in separate files/classes.

### 1.6 - Pre-made validations <a name="1.6"></a>
The `jakarta.validation.*` package has the validations that are used in most cases.
But in case they are not enough, some were implemented that were needed at the time and that can be reused, such as:

- <a name="1.6.1"></a> `Digit`: validate that a `Character` is a digit.
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
- <a name="1.6.2"></a> `EnumValidator`: validate that a `String` belongs to any of the values of an enum.

Having the enum:

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

The `EnumValidatorComparator<String>` interface is implemented that allows us to override the `test` method and will be used later in validation.
If the interface is not implemented, the default comparison is used with a `containt` to the `toString` of each enum value.

The object is created and the required field is noted and the validation is run:

```java
        record Parent(
                @EnumValidator(target = Age.class)
                String age) {
        }

        Parent parent = new Parent("other");
        ValidationService.validateAndThrow(parent);
```

Which results:

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

**NOTE**: You can also use the annotation to validate String lists (`List<String>`) where each element of the list is tested to see if it matches any element of the `Enum`.

In addition, in case you do not want to implement the `EnumValidatorComparator<String>` interface, you can override the default comparator of the validator:
```java
        EnumValidatorRegister_String.setDefaultEnumComparator((currentEnumValue, testValue) -> {
            return true;//never validate
            
            //default validator
            //return currentEnumValue.toString().equalsIgnoreCase(testValue);
        });
```

- <a name="1.6.3"></a> `SizeExact`: Validates that a `String`, `List`, `Array` or `Map` has an exact size. Validating for each one its length.

## 2 - Exceptions <a name="2"></a>

## 3 - Repo <a name="3"></a>

## 4 - Rest <a name="4"></a>
##$ 4.1 Api Response - Rest <a name="4.1"></a>

## 5 - Utils <a name="5"></a>
### 5.1 - JACKSON <a name="5.1"></a>
### 5.2 - Enum mappeable <a name="5.2"></a>
### 5.3 - Network <a name="5.3"></a>
### 5.4 - Security Algos <a name="5.4"></a>