### About This App
This is a air quility monitoring app for metro cities

## Code architecture
1. MVVM Desing patter
2. Navigation graph
3. DataBinding 

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





