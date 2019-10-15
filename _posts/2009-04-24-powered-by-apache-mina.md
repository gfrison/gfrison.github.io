---
layout: posts
title: Powered by Apache Mina
published: true
permlink: /2009/04/24/powered-by-apache-mina
comments: true
image: /assets/mam.jpg
tags: apache, apache mina, decoder, demux, encoder, filters, handler, mina, server, tcp
---
The application being discussed has to behave as follows: performs the client authentication, accomplishes request and response operations and forwards notifications asynchronously to the client. The Mina framework fulfills these needs because it was created to be as flexible and easy-fitting as possible in an array of case scenarios. Mina is structured in several layers that briefly can be broken down into: input parsing, execution of concatenated processes, and serialisation of  related responses, if needed. The infrastructure takes care of I/O  quirks, while it lets you write within it your business processes and handles session lifecycles through simple callbacks that you can manage within a  few codes. It is Majestic! You will love it.

In addition, there is a need to look for something that could help me, the author, to write down an application which implements many commands, preferably in a appealing way, where each piece of service could be isolated from the rest of the application.

The demultiplexer is a device with a single input and many outputs. Its role is to select the output line according to context rules. This approach is also implemented within Apache Mina for writing decoders, handlers and encoders. Apache Mina’s demux package includes: [DemuxingIoHandler](http://mina.apache.org/report/trunk/apidocs/org/apache/mina/handler/demux/DemuxingIoHandler.html), [DemuxingProtocolDecoder](http://mina.apache.org/report/trunk/apidocs/org/apache/mina/filter/codec/demux/DemuxingProtocolDecoder.html) and the [DemuxingProtocolEncoder](http://mina.apache.org/report/trunk/apidocs/org/apache/mina/filter/codec/demux/DemuxingProtocolEncoder.html).

<center><img  src="{{ site.url }}/assets/mam.jpg"/></center>

## Filters
Filters are used for several purposes: I/O logging, performance tracking, thread pooling, overload control, blacklists, and so on. In a specific case I once had to configure two filters. One for the user authentication, and the other for thread pooling. Once a user is logged in, the authentication filter removes itself from the client session filter list, while substituting one transforming the raw input into POJOs.

## Decoder
The TCP server must implements a proprietary protocol. It is a simple ASCII protocol for exchanging commands. Such command lengths are undefinable. They, however, are a sequence of characters much like SMTP. Therefore, the [CumulativeProtocolDecoder](http://mina.apache.org/report/trunk/apidocs/org/apache/mina/filter/codec/CumulativeProtocolDecoder.html) class is extended enabling it to gather input through the end of command. It is then left to us to parse of the bytes and create a simple Java Bean. Post the operation, the bean is transferred through the filter chain to be executed.

## Handler
One of the [IoHandler](http://mina.apache.org/report/trunk/apidocs/org/apache/mina/core/service/IoHandler.html) implementations drew my attention while I was looking for something resembling the Command Pattern. Each message coming from clients means a specific action, and I find it so tedious writing a single Handler that switches operations by the type of the incoming request. An elegant solution is provided by DemuxingIoHandler that posts the requests toward the corresponding handler. The handlers have to implement the [MessageHandler](http://mina.apache.org/report/trunk/apidocs/org/apache/mina/handler/demux/MessageHandler.html), the generic type defined will be the object’s class that the DemuxingIoHandler will submit to the handler, and register themselves invoking {% highlight groovy %}addReceivedMessageHandler(Class<E> type, MessageHandler<? super E> handler){% endhighlight%}.

The asynchronous nature of Mina allows to handle a huge number of clients by an handful set of threads. A further decoupling between I/O and business logic may be done by the ExecutorFilter, which is in charge of the messages after the NioProcessor.

## Encoder
The transformation component works in a reverse way compared to the decoder: it serialises the POJO response coming from the handler process, to the output stream, toward the client. Likewise the handlers, it is possible to delegate its own encoder for each response object, but why not to send the IoBuffer straightly from the handler that elaborates the request? Separation of concerns might be the answer. The handler receives a command, a java bean, then it is processed and the handler returns an object for response, a POJO. It’s up to the encoder to transform the abstract response to a concrete message through the agreed TCP protocol.
