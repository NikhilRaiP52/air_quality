# About This App
This is a air quility monitoring app for metro cities

## Code architecture
1. MVVM Desing patter
2. Navigation graph
3. DataBinding 

## Behaviour
There is two Screen in the App.
#### Air Quality List Monitoring  :
``` 
This screen is response to show the list of cities and air quality respectivly
List of the City will be update in each 10 sec
```
#### Air Quality City Chart monitoring  :
``` 
This screen is response to show the chart of air quility for particular selected city
Chart will be update in each 30 sec
```



## Other Dependencies
1. Navigation (communicate with Activity and fragment)
```Kotlin
//nav-graph
    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"
    implementation "androidx.navigation:navigation-runtime-ktx:$nav_version"

```

2. Websocket and Gson 
```Kotlin
//WebSocket
    implementation "org.java-websocket:Java-WebSocket:1.5.1"
    implementation 'com.google.code.gson:gson:2.8.6'
    
```
    
3. Chart 
```Kotlin
// Chart
    implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'
```    





