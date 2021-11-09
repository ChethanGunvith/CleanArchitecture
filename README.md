- Building You can open the project in Android studio and press run.

- Testing The project uses both instrumentation tests that run on the device and local unit tests
  that run on the computer.

- Database Tests The project creates an in memory database for each database test but still runs
  them on the device.

- Local Unit Tests ViewModel Tests Each ViewModel is tested using local unit tests with mock
  Repository implementations.

- Repository Tests Each Repository is tested using local unit tests with mock web service and mock
  database.

- Webservice Tests The project uses [MockWebServer][mockwebserver] project to test REST api
  interactions.

Libraries
- Android Architecture Components, Jet pack components 
- Android Data Binding data-binding
- Hilt for dependency injection
- Retrofit for REST api communication
- Glide for image loading
- Espresso for UI tests
- mockito for mocking in tests


- Architecture pattern used for this Project is MVVM