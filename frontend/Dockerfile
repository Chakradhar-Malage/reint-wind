# Stage 1: build
FROM node:20-alpine AS builder
WORKDIR /app
COPY wind-forecast-ui/package*.json ./
RUN npm install
COPY wind-forecast-ui/ .
RUN npm run build

# Stage 2: production
FROM node:20-alpine
WORKDIR /app
COPY --from=builder /app/.next ./.next
COPY --from=builder /app/public ./public
COPY --from=builder /app/package*.json ./
RUN npm install --production
EXPOSE 3000
CMD ["npm", "start"]