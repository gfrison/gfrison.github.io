---
layout: post
description: Simple but effective way to extract relevant entities from user's queries and rank them against an product catalog and an ontology database.
title: Catalog Entity Extraction for Search
published: true
permlink: /2018/02/12/catalog-entity-extraction-search
comments: true
---

Keyword extraction from search queries is a fundamental aspect of conversational commerce. In this article I illustrate a simple but effective way to get relevant entities from user's utterances and rank them against an unstructured product catalog and an ontology database.

The primary purpose of a conversational application is to serve user demands, and when an user search in a e-commerce context, he is mostly
looking for products. There is one main distinction that characterize a query when it is performed in the website rather than
a messaging application. In the website, when users submit a query they already express their search intention, therefore
the terms are usually concise and descriptive. Conversely, when inquiring a Chatbot, users use more expressive forms
such as: `Could you suggest me pale ale beers and ice creams for my party?`.
While the intention is deducted by a classification task, relevant terms for search, are just a subset of the entire sentence.

[see full article](https://labs.hybris.com/2018/02/19/catalog-entity-extraction-search/)

Baseline approach for searching, would be to take all text as query, returning innumerable hits of everything even remotely relevant, providing little help for customers.
Another solution regards Named Entity Recognition, a class of algorithms  that seeks and classify entities, also by means of [neural networks](http://nlp.town/blog/ner-and-the-road-to-deep-learning/).

While machine learning techniques can reach high levels of accuracy, they might not be the favorite solution for production usage. They require hardly available training data, and  what will work for a specific product segment will not work for another. That is why the following approach could offer the flexibility demanded for real use case scenarios. It is easily plugged in any e-commerce without any particular adaptation.
This method is very simple. I don't consider structured product features, rather I take in account only simple and concise information that is obtained just by the product name.

>I want to extract the features that might affect the Chatbot's answer, based on the _quality_ of the search query.

It is very plausible to give straights result when the query is really pertinent to returned item list, as well as informing the users whenever the query terms do not match _exactly_ with what we can offer them, or even when the query terms demand for something we cannot provide.
The desirable features are:

- _Query entities selection_. When in the query there are more than one entity cluster, the conversational agent will be able to detect it and to ask the user to choose with entity will search first. For example, in query above there 2 terms: _pale ale beers_ and _ice creams_. For example the Chatbot could answer:
> _Are you searching for **pale ale beer** or **ice cream**?_

- _Partial term matching_. The user is prompted that the exact criteria does not match, but a less ranking one is provided. _Pale ale beers_ is not in catalog, but _ale beer_ yes.
> _We don't have **pale ale beer**, but just **ale beer**. These are our suggestions:..._

- _Term out of market segment_. Prompt the user that the inquired item is not sold by this shopping website.
> _We do not sell **insurance**, sorry._

## Indexing and searching tasks

The two fundamental tasks in information retrieval are the one for collecting and storing product informations, and on the other side, the task for obtaining them. Index phase collects features from the products' name, while the search phase extracts matches from text query. Both tasks manipulate text in the following ways: _Entities clusterization_, _Part Of Speech filtering_, _Lemmatization_.


### Entities clusterization

The objective is to isolate every entity within their search space (or _features_) that refines the query.
For doing that, I use _stop words_ (irrelevant terms such as articles, prepositions, adverbs) and some punctuations (full stops, semicolon, exclamation and question marks) to split the entire sentence into
_word clusters_

###### <span style="color:darkcyan">could you suggest</span> *me* <span style="color:chocolate">pale ale beers</span> *and* <span style="color:green">ice creams</span> *for* <span style="color:darkviolet">my party</span>

This rainbowed sentence assume *me, and, for* as stop words for tokenizing the possible entities clusters.

### Part Of Speech filtering

Clusters previously obtained are filter by their Part Of Speech (POS) classification. The POS tagging assigns to each word their definition as noun, verb, adjective, adverb. I explicitly exclude verbs, adverbs and pronouns. This is why *could you suggest* is excluded since it is entirely formed by ignored words. The output is represented as:

~~could you suggest~~ / pale ale beers / ice creams / party

### Lemmatization

Lemmatization refers to the process of returning the root form of inflected words, in order to facilitate the analysis and the search of those terms. For example, _"Finds"_ and _"found"_ are grouped together as _"find"_. In this way, cluster entities are turned into:

pale ale beer~~(s)~~ / ice cream~~(s)~~ / party

## Catalog indexing

Text manipulation, as above described, occurs both for storing the catalog data and for querying.

In the indexing phase, when all catalog is scanned, parsed and tokenized, all particles will be stored into a _Set_. A _Set_ is a collection of distinct items. For efficiently storing the presence of a particular cluster, bloom filters play a fundamental role.

### Bloom Filters

How to check if a n-gram is present in the product list? Bloom filters solve the problem on storing large _Set_ in a fixed and pre-defined sized vector.
By the algorithm, an element is converted in some numeric values (_h_) and  set **true** in a bit vector, at the _h_ position.
> Bloom filters allow to compress a large amount of source data, negotiating a grade of uncertainty.

How could be validated the presence of the element in the bit array? Just checking if the vector is true/false in the _h_ position. That gives the certainty whether the element is _not_ present, or, whether vector checking is positive, a determined _confidence degree_ that such element is present. The _true positive_ probability depends on the vector length and the number of hashes.

## Search

### N-gram generation

An n-gram is a contiguous sequence of _n_ words. [I generate all possible combination of n-grams](https://gist.github.com/gfrison/3e130efeb0f17c7da59d78b520c34e96)  out of word clusters. The emphasized words are the result of this generation:

| Beer | Ice cream | Party |
| ---  | ---   | ---   |
| pale ale beer | ice cream | party |
| pale ale | ice | |
| ale beer | cream | |
| pale  | | |
| ale  | | |
| beer  | | |

Once the n-grams are generated, it is fast to check if one of them is present by inquiring the catalog bloom filter. For each entity cluster, we can check n-grams starting from the longest, in order to prioritize what exactly the user wants. We want to know also if the exact entity cluster is _not_ present but only an its sub-gram.
Moreover, we need to deal with such queries that asks products or services not offered by the given catalog market segment.


### Ontology database

How can we check whether in the query there are valid terms but they are not treated by us? [ConceptNet](http://conceptnet.io) could be the answer. For this purpose more than **400K** terms have been collected among several categories and indexed as the catalog terms in a separate database.


## Conclusion
At the end of this process the final output will look like to this:
```yaml
entity clusters:
  term: pale ale beer
  catalog: false
  subterms:
    term: ale beer
    catalog: true

  term: ice cream
  catalog: true

  term: party
  catalog: false
```
I have described a simple way for extracting query terms from a raw sentence. This approach provides useful information that could be managed by an conversational engine for corroborating search results with meaningful answers.
On the other hand, this model doesn't handle with misspellings, which represent alone about 15% of online search failures. This technique doesn't deal with relatedness matching, or semantic matching. That means we can't satisfy the search with relevant and pertinent results whenever customers use different terms from those in the website. I have already solved this problem by means of neural networks, and I will describe it in another article.

**Acknowledgment** Thank you [Sidi](https://github.com/elaatifi) for the contribution.
