---
permalink: /2017/07/08/machine-comprehension-on-chatbots
layout: single
title: Machine Comprehension on Chatbots
header:
  og_image: /assets/bidaf.png
comments: true
tags:
  - NLP
---

One of the most demanded feature in chatbots is the ability to automatically provide helpful informations. Users might ask about how to pay the purchases online, how to return an defected item, when the purchase could be delivered or just about the opening hours of a shop.

One way to implement this feature is to train a sentence classifier for a determined set of questions the merchant is willing to answer. The system should be instructed on some examples such as: “which credit card do you accept?”, “How do I pay?”, “which payment do you support?” and so on.
This simple technique requires a sequence of manual tasks for every conversational agent, such as set up the training and inference pipeline for questions/answers, or reuse the Natural Language Understanding (NLU) system already adopted by the chatbot, if present.

* https://labs.hybris.com/2017/07/17/machine-comprehension-charly/ *

A second more intriguing and sophisticated approach leverages the advances in machine comprehension, which is the ability to read text and then answer questions about it, automatically.
Stanford NLP group created [SQuAD](https://rajpurkar.github.io/SQuAD-explorer/) a dataset consisting of 107.785 questions pairs on 536 articles, for training and evaluate machine comprehension models. One example of article and question and expected answer is:
>In meteorology, precipitation is any product of the condensation of atmospheric water vapor that falls under gravity. The main forms of precipitation include drizzle, rain, sleet, snow, graupel and hail…

*Question*: What causes precipitation to fall?

*Answer*:   gravity

Understand text is hard. It requires the knowledge of the language and a representation of the topic. Those challenges could be easily compared with the linguistic and  cultural barrier among people. For example, I can hardly understand a paper written in chinese about Panda’s immune system, essentially because I don’t know the chinese language and I don’t know anything about immunology. Similarly, a program can’t do better on unless it masters these two aspects: language on one hand, and high-level concepts on the another.

For running an automatic FAQ responder I used one of the top-ten reading comprehension system available, the [BiDAF](https://arxiv.org/abs/1611.01603) (Bi-Directional Attention Flow). It doesn’t perform badly (81,5% F1 accuracy) compared to human precision, which is 86,8%.
I applied BiDAF on [Charly](http://m.me/charlygrocery), a chatbot for conversational commerce, for serving informations extracted from given text like this:
>My phone number is +4911002233. I live in Munich, Germany.
You can pay with your credit card, we accept: Visa, Mastercard, American Express, Maestro, Visa Debit.
The delivery is twice per week on Tuesday and Saturday.
if the purchase or a product is not good or you are unsatisfied please return the product with the receipt within 30 days to the driver  or call us on +4911002233.

This is how Charly answers:

![Charly chatbot]({{ site.url }}/assets/bidaf.png)

This approach is much scalable than a classical questions’ classifier. It allows automatic responses from text that could be just scanned automatically by the FAQ’s page present in the customer website, or just a plain info text submitted by e-mail or a web form.