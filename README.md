# Feedback Phoebe
Shake to send feedback for Android



Add Internet permission to your manifest:
```` xml


<uses-permission android:name="android.permission.INTERNET"/>

````


## Dependency Project Level

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:



```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

```

## Dependency App Level

Add dependency in your app module

```gradle
	dependencies {
	        implementation 'com.github.vishalscc:Feedback-Phoebe-Kotlin:1.0.1'
	}

```

## Configuration

You can get token from website after sign up, add token in manifest in meta-data like this:

```xml

	 <meta-data
		    android:name="FEEDBACK_PHOEBE_TOKEN"
		    android:value="set-your-token-here" />

```

## Usage

Option 1:  If you don't have another class extended in your activity then extend `FeedbackPhoebeActivity` in Activity which you want to send feedback of that Activity

``` kotlin


class YourActivity : FeedbackPhoebeActivity() {


      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_main)


      }
}
	
```

Option 2: if you have your own class extend in your activity then you can extend `FeedbackPhoebeActivity` to that class like this:

```kotlin

class YourParentActivity : FeedbackPhoebeActivity() {


      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_main)


      }
}
	

```

Option 3: If you don't want to extend class then you can implement without extending `FeedbackPhoebeActivity` class 

#### Note: FeedbackPhoebe must be register in `onResume()` and must be unregister in `onPause()`


```kotlin


open class YourActivity : AppCompatActivity() {

      var feedbackPhoebe: FeedbackPhoebe? = null

      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          feedbackPhoebe = FeedbackPhoebe()
          feedbackPhoebe!!.launch(this)
      }

      override fun onResume() {
          super.onResume()
          feedbackPhoebe?.register()
      }

      override fun onPause() {
          super.onPause()
          feedbackPhoebe?.unRegister()
      }
}
	

```

## Customization

You can customize color by `FeedbackConfig()` class. Set this class in your `Application` class.

```kotlin 
  
  class MyApp : Application() {

      override fun onCreate() {
          super.onCreate()


          FeedbackConfig(this)
              .setCancelButtonColor(R.color.teal_700)
              .setDialogButtonColor(R.color.blue)
              .setSubmitButtonTextColor(R.color.design_default_color_error)
              .setCancelButtonTextColor(R.color.teal_200)
               .setSubmitButtonColor(R.color.teal_200)
              .setFontFromAssets("mark_pro.ttf")
              //or
              .setFontFromResource(R.font.mark_pro);


      }
}
	
```

Don't forget to add `Application` class into manifest

```xml

	<application
		android:name=".YourAppName"
		android:allowBackup="true"
		...>

```




