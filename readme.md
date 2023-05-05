# Order Planner

The Order Planner is a POC about microservices with springboot 3.

## Table of contents

- Requirements
- Modules Description
- Installation

## Requirements

This poc requires Docker and compose.
You also need [Switch Host](https://github.com/oldj/SwitchHosts/releases) or similar tools to add the following entries in the host file:
```
127.0.0.1 order-planner-fe
127.0.0.1 order-planner
127.0.0.1 order-planner-mysql
```

## Modules Description

- Percona mysql database
- Spring Boot back end
- Angular front end

## Installation

```
docker-compose -f docker-compose-env.yml up -d
```
