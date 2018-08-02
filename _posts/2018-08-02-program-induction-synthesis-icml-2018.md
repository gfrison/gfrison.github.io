---
layout: post
description: Can we teach computers to write code? In the ICML 2018 an workshop was dedicated on how machine intelligence and reasoning could be capable on creating software applications. Here a short summary of it and what I found interesting in the subject.
title: Program Induction and Synthesis on ICML 2018
published: true
permlink: /2018/08/02/program-induction-synthesis-icml-2018
image: /assets/pinduction-schema.png
comments: true
tags: program induction, icml, machine learning
---

![](https://s3-ap-south-1.amazonaws.com/av-blog-media/wp-content/uploads/2018/06/images.jpeg)
The International Conference on Machine Learning [ICML](https://icml.cc) took place this year in Europe,
in the beautiful city of Stockholm from 10th to 15th of July.
This is one of the two premiere conferences (within NIPS) on Artificial Intelligence research, and the numbers indicate the magnitude of the event: 612 accepted papers out of 2473 submissions, 9 tutorial and 67 workshop sessions on the latest advances in all disciplines of machine learning. One of the most intriguing workshop was about machine intelligence capable of writing software code for complex procedural behavior.

## Program Induction

Can we teach computers to write code? This is the question that brings out an entire branch of research specialized in program synthesis. Programming is a demanding task that requires extensive knowledge, experience and not a frivolous degree of creativity. Despite the premises that discourage any form of automation, machine learning can reshape the way software is developed. That could be seen as a giant step comparable to the transition of punch cards in the _'60s_ in favor of magnetic tapes. New programming systems could enable non-programmers to produce correct, cheap, safe and efficient software, opening new automation horizons even for not yet foreseeable purposes.

Curious on what does it feel like? Try to describe a problem in plain English and translate it in a routine in your favorite programming language. Let's have a look on [this sample](https://arxiv.org/abs/1807.03168):
> You are given a number var0. You have to set var2 to 2. If var0-2 is divisible by 3 you have to set var1 to 1,
otherwise you have to set var1 to zero. For each var3 between 1 and var0-1, if var2 is less than var0 you have to, add var3*3+2 to var2, if var0-var2 is greater than or equal to zero and var0-var2 is divisible by 3 add 1 to var1;
otherwise you have to break from the enclosing loop. You have to return var1.

As you might have noticed, this is a detailed description of an algorithm. Natural language lacks of precise semantics necessary for describing concepts with mathematic granularity, this is why the generated code looks less amusing than the original _(natural)_ code:
{% gist a4e43b1d6833af9c94b00ab97645f9ab %}


Likewise ML tasks detect patterns, a program synthesis task aims to solve problems that might be identified as a composition of basic programming primitives, such as conditional operators, loop controls and even [recursions](https://arxiv.org/abs/1704.06611) and used for generating specific program
[trees]({% post_url 2018-06-28-first-steps-evolutionary %}).

<center><img title="Program Induction" src="{{ site.url }}/assets/pinduction-schema.png"/></center>

Generating code is a extremely challenging problem and the output's goodness is still very limited by using existing approaches.
One discrepancy in the flow above settle in the different nature of the encoded solution (generally neural networks) and generated code.
Neural networks lay in the differentiable realm (gradient-based training) while source code generation belongs to the discrete parish, because of its rigorous grammar and intolerance to typos.
If you want to get hands dirty on it, it is now available a [public dataset](https://near.ai/research/naps/) for training your system on software programming.

An introductory talk on this matter was hold by [Joshua Tenenbaum](https://www.csail.mit.edu/person/joshua-tenenbaum), professor at MIT and contributor of [Bayesian methods](https://www.researchgate.net/publication/2463513_A_Bayesian_Framework_for_Concept_Learning) for computational learning. He is specialized in cognitive sciences and and he strives to grasp the fundamental mechanics to human learning, in order to ultimately transfer them to computer programs. I find it useful to describe the Bayesian approach as a generic framework for every-day life decisions, where possible solutions are weighted according to our observations and past experiences flavored by doses of uncertainty.

[![DreamCoder]({{ site.url }}/assets/dreamcoder.png)](http://www.youtube.com/watch?v=RB78vRUO6X8?t=3389)

The [DreamCoder](https://uclmr.github.io/nampi/extended_abstracts/ellis.pdf) (Ellis 2018) rumbles on the idea of creating a repository of simple problems (and their paired solutions) for being reused on solving more complex ones. Even though the full paper is so far not publicly available, it seems inspired by the ways programmers organize their work: building shared subroutines that can be composed to develop more complex procedures. Instead of solving all problems from scratch, it tries to think flexibly and brings what it has already learned.

### Program synthesis using example

What I guess would be the most promising and attainable utilization of program induction is coding repairing and migration. Raise an hand 🙋 who hate repetitive, error-prone, edits due to general refactoring or library upgrading. Those tasks occurs during software evolution and they can be done only manually, since they are beyond the capabilities of IDEs. [REFAZER](http://www.dsc.ufcg.edu.br/~spg/refazer/) (rebuild in Portuguese) attempts to classify code transformations by large set of examples. It has been proven to be correct on *84%* of the transformations, which is not bad at all. The research has been published and it is available [here](https://people.eecs.berkeley.edu/~bjoern/papers/rolim-refazer-icse2017.pdf) (Rolim et.al; ICSE 2017). The technique for synthesizing programs from examples quotes:
>Each rewrite rule matches some subtrees of the given AST and outputs modified
versions of these subtrees. Additionally, we specify constraints
for our DSL operators based on the input-output examples to
reduce the search space of transformations, allowing [PROSE](https://microsoft.github.io/prose/) to
efficiently synthesize them.  

 There are large and open repository for such dataset, take for example Github as widely known repository for open-sourced projects, so uniquely for this case, the training data availability should not be a blocking issue.
