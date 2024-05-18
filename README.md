# Scala.js application

This is a simple Scala.js application that I created as supporting material 
for a presentation at [Imperial College London](https://www.imperial.ac.uk/). 

Main purpose of presentation was introducing Scala.js and Laminar (see below), showcasing interop features with JavaScript libraries and briefly touching upon packaging and deployment. Final version of an application uses browser storage for data, but has a JVM backend that invokes OpenAI API to generate titles from descriptions.

The final app is built on these languages, technologies, and frameworks:

- Scala parts:
  - [Scala 3](https://scala-lang.org), specifically Scala 3.4.2
  - [Scala.js](https://www.scala-js.org)
  - [Scala CLI](https://scala-cli.virtuslab.org/)
  - Libraries: [Laminar](https://laminar.dev/) (main UI library), [Upickle](https://com-lihaoyi.github.io/upickle/) for JSON, [Cask](https://com-lihaoyi.github.io/cask/) for HTTP server (**v3** only), [STTP OpenAI](https://github.com/softwaremill/sttp-openai)  for OpenAI API access (**v3** only)
- Frontend tooling
  - [Vite](https://vitejs.dev/) for bundling and live reload
  - [TailwindCSS](https://tailwindcss.com/) for styling
  - [ECharts](https://echarts.apache.org/en/index.html) (from **v2**) for chart visualisation
- Deployment
  - [Fly.io](https://fly.io/) hosting provider
  - [Docker](https://docs.docker.com/build/building/multi-stage/) for packaging

The authors behind those tools and libraries walked so I could at least crawl, I'm deeply grateful.

## V1

This version only has the basic frontend functionality, and is a pure Scala.js frontend. The Dockerfile that deploys it just creates a container that serves static content build by Vite.


![CleanShot 2024-05-18 at 12 56 27](https://github.com/keynmol/scalajs-talk-at-imperial/assets/1052965/7f623a41-9d89-4133-9adb-ef0ee3bae8c2)

## V2

This version is same as V1, but adds an integration with Apache ECharts – a library for charts and plotting. It's a good example to showcase very basic interop with JS libraries. Deployment is the same as V1 – it's still a frontend-only application.

![CleanShot 2024-05-18 at 12 57 54](https://github.com/keynmol/scalajs-talk-at-imperial/assets/1052965/2b2dbdc3-dc7d-43e9-9b1a-90371c6afa24)

## V3

The most involved version, builds upon V2 and adds a JVM backend (using Cask) which exposes an endpoint that generates a title for a description using OpenAI API. The app is packaged as a self-contained JAR file, which serves Vite's bundle and exposes the API at the same time.

![CleanShot 2024-05-18 at 13 00 02](https://github.com/keynmol/scalajs-talk-at-imperial/assets/1052965/cfd0d8bf-3392-4663-8608-44ad73a6b9c2)
