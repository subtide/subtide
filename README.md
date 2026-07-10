# Subtide

A fork of the [Overtone](https://github.com/overtone) ecosystem.

## Usage

### Debian 13

Install [Homebrew](https://brew.sh).

```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

Install [Supercollider](https://supercollider.github.io/).

```bash
sudo apt-get update
sudo apt-get install supercollider sc3-plugins pipewire-jack qpwgraph
```

Install Pipewire/Jack tools:

```bash
sudo apt-get update
sudo apt-get install pipewire-jack qpwgraph
```

Install [Clojure](https://supercollider.github.io/).

```bash
brew install clojure/tools/clojure
```

```clojure
$ pw-jack clj
Clojure 1.12.5
user=> (use 'subtide.live)
--> Loading Subtide...
[subtide.live] [INFO] Found SuperCollider server: /usr/bin/scsynth (PATH)
--> Booting external SuperCollider server...
[subtide.live] [INFO] Booting SuperCollider server (scsynth) with cmd: pw-jack /usr/bin/scsynth -u 36517 -b 1024 -z 64 -m 262144 -d 1024 -V 0 -n 1024 -r 64 -l 64 -D 0 -o 8 -a 512 -R 0 -c 4096 -H Subtide -i 8 -w 64
[subtide.live] [INFO] Found Jack-compatible server process:
[subtide.live] [INFO]    2151     user /usr/bin/pipewire
[subtide.live] [INFO]    2164     user /usr/bin/pipewire -c filter-chain.conf
[subtide.live] [INFO]    2166     user /usr/bin/pipewire
--> Connecting to external SuperCollider server: 127.0.0.1:36517
[scynth] Found 0 LADSPA plugins
[scynth] JackDriver: client name is 'Subtide-97'
[scynth] SC_AudioDriver: sample rate = 48000.000000, driver's block size = 1024
[scynth] SuperCollider 3 server ready.
--> Connection established


            __   __  _    __
  ___ __ __/ /  / /_(_)__/ /
 (_-</ // / _ \/ __/ / _  / -_)
/___/\_,_/_.__/\__/_/\_,_/\__/


   Collaborative Programmable Music. v0.16.3331


Hello User, just take a moment to pause and focus your creative powers...

nil
user=> (demo (sin-osc))
#<synth-node[loading]: user/audition-synth 32>
```

## Tutorial

Subtide
