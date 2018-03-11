---
layout: post
description: Contextual memory in conversational applications play a central role in any type of interaction between two parties, the Chatbot and the user. Here some example on how a contextual based system might improve the communication on some basic scenarios.
title: Stochastic Conversational Engine
published: false
permlink: /2018/03/14/stochastic-conversational-engine
image: /assets/
---

Traditionally, user interfaces in applications are a series of screens and forms for exchanging informations with the user. Most of the applications start with a main screen then the user navigates throughout several steps where forms fill the screen with text boxes and bullet points. This paradigm remained almost unaltered with the hypertext where user may jump from a page or dialog into another by visual links that are immediately accessible. Users can navigate using breadcrumbs, menus, using buttons like back and forward.
Chatbots shift user experience towards _conversational hypertext_  which produces the appearance of having a conversation with the computer. Users can interact naturally with it without any training, since everyone already knows at least one natural language. The naturalness of the medium contrast with the complexity of being able to achieve complex and structured functions. While the web-user can easily switch to a new goal-oriented scenario with a click, in the messaging application this could be done mainly by means of texting.

Conversational applications usually implement workflows not by screens or forms but by piling new dialog scenarios into the conversational stack. Technically, it is something like packing a stack of [finite state machines](https://en.wikipedia.org/wiki/Finite-state_machine) on which every layer represent a particular task. When it is accomplished, the related dialog state closes and it is removed from the stack.
Conversational workflows may be managed by state-machine engines implemented directly in the chatbot system or alternatively by existing flow managers such as Dialogflow (former API.AI), Wit.ai, LUIS.ai (Language Understanding Intelligent Service) by which chatbot designers can setup conversation processes in their web dashboards.

Even though it might be tempting to assume users will follow the exact logical sequence of steps defined by the bot's designer, it rarely occurs.
The austere interface of a Chatbot does not constraint the user in pre-defined  schemas, it does not prevent nor discourage users to behave like they naturally do with other people: express a demand, ask information, change search criteria for a product, ask maybe again, and eventually pay or just abort the process.
![Rule-base vs stochastic workflow process]({{ site.url }}/assets/checkout-workflows.png)

People do not communicate in _stacks_, they tend to jump from a subject to another almost in a random way. The user may decide to do something entirely different, no matter how the process flow has been structured. Users may ask for questions unrelated to the current topic, or cancel and then start over again.

Even though it is easy to reach significant outcomes by using one of the mentioned NLU (Natural Language Understanding) with very little effort, those dialog modelers pose quickly their limitations due to their rigid characteristics concerning activations and behaviors. In other words, such conversational workflows may need to be hard-coded based on few discriminating features, mainly imposed in a limited set of user intentions. Further, variations of such conversational workflows may require a review of the workflows themselves, that's may result in growing complexity of the workflows and may become unmanageable over time. It may be almost impossible to manage programmatically a large amount of variables that can affect such conversational workflows.

## New conversational model
Real world is a stubborn place. It is complex in ways that resist abstraction and modeling. It notices and reacts to our attempts to affect it. Nor can we hope to examine it objectively by pre-defined rules or by pre-programmed state machines.
The fundamental aspect can't be ignored is the probabilistic nature of any type of model that will serve those dialogs. The complexity of the system increase with the number of variables that can lead the conversation to a certain function instead of another. Hence, the obligation to move away from rule-based systems and embrace uncertainty, probability and statistic.

Once the limitations of those approaches are unveiled, we are ready to attain  [context and sequentiality](https://gfrison.com/2018/03/05/conversational-contextualization/)  in a completely different way. We should let machine learning do what it can do best: calculate predictions over large amount of input.
The system should being able to replicate human behavior by learning from real conversation segments. The model can inquiry the system for getting more informations by which can take more plausible decisions in what to present to the user. With those premises, machine learning is entering into the game, and it comes with the form of neural networks.

## Neural classification
Neural networks are adaptable systems whose ability to learn comes from varying the strength of connections between its artificial neurons, they are basically universal function approximators. I described [neural networks for intention classification](https://gfrison.com/2017/09/01/deeplearning-in-text-classification/)  by using convolutions for grasping the semantics behind user's sentences.

## Additional input
For helping the classifier could be provided, other than the raw text, a set of discrete features bringing informations that have some predictive relation with the action to predict. Those informations could be somehow related to text itself, but also they could be contextual to the user profile or to the ongoing marketing campaigns promoted by the merchant. For example, a particular search request should be directed into a special promotion? The time of the day can affect the query selection because in the night a camomile sells better than black tea? Just feed the classifier with those data, and let ML to do the hard work.   
The new conversational model should not just understand the single intention of a phrase, but elaborate it in the overall context the user has engaged with the Chatbot.

## Time series models
The concept of time series is dependent on the idea that past behavior can be used to predict future behavior. In sequence-based models, the output is not just determined by the last input, like in regression predictive models, but also by its proceedings. This peculiar predictor should have some interesting characteristics. Latests input affect more the final output than the ones far in time, and those models should be able to _override_, _remove_, _remember_  qualifiers along the sequence.
Long short-term memory (LSTM) units come to our rescue. LSTMs can _remember_ values over arbitrary event series and they are a more sophisticated extension of the recurrent neural networks.

## Multi-task learning (MTL)
Consider a hypothetical recommender that has learned to predict your preferences about cars. A single task system will be instructed to give a single output that could be the car model or the manufacturer that match with your profile. A multi-task model could be trained to not only return you the model's name, but also the color, the engine type, the accessories. MTL aims to solve simultaneously multiple classification tasks within the same neural network. We can view MTL as a form of transfer learning where commonalities and differences across tasks are exploited to improve the overall learning efficiency. It seems that neural networks _love diversity_: more tasks they learn more accurate are their predictions, compared to training them separately. The conversational engine I created uses multi-task learning (MTL). It not merely returns  a single label, but instead it returns a fine-grained set of parameters that add expressiveness into the behavior of the Chatbot.



A chatbot may be incorporated in a conversational application that is a computer program that combines NLP with underlying services in order to execute the underlying services by means of text. Such a conversational application may make use of a machine learning (ML) based system for detecting the intention of a user. A limited set of intentions of a user may be converted into commands for composing workflows for providing the underlying services. Some procedures of the workflows are not always attainable by a simple, single user input (e.g., a single sentence input by the user, a single action performed by the user etc.) but may require a sequence of user actions for achieving goals. For example, in case of a checkout process of an online shopping service, more than one user inputs (e.g., delivery address, invoice address, delivery options, telephone number, etc.) may be required for completing the workflow of the process.
