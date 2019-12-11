---
layout: posts
description: Bayesian conditional probability requires sharped interpretation skills but the implications of using it are valuable and sometimes counter-intuitive, exactly like paradoxes appear when the solution is revealed. I applied Bayes rules to a couple of problems that roam around probability theory, on which most people’s impulsive intuition, including mine, falls short.
title: Statistical paradoxes for sharping your Bayesian skills
published: false
permalink: /2019/statistical-puzzle-bayesian-conditioning
header:
  og_image: /assets/images/lets-make-deal.jpg
share: true
tags: [bayes, puzzle, statistic, bayesian]
---
<figure class="full">
    <img src="/assets/images/lets-make-deal.jpg">
</figure>
Sometimes despite apparently valid intuitions, it is easy to convey to wrong conclusions when the solution of the problem runs contrary to our expectations. This is the case of  paradoxes that are known to have valid arguments and without a deep insight, their solutions sound counter-intuitive and illogical.
I collected a couple of problems that circle around probability theory on which most people’s impulsive intuition, including mine, falls short. I want to demonstrate you that those puzzles don’t require great talent if we apply some basic rules. Bayesian conditional probability is not difficult, it is just counting. But to apply it you need to sharpen your interpretation skills, and this is hard. The implications of using Bayesian inference are valuable and sometimes  counter-intuitive, exactly like paradoxes appear when the solution is revealed.  

## Bertrand’s box paradox
A classic probability puzzle is the Bertrand’s box paradox and I prefer to narrate this slightly different version rather than the original one. Suppose I have 3 cards, the first card is white on both sides. The second card is white on one side and black on the other. The third has both sides black. Now I put one card on the table, and the visible side of this card is white. What is the probability that the other side is also white?
The tree cards have:
- White in both sides $$(ww)$$
- White on one side and black on the other $$(wb)$$
- Black on both sides  $$(bb)$$

The card on the table is white on the visible side.

This is a hard problem if we don’t solve those problems by applying cold conditional probability. The Bayesian rule tells us to leverage what we already know for refine your knowledge about we don’t know yet.

$$
 P(wish\; to\; know \;|\; known)
$$

In the case of the cards, we know how the different sides are distributed and we know the color of the proposed card. We wish to know the probability that the other side has the same color of the visible one.

$$
P(ww \;|\; w) = \frac{P(w \;|\; ww) \; P(ww)}{P(w)}
$$

P(w) = P(w | ww)P(ww) + P(w | wb)P(wb) + P(w | bb)P(bb)
P(w) = 1*1/3 + 1/2*1/3
$$
P(W) = P(WW)  \dot 1 + P(WB).5 + P(BB) * 0 = (1/3) + (1/3) 0.5 = 0.5

P(WW) = 1/3

P(W | WW) = 1

P(WW | W) =2/3

$$
For having the right intuition about that, the trick is to count sides not the cards themselves. There are two cards with at least one white side and only one with both white. But it is the sides and not the cards that matter.


## The Monty Hall Problem
Suppose you are on a game show, and you are given the choice of three doors. Behind one door is a car, behind the others, goats. You peek a door, say #1, and the host, who knows what’s behind the doors, opens another door, say #3, which has a goat. Then he says you “would you switch your current selection (#1) with t#2?”.
In your opinion, is it better to hold #1 or switch it to #2?

The question is based on a popular televised game show called Let’s Make a Deal conducted by the host Monty Hall. In September 1990, a popular columnist in the US gave an answer to this tricky problem an it created great furor. The question was posed by a reader that asked  what to choose. Hold on #1 or switch to #2? The answer, defended by the “world’s smartest woman” - as billed by the magazine - argued that the contestant should switch doors.
Over the following months, she received thousands of letters even from PhDs in mathematics and statistics. Most of them fervently disagree with her and one of them commented: “May I suggest that you obtain and refer to a standard textbook on probability before you try to answer a question of this type again?”. Critics stated that switching or not, have the same probability, so the decision to change door should be totally random. Let’s understand the mechanics of this problem under the lens of Bayes' rule.

When the contestant choose the doore, the odds are uncontestedly ⅓ for each door
P(Car1)=P(Car2)=P(Car3)=⅓

After Monty Hall open the #3, what is the chance to have the prize behind #1?
P(Car1 | #3) = P(#3 | Car1)* P(Car1) / P(#3)

But What is P(#3)?
Now it's getting interesting because this is the point where misconceptions act cruelly on our judgment. According to the columnist solutions, might be that the producers of the game read the contestant’s mind, otherwise how else could they put the car so that it is more likely to find it behind the door has been not choose?
The conductor is everything but fool on selecting #3 and we should take into account the rules of the game that tell us something that has not been observed. The conductor must choose the door that doesn’t cover the car and probabilities come conditional on this information:
P(#3) = P(#3 | C1) * P(C1) + P(#3 | C2) * P(C2) + P(#3 | C3) * P(C3)
P(#3) = 0.5 * ⅓ + 1 * ⅓ + 0 * 1/3 = ½

P(C1 | #3) = ½  * ⅓ * 2 = ⅓

It turns out that switching door, double the chances to win the car (⅓ vs ⅔).
Indeed, it is an eccentric case and it doesn’t involve telepathy nor any form of collusion with the game producers’ nor the car is running away from us. There is no causation between our first choice and the car, it sounds very unnatural that we should change door! The only one form of causation in this game is ascribable to the conductor, and his choice is determined by a couple of reasons: our initial door and the location of the car. Bayesian conditioning helps us to unfold it.
