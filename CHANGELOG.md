* 5.4.5:
    * **DATE** :date: : 2025-08-20
    * **VALIDATIONS** :
        * **IMPROVEMENT** :raised: : Ignore static fields in the recursive behaviour.

* 5.4.4:
    * **DATE** :date: : 2025-07-27
    * **VALIDATIONS** :
        * **IMPROVEMENT** :raised: : Allow the `ignoreNull` property in `EnumValidator`.
    * **DOCS** :
        * **UPDATE** :+1: : Update all docs with removed sections & minor fixes to `ValidationService` (including the
          changes in this version).
        * **UPDATE** :+1: : Update this changelog with latest versions.

* 5.4.3:
    * **DATE** :date: : 2025-07-02
    * **GENERAL** :
        * **FIX** :raised: : Update dependencies.

* 5.4.2:
    * **DATE** :date: : 2025-05-01
    * **GENERAL** :
        * **FIX** :raised: : Update dependencies.

* 5.4.1:
    * **DATE** :date: : 2025-04-23
    * **VALIDATIONS** :
        * **IMPROVEMENT** :raised: : Clean up `ValidationService`.

* 5.4.0:
    * **DATE** :date: : 2025-04-23
    * **VALIDATIONS** :
        * **IMPROVEMENT** :raised: : Simplify and improve validation logic.

* 5.3.0.RELEASE.20240813:
    * **VALIDATIONS** :
        * **IMPROVEMENT** :raised: : Migrated `ValidationService` to instance based.
    * **DOCS** :
        * **UPDATE** :+1: : Update section 1 with latest changes in `ValidationService`.

* 5.2.2.RELEASE.20240813:
    * **VALIDATIONS** :
        * **IMPROVEMENT** :raised: : Add default naming strategy from `jackson-databind`

* 5.2.1.RELEASE.20240813:
    * **WRAPPERS** :
        * **REMOVED** :-: : Removed `wrappers` folder & spring-jpa dependency

* 5.2.0.RELEASE.20240724:
    * **VERSION** :sparkler: : Fix versioning problems

* 5.1.2.RELEASE.20240211:
    * **REMOVED** :-: : Removed `ApiResponse`

* 5.1.1.RELEASE.20231215:
    * **DOCS** :
        * **UPDATE** :+1: : Update `how to use it`.
        * **UPDATE** :+1: : General updates.

* 5.1.0.RELEASE.20231011:
    * **GENERAL** :
        * **FIX** :bug: : Fix `5.0.1.RELEASE.20231011`: Rename `clean-core` to `commons`. Fix at class level.

* 5.0.1.RELEASE.20231011:
    * **GENERAL** :
        * **FIX** :raised: : Rename `clean-core` to `commons`.

* 5.0.0.RELEASE.20231011:
    * **NEW VERSION** :
        * **REMOVED** :x: : Removed `Repo` layer, use basic Spring Boot architecture from now on.
        * **NEW** :+1: : Deploy new version `5.0`.
        * **NEW** :+1: : **Transfer** package to `Root101` org & **Rename** it to `commons`.

* 4.8.1.RELEASE.20230919:
    * **GENERAL** :
        * **IMPROVEMENT** :raised: : Fix `Jackson` type name class & remove lombok dependencies due to conflict with
          another project.

* 4.8.0.RELEASE.20230914:
    * **DOCS** :
        * **IMPROVEMENT** :raised: : Update all docs & examples.
    * **GENERAL** :
        * **IMPROVEMENT** :raised: : Minor bugs and fixes.

* 4.7.1.SNAPSHOT.20230402:
    * **GENERAL** :
        * **IMPROVEMENT** :raised: : Update all dependencies....

* 4.7.0.SNAPSHOT.20230131:
    * **GENERAL** :
        * **IMPROVEMENT** :raised: : Update all dependencies, include Spring, Jakarta, Validations....

* 4.6.5.RELEASE.20230130:
    * **VALIDATIONS** :
        * **IMPROVEMENT** :raised: : Fix empty list & enum skips.

* 4.6.4.RELEASE.20230128:
    * **DOCS** :
        * **IMPROVEMENT** :raised: : Fixed version number.

* 4.6.3.RELEASE.202220128:
    * **DOCS** :
        * **IMPROVEMENT** :raised: : Added 4.6.2 and 4.6.3 to changelog.

* 4.6.2.RELEASE.202220128:
    * **VALIDATIONS** :
        * **IMPROVEMENT** :raised: : Fixed validation in list running double.

* 4.6.1.RELEASE.202220125:
    * **REPO** :
        * **IMPROVEMENT** :raised: : `findById` with id=null return null. Don't throw exception.
    * **VALIDATIONS** :
        * **IMPROVEMENT** :raised: : ValidationService mantain records of current tree in order to show complete source
          of errors fields.

* 4.6.0.SNAPSHOT.202220113:
    * **GENERAL** :
        * **REFACTOR** :building: : Removed all standars for REST & UC layer.
        * **REFACTOR** :building: : Refactor 'ApiResponse' success's builders.
        * **IMPROVEMENT** :raised: : 'CRUDRepo' & 'DelegatedSpringJpaRepo' incresed methods availables (createAll,
          findAllById...).
        * **NEW** :+: : Created @Annotation 'ValidationFieldName' for speccify the name the field will display if a
          validation is throw.

* 4.5.1.RELEASE.20221122:
    * **VALIDATIONS** :
        * **NEW** :+: : Created validator for List for 'EnumValidator'.

* 4.5.0.RELEASE.20221119:
    * **GENERAL** :
        * **REFACTOR** :building: : Renamed:
            - Defaults UC & Repos to Delegated. Example: 'dev.root101.clean.core.app.usecase.DefaultCRUDUseCase' to '
              dev.root101.clean.core.app.usecase.DelegatedCRUDUseCase'.
            - 'framework' layer to 'spring_boot'.
            - External repo layer to 'Framework'.
        * **REFACTOR** :building: : Moved 'ApiResponse' from old 'framework' layer to 'rest' layer.
        * **REMOVED** :-: : Removed @Licenced annotation.

* 4.4.2.RELEASE.20221025:
    * **GENERAL** :
        * **NEW** :+: : Add EnumMappeable for convert Enum into a list an expose it via api easily.

* 4.4.1.RELEASE.20220919:
    * **GENERAL** :
        * **IMPROVEMENT** :raised: : Fixed JACKSON configuration. Now it support Time Api.

* 4.4.0.RELEASE.20220919:
    * **VALIDATION** :
        * **IMPROVEMENT** :raised: : Fixed EnumValidaton logic with interface to generalize behavior.

* 4.3.0.SNAPSHOT.20220829:
    * **VALIDATION** :
        * **BUG FIX** :raised_bug: : Filter primitive values for not validate.
    * **GENERAL** :
        * **IMPROVEMENT** :raised: : Created Api UC & Api Controller, an UC and a Controller wich return ApiResponse by
          default.
        * **IMPROVEMENT** :raised: : Find by return null if id not found.
        * **IMPROVEMENT** :raised: : Created builder factory in ApiResponse for message.
        * **IMPROVEMENT** :raised: : Created ResponseExtractor.
        * **IMPROVEMENT** :raised: : Created Validation for Enum values.
        * **IMPROVEMENT** :raised: : Upgrade gradle dependencies.

* 4.2.2.RELEASE.20220613:
    * **VALIDATION** :
        * **IMPROVEMENT** :raised_hands: : Add recursive validation.

* 4.1.1.SNAPSHOT.20220531:
    * **GENERAL** :
        * **IMPROVEMENT** :raised: : Change `count()` to type `long`.

* 4.1.0.SNAPSHOT.20220410:
    * **GENERAL** :
        * **IMPROVEMENT** :raised: : Return type of `destroy` & `destroyById` set to void.
        * **IMPROVEMENT** :raised: : Added ID type to superclasses, `findBy` & `destroyById` receive the ID.
        * **NEW** :+: : Added `ReadWriteExternalRepository`.
        * **IMPROVEMENT** :raised_hands: : `CRUDRestService` domain's to `extends Domainbject` & -
          DefaultCRUDRestService.
        * **BUG FIX** :bug: : Fix Validation's dependencies according to latest version of spring.
    * **EXCEPTION** :
        * **IMPROVEMENT** :raised_hands: : Renamed `RestException` to `ApiException`.
        * **NEW** :+: : Created defaults ApiException's: Unauthorized(401), PaymentRequired(402), Forbidden(403) and
          InternalServerError(500) ...
        * **IMPROVEMENT** :raised_hands: : Fix `ExceptionHandler`'s service.

* 4.0.0.SNAPSHOT.20220327: Complete review of the plugin. Updated focussed mainly on server side/microservices
  architecture.
    * **MISSING**:
        * Validation Message and converters.
        * Javadoc examples for all.
        * Authentication & Authorization.
    * **DOMAIN** :
        * **IMPROVEMENT** :raised_hands: : Clened `domain/services`.
        * **IMPROVEMENT** :raised_hands: : Renamed `EntityDomainObject` to `BasicDomainObject`.
        * **IMPROVEMENT** :raised_hands: : Removed `VolatileDomainObject`.
        * **IMPROVEMENT** :raised_hands: : `DomainObject` & `BasicDomainObject` to interface. From now on, it is highly
          recommended to use records to work with domain, and since the record
          does [not allow inheritance](https://stackoverflow.com/questions/63605794/is-there-any-way-of-using-records-with-inheritance),
          everything was migrated to interfaces to preserve polymorphism.
        * **IMPROVEMENT** :raised_hands: : Implemented in `BasicDomainObject` a default `validate()` by it's fields
          annotations.
    * **VALIDATION** :
        * **IMPROVEMENT** :raised_hands: : Migrated Validation logic
          to [`Hibernate Validator 7.0.4.Final - Jakarta Bean Validation`](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-declaring-bean-constraints)
        * **BUG FIX** :raised_bug: : Fix bug in `SizeExactCheckable`'s `check()`.
        * **IMPROVEMENT** :raised_hands: : static `DEFAULT_VALIDATOR` in `ValidationResult` to avoid recreated every
          time a validation occur.
    * **USE CASE** :
        * **IMPROVEMENT** :raised_hands: : Removed Clients Use Case.
        * **IMPROVEMENT** :raised_hands: : Added to `DefaultCRUDUseCase` & `DefaultReadWriteUseCase the `<CRUDRepo>` in
          signature, enforcing the Repo to the UC. (Easy access to repo). EXTRA: + doFirePropertyChanges = true. To
          controll in the future if the propertyChange get fired or not.
        * **IMPROVEMENT** :raised_hands: : CRUDUseCase & DefaultCRUDUseCase: Domain signature to `extends DomainObject`.
    * **REPO** :
        * **IMPROVEMENT** :raised_hands: : Removed Clients Repo.
        * **IMPROVEMENT** :raised_hands: : Refactor Converter method's signature.
        * **IMPROVEMENT** :raised_hands: : `CRUDRepository` & `ReadWriteRepository`: enforce the DomainObject for the
          Domain in signature.
        * **IMPROVEMENT** :raised_hands: : + `DefaultCRUDRepo` with delegate to External Repo. This is the one with the
          converter logic on.
    * **EXTERNAL REPO** : Created a new layer to handle the actually conection with frameworks, delegating all the
      convertion responsability to clasic repo.
        * **IMPROVEMENT** :raised_hands: : +AbstractExternalRepository & +CRUDExternalRepository.
    * **REST SERVICE** :
        * **IMPROVEMENT** :raised_hands: : + `DefaultCRUDRestService`, who delegate in UC.
        * **IMPROVEMENT** :raised_hands: : + `HttpStatus` with all http codes. Forked from `org.springframework.http`.
    * **SERVICES** :
        * **REMOVED** :-1: : Removed from services: **Converter**, **Authentication**, **Exception**, **Navigation**, *
          *Notification**
    * **EXEXPTIONS** :
        * **IMPROVEMENT** :raised_hands: : + `RestException`, with an HttpStatus to map it with external frameworks.
    * **GENERAL** :
        * Fix typos.

