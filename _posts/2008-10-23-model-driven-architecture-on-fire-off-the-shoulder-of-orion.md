---
layout: single
title: MDA on fire off the Shoulder of Orion
published: true
permlink: /2008/10/23/model-driven-architecture-on-fire-off-the-shoulder-of-orion
comments: true
image: /assets/nirvana.gif
tags: mda, mdd, meta-model, metamodelling, model, modelling, mof, roi, uml
---
![]({{ site.url }}/assets/nirvana.gif)
I must admit, frustration has increased over the years. I mean, interacting with the computer in terms of boolean, long, void. I’d rather sit on a sofa and describe the program by voice, or better, get into a 3D virtual reality and cook a software like a lunch meal in the kitchen. Playing with spheres, arrows, to design all program doodles.
I can’t picture it as a possible scenario in the near future, and like for the science fiction movies, we will need to wait a much longer time compared to what envisaged by movie directors or writers to see a minor part of the technologic developments imagined so far actually implemented.
It’s just for the secret ambition to get free from the textual codes that I’m debating about how to easily abstract the definition of information systems. In some ways, I’ve managed to do something related to this, in a restricted domain.

## When Model Driven Architecture turn out right
Once during an interview, I was asked why I didn’t apply MDA over all software projects I leaded. The question should be hooked as a point of discussion on MDA misconceptions, and generally, on modeling and UML. Unfortunately these interpretations are hindered by a phenomenon that a famous observer of human events (Mark Twain) revealed, that I repropose it again in IT terms:
>People commonly use UML like a drunk uses a lamp post; For support rather than illumination.

The initial costs of an MDA are pretty high, the return of the investments starts at the beginning of the automatic code generation. The models are built on the meta-model basis which defines the semantics of the system. Afterwards, the models are turned into code or other resources ready to be installed into the real system.
The initial developing efforts are focused on the meta-model and on transforming tools. The investments are rewarded by the automatic transformations that replace the repetitive coding work of programmers; and deeper is the amortization, the more the investment is profitable.
The ROI increases as much as the model instances are built, as well as the simplicity (in other words, easier to implement) of the metamodel and the transformation tools. Where shall I apply this approach for best results? In a real-time video application? In a powerful compression API? I don’t think so. I guess high values of this coefficient would be found into SOA systems.
MDA is suitable for service domain, like a banking middleware, where you may amortize the modeling system through hundreds of services, with many data structures and flow descriptors, but conformed to few abstract structures, the meta-model actually.

## Models are not code
Do you have to adopt UML for design metamodels? Definitely, not. You may define simple data classes in any textual format. The matter might raise when the metamodel grows in complexity, size and when many aspects of the domain are schematized into. Hence, you have to face UML: either you reinvent it, or you use it.
Perhaps tracing circles and arrows would be more embarrassing compared to typing on a keyboard, but it could be necessary when structures become hinged and interrelated. Try to join a class diagram with a sequence diagram, then enclose all in a composite for interaction with external parts, and do it without formal and conventional visual patterns and… let me know!
UML is a complete and exhaustive, it’s so generalist it would be applied to describe any software model, but for describing not for coding.
Many people confuse UML as a programming language. Wrong. It’s a tool for representing a system, a structure, a flow. As mathematics aims to formulate conjectures among countable entities, UML offers a way to define abstract entities. Such high-level language is a mere conceptual schema, it defines components and services, no programs ready to run.

## MDA layers
So what do you do with a picture with bubbles and arrows? Is it enough design some diagrams, push a button and voilà: getting a working system that fit your requirements? Nobody believes it, neither myself. The object model is unaware of the underlying system and of its implicit matters. As we know, the model declares the structure and the behaviour differences between one service and another, for all the remaining aspects the system applies common platform specific beaviors. For instance, whether you want to enclose the service into a transaction you may setup a ‘transaction’ stereotype in your own profile meta-model, which will be transformed into a Java annotation or Xml attribute and then properly interpreted by the server framework. Once the MDA is ready, one designs the models and transforms them into hardcore resources, the mythic code bullets, finally deployed into the server.
It would appear as too simple, and in order to keep off prejudicial comments I’ll tell you that the real applications are much more complex than this simple vision.
You can’t update code bullets by hand because the changes will be lost next time you generate them, automatically. Many exceptions are to be considered, allowing for example, to update generated code. I’ve used merging API like JMerge, and I found it useful to enrich the code without discontinuity from the model and the generated code.
