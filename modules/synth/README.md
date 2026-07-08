# Subtide

Subtide is based on [Overtone](https://github.com/overtone/overtone).

Subtide is an Open Source toolkit for designing synthesizers and
collaborating with music.  It provides:

* A Clojure API to the SuperCollider synthesis engine
* A growing library of musical functions (scales, chords, rhythms,
  arpeggiators, etc.)
* Metronome and timing system to support live-programming and sequencing
* Plug and play MIDI device I/O
* A full Open Sound Control (OSC) client and server implementation.
* Pre-cache - a system for locally caching external assets such as .wav
  files
* An API for querying and fetching sounds from http://freesound.org
* A global concurrent event stream

## Quick Start

### Installation

- Install Java, since this is a prerequisite for Clojure
  - Quite often Java will already be installed, if `java -version` works and
    shows you a version of 11 or higher you should be good
  - On Linux, you should be able to use your operating system package manager,
    for instance on Ubuntu the package will be called something like
    `openjdk-17-jdk`
  - On MacOS you can use Homebrew if you have it
  - [https://adoptium.net/](Adoptium.net) has installers for most operating systems
  
- Install the Clojure CLI tools, see [Install Clojure](https://clojure.org/guides/install_clojure)
  - Also install `rlwrap` if you can, without it `clojure` will work, but `clj`
    will not, and you won't have history and line editing in your Clojure REPL
  
- Install [SuperCollider](https://supercollider.github.io/), preferrably through your operating system's package manager (apt, yum, pacman, homebrew, chocolatey, etc.)
  - The main package is called `supercollider` everywhere
  - If there's a package names `sc3-plugins`, then install that as well

At this point you should have `clojure` and `scsynth` available.

```shell
$ clojure --version
Clojure CLI version 1.11.1.1413

$ scsynth -v
scsynth 3.13.0 (Built from  '' [na])
```

On macOS, `scsynth` might not be available but it's sufficient to have SuperCollider
installed at `/Applications/SuperCollider.app`.
This is homebrew's behavior.

Now you can add `org.subtide/subtide` as a dependency, and start a Clojure REPL.

```sh
mkdir happy-vibes && cd happy-vibes
echo '{:deps {org.subtide/subtide {:mvn/version "TODO"}}}' > deps.edn
clj

Clojure 1.11.1
user=>
```

### Making sounds


```clj
;; boot the server
user=> (use 'subtide.live)
--> Loading Subtide...
[subtide.live] [INFO] Found SuperCollider server: /usr/bin/scsynth (PATH)
--> Booting external SuperCollider server...
--> Connecting to external SuperCollider server: 127.0.0.1:26325
[scynth] SuperCollider 3 server ready.
--> Connection established

;; listen to the joys of a simple sine wave
user=> (demo (sin-osc))

;; or something more interesting...
user=> (demo 7 (lpf (mix (saw [50 (line 100 1600 5) 101 100.5]))
                    (lin-lin (lf-tri (line 2 20 5)) -1 1 400 4000)))
```

## Contributors

See: https://github.com/overtone/overtone/graphs/contributors

## License

The MIT License, see [[LICENSE]].

Copyright &copy; 2009-2024 Jeff Ross, Sam Aaron, and contributors.
