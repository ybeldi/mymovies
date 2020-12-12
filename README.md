A sample app for displaying a list of movies from TMDB API.

## Architecture

The app's structure is based on The Clean architecture + Architecture component + MVVM. By using that, we have a clear separation of concerns and each layer become independant and easy to test.

## Structure

#### `Data`
The data folder is the core module from where all the source of data is comming. It contains a repository that decides which strategy to use (Remotely or locally) when fetching the data.
The data will be emitted using `Flow` from coroutines, this will help us notifying all other layers about the status of the request ( `Loading`,`Success`,`Failure` ) asynchronously.

#### `Domain`
The Use case handles the business logic of the app, and helps coders easily understand what the business cases of the app are.


#### `UI`
Contains all UI components including the viewmodels. The ViewModel will consume the needed usecase then notify the UI.



## Used technologies
* `Hilt` : For the dependency injection
* `Retrofit` : For network calls
* `Flow` : For handling the stream of data asynchronously.
* `Coroutines` : The little beast of asynchronous programming.
* `Navigation component` : For managing the navigation between the views.
* `Room` : For the local data store.
