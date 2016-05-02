## Override

Gradle plugin to optimize png files in a Android project. Con este plugin puedes optimizar los pngs de tu proyecto para esto el plugin opera sobre la carpeta en donde se ha hecho el merge de recursos, normalmente `app\build\intermediates\res\merged`. Al operar sobre esta carpeta se mantienen los pngs originales en la carpeta `res` y la optimización puede ser reversible en cualquier momento simplemente con un `clean`.

Para realizar la tarea de optimización el plugin hace uso de la librería [Pngtastic] que ofrece su propio compresor y [Zopfli] que ofrece una ridiculously good compression ratios a cambio de compression speed.


## Download & installation

In `build.gradle`
```groovy
buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath 'org.fabiomsr:drawable-optimizer-gradle-plugin:1.0.0'
    }
}
```

And in `app\build.gradle`

```groovy
// make sure this line comes *after* you apply the Android plugin (apply plugin: 'com.android.application')
apply plugin: 'org.fabiomsr.drawableoptimizer'
```

## Configuration

By default you don't need any configuration but if you want you can configure the following parameters. When you change your configuration you'll need to clean your project.

In `app\build.gradle`

```groovy
drawableOptimizer {
    optimizer = 'zopfli'
    compressionLevel = 9
    iterations = 15
    onlyOnRelease = true
    logLevel = 'info'
}
```

|     Parameter    |                       Description                       |            Values            |               Default Value               |
|:----------------:|:-------------------------------------------------------:|:----------------------------:|:-----------------------------------------:|
| optimizer        | Compressor to use                                       |      zopfli or pngtastic      | zopfli                                    |
| compressionLevel | The compression level (useful for pngtastic compressor) |          0-9 allowed         | Default is to try them all by brute force |
| iterations       | Number of compression iterations (useful for zopfli)    |              > 0             | 15                                        |
| onlyOnRelease    | Optimize only on release                                |         true or false         | false                                     |
| logLevel         | The level of logging output                             | none, debug, info or error | info                                      |

License
-------

    Copyright 2016 Fabio Santana (fabiomsr)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[Pngtastic]: https://github.com/depsypher/pngtastic
[Zopfli]: https://github.com/google/zopfli
