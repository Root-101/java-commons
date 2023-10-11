<!-- 

Multilanguage:
[Español](README.es.md)

-->

## Root101 Commons [EN]

This library aims to provide standards and utilities that make work easier when creating microservices.

Docs updated for version: `5.0.1.RELEASE.20231011`

## Table of Contents
- [1 - Validations](#1)
    - [1.1 - Validation Exception](#1.1)
    - [1.2 - Simple object](#1.2)
    - [1.3 - Complex Object & Recursivity](#1.3)
    - [1.4 - Nombre de un campo personalizado](#1.4)
    - [1.5 - Validacion personalizada](#1.5)
    - [1.6 - Validaciones pre-echas:](#1.6)
        - [1.6.1 - Digit](#1.6.1)
        - [1.6.2 - Enum](#1.6.2)
        - [1.6.3 - Size Exact](#1.6.3)
- [2 - Exceptions](#2)
- [4 - Rest](#3)
    - [4.1 - Api response](#3.1) 
    - [4.2 - Response Extractor (Next)](#3.2)
    - [4.3 - Rest Template utils (Next)](#3.3)
- [5 - Utils](#4)
    - [5.1 - Jackson](#4.1)
    - [5.2 - Enum mappeable](#4.2)
    - [5.3 - Network](#4.3)
    - [5.4 - Security Algos](#4.4)
- [6 - How to use this package](#5)

## Validations <a name="1"></a>
- All native validations are loaded from the [`jakarta.validations.*` framework](https://mvnrepository.com/artifact/jakarta.validation/jakarta.validation-api).
- To validate an object, the `dev.root101.commons.utils.validation.ValidationService` class and its static methods are used. Example: `ValidationService.validateAndThrow(some_object);`.
- If all validations passed correctly, the code runs normally. If at least one validation fails, a `ValidationException` will be thrown or **a `List` in case of need** (`ValidationService.validate(some_object);`, without `andThrow`).
- ALL validation examples are located in the examples folder `dev.root101.commons.examples.validation...`.
- **NOTE**: ALL the objects used are `record` to reduce the example code, but everything explained here works EXACTLY the same with standard Java classes.

### 1.1 - Validation Exception <a name="1.1"></a>
Once validations are executed on an object, and some fail, an exception of type `dev.root101.commons.exceptions.ValidationException` will be thrown.

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

Without the annotation the answer would be like [1.2 - Simple object](#1.2).

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
@Constraint(validatedBy = PersonalizedValidationRegister.class)//indicate the class to do re validation logic, can be a list for multiple data type validations
@interface PersonalizedValidation {

    String name(); //new field to work with

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
                @PersonalizedValidation(name = "Pepito")
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

Para estandarizar el uso de las respuestas HTTP se crearon las excepciones(mas comunes) a lanzar en cada momento:
- `ApiException`: General exception from which the others derive, it has:
    - `rawStatusCode`: Indicate the HTTP response code of exception.
    - `message`: Indicates the message for which the exception was thrown.
- `BadRequestException`:
    - `Status Code`: **400**  BAD REQUEST.
    - `Use Case`: Something fails in the service on the part of the client, this exception is thrown.
- `UnauthorizedException`:
    - `Status Code`: **401**  UNAUTHORIZED.
    - `Use Case`: An unlogged user wants to access a resource that requires authorization to access, this exception is thrown.
- `PaymentRequiredException`:
    - `Status Code`: **402** PAYMENT REQUIRED.
    - `Use Case`: One wants to access a resource for which he has to pay; or you reach the limit of what the resource can consume and to continue you have to pay, this exception is thrown.
- `ForbiddenException`:
    - `Status Code`: **403** FORBIDDEN.
    - `Use Case`: An authenticated user wants to access a resource to which they do not have permission, this exception is thrown.
- `NotFoundException`:
    - `Status Code`: **404** NOT FOUND.
    - `Use Case`: It is about accessing or searching for a resource and element that no longer exists, this exception is thrown. Searching for an item that has already been deleted.
    - **NOTE**: This exception is recommended to be thrown if an authenticated user attempts to access an item that does not belong to them. That is, if the `403` is thrown (hich is what is expectedin these cases), it is confirmed that there is an element in that `id` and that it does not belong to it. If the `404` is thrown, you are given to understand that the element does not even exist.
- `ConflictException`:
    - `Status Code`: **409** CONFLICT.
    - `Use Case`: An element will be created with the same name as an existing or similar one, this exception is thrown.
- `ValidationException`:
    - `Status Code`: **422** VALIDATION EXCEPTION.
    - `Use Case`: Validations are run on an object and they fail, this exception is thrown. It is recommended to use the `ValidationService` that throws the exception. In case you want to add validations by hand or similar, add it to a list and pass it as a parameter to this exception when it is going to be thrown.
- `InternalServerErrorException`:
    - `Status Code`: **500** INTERNAL SERVER ERROR.
    - `Use Case`: Some unexpected error occurs on the server's side, and generally the cause is unknown, this exception is thrown.

## 3 - Rest <a name="3"></a>

Oficial docs for HTTP Responses [here](https://datatracker.ietf.org/doc/html/rfc7231).

### 3.1 Api Response <a name="3.1"></a>
The idea of `ApiResponse` is to generalize API responses to a standard.
ALL API responses must follow this guideline.
The `ApiResponse` class has:
- `status`: Representing the HTTP code of the response.
- `message`: The response message to the request.
- `data`: The data or information of the response, if there is a response.
Ejemplos:
1 - A request to modify a record that is executed successfully must return:
```java
ApiResponse{
    status = 200,
    message = "Success",
    data = null
}
```

2 - A request to obtain a list must return:
```java
ApiResponse{
    status = 200,
    message = "Success",
    data = [
        "Data 1",
        "Data 2",
        "Data 3"
    ]
}
```

3 - A request to obtain an object must return:
```java
ApiResponse{
    status = 200,
    message = "Success",
    data = SomeObject{
        field1 = "some data",
        field2 = "some data 2"
    }
}
```

4 - A request to create users with a name that already exists should return:
```java
ApiResponse{
    status = 409,
    message = "Username already exists",
}
```

How to use it:
- For response 200 you can use: `ApiResponse.success()`, which by default says `status = 200`, `message = success` and `data = null`.
- For response 200 you can use: `ApiResponse.success(data)`, which by default says `status = 200` and `message = success`.
- For response 200 you can use: `ApiResponse.success(message, data)`, which by default says `status = 200`.
- For generic responses you can use: `ApiResponse.build(status, message, data)`.
- To extract a response from a `ResponseEntity` you can use: `ApiResponse.build(status, message, data)`, which by default says `status = response.getStatusCode().value()`, `message = response. getStatusCode().toString()` and `data = response.getBody()`.

## 4 - Utils <a name="4"></a>

### 4.1 - Jackson <a name="4.1"></a>
`Jackson` is a utility class for doing **fast** and low-value conversions of objects/strings.

For operations related to business logic, it is recommended to use the ObjectMapper provided by Spring.

How to use it for reading (convert String to Object):
```java
        //class target
        record Parent(
                String parentName) {

        }

        //text source
        String parentString = """
                              {
                                "parentName": "Pepito Simple"
                              }
                              """;

        //convert source `parentString` to target `parent`
        Parent converted = Jackson.read(parentString, Parent.class);
```

For writing (Convert Object to String):

```java
        record Parent(
                @JsonProperty("parent_name")//this annotations works here
                String parentName) {

        }

        Parent object = new Parent("Pepito Simple");

        String converted = Jackson.toString(object);
```

**NOTE**: This class has some other functionalities for further read/write customization, as well as to convert/parse objects from one type to another. For more details consult the source code in `dev.root101.commons.utils.Jackson`.

### 4.2 - Enum mappeable <a name="4.2"></a>
When you want to map an Enum to its list of elements without so much code at hand:

Having the enum:

```java
enum Status_Enum implements EnumMappeable<StatusResponse> {
    ACTIVE("Active"),
    ARCHIVED("Archived");

    record StatusResponse(
            String name,
            boolean active) {

    }

    private final String name;

    private Status_Enum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public StatusResponse map() {
        return new StatusResponse(
                getName(),
                this == Status_Enum.ACTIVE
        );
    }

    @Override
    public String toString() {
        return getName();
    }

}
```

The `EnumMappeable<T>` interface is implemented, with `T` being the data type to be mapped.
The `map` method is overridden with the actual mapping of each element to the response data type.

It is used then:

```java
EnumMappeableService.map(Status_Enum.class);
```

What he gives as an answer:

```
[
    StatusResponse[
        name = Active, 
        active = true
    ], 
    StatusResponse[
        name = Archived, 
        active = false
    ]
]
```

On the other hand, if you do not want the `Enum` to reimplement the `EnumMappeable<T>` interface you can use:

```java
    EnumMappeableService.map(Status_Enum.class, (t) -> {
        return t.getName();
    })
```

The second argument being the mapping function, giving the answer in this example:

```
[
    Activate,
    Archived
]
```

### 4.3 - Network <a name="4.3"></a>
The Network utility was developed to validate that a service is running on a port:ip.

```java
    Network.isRunning("127.0.0.1", 8080)
```

### 4.4 - Security Algos <a name="4.4"></a>
The security algorithms are a quick implementation of `AES` for encryption and `SHA-256` for hashing.

To use `SHA-256`, access the static method: `SecurityAlgorithms.hash256(input)`, passing the initial string through parameters and waiting for the corresponding hash in response.

To use `AES`:

```java
    SecurityAlgorithms secure = new SecurityAlgorithms("aes secret key");

    String textToCipher = "Hiden text";

    byte[] cipherText = secure.cipher(textToCipher);
        
    byte[] decipherText = secure.decipher(cipherText);

    boolean matchingTexts = textToCipher.equals(new String(decipherText));//true
```


## 5 - How to use this package <a name="5"></a>
At the moment this package is not published in [mvnrepository](https://mvnrepository.com/), so we have to upload it directly from `Github Packages`.

In the `settings.gradle` add:
```
sourceControl {
     gitRepository("https://github.com/JesusHdez960717/clean-core.git") {
         producesModule("dev.root101.clean:clean-core")
     }
}
```

Then in the `build.gradle`, under the `dependencies`:

```
dependencies {
     //... mode dependencies

     implementation 'dev.root101.clean:clean-core:VERSION'

     //... mode dependencies
}
```

Being `VERSION` the version you want to use of the package.

**NOTE**: The latest available version is always recommended.