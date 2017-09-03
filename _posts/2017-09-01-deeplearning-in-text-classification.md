---
layout: post
description: An ensemble based on Convolutional Neural Networks for Text Classification in Chatbots could outperform big players like Api.ai
title: Deeplearning in Text Classification
image: /assets/minos-chart.png
---

In the Divine Comedy, Minos is a daemon appointed to guard the entrance of the hell. He listens to the sins of souls
and indicates them their destinations by wrapping his tail as many times as the assigned circle.
The figure is emblematic of the machine learning classification, where an entity is identified as belonging to
a category or to another. Rather than condaming souls to endless pains, the harmless tool I am describing can judge whether an user's utterance belongs to a specific intention, or to a limited range of emotions. Namely, it can serve intention recognition and sentimental analisys.

In the realm of conversational commerce, the examined sentence could be:

`I want to buy some apples and pears`

The system recognizes the intention `search` and presents the results.

Intention prediction is not an untackled problem and the market offers plenty of intention recognition services
There are many players such as Google (Api.ai), Facebook (Wit.ai), Microsoft (Luis.ai) just for mentioning some of them,
but this shouldn't prevent further explorations in the topic, sometimes with unexpected positive surprises, as shows in the graph.

Recently I created a solution using an ensemble of machine learning models which performs well even though it is not yet well tuned,
opening rooms for future improvements. The test was performed with relevant data used for training the deployed model of the Chatbot system,
with those results:

![Minos Accuracy]({{ site.url }}/assets/minos-chart.png)

Minos, the intention recognizer, combines multiple classifiers for getting a good prediction on intentions submitted to [Charly](https://www.facebook.com/charlygrocery/).
One of them is based on Convolutional Neural Networks (CNN).

## CNN in NLP

CNN is mostly applied to image recognition thanks to the tollerance on translations
(rotations, distortions) and the compositionality principle (entities are composed by its constituents).
Admittedly, CNN appeared to me counter-intuitive at a first approach because text looks very different from images:
  1. The order of the words in text is not as important as the order of the pixel in an image.
  2. Humans percept text sequentially, not in convolutions.

### Invariance

The _(1)_ assumption is biased, since one should compare characters (rather than words) with pixels. The proportion is more like:

`text : char = image : pixel`

By this angle of view, the order of characters in sentences is fundamental. Convolutions in text come in form of:

`single word => bi-grams (two adjacent words) => and so on (n-grams)`

like graphical features

`lines , corners => mouths, eyes => faces`

come out of portrait images.

In CNN the pair `adjective + object` for example,
could be at the begin or at the end of a sentence, exactly like a face is recognized wherever it is located in the whole picture.

### Sequentiality

It might seem more intuitive to apply Recurrent Neural Networks (like LSTM, Attention or Seq2seq) for text classification,
due to the sequential nature of RNNs algorithms. I didn't run any test on them so far, but I would promptly play with [TreeLSTM](http://arxiv.org/abs/1503.00075). CNN performs well, and one might say: _Essentially, all models are wrong, but some are useful_.

### Word Embeddings

Alike any NLP, in CNN words are replaced by their correspective semantic vector. Most famous are Google word2vec, GloVe and FastText.
I decided to make use of [ConceptNet Numberbatch](https://arxiv.org/abs/1704.03560)
that took first place in both subtasks at [SemEval 2017 task 2](http://alt.qcri.org/semeval2017/task2/).
Moreover, the vector file is very small (~250M) compared to Google News word2vec (~1.5G) and from an engeneering point of view, those numbers matter.
