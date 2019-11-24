# Bottom Bar Pager
[![](https://jitpack.io/v/OmarRaed/bottomnavigation.svg)](https://jitpack.io/#OmarRaed/bottomnavigation)

An android Library used to easily implement a bottom navigation bar with title and animated vector drawable and set it up with a view pager with fragments.

<img src="/images/screenshoot.gif" width="250" />

---

### Download using Gradle

Add this in your root `build.gradle` at the end of `repositories` in `allprojects` section:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
        ...
    }
}
```

Then add this dependency to your **module-level** `build.gradle` in `dependencies` section:
```groovy
implementation 'com.github.OmarRaed:bottomnavigation:1.0.0'
```

---

## How To Use

Add view to your layout:
```xml
    <com.omaar.bottomnavigation.BottomNavigation
        android:id="@+id/tabs"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_alignParentBottom="true"
        app:selected_tab_background="@color/darker_gray"
        app:selected_text_color="@color/colorPrimary"
        app:bar_background="@color/light_gray"
        app:text_color="@color/blackColor"/>
```

Then add it to java code :


```java
//find the view
BottomNavigation bottomNavigation = findViewById(R.id.tabs);

//Adding titles is optional for each tab
bottomNavigation.add(new FirstFragment(), R.drawable.places_animated_icon, "First Title");
bottomNavigation.add(new SecondFragment(), R.drawable.metro_animated_icon, "Second Title);
bottomNavigation.add(new ThirdFragment(), R.drawable.metro_animated_icon);
```

---
### The Colors :

<img src="/images/colors.png" width="500" />

Default colors :
- app:selected_tab_background -> #CCCCCC
- app:selected_text_color -> #FFFFFF
- app:bar_background -> #EEEEEE
- app:text_color -> #000000
---

### Limitations

- This Library requires api level 21 or more (Android Lollipop) .
- This Library is only limited to five tabs maximum.

---


### License

```
 Copyright 2019 Omar Raed

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
