FROM node:22 as build

WORKDIR /usr/local/bin

RUN wget https://raw.githubusercontent.com/VirtusLab/scala-cli/main/scala-cli.sh && \
    mv scala-cli.sh scala-cli && \
    chmod +x scala-cli && \
    scala-cli config power true && \
    scala-cli version

WORKDIR /source

COPY . .

RUN rm -rf src/.scala-build src/.bsp src/.bloop

RUN npm install 
RUN npm run buildScalajsRelease
RUN npm run build

FROM nginx

COPY --from=build /source/dist /usr/share/nginx/html
