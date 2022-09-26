---
layout: single
published: true
title: "Question Answering on Knowledge Graphs for a Better Consumer Search"
share: true
header:
  og_image: /assets/images/search-kg.jpg
tag:
  - logic programming
  - Search
  - Knowledge Graph
---
The search box is an ubiquitous widget present in almost all websites, that’s because free text searching is one of the most intuitive actions users can do online. System’s duty is to return results that are pertinent to the inquiry, while discarding what it is not. If consumers are satisfied with what you present to them, there is a good chance that they will move forward to the next step. The importance of having a good search mechanism in eCommerce is all there. It may be the architect of your eStore’s success, or the main cause of its failure.

![](/assets/images/search-kg.jpg)

Search text usually consists of one or more keywords in sequence that distinguish the item with increasing accuracy, and it won’t be a surprise that utterances could be in an extremely simple form. Probably similar to the structure of our old ancestors’ early attempts [me Tarzan you Jane]({% link _posts/2018-06-13-basic-principles-language.md %}), search requests might lack prepositions, adverbs, and in general they may not exhibit any kind of grammar. In an electronic eStore, a text such as **mem disk 500gb flash drive** would be then interpreted as a series of constraints that must be all simultaneously satisfied: **mem disk** AND **500gb** AND **flash drive**. As result, it would then prompt us a list of external USB memory storage devices with 500Gb of capacity.

What if the above utterance will include different modalities on how to discriminate attributes? Take the sample **mem disk flash drive with more than 500gb**. I expect to see all USB sticks with at least 500Gb. Unfortunately, this is not the case. The result does not change from the previous query and we can conclude the search engine can’t handle simple inequalities such as **more than** or **less than**. If the limitation is not worrying enough, please pause reading and answer the following quiz: What do you think the query **a Nikon cheaper than a canon EOS-1D** will show you as results? Yes, you will have a mixed list of Nikons and EOS-1Ds together, which is not, indeed, what we meant. I think that it’s time to evolve search engines and align them to the intricacies of natural language.

The reason why in the title is mentioned question answering is because of the shift of paradigm we want to push forward on this new branch of research. Have you ever heard of an IBM agent in Jeopardy! quiz show? We treat user utterances as carriers of semantics that are closer to our natural language rather than a mere search for products. While this approach can help to make better search engines, it can be applied to digital assistants of various nature: IT support, meeting planners, analytics. But for now, let’s focus on search items in eCommerce.

The set of tools developed for business process modelling are extremely helpful and they are paving the way to innovative ideas that share the founding principles:

- Compositionality: the meaning of a complex expression is determined by its constituent parts. Take for example the English language. There are 21 characters, they compose thousands of words, they compose an infinity of essays. We look at the constituent parts of logic edifices, then we assemble them to describe our business domains.
- Generalisation: to maximise ROI and minimise data sets and specialisation efforts we are referring always to classes of problems, not to the single instances.
- Interpretability: QA models are inspectable for auditing, modifiable via low-code tools and outcomes are justifiable. No black boxes in there.

Traditionally, the bulk of effort on setting up a proper search service is directed toward database indexation, corroborated by named entity recognition (NER) and information retrieval techniques. Moreover, QA systems present two faces, one dedicated to disentangling utterances’ logic, the other converts the semantic into an executable query that pick-up results from a given information system. The former is responsible to interpret what the user is texting, and it is invariant in the respect of the domain context, but dependent on the user language. The latter component, the one closer to databases, is specific for customer/domain of interest and data topology. This architecture allows great portability with customization effort reduced to the minimum.

This project does not come out of nowhere, it is rather the continuation of research on semantic technologies and symbolic reasoning applied to a fairly challenging problem. Honestly, I can’t imagine something more complex than language. As in any innovative endeavor we revise what we have done in the past and we try to improve from the foundations every time we face a new kind of problem, and this time, it won’t be different.

>     The world has the structure of the language, and the language has the form of mind

    – Eugenio Montale

I strongly believe that for attaining better solutions, problems should be tackled from several angles. If the symbolic approach provided by knowledge graphs and logic programming help to solve a variety of problems, a hybrid system – symbolic plus non-logic – can expand the reach of our technology. In the Prof Gärdenfors work **Geometry of Meaning** it is summarised more than 20 years of work in a fully comprehensive theory (philosophical, cognitive, neuroscientific) about Concept Spaces. They are computable geometrical representations with a lot of affinity with semantic algebra, but also with the interesting feature to be convex, which is a pillar of gradient-based algos. Basically, all modern machine learning.

Concept spaces describe a theory of meaning and how this theory is adhering to our cognitive model of learning, which I find extremely interesting. The human oriented basis of the theory is inspiring also for computer programs that attempt to solve complicated problems for us, like for example eCommerce search. This is one of the topics I will write more about, so if you are interested in that, stay tuned!