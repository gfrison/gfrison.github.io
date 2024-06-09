---
title: "Improve Answer Style in Conversational Commerce with Knowledge Graphs"
excerpt: "This approach provides useful information that could be managed by an conversational engine for corroborating search results with meaningful answers."
date: 2021-08-03 
permalink: /patents/collations-for-search
redirect_from:
- /2018/02/12/catalog-entity-extraction-search
last_modified_at: 2024-06-05
# header:
#   image: /assets/images/search-bar-man.jpg
---
| Id                                                             | Status | Submission Date | Activation Date   | Authorship      |
| -------------------------------------------------------------- |
| [US11080288B2](https://patents.google.com/patent/US11080288B2) | Active | {{ "2019-09-26" | date_to_string }} | {{ "2021-08-03" | date_to_string }} | 100% |

Chatbots are appointed to return informations from queries or accomplish specific tasks by means of natural language. Conversational commerce apps answer from a wide variety of query types, both very detailed or generic:

Have you `fruit juice`? \\
I search for `Voelkel apfel`. \\
Could you suggest me `pale ale beers` and `vanilla ice cream` for my party?
{: .text-center .notice}

Queries might affect word aggregation that hold a specific semantic:

Please, give me a `red bull`.
{: .text-center .notice}

_Red bull_ does not refer to colored bull, but instead - in the context of a conversational app for food groceries - it unequivocally points to the energy drink can.

It is plausible to return search results that are **really pertinent** to what the user is looking for, as well as informing the user whether the query terms **do not match exactly** with what the merchant can offer them, or even when the query terms demand for something that is **completely off-scope** .

Where informations for discriminating a specific word sequence (namely _collations_) from a meaning to another come from? Those might be obtained by elaborating on huge amount of text specific for the domain of the interested topic, in this case food. By applying statistical analysis on text it is possible to extract NER that could be successively used on extracting query terms from text statements.
But, specific-domain text archives represent a valuable resource which their acquisition could be costly and hard to obtain.

# Solution
The idea roll around three key process, collations gathering, product indexing and key terms extraction from user queries. The index process collects features from the products’ name, while during the search process, the relevant collations are matched out of the tex query.

## Collation gathering
In the era of Big Data, raw and structured representations of human knowledge are available in every higher pace than ever. Open databases such as DBPedia, Wiktionary, WordNet, OpenCyc could be grossly defined as generic commons sense of human knowledge. They are aggregated in ConceptNet.io, a structured ontology database, a semantic network. Collations and words are linked throughout a dense network of relationships such as: ”is part of”, ”is capable of”, ”is a type of”. relations and types could be easily filtered from obtaining the corpus of terms needed for a specific domain case, in our example, food. Collations might be found in those databases instead of elaborating big corpora that might be not available or very difficult to elaborate.

## Data extraction
Once the relevant collations are acquired, it’s turn to describe how online queries (like in the example at the beginning) could be elaborated in order to filter out the key terms we are interested on.
I individuated some desirable features the solution should provide:
* _Query entities selection_. When in the query there are more than one entity cluster, the conversational agent will be able to detect it and to ask the user to choose with entity will search first. For example: _give me a `red bull` and a `coke`_
* _Partial term matching_. The user is prompted that the exact criteria does not match, but a less ranking one is provided. for example in _give me `vanilla ice cream`_ the specific `vanilla ice cream` is not available but `ice cream` it is.
* _Terms off scope_. Prompt the user that the inquired item is not for sale. for example: _I’m looking for an `insurance`_.

The terms extraction from the product catalog and the user text query share the same following proce- dures described below. They are  Lemmatization, N-gram factorization.

## Lemmatization
Lemmatization procedure returns the root for of the inflected word. For example runs and running are pointing to the same root _run_.

## Indexing product catalog
During the product’s name parsing from the catalog, all particles are parsed, tokenized and finally stored in a _in-memory Set_ (bloom filters).

## N-Grams extraction from search query
An n-gram is a contiguous sequence of n words. In the above example could you suggest me vanilla ice cream for my party the collation vanilla ice cream will be exploded as: `vanilla ice cream`, `ice cream`, `vanilla ice`, `ice` , `cream`.

The system will weight the filtered items according to their length: longer first. More consecutive collations terms’ are detected, better the search output will be. therefore, at the end the search output will look like this:

```
entity clusters :
	term : vanilla ice cream
	catalog : false 
	subterms :
		term: ice cream 
		catalog : true
	term : party
	catalog : false
```


