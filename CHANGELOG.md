* 4.0.0.SNAPSHOT.20220000: Complete review of the plugin.
    * **DOMAIN** :
        * **IMPROVEMENT** :raised_hands: : Renamed `EntityDomainObject` to `BasicDomainObject`.
        * **IMPROVEMENT** :raised_hands: : Removed `VolatileDomainObject`.
        * **IMPROVEMENT** :raised_hands: : `DomainObject` & `BasicDomainObject` to interface. From now on, it is highly recommended to use records to work with domain, and since the record does [not allow inheritance](https://stackoverflow.com/questions/63605794/is-there-any-way-of-using-records-with-inheritance), everything was migrated to interfaces to preserve polymorphism.
        * **IMPROVEMENT** :raised_hands: : Implemented in `BasicDomainObject` a default `validate()` by it's fields annotations.

