---
layout: post
description: A novel approach for improving conversational fluency in Chatbots by employing neural networks based on convolutions and long-short memory units.
title: Semantic Search by Word Embeddings
published: false
permlink: /2018/05/16/semantic-search-by-word-embeddings
image: /assets/semantic-search-word-embeddings.png
---
![Semantic Search]({{ site.url }}/assets/semantic-search-wines.png)
Catalog search is one of the most important factor to the success of e-commerce sites and while marketing strategies drive users to what you expect them to do, accurate and relevant search is critical to successful conversion.  
When users are looking for specific products that are not in catalog, they might be willing to accept suggestions with similar characteristics. The following approach aims to reduce user frustration, by presenting products somehow related to what they are actually looking for. User get the option to select similar products and finalize the purchase rather than not having this option at all.

Search engines help to find relevant matches against a query according to various information retrieval algorithms. Those systems find occurrences of a term and classify documents into topics, but regardless their effectiveness, they are unequivocally related to the provided catalog and vocabulary. Therefore, it is not possible to retrieve products  by searching terms that are not already present in the inventory.

Concept matching (a subdomain of semantic search) refers to the quality of retrieved instances based on meaning, instead of lexicographic, similarity. Associating terms by an acceptable grade of relatedness, pivots around those key points:
1. Acquire the knowledge where it is possible to extract semantic relations.
2. Elaborate the data in order to calculate word relatedness.  

The elaboration applied to the data for obtaining our demanded features is called word embedding.

Word embedding is a very popular term undoubtedly because of the contribution of the deep learning community. It is associate to the research of distributional semantics, the branch of studies that develops theories and methods for elaborating semantic similarities between words based on their distributional properties.
> "a word is characterized by the company it keeps".  cit *R. Firth*

Algorithms like skip-gram and cbow train models to predict words as they sequentially appears in a given text corpora.  as result the models associate a word with a vector representation with the peculiarity that similar word show highest cosine similarity. Cosine distance measure the cosine of the angle between vectors, thus detecting the relatedness between two words.

## Concept Matching Algorithm

![Concept Search Diagram]({{ site.url }}/assets/semantic-search-word-embeddings.png)

<small>
**Algorithm** *retrieve_alternatives* is

<small>
&nbsp;&nbsp;**Input**: unrecognized term *query*, word vectors *word2vec*

<small>
&nbsp;&nbsp;**Output**: list of products and ranking

<small>
&nbsp;&nbsp;*Query_embeddings* ← get similarities of *query* from *word2vec*

<small>
&nbsp;&nbsp;*Results* ← empty

<small>
&nbsp;&nbsp;**For each** *w* in *query_embeddings*:

<small>
&nbsp;&nbsp;&nbsp;&nbsp;*result* ←**search by** *w*

<small>
&nbsp;&nbsp;&nbsp;&nbsp;*Result*.ranking ←result.ranking * w.score

<small>
&nbsp;&nbsp;&nbsp;&nbsp;**append** result to *results*

<small>
&nbsp;&nbsp;**Return** *results* **sort by** ranking
