---
layout: post
description:
title: Dynamic Programming
published: false
image:
comments: true
tags:
mathjax: true
---

The term _dynamic programming_ has a curious origin.
When Richard Bellman late in the 1940s were seeking for a viral definition of his method, his boss was apparently not very inclined on science and in particular on mathematical research, precisely the activities that Bellman was deep into when he formulate his famous [equation](https://en.wikipedia.org/wiki/Bellman_equation).
Likewise in marketing campaigns, where names and terms are carefully selected by means of attention teasers, Bellman coined the definition that combines the multi-staging and time-varying of _'dynamic'_ with the objective functions optimization of _'programming'_, 
 Moreover, the fame of Dantzig's _linear programming_, coincidentally induced Bellman to coin the definition that combine the multi-staging and time-varying of

_DP_ describes problems that involve dynamic processes where ones need to find the best decision one after another. The backbone idea in _DP_ is about splitting a problem till its atomic parts are identified, then reducing those parts - in the [functional programming](https://en.wikipedia.org/wiki/Fold_(higher-order_function) sense - from the atomic till the aggregated ones, with a function value. It is basically the core concept of _recursion_. Such sub-problems are repeated in the problem as whole, and their values are evaluated all over again unless a caching mechanism (_memoization_) is displaced for preventing such inefficiency. Considering for example the Fibonacci algorithm:

<center><img title="Fibonacci" src="{{ site.url }}/assets/fib-tree.png"/></center>

The node `fib(2)` appear twice in the tree and that sub-problem value may be cached for efficiency. It shows an _overlapping_ structure, therefore it is eligible to be _DP_. Algorithms inherently recursive but _not_ overlapping are: _binary tree search_, _merge_ and _quick sort_, for example. They don't manifest the property of traversing smaller chunk of data more then once, hence they cannot join the Dynamic Programming family.
