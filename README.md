# Spring Boot Carbon-Aware Metrics

> **_Note:  This [project](https://taikai.network/gsf/hackathons/carbonhack22/projects/cl8tglrxx28248701xckvhba0vg/idea) is a part of the [CarbonHack 2022 Hackathon](https://taikai.network/gsf/hackathons/carbonhack22) (10/13/2022 - 11/05/2022).  Check that out for more context and details on how to get involved._**

## The Problem

One of the hardest parts about making carbon-aware computing decisions is knowing how to start seeing real data and measuring the impact of your everyday decisions.  I've always found it hard to know what to measure and how to do it, and sustainability concepts can feel like a rabbit-hole of "if I count this, do I need to figure out how to count that?  Where do I stop?"

This project has three goals:

1. Make it easy to measure and see the carbon impact of my applications, while they're running and while they're doing specific work
2. Make it easy to measure and see that impact across multiple services interacting with each other
3. Make it easy for lots of developers to get that same data and measure improvements towards their sustainability goals

## How This Project Helps

This project aims to leverage industry-standard metrics and tracing implementations like OpenTelemetry to make it easy to understand this data in the same ways we use other business or technical measurements.

It focused on the Java tech stack and specifically the Spring Boot development ecosystem because many enterprises use these today, and if we can make it easier to move the needle just a little for those large software estates we can make a big impact.

JRebel's [Java Technology Report](https://www.jrebel.com/blog/2021-java-technology-report) shows that Spring Boot is currently used on over two thirds of Java projects! [Research on GitHub's public repositories](https://brainhub.eu/library/most-popular-languages-on-github) shows that Java is a consistent top language for all kinds of projects, big or small. And there are lots of java projects tucked away behind enterprises all around the world. If we can make it easy for this community to get carbon awareness for little or no work, that's a lot of potential first steps toward making today's software more sustainable.
