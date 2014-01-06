Copy all the needed Processing library here (and their native files).

You may copy them directly from the application folder (or inside .app on OSX), or build them from Github using `ant build`.

You should use a structure like this one :

```
├── .jar files (core.jar, jna.jar, jogl-all-natives-xxx-xxx.jar, jsminim.jar, etc.)
├── macosx
│   └── .jnilib files
├── macosx32
│   ├── .dylib files
│   └── plugins
│       ├── .so files
├── windows32
│   ├── .dll files
│   └── plugins
│       ├── .dll files
└── windows64
    ├── .dll files
    └── plugins
        ├── .dll files

```