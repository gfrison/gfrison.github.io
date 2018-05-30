---
layout: post
description: A novel approach for improving conversational fluency in Chatbots by employing neural networks based on convolutions and long-short memory units.
title: Concept Search by Word Embeddings
published: false
permlink: /2018/05/16/concept-search-by-word-embeddings
image: /assets/concept-search-word-embeddings.png
---
![Semantic Search]({{ site.url }}/assets/semantic-search-wines.png)
Catalog search is one of the most important factor to the success of e-commerce sites and accurate and relevant results are critical to successful conversion.  
The central hypothesis behind this proposal is that a user might be willing to accept alternative products with similar characteristics to what they are looking for. The following approach I illustrate from now on, aims to reduce user frustration by presenting products similar or related, when searched items are not available in catalog. The user get the option to select similar products and finalize the purchase rather than not having this option at all.

Search engines help to find relevant matches against a query according to various information retrieval algorithms. Those systems find occurrences of a term and classify documents into topics, but regardless their effectiveness, they are unequivocally related to the provided catalog and vocabulary. Therefore, it is not possible to retrieve products by searching terms that are not already present in the inventory.

Concept matching (a sub-domain of semantic search) refers to the quality of retrieved instances based on significance, instead of lexicographic, similarity. Associating terms by an acceptable grade of relatedness, pivots around those key points:
1. Acquire the knowledge where it is possible to extract semantic relations.
2. Elaborate the data in order to calculate word relatedness.  

The elaboration applied to the data for obtaining our demanded features is called word embedding.

Word embedding is a very popular term undoubtedly because of the contribution of the deep learning community. It is associate to the research of distributional semantics, the branch of studies for elaborating semantic similarities between words based on their distributional properties.
> "a word is characterized by the company it keeps".  cit *R. Firth*

Algorithms (like the well-known skip-gram, cbow, glove) train models to predict words as they sequentially appears in a given text corpora.  As result the models associate a word with a vector representation with the peculiarity that similar word show highest cosine similarity. Cosine distance measure the cosine of the angle between vectors, thus detecting the relatedness between two words.

## Concept Matching Algorithm

![Concept Search Diagram]({{ site.url }}/assets/semantic-search-word-embeddings.png)

In the example above the user submit the unknown search query _Chardonnay_ which has some similar terms retrieved in the word embeddings. Some of them exist in catalog and they are presented to the user sorted by the product of embedding similarity and search score.

<small>
**algorithm** *retrieve_alternatives* **is**

<small>
&nbsp;&nbsp;**input**: unrecognized term *query*, word vectors *embeddings*

<small>
&nbsp;&nbsp;**output**: ordered list of products and ranking

<small>
&nbsp;&nbsp;*query_embeddings* ← get similarities of *query* from *embeddings*

<small>
&nbsp;&nbsp;*results* ← empty

<small>
&nbsp;&nbsp;**for each** *w* in *query_embeddings*:

<small>
&nbsp;&nbsp;&nbsp;&nbsp;*result* ←**search by** *w*

<small>
&nbsp;&nbsp;&nbsp;&nbsp;*result*.ranking ←result.ranking * w.score

<small>
&nbsp;&nbsp;&nbsp;&nbsp;**append** result to *results*

<small>
&nbsp;&nbsp;**return** *results* **sort by** ranking

## Topic-specific Embeddings

Word embeddings are obtained by elaborating a huge quantity of text, namely _corpus_ or _corpora_. There are available several large and structured set of texts for creating word embeddings: Google News corpus, Wikipedia, and so on, as well as word vectors trained against those corpora.
Since the quality of word embeddings reflects the corpus from which it has been generated, I purposely created a topic-specific corpora specialized in food, by scanning more than 600 food blogs and collecting roughly 40 Mb of prepared text. The amount of text is risible in comparison with Google News but nonetheless it is enough for the purposes of computing similarity in the small range of catalog queries. The preparation include the remotion of everything but words, case conversion and sentence tokenization. I choosed [fastText](https://fasttext.cc/) for elaborating text representations, it uses subword information to build vectors for unknown words and as the name might suggest, it is really fast.

This solution has been filed as _"System, computer-implemented method and computer program product for information retrieval"_ at the European patent office. It is applicable to many different domains, like in clothing, automobile, electronics retail, just by getting the proper specialized corpora from which word similarity can be inferred.
