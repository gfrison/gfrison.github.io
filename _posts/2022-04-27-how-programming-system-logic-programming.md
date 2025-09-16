---
permalink: /2022/04/27/how-programming-system-logic-programming
layout: single
published: true
title: "How would you make a logic programming system?"
share: true
tag:
  - logic programming
  - low-code
---
I tried to investigate alternative ways to improve the programming experience (PX) considering several aspects of how users interact with the system. Those aspects include mode of interactions, language notation, data integration, function composability, service externability. Why don't answer questions such as: how can we simplify the interaction with the system in order to build programs with desired behaviour? How can we share knowledge with stakeholders with different attitudes, other than pure technicals?

Unconventional PS have been explored by industries since the beginning of computer programming. Consider for example spreadsheets – largely used by non-professional programmers – where formulas create views on the data stored in the grid. Think of computable notebooks such as Jupyter for live-coding programming snippets with a numerical or graphical output. There is a plethora of PSs that have inspired the exploration in the topics of:

- Low-Code Programming
- Hybrid Programming Experience
- Ambivalence Code/Data
- Logic Programming

# Low-code programming

If LC platforms [reduce ROI and increase productivity]({% link _posts/2008-10-23-model-driven-architecture-on-fire-off-the-shoulder-of-orion.md %}) by automating a narrow set of business specific problems, they hardly can fulfil the same promises in domains for which they have not been initially conceived. A mobile app builder might accelerate the development of a standardised set of use cases but when required features are not covered by those platforms, developers are forced to back into more traditional programming styles.

While it is desirable to clear out the verbosities and the irrelevant code sections that do not bring any value, the complexity of business problems is irreducible. A proper programming system gives full expressivity to skilled programmers for solving complex problems, and allows citizen developers to build simple applications autonomously. Our system should enable us to build complex algorithms but at the same time allow unskilled practitioners to, at least, understand how routines work, and possibly implement basic programs without involving development teams. It’s possible to be open on ‘hello world’ without sacrificing fundamental programming expressivity.
>Let make easy things easy, difficult ones possible

# Hybrid Programming Experience

The diversity of solutions for delivery software artefacts requires an unprecedented flexibility on how to integrate new practices into existing software development pipelines. The multi-stage process that brings programs from coding to live production might be highly customised and automated, but also, for diametral opposite needs of simplicity, it should offer fully managed and standardised lifecycles.

For lowering the barrier in already established architectures without sacrificing the core features, we should provide SDKs of the language of choice without interfering with the established development cycle. At the same time, it should be available on an on-demand offering such as Logic as a Service (LaaS). The online-only managed platform is intended for directly editing services, with near-instantaneous testing then publishing cloud API in a complete serveless fashion.

# Ambivalence Code/Data

Business applications that do not treat stored information are a narrow niche, and for almost all business domains, software services manipulate stored data. Generic PLs incorporate data only as in-memory pointers. Stored data is retrieved through query frameworks which are very distinct pieces of software from the rest of the programming system. The dichotomy between data and computation does not contribute to the business value of services, it rather just makes it harder to think in terms of conceptual logic. An answer to this is by treating [data as code](https://en.wikipedia.org/wiki/Homoiconicity) and lowering the barrier for modelling logic. According to that, the function interface is not different from the data’s ones. Functions take parameters and return new information like a query in the DB. The difference between programs and data is that the former’s output is derived while data is passively given. Data and functions (predicates) are interchangeable entities.

Data stored in relational DBs is often mapped with object-oriented style, opening the way to a [variety of difficulties](https://en.wikipedia.org/wiki/Object%E2%80%93relational_impedance_mismatch) due to the tendency to distort the nature of relational data into hierarchical classes. This is more evident when applied to loosely-structured property/graph DBs defined as knowledge graphs. We encourage modelling data in tuples as they represent the common denominator for almost all data representations. Tuples are abstract enough to convey what is necessary to implement the business logic while omitting the details of integrated information systems.

# Logic Programming

>Logic: “the study of correct reasoning, especially regarding making inferences.”

LP Languages have been the subject of computer science research since decades. Their founding principle was about caring for the logic of complex systems, a principle that has never been of secondary importance in software systems. The duality code/data in LP make it natural to use it like a database language since relational algebra can be expressed directly, including tabular relations, views and integrity constraints.

One of the pillars of LP is recursiveness for managing data structures such as graphs, trees, lists, and even natural numbers. Though it is easy to find similarities with functional programming, LP languages like Prolog offer an intuitive programming experience with the [multi-directionality of computation]({% link _posts/2022-04-26-multi-directionality-prolog.md%}) where once the interface of a predicate is defined, it could be inquired in any possible way, thanks to unification algorithms.

# Strong type system

Correctness and predictable results are facilitated by a compiled and strong typed system. Though it is not widely adopted in LP, nothing prevents the programming system to be backed by type-safe languages. Types are not only primitive strings, numbers or booleans but complex data types such as immutable [named tuples](https://docs.scala-lang.org/tour/case-classes.html) commonly present in all programming languages.

The declarative nature of LP, combined with multi-directionality, relational algebra, homoiconicity and programming capabilities for a smooth programming experience, actually hide the complexity that resembles more a software system on its own rather than a mere programming language.
