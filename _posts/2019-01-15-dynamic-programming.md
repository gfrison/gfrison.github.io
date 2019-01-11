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

The term _dynamic programming_ has a curious origin, when Richard Bellman late in the 1940s, were seeking for a viral definition of his method.
Likewise in modern marketing campaigns, names and terms are carefully selected and Bellman was not unfamiliar with attention teasing.
At that time, his boss was apparently not very inclined on science and in particular on mathematical research, precisely the activities that Bellman was deep into when he formulate his famous [equation](https://en.wikipedia.org/wiki/Bellman_equation), and coincidentally the fame of Dantzig's _linear programming_, induced Bellman to coin the definition that combine the multi-staging and time-varying of _'dynamic'_ with the objective functions optimization of _'programming'_.

The backbone idea in _DP_ is about splitting a problem till its atomic parts are identified, then reducing those parts - from the atomic till the aggregated ones - with a function value. It is basically the core concept of _recursion_. Such sub-problems are repeated in the problem as whole, and their values are evaluated all over again unless a caching mechanism (_memoization_) prevent such inefficiency. Considering for example the Fibonacci algorithm:

<center><img title="Fibonacci" src="{{ site.url }}/assets/fib-tree.png"/></center>

The node `fib(2)` appear twice in the tree, which shows therefore an _overlapping_ structure. Algorithms inherently recursive but _not_ overlapping are: _binary tree search_, _merge_ and _quick sort_, for example. They don't manifest the property of having a sub-problem evaluated more then once, hence they can't benefit of Dynamic Programming.
