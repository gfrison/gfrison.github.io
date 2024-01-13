---
title: "the neurosymbolic nature of Conceptual Spaces"
description: "Conceptual spaces share similarity with neural embeddings because they are both differentiable and convex. At the same time, concepts incorporate symbolism with their multi-facets domains. They naturally enable semantic algebra and computational problem solving."
share: true
permalink: /2023/08/04/conceptual-spaces-neuro-symbolic
header:
  og_image: /assets/images/cs1.png
tag:
  - neurosymbolic
  - logic programming
  - neural networks
---

> “This is the promise of the Semantic Web – it will improve all the areas of your life where you currently use syllogisms. Which is to say, almost nowhere.” 
> 
> _—Clay Shirky_

> “Fortunately, a large majority of the information we want to express is along the lines of ‘a hex-head bolt is a type of machine bolt.’
> 
> _—Berners-Lee_

> "Unfortunately this is not true. If one considers how humans handle concepts, the class relation structures of the Semantic Web capture only a minute part of our information about concepts"
>
> _—Peter Gärdenfors_

I guess that it doesn’t come without notice that the semantic web approach isn’t the favourite of the author of the Conceptual Spaces (CS).
Two distinguished views on knowledge representation are the semantic web and the vectorized embeddings that belong to the symbolic and the connectionist schools respectively. The CS theory comes from a very different vision on how knowledge should be encoded. Concepts in CS are without doubt, close to vectorized embeddings representation though they preserve interpretability  - a strength of the symbolic world. 

## Vector embeddings
It’s not hard to think many of you have already heard about word embeddings for knowledge graphs. Modern natural language processing tasks based on neural networks would not be in there without vectorized embeddings. We’re witnessing the immense progress of natural language processing (ex: GPT and related) in recent times, and their machine learning algorithms rely on word embeddings. A branch of deep learning named graph neural networks (GNN) have brought a similar advancement on machine learning tasks such as link prediction in ontologies. The intuition behind neural network embeddings is that words or graph nodes can be represented as a series of real numbers that embed the semantics, and programs of a very special kind - the neural networks - can use them to accomplish some specific task. Vectorized embeddings come as a byproduct of those processes where there is not contemplated human supervision in the loop. Embeddings, differently from ontologies, don’t convey any semantics that is comprehensible, and even less edited, by people. 

If requested during a talk in a conference, I can hardly imagine someone raising the hand because of past experience with CS.

In the CS framework, concepts are represented as vectors of numbers that are continuous in their spectrum and convex, which means that similarity scores and distances among concepts are naturally derivable, but those vectors are not imperscrutable to human scrutiny. Concepts are built taking in account how we process and categorise them. 

## Properties as region spaces
Domains represent a single quality. They are convex and differentiable because they can be represented in real values. A concept is qualified via a set of domains. An apple has a round shape, a colour in the range of green, yellow and red, a taste (which is apparently related to the colour), a size, a weight. Likewise neural embeddings, similar concepts cluster together in regions. In CS, concepts are defined by vectorized properties where each of them describe a quality in a specific domain:
- The colour domain could be represented as a three dimensional space, where each dimension defines the intensity of one the three basic colours, red, blue and green. The RGB notation is convex in relation to what we perceive as colours. The two ends of RGB scale are `#000000` (black) and `#FFFFFF` (white) and this is numerically consistent with our perception, from nothing to all colours together. Further, a slight change in one parameter implies a small visual change, as more or less reddish, bluish or greenish depending on which parameter. 
- Consider now the price dimension. 43,78€ is certainly a number but also a currency, thereby the price could be encoded in 2 dimensions. 
- Think of time dimension, it is a single dimension domain, where zero is the present, a positive value is projected in the future while the negative numbers are settled in the past. Its magnitude instead, can describe how far that point is from the current present. 

>_"The things have weight,mass, volume, size, time, shape, colour, position, texture, duration, density, smell, value,consistency, depth,boundaries, temperature, function, appearance, price, fate, age, significance.
>
>The things have no peace."_
>
>_—Arnaldo Antunes_

The main similarity with neural embeddings and CS is that both are differentiable and convex. At the same time, concepts incorporate symbolism with their multi-facets domains; they naturally enable [semantic algebra]({% post_url 2023-08-02-argumentation-recommendation-ecommerce-knowledge-graphs  %}) and computational problem solving. For example, consider the request: ‘show me a movie like Casablanca but scarier as Shining’ will consider the properties of Casablanca but with the `scary` domain similar to Shining. Put it in the frame of [logic programming]({{ site.baseurl }}/tags/#logic-programming), and you get it in a line of code.

## Cognitive affinity of cognitive spaces
CS has also some interesting psychological foundations in the regards of how people deal with inner knowledge representations and how they learn them. It has proven some validity in explaining some cognitive aspects, especially those involved in concept learning and understanding. It has been found out that when children have assimilated the meaning of a domain, it’s then easy to learn concepts that represent a flavoured materialisation of that domain. For example, once they know what the domain of ‘colour’ is, it’s easy to learn new concepts related to colour, such as ‘turquoise’. Grasping a new domain is a much more difficult step than adding new terms to an already established one. Conceptual domains are mental buckets where we place concepts based on how their properties fit into that domain and we don’t have to know how ‘turquoise’ is exactly encoded, we just need to think of it in comparison to other concepts as somewhere in the between of light blue and light green. Seems to be a provable trick we use on learning and it is justified by the principle of cognitive economy, for which our mental capabilities are limited and we favour simple and efficient ways to position new information.

I've written more about cognitive aspects in [meeting of minds]({% post_url 2022-11-15-meeting-of-minds %}). Take a look at there! 
