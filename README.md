# Ourivesaria-Rilho-webapp


In order to gain more experience, I decided to make a web application for a family store. This website is a prototype of the original version, so all the data and information are not real.

This project started out as an informative website, with just some essential information about the store, like a static website. However, in order to develop my knowledge further, I created a more complete version. It has a page dedicated to the products in the store, where they can be filtered by category and material. I also created a product management section, which is available to the admin after being authenticated and authorized by the server, and which allows him to create, update, edit and delete products.

In general, the most challenging part to implement was the server security part with the Spring Security framework, together with the Oauth2.0 protocol with JWT tokens (access token and refresh token), as it was my first experience with this technology.


The technologies I used:

Frontend
-HTML
-TailwindCss
-React.js
-Axios
-Figma

Backend
-Java
-Spring boot
-Spring data jpa
-Spring security (with OAuth)
-PostgreSQL
-Hibernate
-Mockito
-Junit

Version Control System
-GIT

Hosting
-Render (with docker)


The functionalities in the web application, which depend on each user's permissions:
 
Admin
-View, create, update and delete products
-View categories
-View materials
-Login in the Dashboard to manage the products (Authentication and Authorization)

User
-View products
-View categories
-View materials
-Filter products by category and material


Features under development:

-Create and update new categories and materials (Admin Dashboard)
-Implement a feature so that the Admin can upload photos from their mobile phone/computer when creating new products (probably with amazon service, like S3)
-Two-factor authentication (probably with email)
-Sign-up/Login/Logout for users (already implemented in the backend but not in the frontend)
-Implement a feature where users can select and add products to a favourites list
-Implement refresh tokens with http only cookie (at the moment they are sent by http inside the auth header as a bearer type)
-More API protections, such as rate limiting
