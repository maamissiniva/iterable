# Iterable

## Dependency

[maven central](https://central.sonatype.dev/artifact/io.github.maamissiniva/maamissiniva-iterable/0.1.1)

## WARNING

This package is an experiment with iterables that I find useful. 
There are probably better things like streams and common libraries that 
do things better than this code.

You may experience performance problems and errors of various kinds.

## What is it ?

This library provides some functions and utilities that implement
of a number of commonly used functional programming functions. 

The provided code is meant to be used to produce new collections using
side-effect free code.

The code provides static methods defined in the Iterables class and fluent
java style methods.

## Methods / Functions

Start by using an Iterables static function. To start you either wrap your iterable
(List, Set, etc...) using the appropriate method:

| Collection | Method              |
| ---------- | ------------------- |
| Iterable   | Iterables.it        |
| List       | Iterables.it        |
| Array      | Iterables.ar        |
| var arg    | Iterables.ar        |
| nothing    | Iterables.empty     |
| object     | Iterables.singleton |

or you use a method that implements some function:

| Function           |
| ------------------ |
| Iterables.map      |
| Iterables.filter   |
| Iterables.foldR    |
| Iterables.foldL    |
| Iterables.filter   |

You can use methods on the returned iterable using fluent style. 

You then collect things with some method:

| Collection | Method  |
| ---------- | ------- |
| Set        | asSet   |
| List       | asList  |
| List       | asMList |
| Map        | asMap   |

which produce the result using the java util collections.

## Examples

Suppose we have a class 

```
class Person {
   public final String name;
   ...
}
```

and a list of persons l.

```
List<Person> l;
```

### Create an index

We can create a name indexed map using directly

```
Iterables.asMap(l, p -> p.name, p -> p);
```

or using fluent style

```
Iterables.it(l)
         .asMap(p -> p.name, p -> p);
```

or using a specialized method

```
Iterables.asValueMap(l, p -> p.name);
```

### Filter 

We create the set of names that start with "A"

```
Iterables.map(l, p -> p.name)
         .filter(n -> n.startsWith("A"))
         .asSet();
```



