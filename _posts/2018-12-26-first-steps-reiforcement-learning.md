---
layout: posts
description: Reinforcement learning process rely solely on the evaluation of its actions. It is the carrot and stock metaphor adapted for cold algorithms, instead of living donkeys. This is the first of a short walk-through on building learning agents.  
title: First steps in Reinforcement Learning
published: true
header:
  og_image: /assets/rl-survey-giancarlo-frison.png
comments: true
tags:
mathjax: true
---

Reinforcement learning covers a family of algorithms with the purpose of maximize a cumulative reward that an _agent_ can obtain from an _environment_.
It seems like [training crows](http://www.thecrowbox.com/) to collect cigarette butts in exchange for peanuts, or paraphrasing an old say, the carrot and stick metaphor for cold algorithms instead of living donkeys.

The _agent_ and _environment_ have not been emphasized vainly, they represent more concretely a vacuum cleaner
sweeping your flat, an A/B testing engine for commerce or a driveless car in a crossroad. If you have heard about latest advances in the field, you would have came across of Deepmind's [AlphaZero](https://deepmind.com/blog/alphazero-shedding-new-light-grand-games-chess-shogi-and-go/), by which it is possible, with an affordable set of hardware, to build from scratch the [best chess player](https://www.chess.com/news/view/updated-alphazero-crushes-stockfish-in-new-1-000-game-match) in the world in just 4 hours.

<iframe src="//www.slideshare.net/slideshow/embed_code/key/vpPWyEJugR0VVp" width="795" height="485" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="border:1px solid #CCC; border-width:1px; margin-bottom:5px; max-width: 100%;" allowfullscreen> </iframe>

>“Difficulties strengthen the mind, as labor does the body.”
― _Lucius Annaeus Seneca_

Researches don't lack of challenges in this field. The most important one comes from the intrinsic nature of RL learning process, which rely solely on the evaluations of its actions. Improvements are driven by just one signal, the reward.

>Rewards are often _very sparse_.

They come after hundred or thousands of steps, exponentially increasing the combination of actions the agent must explore for finding a barely better sequence among of them. If that does not seem arduous, consider also the _non-stationary_ nature of some environments, particularly common in dynamic scenarios where the learning phase resemble pursuing a moving target.

### Non-stationary environments
Non-stationary means that the return of an action, performed in the precisely exact conditions of a past experience, might be different from what expected. This is particularly intuitive in the case of multi-agent scenario ([MARL](http://www.dcsc.tudelft.nl/~bdeschutter/pub/rep/10_003.pdf)), in which the agent plays with one or more other agents that are learning too.

### What distinguish RL from other optimization methods
While RL helps on creating agents that can autonomously take decisions, other algorithms attain this goal too, but with different working principles.

_Supervised learning_ could be easily distinguished because it is trained with correct samples instead of vague rewards, making it simpler for [loss functions](https://towardsdatascience.com/common-loss-functions-in-machine-learning-46af0ffc4d23) to converge into an useful solution.

_Mathematical optimization_ differs from RL in a more subtle way. Likewise RL, the [simplex algorithm](https://en.wikipedia.org/wiki/Simplex_algorithm) find solutions by iterating on optimization loops, but it works only on perfect information problems.
When considering what it exactly means, let's look at the [knapsack](https://en.wikipedia.org/wiki/Knapsack_problem) or the [traveling salesman](https://en.wikipedia.org/wiki/Travelling_salesman_problem) problems.
>All the necessary informations for elaborating the optimal solution are readily there.

In other words, there is no exploration.
Conversely, an RL agent is like a probe on an heavenly body, where the assumptions on the environment are nearly absent. The agent needs to figure out autonomously the good and the bad actions only by the feedback from environment, the so called _model free_ learning approach.

_Genetic Programming_ is an evolutionary optimization method that share most of the characteristics of RL, it is iterative, suitable for imperfect information systems due to its stochastic explorative nature. Even the terminology is somehow related. For example, what is the objective function, in _GP_ is named _fitness function_, just a polyseme.
What differentiate it from RL is it's evolutionary method, I briefly explained in [this post]({% post_url 2018-06-28-first-steps-evolutionary %}).
