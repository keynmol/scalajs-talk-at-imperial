# Scala.js application

This is a simple Scala.js application that I created as supporting material 
for a presentation at [Imperial College London](https://www.imperial.ac.uk/).

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

The authors behind those tools and libraries walked so I could at least crawl.

