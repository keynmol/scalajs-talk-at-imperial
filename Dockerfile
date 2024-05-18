FROM node:22 as build

WORKDIR /usr/local/bin

RUN wget https://raw.githubusercontent.com/VirtusLab/scala-cli/main/scala-cli.sh && \
    mv scala-cli.sh scala-cli && \
    chmod +x scala-cli && \
    scala-cli config power true && \
    scala-cli version && \
    echo '@main def hello = println(42)' | scala-cli run _ --js -S 3.4.2

WORKDIR /source

COPY . .

RUN rm -rf frontend/.scala-build frontend/.bsp frontend/.bloop
RUN rm -rf backend/.scala-build backend/.bsp backend/.bloop

WORKDIR /source/frontend
RUN npm install 
RUN npm run buildScalajs
RUN npm run build

WORKDIR /source/backend
RUN scala-cli package . --assembly -f -o ./imperial-backend

FROM eclipse-temurin:22

COPY --from=build /source/backend/imperial-backend /app/imperial-backend

CMD ["/app/imperial-backend"]
