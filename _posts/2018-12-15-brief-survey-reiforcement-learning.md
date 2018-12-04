---
layout: post
description: Can we teach computers to write code? In ICML 2018 an workshop was dedicated on how machine intelligence and reasoning could be capable of creating software applications. Here a short summary of it and what I found interesting in the subject.
title: Reinforcement learning
published: false
image: /assets/dreamcoder.png
comments: true
tags:
mathjax: true
---

reinforcement learning covers a family of algorithms with the purpose of maximize a cumulative reward an _agent_ can experience interacting with an _environment_.
It is more like training a dog with a biscuit on complying desired tasks, or on paraphrasing an old say, it is like the carrot and stick metaphor transposed on
cold algorithms instead of living creatures. The _agent_ and _environment_ have not been emphasized vainly, but they represent more concretely a vacuum cleaner
sweeping your flat, an A/B testing engine for commerce or a driveless car in a crossroad.

>“Difficulties strengthen the mind, as labor does the body.”
― _Lucius Annaeus Seneca_

Researches don't lack of challenges and this particular field don't deny none of them. The most important challenge comes from the intrinsic nature of RL which is based solely on the evaluations of its actions, all the learning is driven by just one signal, the reward. Those prizes are often very sparse, they come after hundred or thousands of steps, dramatically increasing the combination of actions the agent must explore for finding the winning sequence of them. Another source of hassle is the _non-stationary_ nature of the environment where conditions change with time and the same action in the exactly same situation can bring different outcomes. Take for example a multi agent scenario, where all parts are learning and consequently changing and adapting their behavior dynamically.

RL helps on creating agents that can autonomously take decisions, this is a shared goal with other families of algorithms, but their working principles are different. Supervised learning could be easily distinguished because it ingests correct actions, but differences with mathematical optimization might appear more subtle.
When considering the [knapsack ](https://en.wikipedia.org/wiki/Knapsack_problem) or the [traveling salesman](https://en.wikipedia.org/wiki/Travelling_salesman_problem) problems all the necessary informations for elaborating the optimal solution are readily there i.e. there is no exploration. Conversely, an RL agent is like a probe on an heavenly body, the assumptions on the environment are nearly absent, and it has t figure out the good and the bad actions only by the feedback from environment, the so called _model free_ learning approach.

## Multi-armed Bandit
That's somewhat a hoary term for slot machines, well-known advised to split forever players from their own money.
The motivating story is about a gambler and a row of levels to pull. Given that the gambler doesn't know which bandit has the best payout, or even whether there is one bandit better than another, he has to _explore_ in first place, then he's faced with the dilemma of exploiting the knowledge he got so far, or continuing exploring alternatives for better outcomes in the future. He has somehow to develop a sequential strategy for pulling arms that balance this trade-off and maximize the cumulative payout. Let's see a more concretely what does it mean. Nominating $$Q_n$$ the return value after $$n$$ pulls, it is the average of earnings $$r$$ got during the iterations:

$$ Q_{n+1} = \frac{r_1 +r_2 + ... +R_{n}}{n} $$

A more scalable formula, equivalent to:

$$ Q_{n+1} = Q_n + \frac{1}{n}(R_n-Q_n)$$

Bring us to a general expression as a fundamental concept in following algorithms:

$$NewEstimate = OldEstimate + StepSize(Target - OldEstimate) $$

## Markov Decision Process
The armed bandit doesn't hold on complex tasks with multiple steps on reaching their goals. Despite in bandit, MDP formalizes the decision making (policy $$π$$)
in a sequence of steps, aggregated in episodes. MDP strives to find the optimal $$π$$ among all possible _trajectories_ the agent can follow.
<center><img title="GridWorld" src="{{ site.url }}/assets/gridworld.png"/></center>
Among many examples I may show for explaining what does MDP stands for, this very simple _GridWorld_ conveys the idea of an agent displaced in an 2D environment. The reward drives the agent to move toward the flagged corners regardless the position where the agent is located.  
How the agent could learn the optimal ways? I give you an hint, let's start from the end, the flagged boxes.
<center><img title="GridWorld" src="{{ site.url }}/assets/mdp-t1.png"/></center>
Clearly, the agent moves toward the most promising among the surrounding cells, once it has realized their value.  Let's keep going.
<center><img title="GridWorld" src="{{ site.url }}/assets/mdp-t2.png"/></center>
The grid starts to unveil the optimal trajectories by just going backward and evaluating what could be the best move, taking the cell with highest value.
A pattern is identifiable, something that programmers knows well, the _recursion_, and simplified as:
$$Q_t=r_t+argmax(Q_{t+1})  $$
