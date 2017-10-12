[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Material%20Search%20View-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/4765)

# Material Search View
Cute library to implement SearchView in a Material Design Approach. *Works from Android API 14 (ICS) and above*.

<a href="https://play.google.com/store/apps/details?id=com.claudiodegio.sample.msv">
  <img alt="Get it on Google Play"
       src="https://developer.android.com/images/brand/en_generic_rgb_wo_60.png" />
</a>

![sample](https://raw.githubusercontent.com/claudiodegio/MsvSearch/master/screen/Resized-1.png)
![sample](https://raw.githubusercontent.com/claudiodegio/MsvSearch/master/screen/Resized-2.png)
![sample](https://raw.githubusercontent.com/claudiodegio/MsvSearch/master/screen/Resized-3.png)
![sample](https://raw.githubusercontent.com/claudiodegio/MsvSearch/master/screen/Resized-4.png)


# Usage
**Add the dependencies to your gradle file:**
```javascript
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
        compile 'com.github.claudiodegio:MsvSearch:1.0.0'
}
```
**Add MaterialSearchView to your layout file along with the Toolbar** *(Add this block at the bottom of your layout, in order to display it over the rest of the view)*:

```xml
<!— Must be last for right layering display —>
<FrameLayout
    android:id="@+id/toolbar_container"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <android.support.v7.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:background="@color/theme_primary" />

   <com.claudiodegio.msv.MaterialSearchView
        android:id="@+id/sv"
        android:elevation="5dip"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    </com.claudiodegio.msv.MaterialSearchView>
</FrameLayout>
```

**Create a menu resource file and add the search item into the menu file:**
```xml
	<menu xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto">

        <item
            android:id="@+id/action_search"
            android:title="Search"
            android:icon="@drawable/ic_magnify_white_24dp"
            app:showAsAction="always"
            />
    </menu>
```
**Add define it in the *onCreateOptionsMenu*:**
```java
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }
```

**Hook up your OnSearchViewListener and implement following methods**
```java
    @Override
    public void onSearchViewShown() {

    }

    @Override
    public void onSearchViewClosed() {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // handle text submit and then return true
        return false;
    }

    @Override
    public void onQueryTextChange(String newText) {

    }
```

# Help me
Pull requests are more than welcome, help me and others improve this awesome library.

# License
	Copyright 2016 Claudio Degioanni

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.