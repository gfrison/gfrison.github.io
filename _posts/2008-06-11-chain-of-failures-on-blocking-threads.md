---
permalink: /2008/06/11/chain-of-failures-on-blocking-threads
layout: single
title: Chain of failures on blocking threads
published: true
permlink: /2008/06/11/chain-of-failures-on-blocking-threads/
comments: true
image: /assets/chain-failure-effect.jpg
tags: callable, concurrent, decoupling, executorservice, failure, java, openfire, scheduledthreadpoolexecutor, service, SOA, timeout
---
![]({{ site.url }}/assets/chain-failure-effect.jpg)
I came back to Milano little time ago and I’ve bumped into an API implementation in this new job. This will be a library that aims to interact with a remote application through a simple text-based protocol.
The typical process is a sequence of authorization – session initialization – commands processing – session disposing each of which enclosed in atomic request/respose interaction. The simplest and most immediate approach provides to write the protocol stubs, and manage them through simple methods that elaborate such commands at low level handling tcp sockets and the client/server handshaking with synchronous calls.
Sometimes the simplest is the best way, but not this time, especially within multi layer structured systems, where every component depends on many others, and any of those can fail.
This task rings as an alarm bell to me due to a recent project that looked like this one, and I can still remember the effects of hangs and missed responses in a SOA context; fortunately the event happened during a load test:
>The application was a client interacting with openfire through XMPP. The investigation [uncovered a bug](http://www.igniterealtime.org/community/thread/30088?tstart=0) that caused a dead lock in a connection pool in certain conditions, the consequences were easily predictable as the fast resource exhaustion, causing soon an application break down. The application server was over but also the client side was unrecoverable since the unresilient application’s architecture didn’t foresee hang requests.

What is unacceptable is the chain of failures that a problem like this can disseminate along the process path, what about combined systems where one side does not expect the other side to hang off if it stops responding?
Domino is a pleasant show, you watch all pieces tracing doodles during their falls, it’s funny but only when it doesn’t look like your system when it works.

## Don’t play domino, be skeptical (and use concurrent package)
Blocking threads may happen every time you attempt to get resources out of a connection pool, deal with caches or registred objects, or make calls to external systems as this unfortunate experience above. I mean to be distrustful of each component you inquiry decoupling systems as necessary as to skirt the failure propagation. If your component is properly protected from its neighbours the probability of failure clearly drops down .
What does this mean in practice?
If you’re dealing with sockets you’re unaware of peer status, except when you send or receive bytes, then check the connectivity polling with fake sends and using [setSoTimeout(int timeout)](http://java.sun.com/javase/6/docs/api/java/net/Socket.html#setSoTimeout(int)) to prevent blocking reads.
However, I find much more effective isolating the whole business unit in a single timeboxed job, because delays may also come from huge responses as unbounded result set or file fecthing.
If you allow the clients to set timeouts, the request thread quit the operation when the call is not completed in time. Easy?
Concurrent programming is hard and it requires high skills and it is even discoraged unless you don’t want to reinvent the wheel. The [java.util.concurrent](http://java.sun.com/javase/6/docs/api/java/util/concurrent/package-summary.html) package helps to craft your code with timeout controls as in the following example where I’m encapsulating a job unit (a login) into an [ExecutorService](http://java.sun.com/javase/6/docs/api/java/util/concurrent/ExecutorService.html).

{% highlight java %}
public class Login implements Callable {
{% endhighlight %}
The Login action implements the [Callable](http://java.sun.com/javase/6/docs/api/java/util/concurrent/Callable.html) interface; despite Runnable it may throw checked exceptions when executed.
{% highlight java %}
Login login = new Login(user, password);
Future<?> res = exe.submit(login);
try {
  res.get(commandTimeout, TimeUnit.MILLISECONDS);
}
catch (ExecutionException e) {
  log.error("error on login", e);
}
finally {
  res.cancel(true);
}
{% endhighlight %}
The tip shows to launch the callable through [ScheduledThreadPoolExecutor.submit](http://java.sun.com/javase/6/docs/api/java/util/concurrent/ScheduledThreadPoolExecutor.html#submit(java.util.concurrent.Callable)) and waiting the task’s end through [Future.get(long timeout, TimeUnit timeUnit)](http://java.sun.com/javase/6/docs/api/java/util/concurrent/Future.html#get(long,%20java.util.concurrent.TimeUnit)). By specifying the timeout value the operation will be completed in time , otherwise a TimeoutException will be thrown.

*N.B.*: in this last case when timeout occours the ExecutorService doesn’t seem to take care about the still open thread, so don’t forget to execute [Future.cancel(true)](http://java.sun.com/javase/6/docs/api/java/util/concurrent/Future.html#cancel(boolean)) in the final statement.