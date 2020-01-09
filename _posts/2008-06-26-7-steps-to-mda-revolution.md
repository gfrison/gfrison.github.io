---
layout: single
title: 7 steps to MDA revolution
published: true
permlink: /2008/06/26/7-steps-to-mda-revolution
comments: true
image: /assets/supernova.jpg
tags: banking, emf, executable uml, java, mda, methodology, model driven architecture, SOA, uml, uml2
---
![]({{ site.url }}/assets/supernova.jpg)
I didn’t believe that such a successful project was such a rare event in the IT industry, that’s why I’ve never caught another chance for applying the learned lessons again. I thought that the experience accrued on Model Driven Architecture will be reusable in other circumstances, though I’ve never seen concepts as executable UML or MDA either applied or mentioned in the following commitments I’ve pursued into.
The idea of this project wasn’t conceived by external consultants thirsting for selling their cool technology; instead, it was born and grew up just inside the development team. The architecture’s transition had been gradual, and little by little, as new automation scenarios penetrated our excited minds, we moved as many as possible development processes under MDA framework.
Despite my early impressions while considering to undertake the project, the upper management embraced it and laid down investments counting on the benefits that this new approach would provide to the development.
What is difficult to change is the modus operandi of a 300 employee company that offers banking services and applications, which is engaged in one of the most conservative field in technology and development methodologies by default. It was about a significant jump in the services development and as the PM remarked:
>_“We are developing as dinosaurs, don’t you know what the hell happened to them?”_

the way to MDA was traced.

The issues we faced with the introduction of modeling notions would be defined as practical contingencies rather than theoretic or philosophical reasons, foremost the mess in the business layer. It raised reliance and maintenance weaknesses with time, even security holes that sounded so bad in such a company with a plenty of  banks as customers.
The hundreds of cases developed by dozens of engineers turning over throughout the months in the Java development area had reached the critical mass, enough to trigger an explosion/implosion of the whole system. On the other hand, the applications can stand up only by high costs of maintenance and lazy deliveries, due to the difficulties on integrating incoming services with the underlying system.
The application layer managed the data flows between clients at the top and feeds and legacy information systems at the bottom. On their way, they affected several mixes by business process rules hard-coded in obscure java classes. Unfortunately, most of those shaking details were lost, because of the policy related to the development, which didn’t claim about missing documentation, and then it was so damned annoying to go back and take over old artifacts for maintenance or updating rules. Only skillful programmers might extricate the balled up code. The critical mass had to drop down and be brought to lower temperatures quickly. New developments and dozens of  incoming features were planned, so a deep refactoring was a must; it can wait no more.

How was the domain layer implementation that popped up the highlighted problems?
The developer’s effort was mainly focused on the creation of java classes implementing a [Command](http://en.wikipedia.org/wiki/Command_pattern) and defining the service to the framework through an xml descriptor. The input and output of such a command was a raw DOM argument, which was parsed to extract the input data needed by the business transaction, the most part of coding was regarded for parsing and filling the response’s service that was a raw xml document too. I think it isn’t agreeable to put most of the developing efforts merely on managing input/output data and mapping, but this was the daily job.
Apply the MDA take time, it was an one year evolution, and it would be summarized with:


- *XSD barriers*. It was necessary to set some boundaries for developers, in order to get a minimum of control over the data flows. Each service had its own formal validation on input/output data, though no restrictions were settled on how implementing the services. Never ever elements or attributes not defined in advance by commitments.
- *Pojo*. Replace the raw document with simple pojo as an argument in the call-back methods; this operation aggregates the formal validation with an easy approach on data manipulation. The binding xml-java isn’t hurdle, it is automatic and many available libraries can accomplish this step.
- *First hints with EMF*. Xsd files are models for xml data, EMF is a MOF java implementation, a general abstraction for writing all sorts of models, I don’t linger over it now, but it represented a jump to the service modeling. EMF is an open source library enclosed in the Eclipse platform easy to use and customizable, it aims to separate the abstract model from the ground.
- *Choice of technology*. The play with EMF opened new horizons on modeling facilities. Hooked by this methodology to design SOA applications I realized EMF is not enough, the UML (which core principles are inherited from MOF) can fit much better with my purpose to design the object model, define the process flow and the user experience. UML offers diagrams that you can join together, static and dynamic model may describe most of application structure and behaviour.
- *Executable UML*. What do you do with this bunch of diagrams if you can’t transform them in real artifacts and plug-in them in your SOA framework? Not so much, keeping UML diagrams without related transformations and executions is merely fine for documentation, not much more than this. At that time the company joined the Rational beta-program and I started to develop Eclipse compliant plugins which leveraged the power of UML2 eclipse implementation.
- *How to define data mapping?* One of the main obstacles encountered was the data mapping between two different structures. It happens when you need to connect two or more components inside a service call, and each of those have different data structures. In this case UML doesn’t provide any help and you have to customize the model with special stereotypes and profiles.
- *Sequence and state diagrams*. Class diagram were used to generate java classes, xsd files, copy cobol. Sequence diagrams on the other hand describe the flow of processes and their business rule, even conditional instructions which may be transformed to bpel or custom service descriptors. State diagram shows its benefits modeling the user experience and the steps to complete an operation, it easily tracks the state of sessions and will be transformed into the MVC system, as well as in whatever rich client forms.

Have your say.
