---
layout: posts
description: A novel approach for improving conversational fluency in Chatbots by employing neural networks based on convolutions and long-short memory units.
title: Stochastic Conversational Workflows
published: true
permlink: /2018/03/14/stochastic-conversational-workflows
image: /assets/stochastic-conversational-workflows.png
comments: true
---

Traditionally, user interfaces are a series of screens and forms for exchanging informations with the user. Most of the applications start with a main screen from which users can navigate using breadcrumbs, menus, buttons like back and forward. This paradigm remained almost unaltered with the coming of hypertext where one may jump from a page or dialog into another by visual links, that are immediately accessible.
Chatbots shift UX towards _conversational hypertext_ that produces the appearance of having a conversation with the computer. People can interact naturally, and since everyone already knows at least one natural language, nobody needs any training for it.

[see full article](https://labs.hybris.com/2018/03/20/stochastic-conversational-workflows/)

<!--

The casualness of the medium contrasts with the complexity of the structured, and sometimes cumbersome, functions for achieving a specific goal, but while web-users can easily switch to a new goal-oriented scenario with a click, in the messaging application this could be mainly done by texting.
Conversational applications usually implement workflows not by screens or forms but by piling new dialog scenarios into the conversational stack. Technically, it is something like packing [finite state machines](https://en.wikipedia.org/wiki/Finite-state_machine), on which every layer represents a particular task. When the current one is accomplished, the dialog state closes and it is removed from the stack.
Conversational workflows may be managed by state-machine engines implemented directly in the chatbot or alternatively by existing flow managers such as Dialogflow (former API.AI), Wit.ai, LUIS.ai (Language Understanding Intelligent Service) by which designers can setup conversation processes in their web dashboards.

![Conventional Dialog Stack]({{ site.url }}/assets/dialog-stack.png)

Even though it might be tempting to assume users will follow the exact logical sequence of steps defined by the bot's designer, it rarely occurs.
The austere interface of a Chatbot does not constraint users in pre-defined schema, it does not prevent nor discourage them to behave like they naturally do with other people: express a demand, ask information, change search criteria for a product, ask maybe again, and eventually pay or just abort the process.

![Rule-base vs stochastic workflow process]({{ site.url }}/assets/checkout-workflows.png)

People do not communicate in _stacks_. They tend to jump from a subject to another almost in a random way. Users may decide to do something entirely different, no matter how the process flow has been structured. They may ask for questions unrelated to the current procedure, or cancel it and then start over again. It
is natural that humans switch topics during dialogue for whatever reason.

Even though it is easy to reach significant outcomes by using one of the mentioned NLU (Natural Language Understanding) systems with little effort, those modelers pose quickly their limitations due to their rigid characteristics concerning _activation_ and _behavior_. In other words, conversational workflows may need to be hard-coded based on few discriminating features, mainly imposed in a limited set of user intentions. It may be almost impossible to manage programmatically a large amount of features that can affect dialogs. Further, variations of such processes may result in growing complexity, becoming unmanageable over time.

## New conversational model
Real world is a stubborn place. It is complex in ways that resist abstraction and modeling. It notices and reacts to our attempts to affect it. Nor can we hope to examine it objectively by pre-defined rules or by programmed state machines.
> The fundamental aspect can't be ignored is the probabilistic nature of the model that will serve those dialogs.

The complexity of the system increases with the number of variables, leading the conversation to a certain function instead of another. Hence, the obligation to move away from rule-based systems and embrace uncertainty, probability and statistic.

Once the limitations of those approaches are unveiled, we are ready to attain  [context and sequentiality](https://gfrison.com/2018/03/05/conversational-contextualization/) in a completely different way. We should let machine learning do what it can do best: calculate predictions over large amount of input.
> The system should being able to replicate human behavior by learning from real conversation segments.

The model should inquiry the system for getting more informations and take more plausible decisions on what to present to the user. With those premises, machine learning is entering into the game, and it comes with the form of neural networks.

## Text classification
Neural networks are adaptable systems whose ability to learn comes from varying the strength of connections between its artificial neurons. They are basically universal function approximators. I described [neural networks for intention classification](https://gfrison.com/2017/09/01/deeplearning-in-text-classification/)  by using convolutions for grasping the semantics behind user's sentences.

## Additional input
For helping the classifier could be provided, other than the raw text, a set of discrete features bring informations that share some predictive relation with the action to choose. Those informations could be somehow related to text itself, but also they could be contextual to the user profile or to the ongoing marketing campaigns promoted by the merchant.

For example, a particular search request should be directed into a special promotion? The time of the day can affect the query selection because in the night a camomile sells better than black tea? Just feed the classifier with those features, and let ML to do the hard work.   
The new conversational model should not just understand the single intention of a phrase, but elaborate it in the overall context the user has engaged with the Chatbot.

## Time series models
The concept of time series is dependent on the idea that past behavior can be used to predict future behavior. In sequence-based models, the output is not just determined by the last input, like in regression predictive models, but also by its proceedings. This peculiar predictor should have some interesting characteristics. Latests input affect more the final output than the ones far away in time, and those models should be able to _override_, _remove_, _remember_  qualifiers along the sequence.
Long short-term memory (LSTM) units come to our rescue. LSTMs can _remember_ values over arbitrary event series and they are a more sophisticated extension of the recurrent neural networks.

## Multi-task learning (MTL)
Consider a hypothetical recommender that has learned to predict your preferences about cars. A mono-task system could be trained to give a single output that might match the car model with your profile. A multi-task model could be trained to not only return you the model's name, but also the color, the engine type, the accessories. MTL aims to solve simultaneously multiple classification tasks within the same neural network. We can view MTL as a form of transfer learning where commonalities and differences across tasks are exploited to improve the overall learning efficiency. It seems that neural networks _love diversity_: more tasks they learn more accurate are their predictions, compared to training them separately. The conversational engine I created uses multi-task learning (MTL). It not merely returns  a single label, but instead it returns a fine-grained set of parameters that add expressiveness into the behavior of the Chatbot.

## Neural network architecture
My proposition is about to use both (CNN, LSTM) in a fully-connected neural network,
in order to leverage classification qualities of CNN with the sequentiality of LSTM.
That means, a particular meaning of an user's utterance is not considered alone like
the current state of the art of Chatbot classifiers, but it is evaluated in the context
of the conversation.
Fully-connected neural network means that the different layers of the network (CNN, LSTM) are affected by the sames feed-forward and back-propagation iterations.

![Stochastic Conversational Workflows]({{ site.url }}/assets/stochastic-conversational-workflows.png)

While CNN extracts a relevant representation of the user's input, other type of inputs can be feed into the LSTM layer as illustrated. In this way, the meaning of the user's sentence is evaluated within a set a additional parameters that can affect the decision outcome of a particular conversational step.
-->
