---
title: "Smoothing Conversational Contexts for Language-based Digital Agents"
excerpt: "Neural network architecture for smoothing context switching on conversations between human and automatic agents."
date: 2021-10-19 
header:
  # image: /assets/images/lstm.png
tags:
 - NLP
 - Neural Networks
---
| Id                                                                   | Status  | Submission Date | Activation Date   | Authorship      |
| -------------------------------------------------------------------- |
| [US20190278845A1](https://patents.google.com/patent/US20190278845A1) | Granted | {{ "2018-03-06" | date_to_string }} | {{ "2021-10-19" | date_to_string }} | 100% |

A bit surprised for having this invention granted in a exploding sector like the one of natural language processing. The 2017, when I [implemented this idea]({% post_url 2018-03-14-stochastic-conversational-workflows %}), was still a _golden age_ for [NLP]({{ site.baseurl }}/tags/#nlp), where was possible for non-specialists like me, to have some breakthroughs via new applications of neural networks.

Nowdays (2022), this method might look archaic and outdated by far more sophisticated approaches (e.g.: transformers and large language models), but the problem was intended to solve, seems to me largely ignored even today by a variety of commercial digital assistants out there in the market.

Setup a chatbot for managing a business process in the consumer retail means mostly designing a [tree of steps]({% post_url 2018-03-05-conversational-contextualization %}) users will follow for finalizing what they intend to do. Those states are the direct successors of previous choices, and from there, users may do one or more actions to move down in the decision tree. I believe/d it is/was a rigid paradigm. We are very flexible to jump among different topics even though we are focusing on completing a specific task. Chatbots (aka digital assistants) expose a textual interface that make user inclined to communicate as they do with people, not with computer programs, and we don't follow decision trees very much. When a consumer is finalizing a purchase process but then asks info about delivery, the chatbot will switch to that context, ready to back to the checkout as soon as the information is provided, or at user's request. 

Moreover, the same utterance in a context might be handled differently in another context. In an online grocery, if you ask _how much it costs?_ when browsing an item, it clearly means you request the price of that product. If you are asking information about delivery, the same call means you request the price for that service, nothing else.

![Chatbot delivery price information](/assets/images/charly-cost1.png) 

👉 [Here](https://video.sap.com/media/t/1_kp5hbyih) you can find a demo of Charly, the conversational commerce prototype that inspired this idea.

Technically, the conversational flow is managed by a neural network that comprises convolution neural network (CNN) layer and a long/short term memory (LSTM) downstream. 
The CNN was feed with word embeddings of a clearified input text, corroborated with 13 features that signal the presence of questions, catalog products, addresses, named entity records and their classification, sentiment markers, etc.
LSTMs will get as input the CNN representation, and they will keep pace of contextual dialog.

Training will be performed by automatically mixing _conversational units_ in order to mimic an artificial randomization of what might happen when interacting with a real user. Those units are sub-modules of broader business processes (checkout, ticket creation, restaurant booking), and represent the smallest meaningful exchange of information with the human user. Simply enough, it worked. It could have been an promising branch of research and the basis of other fruitful inventions.

![](/assets/stochastic-conversational-workflows.png)



