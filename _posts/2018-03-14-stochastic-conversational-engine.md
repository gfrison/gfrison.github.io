---
layout: post
description: Contextual memory in conversational applications play a central role in any type of interaction between two parties, the Chatbot and the user. Here some example on how a contextual based system might improve the communication on some basic scenarios.
title: Stochastic Conversational Engine
published: false
permlink: /2018/03/14/stochastic-conversational-engine
image: /assets/
---

Traditionally, user interfaces in applications are a series of screens and forms for exchanging informations with the user. Most of the applications start with a main screen and then the user navigate throughout several steps in succession for various functions like searching products or complete a checkout. This paradigm remained almost unaltered with the hypertext where user may jump from a page or dialog into another by visual links that are immediately accessible.
Users can navigate using breadcrumbs, menus, using buttons like back and forward.
Chatbots shift this standard towards a _conversational hypertext_  which produces the appearance of having a conversation with the computer. Users can use without any training: everyone already knows at least one natural language. The naturalness of the medium contrast with the complexity of being able to achieve complex and structured functions, while in the web the user has graphically all the options available for switching to a new goal-oriented scenario, in the messaging application this could be done mainly by means of texting.

Conversational applications implement workflows not by stacking screens or forms but by adding dialogs states on top of the dialog stack. Technically it is something which resemble a stack of _finite state machines_ where user can accomplish a particular goal, and moving from one functionality to another means the system flips the current dialog with the new one. When a task is accomplished, the related dialog state closes and it is removed from the stack.

Even though it might be tempting to assume users will follow the exact logical sequence of steps defined by the bot's designer, it rarely occurs.
The austere interface of a Chatbot does not constraint the user in pre-defined usage schemas, and while that freedom could be disarming, it does not prevent nor discourage users to behave like the naturally do with a normal clerk in a shop: express a demand, ask information, change criteria of selection, ask again maybe, and eventually pay.
![Rule-base vs stochastic workflow process]({{ site.url }}/assets/checkout-workflows.png)

People do not communicate in _stacks_, they tend to remain or jump from a subject to another in a random way. the user may decide to do something entirely different, no matter how the process flow has been structured, or ask a question that may be unrelated to the current topic, or cancel or they want to start over again.

Conversational workflows may be managed by state-machine engines implemented in the chatbot system. Alternatively, conversational workflows may be managed by existing applications such as Dialogflow (former API.AI), Wit.ai, LUIS.ai (Language Understanding Intelligent Service) on which chatbot designers can setup conversation processes by using web dashboards. Even though it is easy to reach gratificant outcomes with very little effort, those conversational modelers pose quickly their limitations due to their rigid characteristics concerning activations and behaviors. In other words, such conversational workflows may need to be hard-coded based on a fixed set of user intentions. Further, variations of such conversational workflows may require a review of the workflows themselves, may result in growing complexity of the workflows and may become unmanageable over time. Moreover, it may be almost impossible to manage a large amount of variables that can affect such conversational workflows since exponential branches of the workflow tree may need to be produced.

It appears clear that rule-based or the stacked dialog system could not apply anymore for Chatbots.



A chatbot may be incorporated in a conversational application that is a computer program that combines NLP with underlying services in order to execute the underlying services by means of text. Such a conversational application may make use of a machine learning (ML) based system for detecting the intention of a user. A limited set of intentions of a user may be converted into commands for composing workflows for providing the underlying services. Some procedures of the workflows are not always attainable by a simple, single user input (e.g., a single sentence input by the user, a single action performed by the user etc.) but may require a sequence of user actions for achieving goals. For example, in case of a checkout process of an online shopping service, more than one user inputs (e.g., delivery address, invoice address, delivery options, telephone number, etc.) may be required for completing the workflow of the process.
