## Description

This is a weather app with a very simple, user friendly interface that is designed to be very simple and portable to use. it provides real time information for weather regarding the users current location, 
and contains within the funcitonality to search and view detailed weather informationg for any country, city, or anything in between, including the average weather for a state or a county or a local administrative unit. 
It is designed to incorporate concepts used in android clean architecture to be very easy to maintain, and it contains within it the possibility to further expand its features in the future.

## Contepts used 

The app uses several concepts to incorporate elemnets of clean archhitecture, these are :

- FireBase Authentication for Login and Registration functionality
- Retrofit for interacting with the chosen API
- okHttp logger for debug mode
- AndroidX navigation component for single activity architecture
- Couroutines and Flow for better optimization
- SplashScreen API
- View Binding
- Dependency Injection using dagger Hilt
- FusedLocationProvider for providing accurate weather withing the vicinity of the user
- Geocoder for determining geographical coordinates of a given city
- Data Store Preferences for saving the current session of the user
- RecyclerView
- MVI architecture

## API Used

https://open-meteo.com/en/docs

The Api used is open-meteo, an open end weather information provider which is very simple to use and customizable, and it is relatively accurate and covers most regions in the world, requiring only customizing the endpoint and prooviding latitude and lontitude to work.

## Authentication Feature

The application uses FireBase authentication to verify its users. it incorporates clean architecture to make transitioning to another service easier in the future. It includes a home page, a login page and a registration page, with navigation set up between them to make transitioning easy.

![image](https://github.com/kalkudin/Midterm_Project_Weather_App/assets/117531275/1882ba86-d230-4346-ac0a-206aa138e213)
![image](https://github.com/kalkudin/Midterm_Project_Weather_App/assets/117531275/c9fe1d17-adcd-40c6-ad15-8a0bf1af2ec6)
![image](https://github.com/kalkudin/Midterm_Project_Weather_App/assets/117531275/26d2e0a3-f291-44a5-905b-9ccf24a82fcd)

## Weather Feature

The application starts with displaying the weather for the given user location. if the user has not granted the application permission to use their location, it will be re-asked againa at this point. the information provided includes the current day, temperature for midday,
humidity, windspeed and the general description of the weather. this is done by using a utility class WeatherType which is built based on weather codes provided by the api, which determines the description and the icon to use for a given weather.

Weather for the current day fragment : 

![image](https://github.com/kalkudin/Midterm_Project_Weather_App/assets/117531275/b4240c68-5893-4931-b71d-c3a96023ead7)

Note that if the user has checked the "remember me" option on the previous fragment, they will be navigated to this fragment immedietly after the application starts. Pressing "Log Out" will clear their session and take them back to sign up fragment on the next app start

Pressing on "Weekly Weather will navigate the user to a page containing information regarding every single day for the following 7 days, the app uses a recyclerview to display the data, and each of the items is clickable, with the user being navigated to the daily page
which displays more detailed information regarding the weather for that current day. 

![image](https://github.com/kalkudin/Midterm_Project_Weather_App/assets/117531275/31a34dd5-70ae-4567-8c11-cc71bd3b84b7)
![image](https://github.com/kalkudin/Midterm_Project_Weather_App/assets/117531275/4c081cd6-aa58-4ae1-b423-035734eef4d5)

on the deaily weather page, there is also the option to press "Search For A City" which will navigate the user to a city search fragment 

![image](https://github.com/kalkudin/Midterm_Project_Weather_App/assets/117531275/94e1c75b-50ce-49cf-b96a-d268e1034552)

on this fragment, the user has the ability to search the weather for a particular city, town, district or any other valid entity. The information is likewise displayed for the following 7 days 

![image](https://github.com/kalkudin/Midterm_Project_Weather_App/assets/117531275/41126a8a-9d21-4fec-98da-9f9d6fa465f2)

![image](https://github.com/kalkudin/Midterm_Project_Weather_App/assets/117531275/200c1f68-6e90-44c3-8570-13fba1cef25e)

For now that is all the functionality the application current supports. 











