* 4.1.0.SNAPSHOT.20220000: ReadWriteExternalRepository
    * **NEW** :+: : Added `ReadWriteExternalRepository`.

* 4.0.0.SNAPSHOT.20220327: Complete review of the plugin. Updated focussed mainly on server side/microservices architecture.
    * **MISSING**:
        * Validation Message and converters.
        * Javadoc examples for all.
        * Authentication & Authorization.
    * **DOMAIN** :
        * **IMPROVEMENT** :raised_hands: : Clened `domain/services`.
        * **IMPROVEMENT** :raised_hands: : Renamed `EntityDomainObject` to `BasicDomainObject`.
        * **IMPROVEMENT** :raised_hands: : Removed `VolatileDomainObject`.
        * **IMPROVEMENT** :raised_hands: : `DomainObject` & `BasicDomainObject` to interface. From now on, it is highly recommended to use records to work with domain, and since the record does [not allow inheritance](https://stackoverflow.com/questions/63605794/is-there-any-way-of-using-records-with-inheritance), everything was migrated to interfaces to preserve polymorphism.
        * **IMPROVEMENT** :raised_hands: : Implemented in `BasicDomainObject` a default `validate()` by it's fields annotations.
    * **VALIDATION** :
        * **IMPROVEMENT** :raised_hands: : Migrated Validation logic to [`Hibernate Validator 7.0.4.Final - Jakarta Bean Validation`](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-declaring-bean-constraints)
        * **BUG FIX** :raised_bug: : Fix bug in `SizeExactCheckable`'s `check()`.
        * **IMPROVEMENT** :raised_hands: : static `DEFAULT_VALIDATOR` in `ValidationResult` to avoid recreated every time a validation occur.
    * **USE CASE** :
        * **IMPROVEMENT** :raised_hands: : Removed Clients Use Case.
        * **IMPROVEMENT** :raised_hands: : Added to `DefaultCRUDUseCase` & `DefaultReadWriteUseCase the `<CRUDRepo>` in signature, enforcing the Repo to the UC. (Easy access to repo). EXTRA: + doFirePropertyChanges = true. To controll in the future if the propertyChange get fired or not.
        * **IMPROVEMENT** :raised_hands: : CRUDUseCase & DefaultCRUDUseCase: Domain signature  to `extends DomainObject`.
    * **REPO** :
        * **IMPROVEMENT** :raised_hands: : Removed Clients Repo.
        * **IMPROVEMENT** :raised_hands: : Refactor Converter method's signature.
        * **IMPROVEMENT** :raised_hands: : `CRUDRepository` & `ReadWriteRepository`: enforce the DomainObject for the Domain in signature.
        * **IMPROVEMENT** :raised_hands: : + `DefaultCRUDRepo` with delegate to External Repo. This is the one with the converter logic on.
    * **EXTERNAL REPO** : Created a new layer to handle the actually conection with frameworks, delegating all the convertion responsability to clasic repo.
        * **IMPROVEMENT** :raised_hands: : +AbstractExternalRepository & +CRUDExternalRepository.
    * **REST SERVICE** :
        * **IMPROVEMENT** :raised_hands: : + `DefaultCRUDRestService`, who delegate in UC.
        * **IMPROVEMENT** :raised_hands: : + `HttpStatus` with all http codes. Forked from `org.springframework.http`.
    * **SERVICES** :
        * **REMOVED** :-1: : Removed from services: **Converter**, **Authentication**, **Exception**, **Navigation**, **Notification**
    * **EXEXPTIONS** :
        * **IMPROVEMENT** :raised_hands: : + `RestException`, with an HttpStatus to map it with external frameworks.
    * **GENERAL** :
        * Fix typos.

