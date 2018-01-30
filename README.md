# SocialMeal

Open source social networking site for people who want to eat together
It is only Android application, API for application in ASP.NET Core is here: https://github.com/Arkaady/SocialMeal

## Continuous integration

Master status:  [![Build Status](https://travis-ci.org/stramek/SocialMeal.svg?branch=master)](https://travis-ci.org/stramek/SocialMeal)

## Introduction

Social meal is an application that allows people to meet at a shared meal. 
The main purpose of the application is to improve interpersonal relationships, make new friends and a way to spend free time. The application is dedicated to people who like to cook and willingly share a meal and also for people who like to eat well.

## Working features
- basic Dagger2 modules used for dependency injection,
- continous integration (travis),
- configuration of Retrofit2 to automatically refresh token,
- login screen with presenter unit tests,
- registration screen with presenter unit tests,
- forgot password screen with presenter unit tests,
- basic models to handle API requests on screens mentioned above

## Upcoming features
- authorization server units tests,
- profile screen layout,
- profile screen logic,
- profile screen upload avatar,
- profile screen upload background photo,
- profile screen unit tests

## In the more distant future
- ability to create meal share events,
- ability to search and filter other people events,
- ability to willingness to participate in other people events,
- ability of event creator to accept or decline people,
- push notifications
- ability to login with facebook, twitter or google account

## Used popular libraries and technologies
- Kotlin language
- MVP architecture
- Dagger2
- Retrofit2
- RxJava2
- JUnit4

## Application screenshots
![Sign in screen 1](https://github.com/stramek/SocialMeal/blob/master/app_images/1.png?raw=true)
![Sign in screen 2](https://github.com/stramek/SocialMeal/blob/master/app_images/2.png?raw=true)
![Reset password screen](https://github.com/stramek/SocialMeal/blob/master/app_images/3.png?raw=true)
![Sign up screen](https://github.com/stramek/SocialMeal/blob/master/app_images/4.png?raw=true)

## License
- MIT
