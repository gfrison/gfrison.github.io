---
title: "Rhetoric LLMs and Argumentation"
share: true
permalink: /2024/12/01/rhetoric-llms-argumentation
description: "How much are LLMs persuasive? Here a brief excursus on how automatic argumentation can benefit from LLMs linguistic skills"
header:
  og_image: /assets/images/argue-llm.webp
last_modified_at: 2024-12-01
tag:
  - argumentation
  - LLM
---
Since the advent of deep neural transformer architecture - a portfolio of techniques aim to correlate long distant semantic dependencies among words -  large language models (LLMs) have shown an unprecedented ability on a multitude of tasks. 
Of course, by initial purpose of the design, the main task is the mastery of human language. Argumentation is a linguistic exercise among two or more persons intended to convey some sort of belief from one participant to another. 
LLMs linguistic skills can be directed towards a better argumentation, focusing on rhetoric and persuasive means.

For more logic based argumentation, [here a deep dive]({% post_url 2024-12-01-defeasible-logic-automatic-argumentation %}) into the world of defesabile logic.
{: .notice--primary}

Adopting neural processes in argumentative tasks is not a fresh idea. Usually there have been initiatives for **argumentation mining** - extraction of latent parts (claim, warrant, baking, etc.) from  raw text - a kind of process similar to named entity recognition for example. Another task where deep learning have found applicability was **argument generation** which is reversal in the respect of mining. The generation phase transform a given argument's structure into coherent and fluent paraphrases. 

LLMs are quite effective in those tasks without particularly laborious setup. I'm referring to the capability of solving those tasks with minimal (few-shot learning) or even without (zero-shot learning) any sample on how to accomplish the required work.

Though LLMs have outstandingly raised the bar of _machine intelligence_ - whatever meaning could be pointed to the terminology - they are affected by the collateral effects of their own designing.  

The fundamental LLMs' feature is about predicting what comes next the last token (word or any symbol), the so-called **auto-regressive** property of generative models that consists of recursively iterate over what has been already generated. This enable the generation of fluent text and the answering to an incredible variety of questions. The next-token prediction lays on the scale a minimalist process (thanks to the transformer architecture) to an enormous amount of data. But auto-regression leads also to **hallucinations** - generated chunks not aligned with the reality. 

<figure style="width: 400px" class="align-center">
  <img src="/assets/images/argue-llm.webp" alt="Arguing LLM">
</figure>

Those fictitious answers are a collateral effect of the LLM design. Basically, a LLM _always_ hallucinates[^subbarao]. Sometimes the generated output is sound and valid, sometimes it is just a well-assembled bunch of fantasies. One way to mitigate generation of statements not adherent to our world is the adoption of the **chain of thought** (CoT), a technique that is also adopted for argumentation, as we see next.

### argumentation step by step
CoT is a technique used to encourage an LLM generating higher quality answers. As previously mentioned, an LLM generates a series of words (tokens) according to what submitted (or self-produced) and the corpus on which the model has been trained. If the request implies a concatenation of [reasoning steps not _explicitly_ expressed](https://community.sap.com/t5/artificial-intelligence-and-machine-learning/cracking-the-knowledge-code-hybrid-ai-for-matching-information-systems-and/m-p/13711568), the generative part will jump to the conclusions with the obvious risk to fail on sequencing a correct chain of reasoning, causing diversions and utterly wrong answers. 

This approach is remarkably similar to what we do every time we mentally assess a complex argument with several check-points. This problem is mitigated by instructing on explicitly tracking the logical path from premises to conclusion. The goal of CoT is to divide a problem in small chunks and facilitate a reasoning in smaller steps, where the effort to connect premises and conclusions is much lower than the original (bigger) argument.

### multiagent automated debate
The CoT is even more effective when it is augmented not by simply instructing the LLM to take care of the intermediate steps, but by arguing on each intermediate conclusion with a **multiagent debate**[^du]. What could be the opponent of an LLM agent? Of course, another LLM agent. This approach is inspired by the concept of _"The Society of Mind"_[^minsky] and the core idea is to have multiple LLMs independently generating arguments. The indipendent agents will critique and refine their responses based on the arguments and reasoning presented by the others, for converging eventually on a single, more accurate conclusion. 
<figure style="width: 400px" class="align-center">
  <img src="/assets/images/multi-debate.png" alt="Multi-debate LLM">
  <caption>Peformance gap in multi-debating agents (Du, Yilun, et at. 2023)</caption>
</figure>

The multiagent debate is mainly focused on unfolding a particular reasoning path while the focus of the task is _not_ on searching _alternative_ ways of ~~thinking of~~ assessing conclusions. Which is the point of the next paragraph.

### self-consistency and contrastive argumentation 
Consistency is a crucial aspect in argumentation and it is scrupulously controlled in logic frameworks like the [ones previously depicted]({% post_url 2024-12-01-defeasible-logic-automatic-argumentation %}). The probabilistic nature of LLMs and their intrinsic hallucinating mechanisms may often clash with this important value, and without specific mechanisms to promote self-consistency, LLMs may produce conflicting outputs. 

A variation of CoT that aims to boost (according to the authors) the performance of LLMs is a strategy that leverages the idea of generating multiple reasoning paths[^contrastive] and then a self-consistency check could identify contradictory claims, discarding them and selecting only conflict-free candidates, or it will attempt to solve them by inspecting additional steps. 

Self-consistency may be reinforced by a technique that help the LLM to discriminate good reasoning path from the bad ones. The **contrastive argumentation** works by providing sample of claims with good and bad conclusions with related explanations such as: if all dogs are mammals and all mammals have fur, does it follows that all dogs have fur? i.e.: $?has(dog,fur)$
$$\begin{eqnarray}
has(x,fur) &\Leftarrow& is(x,mammal)\\
is(x,mammal) &\leftarrow& is(x,dog)
\end{eqnarray}$$

- Correct Answer Explanation: _Yes_, since all dogs are mammals and all mammals have fur, it logically follows that all dogs must have fur.
- Incorrect Answer Explanation: _No_, some dogs might be hairless. (The incorrect explanation ignores the stated premises and introduces an exception not supported by the given information).

## automated rhetoric
So far, we have seen the building blocks of the logic behind argumentation, which is addressed by the initiatives in automated argumentation as discussed in previous sections. The advent of LLMs offers new opportunities on addressing communicative aspects such as rhetoric, and enhance argumentation within human language. 

Rhetoric is the study of effective speaking and writing for what it relates to persuasion. It requires understanding the distinction between the **content** and the **form** of communication. Rhetoricians emphasize their interdependence, highlighting the connection between language and meaning, argument and ornament, thought and its expression. This means linguistic forms are fundamental, not just to persuasion, but to thought itself. 

### _content_: topics of invention
The topics of invention (TI) are categories of thought that can be used to generate arguments. They are like lenses through which you can view the claim, the baking and even the evidence. They are used in combination to create a strong and persuasive argument. For example, you might start by defining the problem, then explore its **causes and effects**. You could then propose a solution, **compare** and **contrast** it to other solutions, make **analogies** with other arguments and cite expert **testimony** to support your claims. Finally, you could address potential objections and refute them using counterarguments[^elephant].

**Cause and Effect:** This involves exploring the causes of a problem or the effects of a proposed solution.
**Comparison and Contrast:** This involves comparing and contrasting different ideas, arguments, or proposals.
**Definition:** This involves defining key terms and concepts in the argument.
**Testimony:** This involves citing the opinions of experts or authorities to support your argument.
**Analogy:** This involves drawing comparisons between different things to illustrate a point.
**Generalization:** This involves making broad statements based on specific examples or evidence.
**Division and Partition:** This involves breaking down a complex issue into smaller, more manageable parts.
**Concession and Refutation:** This involves acknowledging the opposing side's arguments and then refuting them.

### _form_: figures of speech
It is subset of rhetorical devices that specifically use language in creative ways to convey meaning and evoke emotions. Rhetorical devices are techniques used to enhance communication and persuasion, and figures of speech are specific tools within this broader category. 
- **Simile**: A comparison using "like" or "as." _Example:_ "Her eyes are _like_ stars."
- **Metaphor**: A direct comparison without using "like" or "as." _Example:_ "He is a _lion_ in battle."
- **Personification**: Giving human qualities to non-human things. _Example:_ "The wind _whispered_ through the trees."
- **Hyperbole**: Exaggeration for emphasis.   _Example:_ "I'm so hungry, I could eat a horse."
- **Understatement**: Deliberately saying less than what is meant. _Example:_ "It's a bit chilly outside" (when it's freezing).
- **Oxymoron**: Combining contradictory terms. _Example:_ "Bittersweet," "jumbo shrimp"
- **Pun**: A play on words, often humorous. _Example:_ "I'm reading a book about anti-gravity. It's impossible to put down."

### how much are LLMs persuasive?
As one could expect from a training with a big slice of the entire human-written corpus, LLM can adopt the most effective and persuasive rhetoric strategies. Have ever opened a random prompt for a practical task? Usually they begin with _"You are a X and you are demanded to..."_ where _X_ could be a professional figure or any kind of specifically behavioral-driven agent that the LLM should impersonate, in order to run the task with a very specific taste. Not only the adaptability to different prompts, but also the control over persuasive strategies is totally mastered. For example, LLMs push forward on moral values - especially on negative moral bias. This could be due maybe to the fact that we as humans are more sensitive to negative outcomes rather the positive ones, and LLM may emphasize more on bad effects of a particular action or policy, appealing to the audience's desire to avoid causing harm.

LLM seems to use more complex grammatical and lexical sentences, probably because human readers may interpret the need for greater cognitive effort as an indicator of the argument's significance or substance, despite the common notion that that simpler, easier-to-process arguments are inherently more persuasive[^carrasco].

# Conclusion
This is a really brief introduction on how argumentation is an interdisciplinary subject that is tackled from many different angles and perspectives. It triggers interests of a very diverse set of domains such as philosophy, logic, symbolic and neural processing, but also cognitive sciences and social matters. Each field of research move forward its own contribution that cover some aspects of the vast panorama that argumentation involves. Though, it is important to summarize them and synthesize some converging points where different fields can contribute to each other. For example the integration of logic soundness and validity into rhetorical devices - for enabling a more precise dialectic process - is an open issue that roots into symbolic-neural dualism that might look very promising from the point of view of automated argumentation, but that it is still far from very effective applicability. 

***
[^subbarao]:Kambhampati, Subbarao, et al. "LLMs can't plan, but can help planning in LLM-modulo frameworks." _arXiv preprint arXiv:2402.01817_ (2024).
[^du]:Du, Yilun, et al. "Improving factuality and reasoning in language models through multiagent debate." _arXiv preprint arXiv:2305.14325_ (2023).
[^minsky]:M. Minsky. Society of mind. Simon and Schuster, 1988.
[^contrastive]: Chia, Yew Ken, et al. "Contrastive chain-of-thought prompting." _arXiv preprint arXiv:2311.09277_ (2023).
[^elephant]: Proceedings of the 4th Workshop on Figurative Language Processing (FLP), pages 45–52June 21, 2024 ©2024 Association for Computational Linguistics - The Elephant in the Room: Ten Challenges of Computational [Detection of Rhetorical Figures](https://aclanthology.org/2024.figlang-1.6.pdf).
[^carrasco]: Carrasco-Farre, Carlos. "Large Language Models are as persuasive as humans, but how? About the cognitive effort and moral-emotional language of LLM arguments." _arXiv preprint arXiv:2404.09329_ (2024).
