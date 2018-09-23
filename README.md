# Git-User-Retriever

An android app written in Kotlin that retrieves and displays Github user information based on the username provided.

## Design

The android app follows utilises MVP (model-view-presenter) pattern. For every UI component, there is a *View* component (activity or fragment), a *Presenter* that handles the actions upon user interaction and a *Contract* file that manifests the methods that the View and Presenter are supposed to implement. The asynchronous HTTP API calls are based on observer pattern through RXJava and handle receiving responses from the git server. Moreover, dependency injection takes care of providing the objects the objects it is dependant to.

## Libraries Used

* **Retrofit** -> used for making api calls
* **RxJava2** -> used for making async calls
* **Dagger2** -> used for dependency injection
* **Butter Knife** -> used for view binding
* **Picasso** -> used for image downloading and caching

## How to Run the Code

1. Download the source code 
2. Import the code in Android Studio/IntelliJ
3. Run the code on a device or on an emulator

Or see it in action in this video

<a href="http://www.youtube.com/watch?feature=player_embedded&v=CQO_XLwwr0o
" target="_blank"><img src="http://img.youtube.com/vi/CQO_XLwwr0o/0.jpg" 
alt="Git User Retriever in Action" width="240" height="180" border="10" /></a>
