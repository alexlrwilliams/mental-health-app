FROM node:latest as build-stage
WORKDIR /app
ARG VUE_APP_API_URL
ENV VUE_APP_API_URL="http://localhost:8081/api"
COPY package*.json ./
RUN npm install
COPY ./ .
RUN npm run build

FROM nginx as production-stage
RUN mkdir /app
COPY --from=build-stage /app/dist /app
COPY nginx.conf /etc/nginx/nginx.conf