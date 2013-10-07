AXON-E Java Tools
===============================================================================

A Set of tools which make life as a Java coder more easy by making often used methods as simple as they should be.

This is not intended to be a complete library for a certain purpose but more as a swiss army collection of this or that to copy and paste.

There are a couple of *shortcut* classes:

E
-------------------------------------------------------------------------------

```java
E.cho( Object ... value );
E.rr( Object ... value );
```
is more like `print` or `echo` in scripting languages. It tries to do it's best to output what you give to it, tells you the file and linenumber it is called from and saves keystorkes.


F
-------------------------------------------------------------------------------
F is the companion to E which does the formating of objects
```java
String formated = F.ormat( Object value )
```

PirateNameGenerator
-------------------------------------------------------------------------------
Generates (german) pirate names:
```java
PirateNameGenerator png = new PirateNameGenerator();
png.setFlowery( true );
png.generate( false ); // --> "Einaeugige Smilla Moosbein"
```

... more examples to come ...


