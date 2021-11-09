
This sample respresents Clear Architecture(Three layers - Domain, Data, App layers with MVVM)

its main goal is the separation concerns by dividing software into layers , main idea is to separate code into different layers so that each layers will
mind its own business. 

Clean Architecture maximizes the use of SOLID  principles.
	Separation of Concerns 
	Loose coupling
	Easily Testable


Three layers 
Domain
Data
App(Presentation), 



### Presentation layer : 
It is mainly concerns with what user sees , drawing things, animations , in the most part its not going to handle a lot of thinking and its actually just going to pass that job on to the logic layer 
 
 This layer is consisting of
 - View , View model 
 
### Domain (or Logic) layer : 
logic layer is the first layer that the presentation layer communicate ,Here we put the logic of business: convert, filter, mix and sort raw data that comes from Data layer to be ready and easy to handle in Presentation layer.

 This layer is consisting of
 - Repository class - Single source of truth, most of logic will be hold here
 
### Data Layer
Here we put the logic of bringing data either from local source or server.
persistence layer 
- Entity, data base, retrofit interfaces



# App Demo
https://user-images.githubusercontent.com/3158100/140880195-d9fb7f17-151e-4eba-8c52-9d4f6756d148.mp4


 
###  MVVM architectural design?

  Below are my links for the explainations on MVVM architecturem Jitpack components 

- [Architectural Component](https://github.com/chethu/Android-Architecture-Component)

- [Github Sample to demonstrate online and offline data in MVVM ](https://github.com/chethu/Near-by-venus-browsing-sample-with-Android-Architecture-Components)

- [Sample project on MVVM ¥, Live Data , Retrofit](https://github.com/chethu/Kotlin-MVVM-LiveData-Retrofit)

- [Different form of live Data](http://chethanmandya.com/2019/08/11/Live-Data.html)

- [Overview of data binding](http://chethanmandya.com/2019/04/28/Android-Data-Binding.html)

- [Coroutine](http://chethanmandya.com/2019/09/01/Android-Coroutine-Example.html)




### Hilt Dependency Injection (Dagger2 or Koin is acceptable as well)

[My explaination on Hilt Dependency Inject](https://github.com/chethu/Hilt-Dependency-Injection)

