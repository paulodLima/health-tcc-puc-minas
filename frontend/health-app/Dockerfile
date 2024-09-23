FROM ubuntu:latest
LABEL authors="paulo"
FROM node:18.20.0-alpine as health
WORKDIR /usr/src/app
WORKDIR /app
COPY . .
RUN npm install
RUN  npm install -g @angular/cli@17.3.5;
EXPOSE 4200
CMD ["ng", "serve", "--host", "0.0.0.0","--port", "4200"]
