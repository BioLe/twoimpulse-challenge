FROM node:current-alpine as build

WORKDIR /app

COPY package*.json /app/
RUN npm install 
RUN npm install react-scripts -g --silent

COPY ./ /app/
# Evitar erro do webpack (https://github.com/webpack/webpack/issues/14532)
ENV NODE_OPTIONS=--openssl-legacy-provider

RUN npm run build

#Nginx para servir o conteudo

# nginx state for serving content
FROM nginx:alpine

# Copy static assets from builder stage
COPY --from=build /app/build /usr/share/nginx/html

#Remover configs antigas
RUN rm /etc/nginx/conf.d/default.conf
#Usar a minha config
COPY nginx/nginx.conf /etc/nginx/conf.d

# Containers run nginx with global directives and daemon off
ENTRYPOINT ["nginx", "-g", "daemon off;"]