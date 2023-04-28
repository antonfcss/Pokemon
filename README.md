# Pokemon

#### Hi, this is my internship test for [Innowise Group](https://innowise-group.com).  

I have created an application to view pokemons that meets the given requirements. The application allows users to view a list of pokemons with their names and detailed information about each pokemon, including image, type, weight, and height.All information is accepted from the [API](https://pokeapi.co). The list supports pagination, and data is cached in the database to support offline mode.  I used Clean Architecture with MVVM and my own design for the application created with XML Layouts.

During the development of the project, I used the following tools:
  - Hilt was used for dependency injection
  - Navigation component for navigating between screens
  - Retrofit for executing API requests
  - Room for caching data in the database
  - Glide for loading and displaying images
  - Paging Library for implementing pagination in the list
  - MVVM architectural template for implementing viewing logic and data model
  - Clean Architecture to separate the application into layers and reduce coupling between them
