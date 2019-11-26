---
layout: posts
description: Prototype that combines NLP analysis and semantic data sources for answering simple generic questions, by learning how to get the informations given a fairly small amount of question/answer pairs.
title: Automated Question Answering using Semantic Networks
header:
  og_image: /assets/conceptnetQA.png
comments: true
---
I worked recently in a small prototype that combines NLP analysis and semantic datasources
for answering simple generic questions, by learning how to get the informations given a fairly small amount of question/answer pairs.

Conversational interactions represents the core of any modern Chatbot and the ability to manage utterances and conversations
is the strongest indicator of user’s satisfaction.
A natural and spontaneous QA dialogue, as every Chatbot would aim to engage, will attempt to solve 3 fundamental issues:
1. *Classify* utterances and extract dependencies between words.
2. *Integrate* source of knowledge.
3. *Infer* transitive semantics (e.g., reconstructing what it is implied but not written).

* https://labs.hybris.com/2017/08/02/automated-question-answering-using-semantic-networks/ *

Neural networks are particularly effective in [conversational modelling](https://arxiv.org/abs/1506.05869 "Neural Conversational Model").
Architectures like [*seq2seq*](https://papers.nips.cc/paper/5346-sequence-to-sequence-learning-with-neural-networks.pdf)
have demonstrated to generate sounding conversational interactions, by predicting sentences given the previous sentences in the dialogue.
The [*end-to-end*](https://www.quora.com/What-does-it-mean-for-a-neural-network-to-be-trained-end-to-end)
nature of those models represents one of their major strengths. Those models have no hand-crafted rules, since
they are trained against large conversational datasets.  
Nevertheless, these models can’t incorporate content in the form of factual informations from sources
rather than the training corpora; the conversational analyzer *(1)*  and the knowledge representation *(2)* are not distinguishable.

### Split language and knowledge domains
The approach I’m going to describe will allow the model to be versatile and applicable to different domains
since it can clearly distinguish *(1)* and *(2)* as interoperable components of a QA system.

>The goal of the prototype is to obtain meaningful answers out of simple questions,
by learning *how to get* the information instead of learning *the information*.
Basically, the machine learning system won’t be instructed merely on which is the right answer, but rather how to find it in a given datasource.

I was inspired by this [paper](https://aboteanu.github.io/pdf/AAAI2015.pdf "Solving and Explaining Analogy Questions Using Semantic Networks")
that uses [ConceptNet](http://conceptnet.io/), an open-source semantic database which gather informations from several sources such as WordNet, DBPedia, Wiktionary. It helps computers to understand the meaning of the words by solving their analogies with others. ConceptNet serves as [ontology](https://en.wikipedia.org/wiki/Ontology) source and also covers the inference  service *(3)* by its graph based nature, where entities are connected by semantic relations.

### Sentences classification and entities extraction
As could be intuitively deducted, the sentences structure is invariant in respect of the number of entities used for querying the datasource, therefore the samples necessary to train could be much lower than the seq2seq previously mentioned approach.

![NLP dependency tree]({{ site.url }}/assets/whatis-the-color-of-the-sea.png "CoreNLP dependency tree")

### Knowledge crawler
Within the entities extracted from the utterance and the expected answer (or a list of optional answers) the crawler should return the shortest navigable path in the knowledge graph for obtaining the accepted answer.

*Input*: `nsubj: colour, nmod: sea`

*Expected answer*: `blue`

*Compiled model*: `[{nsubj}:/r/IsA] and [{nmod}:/r/RelatedTo]`

The outcome is correlated to the logic of the sentence, and different types of sentences are classified accordingly.
However, similar grammar structures can also have different semantics and they should be treated differently. In the case of

*Question*:`what is the capital of germany?`

the crawler will find the query with the least number of joins required to get the expected results. It outputs a different model: `{nmod}:/r/dbpedia/capital`

In this case, a direct relation (*dbpedia/capital*) univocally describes the expected relation, and it is selected as the best alternative for answering that specific class of questions.

### Run the model
Now let's run the model asking: `what is the color of the sun?`. The inference component will first classify the sentence and it will associate it with the first model have been previously compiled.
The crawler will search entities that fulfill both relations (`X IsA color and X RelatedTo sea`), and gets the result: `yellow`.

These are some of possible outcomes:

```
>Tell me some cities in Italy in front of the sea
Venice, genoa

>What is the capital of Germany?
Berlin

>Is blue the color of the sea?
Yes

>Which lakes can you find in Italy?
Lago como, lago garda
```
