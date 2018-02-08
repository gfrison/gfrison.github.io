---
layout: post
description: Simple and effective way to get relevant entities from user utterance and rank them by their relevance from unstructured catalog
title: Catalog Entity Extraction for Search
#image: /assets/minos-chart.png
published: false
permlink: /2018/02/06/catalog-entity-extraction-search
---

Keyword extraction from queries is a fundamental aspect of conversational commerce. In this article I illustrate
a simple but effective way to get relevant entities from users' utterances and rank them by their relevance and their
presence in unstructured product catalog.

The primary purpose of a conversational application is to serve user demands, and when an user search in a e-commerce context, he is mostly
looking for products. There are several distinctions that characterize a query when it is performed in the website text form rather than
a messaging application. In the website users when they submit a query to a search form they already express their search intention, therefore
the terms are quite concise and descriptive of what they are searching for. Conversely, when inquiring a chatbot, users use a more expressive form
such as,
- `Could you suggest me pale ale beers and ice creams for my party`
- `Can you give me some lactose free yogurt`
- `I'm looking for a car insurance`

While the intention is deducted by a classification task, the words relevant for the query are just a subset of the entire phrase.
Baseline approach would be to use all the text as query, returning innumerable hits of everything even remotely relevant and providing little help
for the customers. What instead I want to achieve is to determine:
- Distinct entities. For example, in (1) there 2 terms: _pale ale beers_ and _ice creams_
- Exact or partial query match. Determine if a query search exist in catalog as requested or only partially. _Lactose free yogurt_ is not in catalog, but just _yogurt_.
- Entities not in catalog.

## Entities clusterization

The object is to isolate every entity within their search space or 'features' that refine the query.
For doing that, I use _stop words_ (irrelevant terms such as articles, prepositions, adverbs) and some punctuations (full stops, semicolon, exclamation and question marks) to split the entire sentence into
_word clusters_

###### <span style="color:darkcyan">could you suggest</span> *me* <span style="color:chocolate">pale ale beers</span> *and* <span style="color:green">ice creams</span> *for* <span style="color:darkviolet">my party</span>

This rainbowed sentence assume *me, and, for* as stop words for tokenizing the possible entities clusters.

## Part Of Speech filtering

The clusters previously obtained are filter by their Part of Speech (POS) classification. The POS tagging assigns to each word their definition as noun, verb, adjective, adverb. I explicitly exclude verbs, adverbs and pronouns. The filtered clusters exclude *could you suggest* since it is entirely formed by ignored words, and they are represented as:

~~could you suggest~~ / pale ale beers / ice creams / party

## Lemmatization

Lemmatization refers to the process of returning the root form of inflected words, in order to facilitate the analysis and the information  retrieval of terms. For example, _"Finds"_ and _"found"_ are grouped toghether as _"find"_. In this way, the cluster entities are turned into:

pale ale beer~~(s)~~ / ice cream~~(s)~~ / party

## N-gram generation

An n-gram is a contiguous sequence of _n_ words. [I generate all possible combination of n-grams](https://gist.github.com/gfrison/3e130efeb0f17c7da59d78b520c34e96)  out of word clusters. The emphasized words are the result of this generation:

| Beer | Ice cream | Party |
| ---  | ---   | ---   |
| pale ale beer | ice cream | party |
| pale ale | ice | |
| ale beer | cream | |
| pale  | | |
| ale  | | |
| beer  | | | |

## Catalog indexing

Text manipulation, as above described, occurs both for storing the catalog data and for querying. As already mentioned, I don't consider structured product features, rather I take in account only simple and concise information that is obtained just by the product name.

In the indexing phase, when all catalog is scanned, parsed and tokenized, all n-grams will composed into a _Set_. A _Set_ is a collection of distinct items. For efficiently storing the presence of a particular n-gram, bloom filters play a fundamental role.

#### Bloom Filters

How to check if a n-gram is present in the product list? Bloom filters solve the problem on storing large _Set_ in a fixed and pre-defined sized vector.
By the algorithm, an element is converted in some numeric values (_h_) and  set **true** in a bit vector, at the _h_ position. How could be validated the presence of the element in the bit array? Just checking if the vector is true/false in the _h_ position. That gives the certainty whether the element is _not_ present, or, if vector checking is positive, the element presence with a determined _confidence degree_. The _true positive_ probability depends on the vector length and the number of hashes. This technique allows to compress a large amount of source data, negotiating a grade of uncertainty.

## Information retrieval

Once the n-grams are generated, it is fast to check if one of them is present in the products by inquiring the catalog bloom filter. For each entity cluster, we can check n-grams starting from the longest, in order to prioritize what exactly the user wants. We want to know also if the exact entity cluster is _not_ present but only an its sub-gram. For example we may write back:
> _We don't have **pale ale beer**, but just **ale beer**. These are our suggestions:..._

Moreover, we need to deal with such queries that asks products or services not offered by the given catalog market segment.

#### Ontology database

How can we check whether in the query there are valid terms but they are not treated by us? [ConceptNet](http://conceptnet.io) would be an answer. For this purpose more than **400K** terms have been collected among several categories and indexed as the catalog terms in a separate database.


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
I have described a simple way for extracting query terms from a raw sentence, that approach provide useful information that could be managed by an conversational engine for deal with following cases:
- Query entities selection. The user is asked to search one entity at time.
- Partially term matching. The user is prompted that the exact criteria doesn't match, but a less ranking one is provided.
- Terms out of scope. Point the user the online shop doesn't sell such products.

Conversely, this model doesn't handle with misspellings, which represent alone about 15% of online search failures. Another missing point is the term relatedness matching, or semantic matching that can satisfy the search with relevant and pertinent results whenever customers use different terms from those in the website. This is already solved by mean of neural networks, I will describe it in another article, soon.
