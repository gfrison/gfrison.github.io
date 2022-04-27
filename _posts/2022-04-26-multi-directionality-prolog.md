---
layout: single
published: true
title: "Multi directionality in Prolog"
share: true
tag:
  - logic programming 
---
When it comes to software programming, learning anything new expands problem-solving skills, and the purpose of this post is to instil a different way of thinking on what a computer can do for you. Prolog comes from the ‘70s initially for representing conceptual graphs and semantic networks for natural language processing. Here, I’m advocating not its fitness to model complex grammars, but rather the benefits a software programmer or functional analyst might bring from it.  What’s different in Prolog that can’t be found in other languages? I’ve tried to dig into it by implementing a programming tool & service that mimics Prolog’s paradigms for knowledge reasoning.

One main fundamental thing a Prolog-inspired language has, is the idea of finding answers for your questions. Which kind of questions? Anything can be encoded in a logical form, for example _“Where is Venice?”_

```scala
?(“locatedAt”, “Venice”, x)
```
_What is the aggregated cost (y) of delivery (d) and warehouse (w) for our eCommerce store?_
```scala
?(“deliveryCost”, d), (“warehouseCost”, w), y := d + w
```
_Is it true that that item does not cost more than 200?_
```scala
?(“price”, item, x), x <= 200
```

The engine iterates over the data and it will return the values you asked for, or it will say whether your statement is true or not. Basically, it implements and run the routines for you. What you have to do is to tell the system what is true and under what conditions, then the system takes the hurdle of computation. With it, the model of your business domain is purely abstract and logical, and the representation you define is closer to your mind than the machine. Ludwig is intended to be an easy tool to use, by application programmers, while at the same time, be understandable by analysts

I would frame Prolog into a programming style for tracing relations between facts and implications. Facts are assertions that we know to be true. They are information, data. Implications are the derived information that can be drawn from facts, either they are explicit or, more interestingly, they are derived from other implications. What I find interesting from a programming experience perspective, is that facts and implications are represented in the same way, lowering the learning curve to master it compared to other approaches. This is the principle of homoiconicity, which is summarised by saying that the language treats “code as data”.

One of the most intriguing ideas in Prolog that I would be extremely happy to have fully supported in Ludwig is the concept of unification. It’s the feature that distinguishes a high level language like Prolog from all others, and I show you what it is with an example taken from the SWI-Prolog.

`nth0` is an apparently not-so-cool predicate for some operations on lists. It looks like this:
```prolog
nth0(I, L, E, R)
```
Where the variable `I` is the index of the `E` element in the `L` list, and `R` is the remainder of `L` without `E`. If it sounds cryptic, actually it is extremely simple. I provide a list `[a,b,c]` and I ask to the system: please, give me back `I, E, R`:
```prolog
? nth0(I, [a,b,c], E, R)
```
As you might imagine, there are multiple answers, `E` could be `a` or `b` or `c`, right? And the answer will reflect this:
```prolog
I = 0, E = a, R = [b, c]

I = 1, E = b, R = [a, c]

I = 2, E = c, R = [a, b]
```
Unification in Prolog allows us not just to use predicates in a single way like any other programming language, but to abuse it in any conceivable way. In the following case, If I give the original list and the remainder, what is the missing piece `E`?

```prolog
?nth0(I, [a, b, c, d], E, [b, c, d]) => I = 0, E = a
```
If instead, I give the remainder without the original list but I provide only the character `E`, what could be then the originating list?
```prolog
? nth0(I, L, a, [b, c, d])

I = 0, L = [a, b, c, d]

I = 3, L = [b, c, d, a]
```
Intuitively, we have 2 solutions. In the first, the single element is prepended at the beginning of the remainder, in the second it is appended to the end of the reminder. In both cases, the definition of the `nth0` predicate is satisfied. The system is returning the missing slots that satisfy the logical contract formulated in that predicate. Differently from any other language, a predicate definition is sufficient for an entire set of functionalities.

Unification should not be seen just for implications. Even independent facts might extend this principle for defining entire classes of concepts. What distinguishes a vertical segment from any other in the space? I think, any line with 2 points lying on it that shares the same x-axis is vertical. I can express it without any explicit condition. I just translate the definition of being `vertical` in logical form, by posing the same variable in both edges:
```scala
("isVertical", ("point", x, _), ("point", x, _))
```

While unification stands for unifying variables like an AND operator, in Ludwig we also have the OR operation that, if we use the logic programming lingo, is named resolution. Resolution is the programming feature with which the system is performing pattern matching, predicate calls and recursion. In the factorial example:
```scala
object x extends VarInt 
object y extends VarInt 

val model = ludwig(
  ("factorial", 0, 1),
  ("factorial", x, x * y) :- (x > 0, ("factorial", x - 1, y))
)
```

We have one fact and one implication with the same signature: <”factorial”, Int, Int>. Then we query:
```scala
model ? (“factorial", -1 to 3, x)
```
we get:
```scala
| Check | @3fd8v |   x |
|   --- |  ----- |  -- |
|  ❌   |     -1 |     |
|  ✅   |      0 |   1 |
|  ✅   |      1 |   1 |
|  ✅   |      2 |   2 |
|  ✅   |      3 |   6 |
```
When the first argument is -1 none of the predicates is matching, then simply the system is telling us that this statement is false.

When the first argument is 0 only the static fact is matching, and the corresponding paired argument is returned.

When the first argument is greater than 0 the second predicate matches, and recurversely invokes itself until, as any recursive function, reaches the bottom condition. The one defined by the fact.

In the beginning of this article I was comparing Ludwig with SQL. I see it as a sophisticated language for both querying systems and programming at the same time, leaving out the hurdle of having a dedicated language – SQL/SparQL on one end, Java/python on the other – in your software application.

