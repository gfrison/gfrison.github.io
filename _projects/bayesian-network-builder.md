---
title: "Bayesian Network Builder"
excerpt: "Tools such as BnB brings in the hands of developers the ability to infer probabilities in software applications without being professional statisticians."
header:
  image: /assets/images/bnb.png
  teaser: /assets/images/bnb.png
---
There are several paradigms in software development, functional programming, object oriented programming, and each of them is intended to solve a particular family of problems. Probabilistic programming is a paradigm that addresses statistical problems. [Bayesian Network Builder](https://github.com/sap/bayesian-network-builder) brings in the hands of software developers the ability to deal with probabilities without being statisticians.

I like to compare the [Bayes Rule](/tags#bayes) to car driving. While we are confident in our ability to drive forward in the direction in front of us, we are a little bit reluctant to drive backward. Let's watch it in action with the burglary example:

## Burglary Example

I'm at work, neighbour John calls to say my alarm is ringing, but neighbour Mary doesn't call. Sometimes the alarm is set off by minor earthquakes. Both burglary and earthquake are rather rare events.

The question we want to answer: _Is there really a burglar at home?_

Considering that:

- John always calls when he hears the alarm, but sometimes confuses the telephone ringing with the alarm.

- Mary likes rather loud music and sometimes misses the alarm.

![](https://raw.githubusercontent.com/SAP/bayesian-network-builder/master/docs/alarm.png)

{% highlight scala %}
import com.sap.bnb.bn._
import com.sap.bnb.dsl._

val g = graph {
 "burglar" <~ Flip(.001)
 "earthquake" <~ Flip(.002)
 "alarm" <~ ("burglar", "earthquake",
     (true, true) -> Flip(.95),
     (true, false) -> Flip(.94),
     (false, true) -> Flip(.29),
     (false, false) -> Flip(.001))
  "alarm" ~ (true -> Flip(.9), false -> Flip(.05)) ~> "JohnCalls"
  "alarm" ~ (true -> Flip(.7), false -> Flip(.01)) ~> "MaryCalls"
}
val burglar = g.evidences("JohnCalls" -> true, "MaryCalls" -> false)
      .solve("burglar").value.get
println(s"posterior: $burglar")
println("chances burglary: " + f"${burglar.chances(true) * 100}%2.1f%%")
{% endhighlight %}
```
posterior: true -> .005, false -> .995
chances burglary: 0,6%
```
